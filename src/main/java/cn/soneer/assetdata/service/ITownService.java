package cn.soneer.assetdata.service;

import cn.soneer.assetdata.entity.Town;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:19
 * @Version 1.0
 */
public interface ITownService {
    /**
     *
     * @param pronvince
     * @param city
     * @param area
     * @return
     */
    List<Town> getTownByProvinceAndCityAndArea(String pronvince,String city,String area);

}
