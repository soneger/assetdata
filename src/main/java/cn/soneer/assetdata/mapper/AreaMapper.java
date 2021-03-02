package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.Area;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/7 14:39
 * @Version 1.0
 */
public interface AreaMapper  {

    List<Area> list();

    /**
     * 直辖市
     * @param province
     * @return
     */
    List<Area> getAreaByProvince(String province);

    /**
     * 省
     * @param city
     * @param provice
     * @return
     */
    List<Area> getAreaByCity(String city,String provice);
}
