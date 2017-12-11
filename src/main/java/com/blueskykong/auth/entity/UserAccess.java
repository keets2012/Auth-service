package com.blueskykong.auth.entity;

import lombok.Data;

import java.util.UUID;

/**
 * Created by keets on 2016/12/5.
 */
@Data
public class UserAccess {
    private Long id;

    private UUID userId;

    private Integer accessLevel;
}
