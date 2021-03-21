package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.ApiRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * project：assetdata
 * class：ApiRecordMapper
 * describe：TODO
 * time：2021/3/21 10:55
 * author：soneer
 * version:1.0
 */
@Mapper
public interface ApiRecordMapper {
    /**
     * 新增
     *
     * @author Soneer
     * @date 2021/03/21
     **/
    int insert(ApiRecord apiRecord);

    /**
     * 刪除
     * @author Soneer
     * @date 2021/03/21
     **/
    int delete(int id);

    /**
     * 更新
     * @author Soneer
     * @date 2021/03/21
     **/
    int update(ApiRecord apiRecord);

    /**
     * 查询 根据主键 id 查询
     * @author Soneer
     * @date 2021/03/21
     **/
    ApiRecord load(int id);

    /**
     * 查询 分页查询
     * @author Soneer
     * @date 2021/03/21
     **/
    List<ApiRecord> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author Soneer
     * @date 2021/03/21
     **/
    int pageListCount(int offset,int pagesize);
}
