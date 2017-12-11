package com.blueskykong.auth.dao.mapper;

import com.blueskykong.auth.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author keets
 * @date 2017/11/22
 */

public interface RoleMapper {

    int deleteByPrimaryKey(@Param("id") UUID id);

    int insert(Role record);

    Role selectByPrimaryKey(@Param("id") UUID id);

    int updateByPrimaryKey(Role record);

    List<Role> selectAll();

}
