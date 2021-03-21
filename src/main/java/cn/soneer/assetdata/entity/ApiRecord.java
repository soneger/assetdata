package cn.soneer.assetdata.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * project：assetdata
 * class：ApiRecord
 * describe：TODO
 * time：2021/3/21 10:46
 * author：soneer
 * version:1.0
 */
@Data
public class ApiRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口方法
     */
    private String apiMethod;

    /**
     * 用户appid
     */
    private String userAppid;

    /**
     * 请求次数
     */
    private Integer reqCount;

    /**
     * 请求状态
     */
    private Integer reqStatus;

    /**
     * 请求ip
     */
    private String reqIp;

    /**
     * 耗时
     */
    private Long runTime;
    /**
     * 创建时间
     */
    private Date createTime;
}
