package cn.soneer.assetdata.enums;

/**
 * project：assetdata
 * class：BizEnums
 * describe：TODO
 * time：2021/3/11 22:20
 * author：soneer
 * version:1.0
 */

public enum BizEnums {

    API_REQ_OFTEN(10001,"接口访问太频繁啦"),
    API_UNAUTHORIZED(10002,"接口未授权"),
    API_RUN_OUT_OF(10003,"接口调用次数已用完");

    private Integer code;

    private String message;

    BizEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
