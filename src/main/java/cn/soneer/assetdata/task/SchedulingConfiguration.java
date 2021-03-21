//package cn.soneer.assetdata.task;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//
///**
// * project：assetdata
// * class：SchedulingConfiguration
// * describe：TODO
// * time：2021/3/21 12:25
// * author：soneer
// * version:1.0
// */
//
//@Configuration
//@EnableScheduling
//public class SchedulingConfiguration {
//    /**
//     *  默认只有1个，时间太长会阻塞
//     * @return
//     */
//    @Bean(destroyMethod = "shutdown")
//    public ThreadPoolTaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setPoolSize(10);
//        // 设置线程名开头
//        scheduler.setThreadNamePrefix("apiTaskScheduler-");
//        scheduler.setAwaitTerminationSeconds(60);
//        scheduler.setWaitForTasksToCompleteOnShutdown(true);
//        return scheduler;
//    }
//}
//
