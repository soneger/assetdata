package cn.soneer.assetdata.business;

import cn.soneer.assetdata.constant.APIConst;
import cn.soneer.assetdata.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Soneer
 * @Date 2021/3/1 10:31
 * @Version 1.0
 */
@Slf4j
public class ExpressApi {
    public static void main(String[] args) {
        String host = "https://qyexpress.market.alicloudapi.com";
        String path = "/composite/queryexpress";
        String method = "GET";
        String appcode = "b333b9eaf1744300a8ad7f030c2b2732";
        String number = "549116918826";
        String phone = "";
        JSONObject expressInfo = getExpressInfo(number, null);
    }

    /**
     * 根据单号获取快递信息（顺丰需要传入手机号）
     * @param number
     * @param phone
     * @return
     */
    public static JSONObject getExpressInfo(String number,String phone){
        Map<String, String> headers = new HashMap<>(1);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + APIConst.APPCODE);
        Map<String, String> querys = new HashMap<String, String>(2);
        if(phone!=null&&!phone.isEmpty()){
            querys.put("mobile", phone);
        }
        querys.put("number", number);
        try {
            HttpResponse response = HttpUtils.doGet(APIConst.EXPRESS_DOMAIN, APIConst.QUERYEXPRESS_PATH, "GET", headers, querys);
//            log.info("response:{}",response.toString());
            //获取response的body
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            log.info("body:{}",jsonObject);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
