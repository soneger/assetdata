package cn.soneer.assetdata.exception;

import cn.soneer.assetdata.commons.dto.RespData;
import io.lettuce.core.RedisCommandTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

/**
 * @Author Soneer
 * @Date 2021/2/5 15:40
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class ResultExceptionHandler {
    /** 拦截自定义异常 */
    @ExceptionHandler(ResultException.class)
    public RespData resultException(ResultException e){
        return RespData.error(e.getCode(),e.getMessage());
    }


    /** 拦截参数验证异常 */
    @ExceptionHandler(BindException.class)
    public RespData bindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        return RespData.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /** 拦截未知的运行时异常 */
    @ExceptionHandler(RuntimeException.class)
    public RespData runtimeException(RuntimeException e) {
        log.error("【系统异常】:{}", e);
        return RespData.error("系统异常");
    }

    @ExceptionHandler(RedisCommandTimeoutException.class)
    public RespData runtimeException(RedisCommandTimeoutException e) {
        log.error("【缓存异常】:{}", e);
        return RespData.error("缓存异常");
    }



    /** 拦截参数错误异常 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespData runtimeException(MethodArgumentNotValidException e) {
        log.error("参数异常：{}", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return RespData.error(500,fieldError.getDefaultMessage());
            }
        }
        return RespData.error("系统异常");
    }

}

