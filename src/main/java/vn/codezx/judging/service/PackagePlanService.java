package vn.codezx.judging.service;

import vn.codezx.judging.dtos.PackagePlanDTO;
import vn.codezx.judging.entity.PackagePlan;

import java.util.List;

public interface PackagePlanService {
    public PackagePlan createPackagePlan(PackagePlanDTO packagePlanDTO);
    List<PackagePlan> getAllPackagePlan();
    PackagePlan getPackagePlanByName(String packagePlanName);
    PackagePlan getPackagePlanById(long id);
    PackagePlan updatePackagePlanById(long id, PackagePlanDTO packagePlanDTO);
    void deletePackagePlanById(long id);
    boolean existsByPackagePlanName(String name);
}
