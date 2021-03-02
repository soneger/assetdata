package cn.soneer.assetdata.api;

import cn.soneer.assetdata.commons.dto.RespData;
import cn.soneer.assetdata.videoparser.VideoParse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Soneer
 * @Date 2021/3/2 15:15
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("api/video/")
public class VideoParseController {

    @RequestMapping("/parse")
    public RespData expressQuery(String url){
        log.info("视频地址：{}，",url);
        String videoUrl = VideoParse.videoParse(url);
        if(videoUrl!=null&&!videoUrl.isEmpty()){
            log.info("返回地址：{}，",url);
            return RespData.success("解析成功",videoUrl);
        }
        return RespData.error(500,"解析失败，目前暂支持抖音、快手、皮皮虾解析");
    }
}
