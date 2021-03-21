package cn.soneer.assetdata.weixin.controller;

import cn.soneer.assetdata.annotation.RequestAstrict;
import cn.soneer.assetdata.commons.dto.RespData;
import cn.soneer.assetdata.constant.Const;
import cn.soneer.assetdata.weixin.service.WeiXinService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Soneer
 * @Date 2021/3/3 17:25
 * @Version 1.0
 */
@RestController
@RequestMapping("v1/wx/user")
@Slf4j
public class WeiXinController {

    @RequestAstrict(name = "小程序登录")
    @PostMapping(value = "/login", produces = Const.PRODUCES)
    public RespData userLogin(@RequestBody Map<String,Object> params){
        log.info("微信小程序请求参数：{}", params.toString());
        String code = params.get("code").toString();
        String userinfo = params.get("userinfo").toString();
        log.info("code:{}",code);
        log.info("userinfo:{}",userinfo);
        if (code.isEmpty()){
            return RespData.error(500,"code is null");
        }
        JSONObject object = WeiXinService.authCode2Session(code);
        return RespData.success(object);
    }
}
