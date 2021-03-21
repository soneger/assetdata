package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.AncientPoems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface AncientPoemsMapper {

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
    AncientPoems getByTypeIdAndId(@Param("typeId") Integer typeId,@Param("id") Integer id);

    /**
     * [查询] 分页查询
     * @author Soneer
     * @date 2021/03/10
     **/
    List<AncientPoems> pageList(int offset, int pagesize);

    /**
     * 根据类别获取古诗文
     * @param typeId
     * @return
     */
    List<AncientPoems> listByType(Integer typeId);


}
