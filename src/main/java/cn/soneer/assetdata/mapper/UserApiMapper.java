package cn.soneer.assetdata.mapper;

import cn.soneer.assetdata.entity.UserApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * project：assetdata
 * class：UserApiMapper
 * describe：TODO
 * time：2021/3/21 15:00
 * author：soneer
 * version:1.0
 */
@Mapper
public interface UserApiMapper {
    /**
     * 新增
     * @author Soneer
     * @date 2021/03/21
     **/
    int insert(UserApi userApi);

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
    int update(UserApi userApi);

    /**
     * 查询 根据主键 id 查询
     * @author Soneer
     * @date 2021/03/21
     **/
    UserApi load(int id);

    /**
     *
     * @param appId
     * @return
     */
    List<UserApi> getByAppId(String appId);

    /**
     * 查询 分页查询
     * @author Soneer
     * @date 2021/03/21
     **/
    List<UserApi> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author Soneer
     * @date 2021/03/21
     **/
    int pageListCount(int offset,int pagesize);
}
