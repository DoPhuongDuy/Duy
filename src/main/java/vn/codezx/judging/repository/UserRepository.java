package vn.codezx.judging.repository;

import vn.codezx.judging.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccount(String account);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    Optional<User> findByAccount(String account);
}
