package cn.soneer.assetdata.service;

import cn.soneer.assetdata.entity.AncientPoems;
import cn.soneer.assetdata.entity.PoemsType;

import java.util.List;

/**
 * project：assetdata
 * class：IAncientPoemsService
 * describe：TODO
 * time：2021/3/10 22:22
 * author：soneer
 * version:1.0
 */

public interface IAncientPoemsService {

    /**
     *
     * @return
     */
    List<PoemsType> poemsTypeList();
    /**
     * [新增]
     * @author Soneer
     * @date 2021/03/10
     **/
    int insert(AncientPoems ancientPoems);

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
    int update(AncientPoems ancientPoems);

    /**
     * [查询] 根据主键 id 查询
     * @author Soneer
     * @date 2021/03/10
     **/
    AncientPoems getById(Integer id);

    /**
     *
     * @param typeId
     * @param id
     * @return
     */
    AncientPoems getByTypeIdAndId(Integer typeId,Integer id);

    /**
     * [查询] 分页查询
     * @author Soneer
     * @date 2021/03/10
     **/
    List<AncientPoems> pageList(int page, int size);

    /**
     * 根据类别获取古诗文
     * @param typeId
     * @return
     */
    List<AncientPoems> listByType(Integer typeId);
}
