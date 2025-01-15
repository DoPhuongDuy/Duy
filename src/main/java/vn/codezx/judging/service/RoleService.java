package vn.codezx.judging.service;

import vn.codezx.judging.dtos.RoleDTO;
import vn.codezx.judging.entity.Role;

import java.util.List;

public interface RoleService {
    // Retrieve all roles
    List<Role> getAllRoles();

    // Retrieve a role by its ID
    Role getRoleById(long id);

    // Retrieve a role by its name
    Role getRoleByName(String roleName);

    // create a role
    Role createRole(RoleDTO roleDTO);

    Role updateRoleById(long id, RoleDTO roleDTO);
    // Update a role
    // Delete a role by its ID
    void deleteRoleById(long id);
}
