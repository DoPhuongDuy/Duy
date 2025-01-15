package vn.codezx.judging.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class PackagePlanDTO {

    @NotNull(message = "Plan name cannot be null")
    @Size(max = 50, message = "Plan name must be at most 50 characters")
    private String planName;  // Tương ứng với "plan_name"

    @Size(max = 256, message = "Description must be at most 256 characters")
    private String description;  // Tương ứng với "description"

    @Min(value = 1)
    private int maxEvents;  // Tương ứng với "max_events"
    @Min(value = 1)
    private int maxProjects;  // Tương ứng với "max_projects"
    @Min(value = 1)
    private int maxJudges;  // Tương ứng với "max_judges"
    @Min(value = 1)
    private int maxEventDuration;

}

