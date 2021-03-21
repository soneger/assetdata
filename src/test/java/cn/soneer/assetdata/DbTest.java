package cn.soneer.assetdata;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.soneer.assetdata.mapper.PoemsTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


/**
 * project：assetdata
 * class：DbTest
 * describe：TODO
 * time：2021/3/20 17:08
 * author：soneer
 * version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DbTest {

    @Autowired
    private PoemsTypeMapper poemsTypeMapper;
    @Test
    public void sorts() throws SQLException {
        List<Entity> list = Db.use().query("select id from ancient_poems where id > ?",279);
        log.info("size == {}",list.size());
        for(int i=0;i<list.size();i++){
            Integer id = list.get(i).getInt("id");
            log.info("id == {}",id);
            List<Integer> ids = poemsTypeMapper.ids(id);
            String s = ids.toString();
            log.info("poems_type_id == {}", s);
            Db.use().update(
                    Entity.create().set("category", s), //修改的数据
                    Entity.create("ancient_poems").set("id", id) //where条件
            );
        }
    }
}
