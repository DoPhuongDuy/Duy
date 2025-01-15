package vn.codezx.judging.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class UserPackageMappingDTO {
    private long userId;
    private long packagePlanId;
    private int numberEvent;
}
