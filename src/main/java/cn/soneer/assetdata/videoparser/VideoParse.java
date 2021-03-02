package cn.soneer.assetdata.videoparser;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.soneer.assetdata.utils.RegexUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author:soneer
 * @date: 2021/2/23
 * @time: 19:28
 */
@Slf4j
public class VideoParse {
    private final static String KSAPI = "";
    private final static String DYAPI = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
    private final static String PPXAPI = "https://is.snssdk.com/bds/item/detail/?app_name=super&aid=1319&item_id=";

    /**
     * 视频合集解析（快手、抖音、皮皮虾）
     * @param url
     * @return
     */
    public static String videoParse(String url){
        String hostName = RegexUtil.getHostNameFromUrl(url);
        switch (hostName){
            case "pipix" :
                return ppxParseUrl(url,PPXAPI);
            case "douyin" :
                return dyParseUrl(url,DYAPI);
            case "kuaishou" :
                return ksParseUrl(url);
            default:
            return "";
        }
    }

    /**
     * 快手视频解析
     * @param ksUrl
     * @return
     */
    public static String ksParseUrl(String ksUrl) {
        String url = RegexUtil.filterUrl(ksUrl);
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
        String redirectUrl = HttpUtil.createGet(url).addHeaders(headers).execute().header("Location");
        System.out.println("重定向地址："+redirectUrl);
        String body= HttpUtil.createGet(redirectUrl).addHeaders(headers).execute().body();
        Document doc= Jsoup.parse(body);
        Elements videoElement = doc.select("script[type=text/javascript]");
        String videoInfo = videoElement.get(3).data().replaceAll("window.pageData= ","");
        JSONObject json = JSONObject.parseObject(videoInfo);
        String title = json.getJSONObject("video").getString("caption");
        String videoUrl=json.getJSONObject("video").getString("srcNoMark");
        videoUrl=videoUrl.substring(0,videoUrl.indexOf("?"));
        log.debug(videoUrl);
        log.debug(title);
        return videoUrl;
    }

    /**
     * 皮皮虾视频解析
     * @param ppxUrl
     * @param ppxApi
     */
    public static String ppxParseUrl(String ppxUrl,String ppxApi) {
        String url = RegexUtil.filterUrl(ppxUrl);
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; MEIZU 16th Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
        String redirectUrl = HttpUtil.createGet(url).addHeaders(headers).execute().header("Location");
        log.info("重定向地址：{}",redirectUrl);
        String[] split = redirectUrl.split("/");
        log.info("分割后的地址参数：{}",Arrays.toString(split));
        String item_id = redirectUrl.substring(redirectUrl.indexOf(split[4]), redirectUrl.indexOf("?"));
        log.info("item_id:{}",item_id);
        String finalUrl = ppxApi+item_id;
        log.info("finalUrl:{}",finalUrl);
        String body= HttpUtil.createGet(finalUrl).addHeaders(headers).execute().body();
        log.info("body:{}",body);
        JSONObject object = JSONObject.parseObject(body);
        JSONObject data = object.getJSONObject("data").getJSONObject("data").getJSONObject("origin_video_download");
        JSONArray comments = JSONArray.parseArray(data.getString("url_list"));
//        JSONObject video_high = comments.getJSONObject(0).getJSONObject("item").getJSONObject("video")
//                .getJSONObject("video_high");
//        JSONArray url_list = JSONArray.parseArray(video_high.getString("url_list"));
        log.info("data:{}",comments.get(0));
        String videoUrl = comments.getJSONObject(0).getString("url");
        return videoUrl;
    }


    public static String dyParseUrl(String dyUrl,String dyApi) {
        String url = RegexUtil.filterUrl(dyUrl);
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; MEIZU 16th Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
        String redirectUrl = HttpUtil.createGet(url).addHeaders(headers).execute().header("Location");
        log.info("抖音重定向地址：{}",redirectUrl);
        String[] split = redirectUrl.split("/");
        log.info("分割后的地址参数：{}",Arrays.toString(split));
        String item_id = split[5];
        log.info("item_id:{}",item_id);
        String finalUrl = dyApi+item_id;
        log.info("finalUrl:{}",finalUrl);
        String body= HttpUtil.createGet(finalUrl).addHeaders(headers).execute().body();
        log.info("body:{}",body);
        JSONObject object = JSONObject.parseObject(body);
        String videoAddress = object.getJSONArray("item_list").getJSONObject(0).getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").get(0).toString();
        //替换成无水印链接
        String replace = videoAddress.replace("playwm", "play");
        log.info("替换成无水印链接:{}",replace);
        HttpResponse execute = HttpUtil.createGet(replace).addHeaders(headers).execute();
        String finalVideoAddress = execute.header("Location");
        log.info("finalVideoAddress:{}",finalVideoAddress);
        return finalVideoAddress;
    }

    public static String weiShiParseUrl(String weiShiUrl){
//        String url = RegexUtil.filterUrl(weiShiUrl);
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; MEIZU 16th Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
        String body = HttpUtil.createGet(weiShiUrl).addHeaders(headers).execute().body();
        log.info("body：{}",body);
        //        Connection con = Jsoup.connect(weiShiUrl);
//
//        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

//        try {
////            Connection.Response resp = con.method(Connection.Method.GET).execute();
////            String body = con.execute().body();
//            log.info("body：{}",body);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "";
    }
}
