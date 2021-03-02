package cn.soneer.assetdata;

import cn.soneer.assetdata.utils.FileUtil;
import cn.soneer.assetdata.utils.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Soneer
 * @Date 2021/2/26 14:10
 * @Version 1.0
 */
public class BgmExtract {
    // webapi接口地址
    private static final String WEBSING_URL = "http://webqbh.xfyun.cn/v1/service/v1/qbh";
    // 应用ID
    private static final String APPID = "60279ad7";
    // 接口密钥
    private static final String API_KEY = "63fabf2d35124161c4665cb649e2c5d6";

    private static final String ENGINE_TYPE = "afs";


    private static final String AUE = "aac";

    /*客户端传输一个audio_url参数时，Http Request Body需为空*/
    private static final String AUDIO_URL_BZ = "http://cdn.inyuapp.com/audios/20180524120030bEAwdc.wav";
    private static final String AUDIO_URL = "https://s23.aconvert.com/convert/p3r68-cdx67/avjab-0iycv.aac";

    private static final String AUDIO_PATH = "D:/audio/demo.aac";
    /**
     * 合成 WebAPI 调用示例程序
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Map<String, String> header = buildHttpHeader();

        byte[] audioByteArray = FileUtil.read(AUDIO_PATH);
        String result = HttpUtil.doPost(WEBSING_URL, header,audioByteArray);
        System.out.println("WebAPI 接口调用结果：" + result);
    }

    /**
     * 组装http请求头
     */
    private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";
        // 如果使用audio_url传输音频，须在param中添加audio_url参数
        String param = "{\"aue\":\"" + AUE + "\",\"engine_type\":\"" + ENGINE_TYPE + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", APPID);
        return header;
    }
}
