package cn.soneer.assetdata.interceptor;

import cn.soneer.assetdata.annotation.RequestAstrict;
import cn.soneer.assetdata.commons.dto.RespData;
import cn.soneer.assetdata.commons.enums.ResultEnum;
import cn.soneer.assetdata.enums.BizEnums;
import cn.soneer.assetdata.exception.ResultException;
import cn.soneer.assetdata.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * project：assetdata
 * class：APIInterceptor
 * describe：TODO
 * time：2021/3/11 21:54
 * author：soneer
 * version:1.0
 */
@Component
@Slf4j
public class APIInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestAstrict requestAstrict = handlerMethod.getMethodAnnotation(RequestAstrict.class);
        log.info("requestAstrict ====>{}", requestAstrict);
        if(requestAstrict == null){
            return true;
        }
        int seconds = requestAstrict.seconds();
        int maxCount = requestAstrict.count();

        String ip=request.getRemoteAddr();
        String key = request.getServletPath() + ":" + ip ;
        log.info("redis key :{}",key);
        Object value = redisUtils.get(key);
        if (null == value ) {
            redisUtils.set(key, 1,seconds);
            return true;
        }
        Integer count = Integer.valueOf(value.toString());
        log.info("请求次数：{}",count);
        if (count < maxCount) {
            count = count+1;
            redisUtils.set(key, count,seconds);
            return true;
        }

        if (count >= maxCount) {
            //response 返回 json 请求过于频繁请稍后再试
            throw new ResultException(ResultEnum.API_REQ_OFTEN);
        }

        return super.preHandle(request, response, handler);
    }
}
