package cn.soneer.assetdata.service.impl;

import cn.soneer.assetdata.entity.AncientPoems;
import cn.soneer.assetdata.entity.PoemsType;
import cn.soneer.assetdata.mapper.AncientPoemsMapper;
import cn.soneer.assetdata.mapper.PoemsTypeMapper;
import cn.soneer.assetdata.redis.utils.RedisUtils;
import cn.soneer.assetdata.service.IAncientPoemsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * project：assetdata
 * class：AncientPoemsServiceImpl
 * describe：TODO
 * time：2021/3/10 22:24
 * author：soneer
 * version:1.0
 */
@Slf4j
@Service
public class AncientPoemsServiceImpl implements IAncientPoemsService {
    @Resource
    private AncientPoemsMapper ancientPoemsMapper;

    @Resource
    private PoemsTypeMapper poemsTypeMapper;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public List<PoemsType> poemsTypeList() {
        return poemsTypeMapper.list();
    }

    @Override
    public int insert(AncientPoems ancientPoems) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(AncientPoems ancientPoems) {
        return 0;
    }

    @Override
    public AncientPoems getById(Integer id) {
        return ancientPoemsMapper.getById(id);
    }

    @Override
    public AncientPoems getByTypeIdAndId(Integer typeId, Integer id) {
        return ancientPoemsMapper.getByTypeIdAndId(typeId,id);
    }

    @Override
    public List<AncientPoems> pageList(int page, int size) {
        return null;
    }

    @Override
    public List<AncientPoems> listByType(Integer typeId) {
        Object o = redisUtils.get(typeId.toString());
        log.info("redis result {}", JSON.toJSON(o));
        if(o!=null){
            return (List<AncientPoems>)o;
        }
        List<AncientPoems> ancientPoems = ancientPoemsMapper.listByType(typeId);
        redisUtils.mSet(typeId.toString(),ancientPoems,5);
        return ancientPoems;
    }
}
