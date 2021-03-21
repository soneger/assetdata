package cn.soneer.assetdata.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * project：assetdata
 * class：PoemsType
 * describe：TODO
 * time：2021/3/10 22:15
 * author：soneer
 * version:1.0
 */
@Data
public class PoemsType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据创建时间
     */
    @JsonIgnore
    private Date create_time;

    /**
     * 数据修改时间
     */
    @JsonIgnore
    private Date update_time;
}
