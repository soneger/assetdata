package cn.soneer.assetdata.service.impl;

import cn.soneer.assetdata.entity.Area;
import cn.soneer.assetdata.mapper.AreaMapper;
import cn.soneer.assetdata.service.IAreaService;
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
public class AreaServiceImpl implements IAreaService {
    @Resource
    private AreaMapper AreaMapper;


    @Override
    public List<Area> list() {
        return AreaMapper.list();
    }

    @Override
    public List<Area> getAreaByProvince(String province) {
        return AreaMapper.getAreaByProvince(province);
    }
}
