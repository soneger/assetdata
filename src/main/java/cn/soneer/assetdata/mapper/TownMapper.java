package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.Town;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/7 14:39
 * @Version 1.0
 */
public interface TownMapper {
    /**
     * 直辖市
     * @param pronvince
     * @param area
     * @return
     */
    List<Town> getTownByProvinceAndArea(String pronvince,String area);

    /**
     * 省
     * @param pronvince
     * @param city
     * @param area
     * @return
     */
    List<Town> getTownByProvinceAndCityAndArea(String pronvince,String city,String area);
}
