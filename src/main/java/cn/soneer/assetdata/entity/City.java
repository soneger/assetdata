package cn.soneer.assetdata.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:11
 * @Version 1.0
 */
@Data
public class City extends BaseEntiry{
    private Long id;
    private String province;
    private String city;
    private List<Area> area;
}
