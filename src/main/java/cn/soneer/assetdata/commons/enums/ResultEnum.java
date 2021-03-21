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

    APP_ID_SECRETLY_NOT_NULL(10000,"appId或secretKey为空，邮箱联系：344309663@qq.com，或者搜索小程序《牛牛小助理》自行申请appId和secretKey。"),
    API_REQ_OFTEN(10001,"接口访问太频繁啦"),
    API_UNAUTHORIZED(10002,"接口未授权，邮箱联系：344309663@qq.com，或者搜索小程序《牛牛小助理》自行申请appId和secretKey。"),
    API_RUN_OUT_OF(10003,"调用次数已用完"),

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
