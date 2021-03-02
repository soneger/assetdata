//package cn.soneer.assetdata;
//
///**
// * @Author Soneer
// * @Date 2021/2/23 9:52
// * @Version 1.0
// */
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.http.HttpUtil;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//import java.io.*;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.HashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
///**
// * 快手去水印
// *
// * @author taishan
// * @version 1.0
// * @date 2020/8/4
// * @since JDK1.8
// */
//@Slf4j
//public class KuaiShou {
//
//    //视频保存目录
//    private static final String videoSavePath="d:/快手视频/";
//
//    //分享链接（手动修改）
//    private static String targetPath = "陪伴是最常情的告白，守护是最沉默的陪伴…… #汪星人 #宠物避障挑战 https://v.kuaishou.com/5xXNiL 复制此链接，打开【快手App】直接观看！";
//
//    public static void main(String[] args) {
//        ksParseUrl(filterUrl(targetPath));
//    }
//
//    /**
//     * 解析下载视频
//     * @param url
//     */
//    public static void ksParseUrl(String url) {
//        HashMap<String, String> headers = MapUtil.newHashMap();
//        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
//        String redirectUrl = HttpUtil.createGet(url).addHeaders(headers).execute().header("Location");
//        System.out.println("重定向地址："+redirectUrl);
//        String body= HttpUtil.createGet(redirectUrl).addHeaders(headers).execute().body();
//        Document doc= Jsoup.parse(body);
////        System.out.println(doc);
//        Elements videoElement = doc.select("script[type=text/javascript]");
//        String videoInfo = videoElement.get(3).data().replaceAll("window.pageData= ","");
//        JSONObject json = JSONObject.parseObject(videoInfo);
//        String title = json.getJSONObject("video").getString("caption");
//        String videoUrl=json.getJSONObject("video").getString("srcNoMark");
//        videoUrl=videoUrl.substring(0,videoUrl.indexOf("?"));
//        log.debug(videoUrl);
//        log.debug(title);
////        downVideo(videoUrl,videoSavePath+title+".mp4");
//    }
//
//}
