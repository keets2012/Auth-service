package com.blueskykong.auth.entity;

import lombok.Data;

import java.util.UUID;

/**
 * Created by keets on 2017/11/22.
 */
@Data
public class Permission {

    private UUID id;

    private String permission;

    private String description;
}
