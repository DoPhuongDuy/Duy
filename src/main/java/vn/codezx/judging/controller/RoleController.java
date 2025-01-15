package vn.codezx.judging.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.codezx.judging.dtos.RoleDTO;
import vn.codezx.judging.entity.Role;
import vn.codezx.judging.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // Create a new role
    @PostMapping
    public ResponseEntity<Role> createRole(
            @Valid @RequestBody RoleDTO role,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessages);
        }
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    // Retrieve all roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Retrieve a role by ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") long id) {
        Role role = roleService.getRoleById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.notFound().build();
    }

    // Retrieve a role by name
    @GetMapping("/search")
    public ResponseEntity<Role> getRoleByName(@RequestParam String roleName) {
        Role role = roleService.getRoleByName(roleName);
        if (role != null) {
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.notFound().build();
    }

    // Update an existing role
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") long id, @Valid @RequestBody RoleDTO roleDTO) {
        Role savedRole = roleService.updateRoleById(id,roleDTO);
        return ResponseEntity.ok(savedRole);
    }

    // Delete a role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable("id") long id) {
        Role existingRole = roleService.getRoleById(id);
        if (existingRole != null) {
            roleService.deleteRoleById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
