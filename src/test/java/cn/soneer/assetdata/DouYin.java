//package cn.soneer.assetdata;
//
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.http.HttpUtil;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.HashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @author:soneer
// * @date: 2021/2/23
// * @time: 19:07
// */
//@Slf4j
//public class DouYin {
//    //分享链接（手动修改）
//    private static String targetPath = "https://h5.pipix.com/s/e1U7D26/";
//    private static String dyApi = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
//
//    public static void main(String[] args) {
//        dyParseUrl(filterUrl(targetPath));
//    }
//
//    /**
//     * 解析下载视频
//     * @param url
//     *
//     */
//    public static void dyParseUrl(String url) {
//        HashMap<String, String> headers = MapUtil.newHashMap();
//        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
//        String redirectUrl = HttpUtil.createGet(url).addHeaders(headers).execute().header("Location");
//        log.info("重定向地址：{}",redirectUrl);
//        String[] split = redirectUrl.split("/");
//        log.info("斜杠：{}",split);
//        String item_id = redirectUrl.substring(redirectUrl.indexOf(split[4]), redirectUrl.indexOf("?"));
//        log.info("item_id:{}",item_id);
//        String finalUrl = dyApi+item_id;
//        log.info("finalUrl:{}",finalUrl);
//        String body= HttpUtil.createGet(finalUrl).addHeaders(headers).execute().body();
////        log.info("body:{}",body);
//        JSONObject object = JSONObject.parseObject(body);
//        JSONObject data = object.getJSONObject("data").getJSONObject("item");
//        JSONArray comments = JSONArray.parseArray(data.getString("comments"));
//        JSONObject video_high = comments.getJSONObject(0).getJSONObject("item").getJSONObject("video")
//                .getJSONObject("video_high");
//        JSONArray url_list = JSONArray.parseArray(video_high.getString("url_list"));
//        log.info("data:{}",url_list.get(0));
////        JSONArray objects = JSONObject.parseArray(data.toJSONString());
////        Document doc= Jsoup.parse(body);
//////        System.out.println(doc);
////        Elements videoElement = doc.select("script[type=text/javascript]");
////        String videoInfo = videoElement.get(3).data().replaceAll("window.pageData= ","");
////        JSONObject json = JSONObject.parseObject(videoInfo);
////        String title = json.getJSONObject("video").getString("caption");
////        String videoUrl=json.getJSONObject("video").getString("srcNoMark");
////        videoUrl=videoUrl.substring(0,videoUrl.indexOf("?"));
////        log.debug(videoUrl);
////        log.debug(title);
////        downVideo(videoUrl,videoSavePath+title+".mp4");
//    }
//
//
//
//
//}
