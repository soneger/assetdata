package cn.soneer.assetdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.soneer.assetdata.mapper")
public class AssetdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetdataApplication.class, args);
    }

}
