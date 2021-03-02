package cn.soneer.assetdata.controller;

import cn.soneer.assetdata.commons.dto.RespData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Soneer
 * @Date 2021/2/25 17:03
 * @Version 1.0
 */
@RestController
@RequestMapping("sys")
public class SysConfigController {

    @RequestMapping("/notice")
    public RespData getProvinceList(){
        Map<String,Object> map = new HashMap(6);
        map.put("isShow",true);
        map.put("icon","volume-o");
        map.put("fontColor","#f87c12");
        map.put("bgColor","#FFF8DC");
        map.put("speed",30);
        map.put("content","在代码阅读过程中人们说脏话的频率是衡量代码质量的唯一标准。");
        return RespData.success(map);
    }
}
