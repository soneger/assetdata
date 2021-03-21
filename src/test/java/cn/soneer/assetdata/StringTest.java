package cn.soneer.assetdata;

import cn.soneer.assetdata.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.UUID;

/**
 * project：assetdata
 * class：StringTest
 * describe：TODO
 * time：2021/3/20 23:21
 * author：soneer
 * version:1.0
 */
@Slf4j
public class StringTest {
    @Test
    public void uuid(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        log.info("uuid  === >{}",uuid);
        String randomSalt = EncryptUtil.getRandomSalt(32);
        log.info("randomSalt  === >{}",randomSalt);
        String appID = "c4e7ef4ee1844a5595457fc1365a7f87";
        String secretKey = "BikllnW8e17EAP4q3RT2tZP5Oq8Z6Dzg";
        String finalStr = "e70cecd53acbf10d693233e1328d0964";
        String md5Str = EncryptUtil.getMD5Str(appID, secretKey);
        log.info("md5Str  === >{}",md5Str);
        if (finalStr.equals(md5Str)){
            log.info("校验成功  === >{}",md5Str);
        }
    }
}
