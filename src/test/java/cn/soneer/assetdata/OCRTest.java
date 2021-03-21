package cn.soneer.assetdata;


import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

/**
 * @Author Soneer
 * @Date 2020/10/26 14:25
 * @Version 1.0
 */
public class OCRTest {

    @Test
    public void OCRTest(){
//        ITesseract instance = new Tesseract();
//        //如果未将tessdata放在根目录下需要指定绝对路径
//        instance.setDatapath("src/main/resources/tessdata");
//        //如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中
//        instance.setLanguage("chi_sim");
//
//        // 指定识别图片
//        File imgDir = new File("D:/OCR/3.JPG");
//        long startTime = System.currentTimeMillis();
//        try {
//            String ocrResult  = instance.doOCR(imgDir);
//            // 输出识别结果
//            System.out.println("OCR Result: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void BaiduOCRTest() throws JSONException {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr("22915446", "nNPbeS4oGtyBWFVHpNUu83i4", "jyPojpVlqlAla2CUAkFGAa39ZNh5fq7a");
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "false");


        // 调用接口
        String path = "E:/OCR/1.JPG";

        JSONObject res = client.basicAccurateGeneral(path, options);
        JSONArray words_result = res.getJSONArray("words_result");
        System.out.println(res.toString(2));
        String str = "";
        for (int i=0;i<words_result.length();i++) {
            JSONObject jsonObject = words_result.getJSONObject(i);
            String words = jsonObject.getString("words");
            str+="\n"+words;
        }
        System.out.println(str);

    }

    @Test
    public void idCardOCR() throws JSONException {
        AipOcr client = new AipOcr("22915446", "nNPbeS4oGtyBWFVHpNUu83i4", "jyPojpVlqlAla2CUAkFGAa39ZNh5fq7a");
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");

        String idCardSide = "back";

        // 参数为本地图片路径
        String idCard1 = "E:/OCR/idCard1.JPG";
        String idCard2 = "E:/OCR/idCard2.JPG";
        String mp = "E:/OCR/mp.JPG";
        JSONObject res = client.idcard(idCard1, idCardSide, options);
        System.out.println(res.toString(2));

        // 参数为本地图片二进制数组
//        byte[] file = readImageFile(image);
//        res = client.idcard(file, idCardSide, options);
//        System.out.println(res.toString(2));

    }
}

