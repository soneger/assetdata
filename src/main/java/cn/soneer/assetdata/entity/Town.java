package cn.soneer.assetdata.entity;

import lombok.Data;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:15
 * @Version 1.0
 */
@Data
public class Town extends BaseEntiry{
    private Long id;
    private String province;
    private String city;
    private String area;
    private String town;

}
