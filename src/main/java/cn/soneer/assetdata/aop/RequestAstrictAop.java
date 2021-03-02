package cn.soneer.assetdata.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author Soneer
 * @Date 2021/2/5 16:24
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class RequestAstrictAop {

    @Pointcut("@annotation(cn.soneer.assetdata.annotation.RequestAstrict)")
    public void req(){}

    @Before("req()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info("进入切面前");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("URL : {}" , request.getRequestURL().toString());
        log.info("HTTP_METHOD : {}" , request.getMethod());
        log.info("IP : {}" , request.getRemoteAddr());
        log.info("CLASS_METHOD : {},{}" , joinPoint.getSignature().getDeclaringTypeName() , joinPoint.getSignature().getName());
        log.info("ARGS : {}" , Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "req()")
    public void doAfterReturning(Object ret) throws Throwable {
        log.info("进入切面后");
        // 处理完请求，返回内容
        log.info("RESPONSE : {}" , ret);
    }

    @Around("req()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable{
        long startTime = System.currentTimeMillis();
        // ob 为方法的返回值
        Object ob = point.proceed();
        log.info("耗时 : " + (System.currentTimeMillis() - startTime)+"ms");
        return ob;
    }
}
