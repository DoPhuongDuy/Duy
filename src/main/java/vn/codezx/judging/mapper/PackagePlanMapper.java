package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.PackagePlanDTO;
import vn.codezx.judging.entity.PackagePlan;

@Component
public class PackagePlanMapper {

    // Convert Entity to DTO
    public PackagePlanDTO toDTO(PackagePlan packagePlan) {
        return PackagePlanDTO.builder()
                .planName(packagePlan.getPlanName())
                .description(packagePlan.getDescription())
                .maxEvents(packagePlan.getMaxEvents())
                .maxProjects(packagePlan.getMaxProjects())
                .maxJudges(packagePlan.getMaxJudges())
                .maxEventDuration(packagePlan.getMaxEventDuration())
                .build();
    }

    // Convert DTO to Entity
    public PackagePlan toEntity(PackagePlanDTO packagePlanDTO) {
        return PackagePlan.builder()
                .planName(packagePlanDTO.getPlanName())
                .description(packagePlanDTO.getDescription())
                .maxEvents(packagePlanDTO.getMaxEvents())
                .maxProjects(packagePlanDTO.getMaxProjects())
                .maxJudges(packagePlanDTO.getMaxJudges())
                .maxEventDuration(packagePlanDTO.getMaxEventDuration())
                .build();
    }
}
