package cn.soneer.assetdata.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 10:28
 * @Version 1.0
 */
@Data
public class Province extends BaseEntiry{
    private Long id;
    private String province;
    private List<City> city;
}
