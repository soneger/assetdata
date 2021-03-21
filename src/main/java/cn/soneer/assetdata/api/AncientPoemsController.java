package cn.soneer.assetdata.api;

import cn.soneer.assetdata.annotation.RequestAstrict;
import cn.soneer.assetdata.commons.dto.RespData;
import cn.soneer.assetdata.entity.AncientPoems;
import cn.soneer.assetdata.entity.PoemsType;
import cn.soneer.assetdata.redis.utils.RedisUtils;
import cn.soneer.assetdata.service.IAncientPoemsService;
import cn.soneer.assetdata.utils.ValidUtils;
import cn.soneer.assetdata.valid.PoemsValid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * project：assetdata
 * class：AncientPoemsController
 * describe：古诗文接口
 * time：2021/3/10 22:07
 * author：soneer
 * version:1.0
 */
@RestController
@RequestMapping("poems")
public class AncientPoemsController {
    private IAncientPoemsService ancientPoemsService;

    public AncientPoemsController(IAncientPoemsService ancientPoemsService,RedisUtils redisUtils) {
        this.ancientPoemsService = ancientPoemsService;
    }

    @RequestAstrict(name = "获取类别列表")
    @RequestMapping("type/list")
    public RespData typeList(){
        List<PoemsType> poemsTypes = ancientPoemsService.poemsTypeList();
        return RespData.success(poemsTypes);
    }

    @RequestAstrict(name = "根据类别ID获取详情")
    @RequestMapping("type/details")
    public RespData cateList(String typeId){
        if (typeId!=null&&typeId.isEmpty()|| !ValidUtils.isNumber(typeId)){
            return RespData.error("参数格式不正确");
        }
        List<AncientPoems> ancientPoems = ancientPoemsService.listByType(Integer.valueOf(typeId));
        return RespData.success(ancientPoems);
    }


    @RequestAstrict(name = "获取古诗详情")
    @RequestMapping("/detail")
    public RespData getByTypeIdAndId(@Valid PoemsValid poemsValid){
        Integer typeId = Integer.valueOf(poemsValid.getTypeId());
        Integer id = Integer.valueOf(poemsValid.getId());
        AncientPoems ancientPoems = ancientPoemsService.getByTypeIdAndId(typeId, id);
        return RespData.success(ancientPoems);
    }
}

