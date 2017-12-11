package com.blueskykong.auth.dao.mapper;

import com.blueskykong.auth.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author keets
 * @date 2017/11/22
 */

public interface RolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    Long insert(RolePermission record);

    List<RolePermission> selectByRoleId(@Param("roleId") UUID roleId);

    int updateByPrimaryKey(RolePermission record);

    void deleteByRoleIdAndPermissionId(@Param("roleId") UUID roleId, @Param("permissionId") UUID permissionId);

}
