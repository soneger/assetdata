package cn.soneer.assetdata.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:12
 * @Version 1.0
 */
@Data
public class Area extends BaseEntiry{
    private Long id;
    private String province;
    private String city;
    private String area;
    private List<Town> town;
}
