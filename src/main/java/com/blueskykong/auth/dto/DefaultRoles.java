package com.blueskykong.auth.dto;

import java.util.UUID;

/**
 * Created by keets on 2017/11/23.
 */
public class DefaultRoles {
    public static DefaultRole PLATFORM_ADMIN = new DefaultRole(UUID.fromString("23f59f5d-427d-401b-a676-6cca3a9d99f5"), "PLATFORM_ADMIN", "the platform admin was hold by the website administrators");
    public static DefaultRole BRANCH_ADMIN = new DefaultRole(UUID.fromString("6405f6af-d892-4bf9-b13c-f7ca2ae1d71e"), "BRANCH_ADMIN", "the branch company admin");
    public static DefaultRole MASTER_ADMIN = new DefaultRole(UUID.fromString("fe96c94c-e3a9-4d30-88a5-c2acbba8917d"), "MASTER_ADMIN", "the master company admin");
    public static DefaultRole GENERAL_USER = new DefaultRole(UUID.fromString("7bb37b99-6d6d-403b-88c1-95d3944f5403"), "GENERAL_USER", "a normal user");
    public static DefaultRole EMPLOYEE = new DefaultRole(UUID.fromString("d4a65d04-a5a3-465c-8408-405971ac3346"), "EMPLOYEE", "the employee for a company");
}
