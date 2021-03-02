package cn.soneer.assetdata;

import cn.soneer.assetdata.utils.RegexUtil;
import cn.soneer.assetdata.videoparser.VideoParse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author:soneer
 * @date: 2021/2/23
 * @time: 20:19
 */
@Slf4j
public class VideoParseTest {

    @Test
    public void douYin(){
        String dyUrl = "重庆开往春天的轻轨你赶上了么%开往春天的列车  https://v.douyin.com/e1Lfo4j/ 复zhi佌链接，达开Dou愔搜索，直接观看視频！";
        String dyUrl1 = "%每一帧都是热爱 %禁闭 水泡 车帅不帅  %高清壁纸%60帧  https://v.douyin.com/e1asRfP/ 緮制佌lian接，打开Dou音搜索，直接观kan视频！";
        String dyApi = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
        String dyParseUrl = VideoParse.dyParseUrl(dyUrl1, dyApi);
        log.info("抖音无水印视频地址：{}",dyParseUrl);
    }

    @Test
    public void kuaiShou(){
        String ksUrl = "新作品来了，6666张多米诺纸牌搭建而成，能火吗 https://v.kuaishou.com/dwCb17 复制此消息，打开【快手】直接观看！";
        String s = VideoParse.ksParseUrl(ksUrl);
        log.info("快手无水印视频地址：{}",s);
    }

    @Test
    public void ppXia(){
        String ppxUrl = "https://h5.pipix.com/s/e15U9ad/";
        String ppxUrl1 = "https://h5.pipix.com/s/e1U7D26/";
        String ppxUrl2 = "https://h5.pipix.com/s/e1xkuEr/";
        String ppxApi = "https://is.snssdk.com/bds/item/detail/?app_name=super&aid=1319&item_id=";
        String ppxApi1 = "https://h5.pipix.com/bds/webapi/item/detail/?item_id=";
        String ppxParseUrl = VideoParse.ppxParseUrl(ppxUrl2, ppxApi);
        log.info("皮皮虾无水印视频地址：{}",ppxParseUrl);
    }

    @Test
    public void weiShi(){
        String weiShiUrl = "https://isee.weishi.qq.com/ws/app-pages/share/index.html?wxplay=1&id=6Ytqpd2Qe1Lac26Vl&spid=1533682131998375&qua=v1_and_weishi_8.10.2_588_312028000_d&chid=100081014&pkg=3670&attach=cp_reserves3_1000370011";
        String weiShiParseUrl = VideoParse.weiShiParseUrl(weiShiUrl);
        log.info("weiShiParseUrl:{}",weiShiParseUrl);
    }

    @Test
    public void videoParse(){
        String ppxUrl = "https://h5.pipix.com/s/e15U9ad/";
        String dyUrl = "重庆开往春天的轻轨你赶上了么%开往春天的列车  https://v.douyin.com/e1Lfo4j/ 复zhi佌链接，达开Dou愔搜索，直接观看視频！";
        String ksUrl = "新作品来了，6666张多米诺纸牌搭建而成，能火吗 https://v.kuaishou.com/dwCb17 复制此消息，打开【快手】直接观看！";
//        String videoParse = VedioParser.videoParse(ppxUrl);
        String videoParse = VideoParse.videoParse(dyUrl);
//        String videoParse = VedioParser.videoParse(ksUrl);
        log.info("解析后的视频地址：{}",videoParse);
    }

    @Test
    public void xiGua(){

    }

    @Test
    public void touTiao(){

    }

    @Test
    public void hostNameTest(){
        String url = "新作品来了，6666张多米诺纸牌搭建而成，能火吗 https://v.kuaishou.com/dwCb17 复制此消息，打开【快手】直接观看！";
        String ppxUrl = "https://h5.pipix.com/s/e15U9ad/";
        String filterUrl = RegexUtil.filterUrl(ppxUrl);
        String hostNameFromUrl = RegexUtil.getHostNameFromUrl(filterUrl);
        log.info("提取域名商名字：{}",hostNameFromUrl);
    }

}
