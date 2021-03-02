package cn.soneer.assetdata.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:soneer
 * @date: 2021/2/23
 * @time: 19:19
 */
public class RegexUtil {

    /**
     *
     * 过滤链接的中文汉字
     * @param targetPath
     * @return
     */
    public static String filterUrl(String targetPath) {
        //匹配网址
        String regex = "https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(targetPath);
        if(m.find()){
            return  targetPath.substring(m.start(),m.end());
        }
        return "";
    }

    /**
     * 提取域名商名字
     * 域名格式xxx.xxx.xx 对于此格式不适用
     * @param url
     * @return
     */
    public static String getHostNameFromUrl(String url){
        String hostName = url.substring(url.indexOf(".")+1, url.lastIndexOf("."));
        return hostName.isEmpty()? "":hostName;
    }
}
