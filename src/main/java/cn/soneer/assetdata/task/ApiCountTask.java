package cn.soneer.assetdata.task;

import cn.soneer.assetdata.entity.ApiRecord;
import cn.soneer.assetdata.mapper.ApiRecordMapper;
import cn.soneer.assetdata.redis.utils.RedisUtils;
import cn.soneer.assetdata.service.ApiTaskService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * project：assetdata
 * class：Api
 * describe：TODO
 * time：2021/3/21 12:03
 * author：soneer
 * version:1.0
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务,可以加在启动类上
@Slf4j
public class ApiCountTask{
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ApiRecordMapper apiRecordMapper;
    //3.添加定时任务
    @Scheduled(cron="0 0/10 * * * *")
    @Async("apiTaskExecutor")
//    @Scheduled(fixedRate = 10000)
    public void apiRecordTasks() {
        try {
            Set keys = redisUtils.getKeys("*");
            if (keys.size()>0){
                log.info("keys : {}",keys.toString());
                Iterator iterator = keys.iterator();
                while (iterator.hasNext()){
                    String next = (String)iterator.next();
                    Object o = redisUtils.get(next);
                    if(o instanceof ApiRecord){
                        ApiRecord a = (ApiRecord) o;
                        log.info("api redis result : {}", JSON.toJSON(a));
                        int b = apiRecordMapper.insert(a);
                        if (b>0){
                            redisUtils.del(next);
                        }
                    }
                }
            }
        }catch (Exception e){
            log.info("定时读取缓存出现错误！请检查。。。");
        }

    }
}
