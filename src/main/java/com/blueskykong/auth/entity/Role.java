package com.blueskykong.auth.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author keets
 * @date 2017/11/22
 */
@Data
public class Role {

    private UUID id;

    private String name;

    private Timestamp updateTime;

    private String description;

    public static class RoleBuilder {
        private Role role = new Role();

        public RoleBuilder withId(UUID id) {
            role.setId(id);
            return this;
        }

        public RoleBuilder withName(String name) {
            role.setName(name);
            return this;
        }

        public RoleBuilder withDescription(String description) {
            role.setDescription(description);
            return this;
        }

        public RoleBuilder withUpdateTime(Timestamp time) {
            role.setUpdateTime(time);
            return this;
        }

        public Role build() {
            return role;
        }
    }
}
