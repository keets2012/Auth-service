package com.blueskykong.auth.entity;

import lombok.Data;

import java.util.UUID;

/**
 * @author keets
 * @date 2017/12/5
 */
@Data
public class UserRole {
    private Long id;

    private UUID userId;

    private UUID roleId;

    private Boolean isDefault = true;

    public static class UserRoleBuilder {

        private UserRole userRole = new UserRole();

        public UserRoleBuilder withUserId(UUID userId) {
            userRole.setUserId(userId);
            return this;
        }

        public UserRoleBuilder withRoleId(UUID roleId) {
            userRole.setRoleId(roleId);
            return this;
        }

        public UserRole build() {
            return userRole;
        }
    }
}
