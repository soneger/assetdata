package cn.soneer.assetdata.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author:soneer
 * @date: 2021/2/23
 * @time: 19:24
 */
@Slf4j
public class DownloadUtil {
    /**
     * 下载文件到本地
     * @param httpUrl
     * @param saveFile
     */
    public static void Download(String httpUrl, String saveFile) {
        int byteRead;
        try {
            URL url = new URL(httpUrl);
            //获取链接
            URLConnection conn = url.openConnection();
            //输入流
            InputStream inStream = conn.getInputStream();
            //封装一个保存文件的路径对象
            File fileSavePath = new File(saveFile);
            //注:如果保存文件夹不存在,那么则创建该文件夹
            File fileParent = fileSavePath.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            //写入文件
            FileOutputStream fs = new FileOutputStream(fileSavePath);
            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();
            log.info("文件保存路径:{}"+fileSavePath.getAbsolutePath());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
