package cn.soneer.assetdata.service;

import cn.soneer.assetdata.entity.City;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:19
 * @Version 1.0
 */
public interface ICityService {


    List<City> listAll();

    List<City> getCityByProvince(String province);

}
