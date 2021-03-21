package cn.soneer.assetdata.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * project：assetdata
 * class：UserApi
 * describe：TODO
 * time：2021/3/21 14:51
 * author：soneer
 * version:1.0
 */
@Data
public class UserApi implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 接口id
     */
    private Integer apiId;
    /**
     * 用户appid
     */
    private String appId;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 私钥
     */
    private String privateKey;


    /**
     * 授权可调用总次数
     */
    private Integer totalCount;

    /**
     * 已使用次数
     */
    private Integer usedCount;

    /**
     * 成功次数
     */
    private Integer successCount;

    /**
     * 失败次数
     */
    private Integer errorCount;

    /**
     * 状态：1正常、0禁用
     */
    private Integer status;

    /**
     * create_time
     */
    private Date createTime;
}
