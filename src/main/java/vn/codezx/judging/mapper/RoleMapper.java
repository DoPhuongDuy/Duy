package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.RoleDTO;
import vn.codezx.judging.entity.Role;

@Component
public class RoleMapper {

    public RoleDTO toDto(Role role) {
        return RoleDTO.builder()
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .build();
    }

    public Role toEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        role.setDescription(roleDTO.getDescription());
        return role;
    }
}