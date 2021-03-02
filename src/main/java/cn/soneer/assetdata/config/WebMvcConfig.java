package cn.soneer.assetdata.config;

import cn.soneer.assetdata.interceptor.IPWhiteListInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Soneer
 * @Date 2021/2/5 15:49
 * @Version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${ip.whilteListEnable}")
    private boolean ipWhilteListEnable;
    @Autowired
    private IPWhiteListInterceptor ipWhiteListInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(ipWhilteListEnable){
            registry.addInterceptor(ipWhiteListInterceptor).addPathPatterns("/**");
        }
    }

}
