package com.blueskykong.auth.dao.mapper;

import com.blueskykong.auth.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by keets on 2017/11/22.
 */
public interface PermissionMapper {

    int deleteById(@Param("id") UUID id);

    int insert(Permission record);

    Permission selectById(@Param("id") UUID id);

    List<Permission> selectAll();

    int updateById(Permission record);

    void updateName(UUID id, String newName);

    List<Permission> selectByMap(Map paraMap);
}
