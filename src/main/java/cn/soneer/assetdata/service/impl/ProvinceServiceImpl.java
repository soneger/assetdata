package cn.soneer.assetdata.service.impl;

import cn.soneer.assetdata.entity.Municipality;
import cn.soneer.assetdata.entity.Province;
import cn.soneer.assetdata.mapper.ProvinceMapper;
import cn.soneer.assetdata.service.IProvinceService;
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
public class ProvinceServiceImpl implements IProvinceService {
    @Resource
    private ProvinceMapper provinceMapper;
    @Override
    public List<Province> provinceList() {

        return provinceMapper.provinceList();
    }

    @Override
    public List<Province> listAll() {
        return provinceMapper.listAll();
    }

    @Override
    public Province getByProvince(String province) {
        return provinceMapper.getByProvince(province);
    }

}
