package cn.soneer.assetdata;

import cn.soneer.assetdata.entity.Province;
import cn.soneer.assetdata.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test() {
        String key = "Provice:1";
        Province p = new Province();
        p.setId(1L);
        p.setProvince("chongqing");
        redisUtils.set(key,p);
        Province Provice = (Province) redisUtils.get(key);
        log.info("Provice: "+Provice.toString());
    }
}