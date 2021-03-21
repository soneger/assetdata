package cn.soneer.assetdata;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PaChong {
    @SneakyThrows
    @Test
    public void tangShi() throws IOException {
        //唐诗爬取
        String url = "https://so.gushiwen.cn/gushi/tangshi.aspx";
        //获取编辑推荐页
        Document document = null;
        try {
            document = Jsoup.connect(url).userAgent("Mozilla").get();
//            log.info("start : {}",document.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements main3 = document.getElementsByClass("main3");
        final Elements left = main3.get(0).getElementsByClass("left");
        final String type = left.get(0).getElementsByClass("title").tagName("h1").text();
        log.info("type:{}",type);
        final Elements typecont = left.get(0).getElementsByClass("sons").get(0).getElementsByClass("typecont");
        log.info("typecont=======:{}",typecont.size());
        Document details = null;
        for (Element m:typecont ) {
            final String bookMl = m.getElementsByClass("bookMl").text();
            log.info("Classify:{}",bookMl);
//            if(!"五言绝句".equals(bookMl)) break;
            final Elements span = m.getElementsByTag("span");
            for (Element sp: span) {
                String titleUrl = sp.select("a").attr("abs:href");
                System.out.println(titleUrl);
                Thread.sleep(1000);
                details = Jsoup.connect(titleUrl).userAgent("Mozilla").get();
                Elements details_main3 = details.getElementsByClass("main3");
                final Element left1 = details_main3.get(0).getElementsByClass("left").get(0);
//                log.info("left1 size:{}",details_main3.get(0).getElementsByClass("sonspic").size());
                Element  sonsyuanwen =  left1.getElementById("sonsyuanwen").getElementsByClass("cont").get(0);
                String title =  sonsyuanwen.getElementsByTag("h1").text();
                log.info("title:{}",title);
                final Elements a = sonsyuanwen.getElementsByTag("p").get(0).getElementsByTag("a");
//            System.out.println(a.get(0).text());
                final String author = a.get(0).text();
                log.info("author:{}",author);
                final String era = a.get(1).text();
                log.info("era:{}",era.substring(1,3));
                final Elements contson = sonsyuanwen.getElementsByClass("contson");
                final String text = contson.text();
                log.info("text:{}",text);
                final String html = contson.html();
                log.info("html:{}",html);

                left1.getElementById("sonsyuanwen").remove();
                final Elements contyishang = left1.getElementsByClass("sons");
                log.info("contyishang size:{}",contyishang.size());
                String yiwen = "";
                String zhushi = "";
                String background = "";
                for (Element c: contyishang) {
                    final String h2 = c.getElementsByTag("div").get(0).getElementsByTag("h2").text();
                    log.info("h2===={}",h2);
                    if("译文及注释".equals(h2)){
                        final Elements contyishang_p = c.getElementsByTag("p");
                        yiwen = contyishang_p.get(0).text().replaceAll("译文","");
                        if(contyishang_p.size()>1){
                            zhushi = contyishang_p.get(1).text().replaceAll("注释","");
                        }

                        log.info("yiwen:{}",yiwen);
                        log.info("zhushi:{}",zhushi);
                    }
                    if("创作背景".equals(h2)){
                        background = c.getElementsByClass("contyishang").get(0).getElementsByTag("p").text();
                        log.info("background:{}",background);
                    }
//                  left1.getElementsByClass("shangxi787")
                }
//                Element sonspic = left1.getElementsByClass("sonspic").get(0);
                String author_information = "";
                if(left1.getElementsByClass("sonspic").size()>0){
                    Element sonspic_cont = left1.getElementsByClass("sonspic").get(0).getElementsByClass("cont").get(0);
//                final String author_head = sonspic_cont.getElementsByClass("divimg").get(0).getElementsByTag("a").text();
                    if(left1.getElementsByClass("sonspic").get(0).getElementsByClass("cont").size()>2){
                    final String author_head  = left1.getElementsByClass("sonspic").get(0).getElementsByClass("cont").get(0)
                            .getElementsByClass("divimg").get(0).getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("src");
                    log.info("author_head:{}",author_head);
                    sonspic_cont.getElementsByTag("a").remove();
                     author_information = sonspic_cont.getElementsByTag("p").get(1).text();
                    log.info("author_information:{}",author_information);
                    }
                }
                final List<Entity> list = Db.use().findAll(Entity.create("study_data").set("title", title));
                if(list.size()>0){
                    final List<Entity> typeList = Db.use().findAll(Entity.create("study_type").set("type", bookMl.trim()));
                    Db.use().insert(
                            Entity.create("study_type_data")
                                    .set("study_type_id", typeList.get(0).getRowId())
                                    .set("study_data_id", list.get(0).getRowId()));

                }
                log.info("查询是否已经存在数据，数据条数：{}",list.size());
                if(list.size()==0){
                    try {
                        Thread.sleep(500);
                        Db.use().insert(
                                Entity.create("study_data")
                                        .set("title", title)
                                        .set("classify", bookMl)
                                        .set("era", era.substring(1,3))
                                        .set("content", text)
                                        .set("content_html", html)
                                        .set("translation", yiwen)
                                        .set("notes", zhushi)
                                        .set("author", author)
                                        .set("background", background)
                                        .set("author_information", author_information)
                                        .set("type", type)
                                        .set("create_time", new Date())
                                        .set("author_head", "")

                        );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
    }

    @Test
    public void detailsTest(){
        String url ="https://so.gushiwen.cn/shiwenv_4508caf3b53b.aspx";
//        getDetails(url);
    }



    public void saveDb(String title,String bookMl,String
            era,String text,String html,String yiwen,String zhushi,String author,String background,String author_information,String type){
        try {
            Thread.sleep(500);
            Db.use().insert(
                    Entity.create("study_data")
                            .set("title", title)
                            .set("classify", bookMl)
                            .set("era", era.substring(1,3))
                            .set("content", text)
                            .set("content_html", html)
                            .set("translation", yiwen)
                            .set("notes", zhushi)
                            .set("author", author)
                            .set("background", background)
                            .set("author_information", author_information)
                            .set("type", type)
                            .set("create_time", new Date())
                            .set("author_head", "")

            );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void seleniumTest() throws InterruptedException, SQLException, IOException {
        boolean f = false;
        System.setProperty("webdriver.gecko.driver", "C:/driver/geckodriver.exe");
        WebDriver  driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.get("https://so.gushiwen.cn/gushi/tangshi.aspx");
        driver.get("https://so.gushiwen.org/gushi/gaozhong.aspx");
        WebElement left = driver.findElement(By.className("main3"));
        final List<WebElement> elements = left.findElement(By.className("left")).findElements(By.className("sons"));
        final List<WebElement> bodyTitle = left.findElement(By.className("left")).findElements(By.className("titleleft"));
        log.info("elements size ==== {}",elements.size());
        String bookMl = "";
//        String typeTitle = bodyTitle.get(0).getText();
//        log.info("typeTitle === {}",typeTitle);
//        for (WebElement we:elements) {
            final List<WebElement> typecont = elements.get(0).findElements(By.className("typecont"));
            log.info("typecont size :{}",typecont.size());
            for (WebElement ty:typecont) {
                bookMl = ty.findElement(By.tagName("strong")).getText();

                final List<WebElement> span = ty.findElements(By.tagName("span"));
                log.info("span size ==== {}",span.size());
                for (WebElement sp:span) {
                    String url = sp.findElement(By.tagName("a")).getAttribute("href");
                    log.info("url:{}",url);

                    if("https://so.gushiwen.org/shiwenv_73887e787a4a.aspx".equals(url)){
                        f = true;
                    }
                    if(f){
                        WebDriver  details = new FirefoxDriver();
                        if(!url.isEmpty()&&url!=null){
                            details.get(url);
                        }
                        //译文及注释
                        final List<WebElement> contyishang = details.findElements(By.className("contyishang"));
                        if(contyishang.size()>=3) {
                            //译文
                            if (contyishang.get(0) != null) {
                                List<WebElement> div = contyishang.get(0).findElements(By.tagName("div"));
                                if (div.size() == 3) {
                                    log.info("dianjie le ");
                                    div.get(2).findElement(By.tagName("a")).click();
                                }
                            }
                            //注释
                            if (contyishang.get(1) != null) {
                                List<WebElement> div = contyishang.get(1).findElements(By.tagName("div"));
                                if (div.size() == 3) {
                                    div.get(2).findElement(By.tagName("a")).click();
                                }
                            }
                        }
                        final String pageSource = details.getPageSource();
                        final Document parse = Jsoup.parse(pageSource);
                        Elements details_main3 = parse.getElementsByClass("main3");
                        final Element left1 = details_main3.get(0).getElementsByClass("left").get(0);
                        Element  sonsyuanwen =  left1.getElementById("sonsyuanwen").getElementsByClass("cont").get(0);
                        String title =  sonsyuanwen.getElementsByTag("h1").text();
                        log.info("bookMl:{}",bookMl);
                        log.info("title:{}",title);
                        final Elements a = sonsyuanwen.getElementsByTag("p").get(0).getElementsByTag("a");
                        String author = a.get(0).text();
                        log.info("author:{}",author);
                        String era = a.get(1).text().substring(1,3);
                        log.info("era:{}",era);
                        Elements contson = sonsyuanwen.getElementsByClass("contson");
                        String text = contson.text();
                        log.info("text:{}",text);
                        String html = contson.html();
                        log.info("html:{}",html);

                        left1.getElementById("sonsyuanwen").remove();
                        Elements sons = left1.getElementsByClass("sons");
                        log.info("contyishang size:{}",contyishang.size());
                        String yiwen = "";
                        String zhushi = "";
                        String analyse = "";
                        String background = "";
                        String authorHead = "";
                        for (Element c: sons) {
                            final String h2 = c.getElementsByTag("div").get(0).getElementsByTag("h2").text();
                            log.info("h2===={}",h2);
                            if("译文及注释".equals(h2)){
                                final Elements contyishang_p = c.getElementsByTag("p");
                                yiwen = contyishang_p.get(0).text().replaceAll("译文","");
                                if(contyishang_p.size()>1){
                                    zhushi = contyishang_p.get(1).text().replaceAll("注释","");
                                }

                                log.info("yiwen:{}",yiwen);
                                log.info("zhushi:{}",zhushi);
                            }
                            if("创作背景".equals(h2)){
                                background = c.getElementsByClass("contyishang").get(0).getElementsByTag("p").text();
                                log.info("background:{}",background);
                            }

                            if("赏析".equals(h2)){
                                analyse = c.getElementsByClass("contyishang").get(0).getElementsByTag("p").text();
                                log.info("analyse:{}",analyse);
                            }
                        }
                        String author_information = "";
                        log.info("left1:==={}",left1.getElementsByClass("sonspic").size());

                        if(left1.getElementsByClass("sonspic").size()>0){
                            Elements sonspic_cont = left1.getElementsByClass("sonspic").get(0).getElementsByClass("cont");
                            Elements author_info = left1.getElementsByClass("sonspic").get(0).getElementsByClass("cont").get(0).getElementsByTag("p");
                            Elements div_img = sonspic_cont.get(0).getElementsByTag("div");
                            log.info("sonspic_cont:{}",div_img.size());
                            if(div_img.size()>1){
                                String authorHeadUrl = div_img.get(0).getElementsByTag("a")
                                        .get(0).getElementsByTag("img").get(0).attr("src");
                                authorHead = imgNameTest(authorHeadUrl);
                                log.info("author_head:{}",authorHead);
                                downLoadAuthorImg(authorHeadUrl);
                            }
                            if(author_info.size()>1){
                                sonspic_cont.get(0).getElementsByTag("a").remove();
                                author_information = author_info.get(1).text();
                                log.info("author_information:{}",author_information);
                            }
                        }
                        String final_analyse = analyse.replaceAll("▲", "");
                        log.info("final_analyse:{}",final_analyse);
                        if(!authorHead.isEmpty()){
                            authorHead = "/authorAvatar/"+ authorHead;
                        }
                        details.close();
                        Thread.sleep(500);
                        final List<Entity> list = Db.use().findAll(Entity.create("study_data").set("title", title));
                        final List<Entity> typeList = Db.use().findAll(Entity.create("study_type").set("type", bookMl.trim()));
                        log.info("查询是否已经存在数据，数据条数：{}",list.size());
                        if(list.size()>0){
                            Db.use().insert(
                                    Entity.create("study_type_data")
                                            .set("study_type_id", typeList.get(0).getRowId())
                                            .set("study_data_id", list.get(0).getRowId()));
                            log.info("系统存在此条数据，保存分类信息。。。。。");
                        }else {

                            log.info("系统无此数据，进行保存。。。。。");
                            final Long aLong = Db.use().insertForGeneratedKey(
                                    Entity.create("study_data")
                                            .set("title", title)
                                            .set("classify", bookMl)
                                            .set("era", era)
                                            .set("content", text)
                                            .set("content_html", html)
                                            .set("translation", yiwen)
                                            .set("notes", zhushi)
                                            .set("analyse", final_analyse)
                                            .set("author", author)
                                            .set("background", background)
                                            .set("author_information", author_information)
                                            .set("type", "高中古诗文")
                                            .set("update_time", new Date())
                                            .set("create_time", new Date())
                                            .set("author_head", authorHead));
//                            if(typeList.get(0).getRowId())typeTitle
                        }

                    }
                }
//            }
            }
        driver.close();
    }

    public Long details(String url,String type) throws InterruptedException, IOException, SQLException {
        WebDriver  details = new FirefoxDriver();
        if(!url.isEmpty()&&url!=null){
            details.get(url);
        }
        //译文及注释
        final List<WebElement> contyishang = details.findElements(By.className("contyishang"));
        if(contyishang.size()>=3) {
            //译文
            if (contyishang.get(0) != null) {
                List<WebElement> div = contyishang.get(0).findElements(By.tagName("div"));
                if (div.size() == 3) {
                    log.info("dianjie le ");
                    div.get(2).findElement(By.tagName("a")).click();
                }
            }
            //注释
            if (contyishang.get(1) != null) {
                List<WebElement> div = contyishang.get(1).findElements(By.tagName("div"));
                if (div.size() == 3) {
                    div.get(2).findElement(By.tagName("a")).click();
                }
            }
        }
        final String pageSource = details.getPageSource();
        final Document parse = Jsoup.parse(pageSource);
        Elements details_main3 = parse.getElementsByClass("main3");
        final Element left1 = details_main3.get(0).getElementsByClass("left").get(0);
        Element  sonsyuanwen =  left1.getElementById("sonsyuanwen").getElementsByClass("cont").get(0);
        String title =  sonsyuanwen.getElementsByTag("h1").text();
        log.info("bookMl:{}",type);
        log.info("title:{}",title);
        final Elements a = sonsyuanwen.getElementsByTag("p").get(0).getElementsByTag("a");
        String author = a.get(0).text();
        log.info("author:{}",author);
        String era = a.get(1).text().substring(1,3);
        log.info("era:{}",era);
        Elements contson = sonsyuanwen.getElementsByClass("contson");
        String text = contson.text();
        log.info("text:{}",text);
        String html = contson.html();
        log.info("html:{}",html);

        left1.getElementById("sonsyuanwen").remove();
        Elements sons = left1.getElementsByClass("sons");
        log.info("contyishang size:{}",contyishang.size());
        String yiwen = "";
        String zhushi = "";
        String analyse = "";
        String background = "";
        String authorHead = "";
        for (Element c: sons) {
            final String h2 = c.getElementsByTag("div").get(0).getElementsByTag("h2").text();
            log.info("h2===={}",h2);
            if("译文及注释".equals(h2)){
                final Elements contyishang_p = c.getElementsByTag("p");
                yiwen = contyishang_p.get(0).text().replaceAll("译文","");
                if(contyishang_p.size()>1){
                    zhushi = contyishang_p.get(1).text().replaceAll("注释","");
                }

                log.info("yiwen:{}",yiwen);
                log.info("zhushi:{}",zhushi);
            }
            if("创作背景".equals(h2)){
                background = c.getElementsByClass("contyishang").get(0).getElementsByTag("p").text();
                log.info("background:{}",background);
            }

            if("赏析".equals(h2)){
                analyse = c.getElementsByClass("contyishang").get(0).getElementsByTag("p").text();
                log.info("analyse:{}",analyse);
            }
        }
        String author_information = "";
        log.info("left1:==={}",left1.getElementsByClass("sonspic").size());

        if(left1.getElementsByClass("sonspic").size()>0){
            Elements sonspic_cont = left1.getElementsByClass("sonspic").get(0).getElementsByClass("cont");
            Elements author_info = left1.getElementsByClass("sonspic").get(0).getElementsByClass("cont").get(0).getElementsByTag("p");
            Elements div_img = sonspic_cont.get(0).getElementsByTag("div");
            log.info("sonspic_cont:{}",div_img.size());
            if(div_img.size()>1){
                String authorHeadUrl = div_img.get(0).getElementsByTag("a")
                        .get(0).getElementsByTag("img").get(0).attr("src");
                authorHead = imgNameTest(authorHeadUrl);
                log.info("author_head:{}",authorHead);
                downLoadAuthorImg(authorHeadUrl);
            }
            if(author_info.size()>1){
                sonspic_cont.get(0).getElementsByTag("a").remove();
                author_information = author_info.get(1).text();
                log.info("author_information:{}",author_information);
            }
        }
        String final_analyse = analyse.replaceAll("▲", "");
        log.info("final_analyse:{}",final_analyse);
        if(!authorHead.isEmpty()){
            authorHead = "/authorAvatar/"+ authorHead;
        }
        details.close();
        Thread.sleep(500);
        Long id = Db.use().insertForGeneratedKey(
                Entity.create("study_data")
                        .set("title", title)
                        .set("classify", type)
                        .set("era", era)
                        .set("content", text)
                        .set("content_html", html)
                        .set("translation", yiwen)
                        .set("notes", zhushi)
                        .set("analyse", final_analyse)
                        .set("author", author)
                        .set("background", background)
                        .set("author_information", author_information)
                        .set("type", type)
                        .set("update_time", new Date())
                        .set("create_time", new Date())
                        .set("author_head", authorHead));

        return id;
    }

    @Test
    public void merge() throws SQLException, IOException, InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C:/driver/geckodriver.exe");
        WebDriver  driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<Entity> typeList = Db.use().findAll(Entity.create("study_type").set("id","> 71"));
        log.info("db type size :{}",typeList.size());
        Integer type_id = 0;
        boolean flag = false;
        boolean f = false;
        for (Entity en:typeList) {
            String type = en.getStr("type");
            log.info("type =={}",type);
            String url = en.getStr("describe");
            log.info("db url =={}",url);
            type_id = en.getInt("id");
            log.info("db type id is === {}",type_id);
//            if("送别".equals(type)){
//                flag = true;
//            }
            if(true){
                Thread.sleep(1000);
                driver.get(url);
                WebElement left = driver.findElement(By.className("main3"));
                List<WebElement> span = left.findElement(By.className("left")).findElements(By.className("typecont")).get(0).findElements(By.tagName("span"));
                log.info("span size :{}",span.size());
                for (WebElement ty: span) {
//                    String title = ty.getText();
//                    log.info("title text :{}",title);
                    String detailsUrl = ty.findElement(By.tagName("a")).getAttribute("href");
                    log.info("url  :{}",detailsUrl);

//                    if("https://so.gushiwen.cn/shiwenv_648aee77b2ec.aspx".equals(detailsUrl)){
//                        f =true;
//                    }
                    if(true){
                        Document  document = Jsoup.connect(detailsUrl).userAgent("Mozilla").get();
                        Elements details_main3 = document.getElementsByClass("main3");
                        final Element left1 = details_main3.get(0).getElementsByClass("left").get(0);
                        Element  sonsyuanwen =  left1.getElementById("sonsyuanwen").getElementsByClass("cont").get(0);
                        String title =  sonsyuanwen.getElementsByTag("h1").text();
                        log.info("title text :{}",title);
                        List<Entity> list = Db.use().findAll(Entity.create("study_data").set("title", title));
                        log.info("查询是否已经存在数据，数据条数：{}",list.size());
                        //数据库无此数据
                        if (list.size()==0){
                            Long data_id = details(detailsUrl,type);
                            Db.use().insert(
                                    Entity.create("stuay_type_data")
                                            .set("study_type_id", type_id)
                                            .set("study_data_id", data_id)
                            );
                        }else{//存在数据
                            log.info("查询是否已经存在数据，数据条数：{}",list.size());
                            Integer db_dataID = list.get(0).getInt("id");
                            Db.use().insert(
                                    Entity.create("stuay_type_data")
                                            .set("study_type_id", type_id)
                                            .set("study_data_id", db_dataID)
                            );
                        }
                    }

                }
            }

        }


    }
    @Test
    public void dbTest() throws SQLException {
        final List<Entity> list = Db.use().findAll(Entity.create("study_data").set("title", "点绛唇·感兴"));
        final List<Entity> typeList = Db.use().findAll(Entity.create("study_type").set("type", "写风 "));
        log.info("查询是否已经存在数据，数据条数：{}",list.get(0).getStr("id"));
        log.info("查询类型数据，数据条数：{}",typeList.get(0).getInt("id"));
//        if(list.size()>0){
//            Db.use().insert(
//                    Entity.create("study_type_data")
//                            .set("study_type_id", typeList.get(0).getRowId())
//                            .set("study_data_id", list.get(0).getRowId()));
//            log.info("系统存在此条数据，保存分类信息。。。。。");
//        }
    }

    @Test
    public void label() throws SQLException {
        System.setProperty("webdriver.gecko.driver", "C:/driver/geckodriver.exe");
        WebDriver  driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.gushiwen.cn");

        List<WebElement> cont = driver.findElement(By.className("main3")).findElement(By.className("right")).findElement(By.className("sons")).findElements(By.className("cont"));
        final List<WebElement> a = cont.get(0).findElements(By.tagName("a"));
        log.info("cont size :{}",a.size());
        for (int i=0;i<a.size()-1;i++) {
            String type = a.get(i).getText();
            log.info("label :{}",type);
            String url = a.get(i).getAttribute("href");
            log.info("url :{}",url);
            Db.use().insert(
                    Entity.create("study_type")
                            .set("type", type)
                            .set("describe", url)
                            .set("update_time", new Date())
                            .set("create_time", new Date()));
        }
        driver.close();
    }

    @Test
    public void sjzl() throws SQLException {
        for (int i = 1174;i<962;i++){
            Db.use().insert(
                    Entity.create("poems_ref")
                            .set("poems_type_id", 6)
                            .set("poems_data_id", i)
            );
        }
    }



//    @Test
    public void downLoadAuthorImg(String img_url) throws IOException {
//        String img_url = "https://song.gushiwen.cn/authorImg/yuanzhen.jpg";
        String path = "H:/study_data/author_head/";
        URL url = new URL(img_url);
        URLConnection uc = url.openConnection();
        InputStream inputStream = uc.getInputStream();
        String imgName = path+imgNameTest(img_url);
        FileOutputStream out = new FileOutputStream(imgName);
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }
        inputStream.close();
    }

//    @Test
    public String imgNameTest(String img_url){
//        String img_url = "https://song.gushiwen.cn/authorImg/yuanzhen.jpg";
        log.info("imgNameURl==={}", img_url);
        String[] imgName = img_url.split("/");
        final String s = imgName[imgName.length - 1];
        return s;
    }

    @Test
    public void diyifanwen() throws IOException, InterruptedException {
        //唐诗爬取
        String url = "https://www.diyifanwen.com/sicijianshang/tangshisanbaishou/";
        //获取编辑推荐页
        Document document = null;
        try {
            document = Jsoup.connect(url).userAgent("Mozilla").get();
//            log.info("start : {}",document.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Element artLeft = document.getElementById("ArtLeft");
        final Elements indexDl = artLeft.getElementsByClass("IndexDl");
        String classify = "";
        for (Element e:indexDl ) {
            final Elements dt = e.getElementsByTag("dt");
            for (Element d:dt) {
                classify = d.text();
                log.info("classify:{}",classify);
            }
            final Elements dd = e.getElementsByTag("dd");
            for (Element ddlist:dd) {
                final String detailsUrl = ddlist.tagName("a").select("a").attr("abs:href");
//                log.info("detailsUrl : {}",detailsUrl);
                Thread.sleep(1000);
                final Document details = Jsoup.connect(detailsUrl).userAgent("Mozilla").get();
                final Elements p = details.getElementById("ArtContent").getElementsByTag("p");
//                final String content = p.get(0).text();
//                log.info("content : {}",content);
//                final String notes = p.get(1).text();
////                final String notes = p.get(2).text();
//                final String analyse = p.get(3).text();
//                log.info("analyse : {}",analyse);
                for (Element ep:p ) {
                    final String c = ep.text();
//                    log.info("content : {}",c);
                    final String[] split = c.split("：");
                    if(split[0].equals("【注解】")){
                        log.info("notes : {}",c);
                    }
                    if(split[0].equals("【评析】")){
                        log.info("analyse : {}",c);
                    }
                }
//                final String body = content.substring(0, content.indexOf("注解"));
//                log.info("body : {}",body);

            }
        }
    }

}
