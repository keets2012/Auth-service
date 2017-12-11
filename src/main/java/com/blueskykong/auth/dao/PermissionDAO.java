package com.blueskykong.auth.dao;

import com.blueskykong.auth.entity.Permission;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author keets
 * @date 2017/11/22
 */
public interface PermissionDAO {

    int deleteById(UUID id);

    int insert(Permission record);

    Permission selectById(UUID id);

    void updateName(UUID id, String newName);

    List<Permission> selectAll();

    List<Permission> getPermissionList(Map paramMap);
}
