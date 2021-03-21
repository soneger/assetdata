package cn.soneer.assetdata.service;

import cn.soneer.assetdata.entity.ApiRecord;
import cn.soneer.assetdata.entity.UserApi;

import java.util.List;

/**
 * project：assetdata
 * class：ApiTaskService
 * describe：TODO
 * time：2021/3/20 22:19
 * author：soneer
 * version:1.0
 */

public interface ApiTaskService {

    /**
     *
     * @param apiRecord
     * @return
     */
    boolean saveApiRecord(ApiRecord apiRecord);

    boolean userApiVerify(String appid,String secretKey,int apiId);


}
