package com.blueskykong.auth.dao.impl;

import com.blueskykong.auth.dao.UserRoleDAO;
import com.blueskykong.auth.dao.mapper.UserRoleMapper;
import com.blueskykong.auth.dto.UserRoleDTO;
import com.blueskykong.auth.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository
public class MybatisURoleDAO implements UserRoleDAO {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public Long insertUtRole(UserRole UserRole) {
        userRoleMapper.insert(UserRole);
        return UserRole.getId();
    }

    @Override
    public List<UserRole> selectByUserId(UUID userId) {
        return userRoleMapper.selectByUId(userId);
    }

    @Override
    public int updateById(UserRole record) {
        return userRoleMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByUserId(UUID userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        return userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public List<UserRoleDTO> selectUserRoleList(UUID userId) {
        return userRoleMapper.selectUserRoleList(userId);
    }
}

