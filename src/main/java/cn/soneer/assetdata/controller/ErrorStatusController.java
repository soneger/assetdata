package cn.soneer.assetdata.controller;

import cn.soneer.assetdata.commons.dto.RespData;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Soneer
 * @Date 2021/2/5 17:31
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
public class ErrorStatusController implements ErrorController {

    @RequestMapping("/error")
    public RespData errorResp(HttpServletRequest request, HttpServletResponse response){
        Object attribute = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return RespData.error(Integer.valueOf(attribute.toString()),"请求错误!");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
