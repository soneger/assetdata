package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.City;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/7 14:39
 * @Version 1.0
 */
public interface CityMapper  {

    List<City> getCityByProvince(String province);

    List<City> cityListAll();

    List<City> list();

}
