package cn.soneer.assetdata.api;


import cn.soneer.assetdata.business.ExpressApi;
import cn.soneer.assetdata.commons.dto.RespData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author Soneer
 * @Date 2021/3/2 9:53
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("api/express/")
public class ExpressController {

    @RequestMapping("/query")
    public RespData expressQuery(String number,String phone){
        log.info("单号：{}，电话：{}",number,phone);
        JSONObject expressInfo = ExpressApi.getExpressInfo(number, phone);
        JSONObject resp = expressInfo.getJSONObject("resp");
        final String code = resp.getString("RespCode");
        final String respMsg = resp.getString("RespMsg");
        JSONObject data = expressInfo.getJSONObject("data");
        if(!"0".equals(code)){
            return RespData.error(Integer.valueOf(code),respMsg);
        }
        JSONArray array = data.getJSONArray("list");
        Map<String,Object> expessHead = new HashMap<>();
        List<Map<String,String>> expressStatus = new ArrayList<>();
        expessHead.put("number",data.getString("number"));
        expessHead.put("deliverystatus",Integer.valueOf(data.getString("deliverystatus")));
        expessHead.put("typename",data.getString("typename"));
        expessHead.put("logo",data.getString("logo"));
        expessHead.put("type",data.getString("type"));
        for (int i = 0;i<array.size();i++){
            Map<String,String> info = new HashMap<>();
            info.put("desc",array.getJSONObject(i).getString("status"));
            info.put("text",array.getJSONObject(i).getString("time"));
            expressStatus.add(info);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("expessHead",expessHead);
        result.put("expressStatus",expressStatus);
        return RespData.success(result);
    }
}