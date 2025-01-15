package vn.codezx.judging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.codezx.judging.entity.EmbeddedIds.UserPackageMappingId;

@Entity
@Table(name = "user_package_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPackageMapping {

    @EmbeddedId
    private UserPackageMappingId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("packagePlanId")
    @JoinColumn(name = "package_plan_id")
    private PackagePlan packagePlan;

    @Column(name = "number_event")
    private int numberEvent;
}
