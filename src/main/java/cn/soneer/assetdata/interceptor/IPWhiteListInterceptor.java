package cn.soneer.assetdata.interceptor;

import cn.soneer.assetdata.commons.enums.ResultEnum;
import cn.soneer.assetdata.exception.ResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * @Author Soneer
 * @Date 2021/2/5 15:45
 * @Version 1.0
 */
@Component
@Slf4j
public class IPWhiteListInterceptor extends HandlerInterceptorAdapter {
    @Value("${ip.whilteList}")
    private String ipWhilteList;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] ips = ipWhilteList.split(",");
        String ip = getIpAddress(request);
        log.info("白名单IP列表：{}", Arrays.toString(ips));
        //判断请求ip地址 是否在白名单呢
        if(checkWhileIP(ip,ips)) {
            return super.preHandle(request, response, handler);
        }
        throw new ResultException(ResultEnum.IP_ILLEGALITY);
    }

    public boolean checkWhileIP(String ip,String[] ips){
        boolean f = false;
        for (String s : ips) {
            if(ip.equals(s)){
                f = true;
                break;
            }
        }
        return f;
    }

    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        log.info("原始IP:{}",ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.info("访问ip====:{}",ip);
            if("127.0.0.1".equals(ip)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if(ip!=null && ip.length()>15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        log.info("访问ip:{}",ip);
        return ip;
    }
}
