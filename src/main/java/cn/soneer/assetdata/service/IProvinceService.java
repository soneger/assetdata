package cn.soneer.assetdata.service;

import cn.soneer.assetdata.entity.Municipality;
import cn.soneer.assetdata.entity.Province;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:19
 * @Version 1.0
 */
public interface IProvinceService {
    /**
     *
     * @return
     */
    List<Province> provinceList();

    List<Province> listAll();

    /**
     * 获取省城市
     * @param province
     * @return
     */
    Province getByProvince(String province);

    /**
     * 获取直辖市区/县
     * @param province
     * @return
     */
    Municipality getAreaByProvince(String province);

}
