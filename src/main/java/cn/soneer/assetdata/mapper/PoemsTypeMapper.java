package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.PoemsType;

import java.util.List;

/**
 * project：assetdata
 * class：PoemsTypeMapper
 * describe：TODO
 * time：2021/3/10 22:13
 * author：soneer
 * version:1.0
 */

public interface PoemsTypeMapper {
    /**
     * [新增]
     * @author Soneer
     * @date 2021/03/10
     **/
    int insert(PoemsType poemsType);

    /**
     * [刪除]
     * @author Soneer
     * @date 2021/03/10
     **/
    int delete(int id);

    /**
     * [更新]
     * @author Soneer
     * @date 2021/03/10
     **/
    int update(PoemsType poemsType);

    /**
     * [查询] 根据主键 id 查询
     * @author Soneer
     * @date 2021/03/10
     **/
    PoemsType getById(int id);

    /**
     *
     * @return
     */
    List<PoemsType> list();

    List<Integer> ids(int id);

}
