package cn.soneer.assetdata.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;

/**
 *  study_data
 *  @author soneer
 *  @data 2021-03-06
*/
@Data
public class AncientPoems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类
     */
    @JsonIgnore
    private String subcate;

    /**
     *
     */
    private String category;

    /**
     * 年代
     */
    private String era;

    /**
     * 作者
     */
    private String author;

    /**
     * author_head
     */
    private String author_head;

    /**
     * 内容
     */
    private String content;

    /**
     * 原网页内容
     */
    @JsonIgnore
    private String content_html;

    /**
     * 译文
     */
    private String translation;

    /**
     * 注释
     */
    private String explanation;



    /**
     * 创作背景
     */
    private String background;

    /**
     * 鉴赏
     */
    @JsonIgnore
    private String appreciate;

    /**
     * 赏析
     */
    private String appreciation;

    /**
     * 作者信息
     */
    private String author_info;

    /**
     * 类型
     */
    @JsonIgnore
    private String type;

    /**
     * other
     */
    @JsonIgnore
    private String other;

    /**
     * create_time
     */
    @JsonIgnore
    private Date create_time;

    /**
     * update_time
     */
    @JsonIgnore
    private Date update_time;





}
