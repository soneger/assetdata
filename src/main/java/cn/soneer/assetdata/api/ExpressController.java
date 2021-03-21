package cn.soneer.assetdata.api;


import cn.soneer.assetdata.annotation.RequestAstrict;
import cn.soneer.assetdata.business.ExpressApi;
import cn.soneer.assetdata.commons.dto.RespData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestAstrict
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

    /**
     *
     * @return
     */
    @GetMapping("/info")
    public RespData getExpressInfo(String number,String phone){
        log.info("单号：{}，电话：{}",number,phone);
        JSONObject expressInfo = ExpressApi.getExpressCompany(number);
        log.info("result data :{}",expressInfo);
        final String code = expressInfo.getString("code");
        final String respMsg = expressInfo.getString("msg");
        JSONObject data = expressInfo.getJSONObject("data");
        if(!"1".equals(code)){
            return RespData.error(Integer.valueOf(code),respMsg);
        }
        JSONArray array = data.getJSONArray("searchList");
        log.info("result list {}",array.get(0).toString());
        final JSONObject jsonObject1 = JSONObject.parseObject(array.get(0).toString());
        final String logisticsTypeId = jsonObject1.getString("logisticsTypeId");
        log.info("logisticsTypeId :{}",logisticsTypeId);
        final Map<String,Object>  details = getDetails(number, logisticsTypeId);
        return RespData.success(details);
    }

    @GetMapping("/details")
    public Map<String,Object>  getDetails(String number,String typeId){
        log.info("单号：{}，类型id：{}",number,typeId);
        JSONObject expressDetails = ExpressApi.getExpressDetails(number, typeId, null);
        final JSONObject jsonObject = expressDetails.getJSONObject("data");
        Map<String,Object> expessHead = new HashMap<>();
        expessHead.put("number",jsonObject.getString("logisticsNo"));
        expessHead.put("deliverystatus",jsonObject.getString("status"));
        expessHead.put("logisticsType",jsonObject.getString("logisticsType"));
        Map<String,Object> result = new HashMap<>();
        result.put("expessHead",expessHead);
        result.put("data",jsonObject.getString("data"));
        return result;
    }
}