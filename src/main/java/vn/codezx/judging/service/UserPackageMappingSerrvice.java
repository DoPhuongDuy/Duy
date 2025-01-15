package vn.codezx.judging.service;

import vn.codezx.judging.dtos.UserPackageMappingDTO;
import vn.codezx.judging.entity.UserPackageMapping;

import java.util.List;

public interface UserPackageMappingSerrvice {
    List<UserPackageMapping> getAllMappings();

    List<UserPackageMapping> getMappingsByUserId(long userId);

    List<UserPackageMapping> getMappingsByPackagePlanId(long packagePlanId);

    UserPackageMapping createMapping(UserPackageMappingDTO userPackageMappingDTO);

    UserPackageMapping updateMapping(UserPackageMappingDTO userPackageMappingDTO);

    void deleteMapping(long userId, long packagePlanId);
}
