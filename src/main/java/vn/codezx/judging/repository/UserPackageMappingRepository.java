package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codezx.judging.entity.UserPackageMapping;

import java.util.List;
import java.util.Optional;

public interface UserPackageMappingRepository extends JpaRepository<UserPackageMapping, Long> {
    List<UserPackageMapping> findByUserId(long userId);
    List<UserPackageMapping> findByPackagePlanId(long packagePlanId);
    Optional<UserPackageMapping> findByUserIdAndPackagePlanId(long userId, long packagePlanId);

    boolean existsByUserIdAndPackagePlanId(long userId, long packagePlanId);
    boolean existsByUserId(long userId);
    boolean existsByPackagePlanId(long packagePlanId);
}
