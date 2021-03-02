package cn.soneer.assetdata.service;

import cn.soneer.assetdata.entity.Area;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:19
 * @Version 1.0
 */
public interface IAreaService {
    /**
     * get list by condition
     * @return
     */
    List<Area> list();

    List<Area> getAreaByProvince(String province);
}
