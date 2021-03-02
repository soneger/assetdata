package cn.soneer.assetdata.commons.dto;

import cn.soneer.assetdata.commons.enums.ResultEnum;
import lombok.Data;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:32
 * @Version 1.0
 */
@Data
public class RespData<T> {
    /** 状态码 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 响应数据 */
    private T data;

    /**
     * 操作成功
     * @param msg 提示信息
     * @param object 对象
     */
    public static RespData success(String msg, Object object){
        RespData<Object> RespData = new RespData<>();
        RespData.setMsg(msg);
        RespData.setCode(ResultEnum.SUCCESS.getCode());
        RespData.setData(object);
        return RespData;
    }



    /**
     * 操作成功，使用默认的提示信息
     * @param object 对象
     */
    public static RespData success(Object object){
        String message = ResultEnum.SUCCESS.getMessage();
        return success(message, object);
    }

    /**
     * 操作成功，返回提示信息，不返回数据
     */
    public static RespData success(String msg){
        Object object = null;
        return success(msg, object);
    }

    /**
     * 操作成功，不返回数据
     */
    public static RespData success(){
        return success(null);
    }

    /**
     * 操作有误
     * @param code 错误码
     * @param msg 提示信息
     */
    public static RespData error(Integer code, String msg){
        RespData RespData = new RespData();
        RespData.setMsg(msg);
        RespData.setCode(code);
        return RespData;
    }

    /**
     * 操作有误，使用默认400错误码
     * @param msg 提示信息
     */
    public static RespData error(String msg){
        Integer code = ResultEnum.ERROR.getCode();
        return error(code, msg);
    }

    /**
     * 操作有误，只返回默认错误状态码
     */
    public static RespData error(){
        return error(null);
    }
}
