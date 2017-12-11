package com.blueskykong.auth.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Created by keets on 2016/12/5.
 */
@Data
public class UserRoleDTO {

    private UUID userId;

    private UUID roleId;

    private String name;

    private String description;
}
