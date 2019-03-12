package com.blueskykong.auth.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class UserAccess {
    private Long id;

    private UUID userId;

    private Integer accessLevel;
}
