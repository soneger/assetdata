package cn.soneer.assetdata.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:03
 * @Version 1.0
 */
@Data
public class BaseEntiry {

    private String code;
    private String name;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    private String ident;

}
