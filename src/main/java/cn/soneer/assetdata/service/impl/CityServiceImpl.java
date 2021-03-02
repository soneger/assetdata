package cn.soneer.assetdata.service.impl;

import cn.soneer.assetdata.entity.City;
import cn.soneer.assetdata.mapper.CityMapper;
import cn.soneer.assetdata.service.ICityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:23
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CityServiceImpl implements ICityService {
    @Resource
    private CityMapper cityMapper;


    @Override
    public List<City> listAll() {
        return cityMapper.cityListAll();
    }

    @Override
    public List<City> getCityByProvince(String province) {
        return cityMapper.getCityByProvince(province);
    }
}
