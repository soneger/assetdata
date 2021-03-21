package cn.soneer.assetdata.aop;

import cn.soneer.assetdata.annotation.RequestAstrict;
import cn.soneer.assetdata.commons.dto.RespData;
import cn.soneer.assetdata.entity.ApiRecord;
import cn.soneer.assetdata.redis.utils.RedisUtils;
import cn.soneer.assetdata.service.ApiTaskService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author Soneer
 * @Date 2021/2/5 16:24
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class RequestAstrictAop {
    @Resource
    private ApiTaskService apiTaskService;
    @Resource
    private RedisUtils redisUtils;
    private static ThreadLocal<Long> startTime = new ThreadLocal<>();
    private static ThreadLocal<ApiRecord> apiRecordThreadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(cn.soneer.assetdata.annotation.RequestAstrict)")
    public void req(){}

    @Before("req()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info("进入切面前");
        startTime.set(System.currentTimeMillis());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 接收到请求，记录请求内容
        String appId = request.getHeader("appId");
        String secretKey = request.getHeader("secretKey");
        boolean b = apiTaskService.userApiVerify(appId, secretKey, 1);
        if (b){
            ApiRecord apiRecord = new ApiRecord();
            apiRecord.setUserAppid(appId);
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            RequestAstrict annotation = signature.getMethod().getAnnotation(RequestAstrict.class);
//        RequestAstrict annotation = joinPoint.getTarget().getClass().getAnnotation(RequestAstrict.class);
            apiRecord.setApiName(annotation.name());
            apiRecord.setApiMethod(joinPoint.getSignature().getName());
            apiRecord.setReqIp(request.getRemoteAddr());
            apiRecord.setCreateTime(new Date());
            apiRecordThreadLocal.set(apiRecord);
            // 记录下请求内容
            log.info("URL : {}" , request.getRequestURL());
            log.info("HTTP_METHOD : {}" , request.getMethod());
            log.info("IP : {}" , request.getRemoteAddr());
            log.info("CLASS_METHOD : {},{}" , joinPoint.getSignature().getDeclaringTypeName() , joinPoint.getSignature().getName());
            log.info("ARGS : {}" , Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterReturning(returning = "ret", pointcut = "req()")
    public void doAfterReturning(Object ret) throws Throwable {
        log.info("进入切面后");
        // 处理完请求，返回内容
        log.info("RESPONSE : {}" , ret);
        RespData resp = (RespData) ret;
        apiRecordThreadLocal.get().setReqStatus(resp.getCode());
        apiRecordThreadLocal.get().setRunTime(System.currentTimeMillis() - startTime.get());
        String userAppid = apiRecordThreadLocal.get().getUserAppid();
        Object value = redisUtils.get(userAppid+apiRecordThreadLocal.get().getApiMethod());
        if (null == value ) {
            apiRecordThreadLocal.get().setReqCount(1);
            redisUtils.mSet(userAppid+apiRecordThreadLocal.get().getApiMethod(), apiRecordThreadLocal.get(),30);
            log.info("用户初次请求接口：{}",apiRecordThreadLocal.get());
        }else {
            ApiRecord a = (ApiRecord) value;
            if(a.getApiName().equals(apiRecordThreadLocal.get().getApiName())){
                apiRecordThreadLocal.get().setReqCount(a.getReqCount()+1);
                redisUtils.mSet(a.getUserAppid()+apiRecordThreadLocal.get().getApiMethod(), apiRecordThreadLocal.get(),30);
                log.info("用户相同接口多次请求接口：{}",apiRecordThreadLocal.get());
            }else {
                apiRecordThreadLocal.get().setReqCount(1);
                redisUtils.mSet(a.getUserAppid()+apiRecordThreadLocal.get().getApiMethod(), apiRecordThreadLocal.get(),30);
                log.info("用户不同请求接口：{}",apiRecordThreadLocal.get());
            }
        }
        //防止内存泄漏
        apiRecordThreadLocal.remove();
    }


    @Around("req()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable{
        long startTime = System.currentTimeMillis();
        // ob 为方法的返回值
        Object ob = point.proceed();
        log.info("耗时 : {} ms" ,System.currentTimeMillis() - startTime);
        return ob;
    }
}
