package cn.soneer.assetdata.weixin.controller;

import cn.soneer.assetdata.commons.dto.RespData;
import cn.soneer.assetdata.constant.Const;
import cn.soneer.assetdata.weixin.service.WeiXinService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Soneer
 * @Date 2021/3/3 17:25
 * @Version 1.0
 */
@RestController
@RequestMapping("v1/wx/user")
public class WeiXinController {

    @PostMapping(value = "/login", produces = Const.PRODUCES)
    public RespData userLogin(@RequestBody Map<String,String> params){
        String code = params.get("code");
        if (code.isEmpty()){
            return RespData.error(500,"code is null");
        }
        JSONObject object = WeiXinService.authCode2Session(code);
        return RespData.success(object);
    }
}
