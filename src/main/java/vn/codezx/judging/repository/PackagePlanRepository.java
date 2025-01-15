package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codezx.judging.entity.PackagePlan;

import java.util.Optional;

public interface PackagePlanRepository extends JpaRepository<PackagePlan, Long> {
    Optional<PackagePlan> findByPlanName(String planName);
    boolean existsByPlanName (String planName);
}
