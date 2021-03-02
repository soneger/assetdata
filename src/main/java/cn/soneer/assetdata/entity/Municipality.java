package cn.soneer.assetdata.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/8 17:07
 * @Version 1.0
 */
@Data
public class Municipality extends BaseEntiry{
    private Long id;
    private String province;
    private List<Area> area;
}
