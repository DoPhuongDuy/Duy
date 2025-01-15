package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.UserPackageMappingDTO;
import vn.codezx.judging.entity.EmbeddedIds.UserPackageMappingId;
import vn.codezx.judging.entity.PackagePlan;
import vn.codezx.judging.entity.User;
import vn.codezx.judging.entity.UserPackageMapping;

@Component
public class UserPackageMappingMapper {

    public UserPackageMappingDTO toDTO(UserPackageMapping entity) {
        return UserPackageMappingDTO.builder()
                .userId(entity.getUser().getId())
                .packagePlanId(entity.getPackagePlan().getId())
                .numberEvent(entity.getNumberEvent())
                .build();
    }

    public UserPackageMapping toEntity(int numberEvent, User user, PackagePlan packagePlan) {
        return UserPackageMapping.builder()
                .id(new UserPackageMappingId(user.getId(), packagePlan.getId()))
                .user(user)
                .packagePlan(packagePlan)
                .numberEvent(numberEvent)
                .build();
    }
}
