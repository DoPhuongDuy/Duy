package vn.codezx.judging.dtos;

import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriteriaPackDTO {

    @NotEmpty(message = "Pack name cannot be empty")
    @Size(max = 50, message = "Pack name must not exceed 50 characters")
    private String packName;

    @Size(max = 256, message = "Description must not exceed 256 characters")
    private String description;

    @Min(value = 1, message = "Round number must be greater than or equal to 1")
    private int roundNumber;

    private long eventId;

    private long criteriaId;
}
