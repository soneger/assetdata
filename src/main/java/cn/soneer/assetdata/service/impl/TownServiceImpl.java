package cn.soneer.assetdata.service.impl;

import cn.soneer.assetdata.entity.Town;
import cn.soneer.assetdata.mapper.TownMapper;
import cn.soneer.assetdata.service.ITownService;
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
public class TownServiceImpl implements ITownService {
    @Resource
    private TownMapper TownMapper;

    @Override
    public List<Town> getTownByProvinceAndCityAndArea(String pronvince, String city, String area) {
        return TownMapper.getTownByProvinceAndCityAndArea(pronvince,city,area);
    }
}
