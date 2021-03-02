package cn.soneer.assetdata.commons.enums;

import cn.soneer.assetdata.commons.interfaces.ResultInterface;
import lombok.Getter;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:36
 * @Version 1.0
 */
@Getter
public enum ResultEnum implements ResultInterface {

    /**
     *
     */
    SUCCESS(200,"成功"),
    /**
     *
     */
    ERROR(500,"失败"),
    IP_ILLEGALITY( 10004,"IP非法访问");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
