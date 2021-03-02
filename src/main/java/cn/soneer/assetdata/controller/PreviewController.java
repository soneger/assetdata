package cn.soneer.assetdata.controller;

import cn.soneer.assetdata.entity.Province;
import cn.soneer.assetdata.service.IProvinceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/7 10:23
 * @Version 1.0
 */
@RequestMapping("preview")
@Controller
@Slf4j
public class PreviewController {
    @Autowired
    private IProvinceService provinceService;

    @RequestMapping("/district")
    public String districtPreview(Model model){
        return "page/district";
    }
}
