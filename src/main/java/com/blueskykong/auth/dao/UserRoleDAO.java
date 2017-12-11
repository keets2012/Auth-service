package com.blueskykong.auth.dao;


import com.blueskykong.auth.dto.UserRoleDTO;
import com.blueskykong.auth.entity.UserRole;

import java.util.List;
import java.util.UUID;


public interface UserRoleDAO {

    Long insertUtRole(UserRole userRole);

    List<UserRole> selectByUserId(UUID userId);

    int updateById(UserRole record);


    int deleteByUserId(UUID userId);

    List<UserRoleDTO> selectUserRoleList(UUID userId);
}
