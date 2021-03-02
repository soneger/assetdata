package cn.soneer.assetdata.exception;

import cn.soneer.assetdata.commons.enums.ResultEnum;
import cn.soneer.assetdata.commons.interfaces.ResultInterface;
import lombok.Getter;

/**
 * @Author Soneer
 * @Date 2021/2/5 15:42
 * @Version 1.0
 */
@Getter
public class ResultException extends RuntimeException{
    private Integer code;

    /**
     * 统一异常处理
     * @param resultEnum 状态枚举
     */
    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 统一异常处理
     * @param resultEnum 枚举类型，需要实现结果枚举接口
     */
    public ResultException(ResultInterface resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 统一异常处理
     * @param code 状态码
     * @param message 提示信息
     */
    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
