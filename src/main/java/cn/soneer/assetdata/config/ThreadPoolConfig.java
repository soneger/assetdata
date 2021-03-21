package cn.soneer.assetdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * project：assetdata
 * class：ThreadPoolConfig
 * describe：TODO
 * time：2021/3/20 22:08
 * author：soneer
 * version:1.0
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    /** 核心线程数（默认线程数） */
    private static final int corePoolSize = 5;
    /** 最大线程数 */
    private static final int maxPoolSize = 10;
    /** 允许线程空闲时间（单位：默认为秒） */
    private static final int keepAliveTime = 10;
    /** 缓冲队列大小 */
    private static final int queueCapacity = 100;
    /** 线程池名前缀 */
    private static final String threadNamePrefix = "Api-Service-";

    @Bean(name = "apiTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveTime);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        return threadPoolTaskExecutor;
    }
}
