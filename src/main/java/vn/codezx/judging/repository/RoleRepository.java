package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.codezx.judging.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom query to find a role by its name
    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName (String roleName);
}
