package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.Municipality;
import cn.soneer.assetdata.entity.Province;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:17
 * @Version 1.0
 */
public interface ProvinceMapper  {

    Province getByProvince(String province);

    Municipality getAreaByProvince(String province);

    List<Province> listAll();

    List<Province> provinceList();
}
