package com.blueskykong.auth.dao.mapper;

import com.blueskykong.auth.dto.UserRoleDTO;
import com.blueskykong.auth.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;


/**
 * @author keets
 */
public interface UserRoleMapper {

    int deleteById(Long id);

    Long insert(UserRole record);

    List<UserRole> selectByUId(@Param("userId") UUID userId);

    int updateByPrimaryKey(UserRole record);

    int deleteByUserId(@Param("userId") UUID userId);

    List<UserRoleDTO> selectUserRoleList(@Param("userId") UUID userId);
}
