package com.blueskykong.auth.rest;

import com.blueskykong.auth.dao.PermissionDAO;
import com.blueskykong.auth.dao.RoleDAO;
import com.blueskykong.auth.dao.RolePermissionDAO;
import com.blueskykong.auth.dao.UserRoleDAO;
import com.blueskykong.auth.dto.UserRoleDTO;
import com.blueskykong.auth.entity.Permission;
import com.blueskykong.auth.entity.RolePermission;
import com.blueskykong.auth.entity.UserRole;
import com.blueskykong.auth.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * @author keets
 * @date 2017/10/15
 */
@Path("/")
public class SecurityResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityResource.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private PermissionDAO permissionDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private RolePermissionDAO rolePermissionDAO;

    @POST
    @Path("permission")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPermission(Permission permission) {
        permission.setId(UUID.randomUUID());
        permissionDAO.insert(permission);
        return Response.ok(permission.getId()).build();
    }

    @GET
    @Path("permissions/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPermission(@PathParam("id") String id) {
        UUID pId = UUID.fromString(id);
        Permission permission = permissionDAO.selectById(pId);
        return Response.ok(permission).build();
    }

    @POST
    @Path("/role-permission")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRolePermission(RolePermission rolePermission) {
        rolePermissionDAO.insert(rolePermission);
        return Response.ok().build();
    }

    @GET
    @Path("role")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRoles() {
        return Response.ok(roleDAO.selectAll()).build();
    }

    @GET
    @Path("permissions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPermissions() {
        return Response.ok(permissionDAO.selectAll()).build();
    }

    @POST
    @Path("user-role")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUserRole(UserRole userRole) {
        userRole.setUserId(UUID.randomUUID());
        userRoleDAO.insertUtRole(userRole);
        return Response.ok().build();
    }
}
