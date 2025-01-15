package vn.codezx.judging.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.codezx.judging.Exception.AlreadyExistsException;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.dtos.RoleDTO;
import vn.codezx.judging.entity.Role;
import vn.codezx.judging.mapper.RoleMapper;
import vn.codezx.judging.repository.RoleRepository;
import vn.codezx.judging.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Role Not Found with id "+id));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(()-> new DataNotFoundException("Role Not Found with name"));
    }

    @Override
    public Role createRole(RoleDTO roleDTO) {
        if (roleRepository.existsByRoleName(roleDTO.getRoleName())) {
            throw new AlreadyExistsException("Role already exists");
        }
        Role newRole = roleMapper.toEntity(roleDTO);
        return roleRepository.save(newRole);
    }

    @Override
    public Role updateRoleById(long id, RoleDTO roleDTO) {
        Role existingRole = getRoleById(id);
        if (roleRepository.existsByRoleName(roleDTO.getRoleName())) {
            throw new AlreadyExistsException("Role already exists");
        }
        existingRole.setRoleName(roleDTO.getRoleName());
        existingRole.setDescription(roleDTO.getDescription());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRoleById(long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        optionalRole.ifPresent(roleRepository::delete);
    }
}
