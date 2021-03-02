package cn.soneer.assetdata.controller;

import cn.soneer.assetdata.commons.dto.RespData;
import cn.soneer.assetdata.entity.*;
import cn.soneer.assetdata.service.IAreaService;
import cn.soneer.assetdata.service.ICityService;
import cn.soneer.assetdata.service.IProvinceService;
import cn.soneer.assetdata.service.ITownService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Soneer
 * @Date 2021/2/5 14:29
 * @Version 1.0
 */
@RestController
@RequestMapping("district")
public class DistrictController {

    private IProvinceService provinceService;
    private ICityService cityService;
    private IAreaService areaService;
    private ITownService townService;

    public DistrictController(IProvinceService provinceService, ICityService cityService
    ,IAreaService areaService,ITownService townService) {
        this.provinceService = provinceService;
        this.cityService = cityService;
        this.areaService = areaService;
        this.townService = townService;
    }

    @RequestMapping("/province/list")
    //@RequestAstrict()
    public RespData getProvinceList(){
        List<Province> list = provinceService.provinceList();
        return RespData.success(list);
    }

    @RequestMapping("/province/getByProvince")
    //@RequestAstrict()
    public RespData getByProvince(String province){
        String[] codes = new String[]{"11","12","31","50"};
        if(Arrays.asList(codes).contains(province)){
            Municipality municipality = provinceService.getAreaByProvince(province);
            return RespData.success(municipality);
        }
        Province list = provinceService.getByProvince(province);
        return RespData.success(list);
    }

    @GetMapping("/city")
    public RespData getCityByProvince(String province){
        List<City> list = cityService.getCityByProvince(province);
        return RespData.success(list);
    }

    @GetMapping("/area")
    public RespData getAreaByCity(String city){
        List<Area> list = areaService.list();
        return RespData.success(list);
    }

    @GetMapping("/town")
    public RespData getTownByArea(String province,String city,String area){
        List<Town> list = townService.getTownByProvinceAndCityAndArea(province,city,area);
        return RespData.success(list);
    }

    @GetMapping("/listAll")
    public RespData getProvinceListAll(){
        List<Province> list = provinceService.listAll();
        return RespData.success(list);
    }

    @GetMapping("/cityListAll")
    public RespData getcityListAll(){
        List<City> list = cityService.listAll();
        return RespData.success(list);
    }

}
