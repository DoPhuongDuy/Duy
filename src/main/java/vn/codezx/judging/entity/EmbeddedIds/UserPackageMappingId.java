package vn.codezx.judging.entity.EmbeddedIds;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPackageMappingId implements Serializable {
    private long userId;
    private long packagePlanId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPackageMappingId that = (UserPackageMappingId) o;
        return userId == that.userId && packagePlanId == that.packagePlanId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, packagePlanId);
    }
}
