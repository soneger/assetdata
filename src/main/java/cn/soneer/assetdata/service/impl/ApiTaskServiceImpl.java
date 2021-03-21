package cn.soneer.assetdata.service.impl;

import cn.soneer.assetdata.commons.enums.ResultEnum;
import cn.soneer.assetdata.entity.ApiRecord;
import cn.soneer.assetdata.entity.UserApi;
import cn.soneer.assetdata.exception.ResultException;
import cn.soneer.assetdata.mapper.ApiRecordMapper;
import cn.soneer.assetdata.mapper.UserApiMapper;
import cn.soneer.assetdata.redis.utils.RedisUtils;
import cn.soneer.assetdata.service.ApiTaskService;
import cn.soneer.assetdata.utils.EncryptUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * project：assetdata
 * class：ApiTaskServiceImpl
 * describe：TODO
 * time：2021/3/21 10:58
 * author：soneer
 * version:1.0
 */
@Slf4j
@Service
public class ApiTaskServiceImpl implements ApiTaskService {
    @Resource
    private ApiRecordMapper apiRecordMapper;
    @Resource
    private UserApiMapper userApiMapper;
    @Resource
    private RedisUtils redisUtils;

    @Override
//    @Async("apiTaskExecutor")
    public boolean saveApiRecord(ApiRecord apiRecord) {
        return apiRecordMapper.insert(apiRecord)>0;
    }

    @Override
//    @Async("apiTaskExecutor")
    public boolean userApiVerify(String appid, String secretKey,int apiID) {
        log.info("参数== appid:{}，secretKey:{}，apiID:{}",appid,secretKey,apiID);
        if(appid==null||secretKey==null){
            throw new ResultException(ResultEnum.APP_ID_SECRETLY_NOT_NULL);
        }
        List<UserApi> userApi = (List<UserApi>)redisUtils.get(appid);
        log.info("用户缓存信息：{}",JSON.toJSON(userApi));
        if(userApi==null){
            userApi = userApiMapper.getByAppId(appid);
            redisUtils.mSet(appid,userApi,30);
        }
        if(userApi.size()==0) {
            throw new ResultException(ResultEnum.API_UNAUTHORIZED);
        }
        for (UserApi u:userApi) {
//            log.info("用户信息:{}", JSON.toJSON(u));
            String md5Str = EncryptUtil.getMD5Str(u.getAppId(), secretKey);
            if (!u.getPrivateKey().equals(md5Str)){
                throw new ResultException(ResultEnum.API_UNAUTHORIZED);
            }
            if (u.getApiId()==apiID){
                if (u.getUsedCount()>=u.getTotalCount()){
                    throw new ResultException(ResultEnum.API_RUN_OUT_OF);
                }

            }
        }
        return true;
    }


}
