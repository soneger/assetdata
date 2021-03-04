package cn.soneer.assetdata.weixin.service;

import cn.soneer.assetdata.utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Soneer
 * @Date 2021/3/3 17:36
 * @Version 1.0
 */
@Slf4j
public class WeiXinService {
    private static final String WX_LOGIN_DOMAIN = "https://api.weixin.qq.com";
    private static final String WX_LOGIN_PATH = "/sns/jscode2session";
    private static final String appId = "wx8ad91b0788a3192f";
    /**
     * 牛牛小助理 小程序
     */
    private static final String secret = "8aaebb56c37f462e61aed077fd937d45";
    private static final String grant_type = "authorization_code";


    public static JSONObject authCode2Session(String code){
        Map<String,String> params = new HashMap<>(4);
        Map<String,String> headers = new HashMap<>(1);
        params.put("appId",appId);
        params.put("secret",secret);
        params.put("grant_type",grant_type);
        try {
            HttpResponse response = HttpUtils.doGet(WX_LOGIN_DOMAIN, WX_LOGIN_PATH, "GET", headers,params);
            return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
