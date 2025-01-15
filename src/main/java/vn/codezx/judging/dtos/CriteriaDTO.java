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
public class CriteriaDTO {

    @NotEmpty(message = "Criteria name cannot be empty")
    @Size(max = 50, message = "Criteria name must not exceed 50 characters")
    private String criteriaName;

    @Size(max = 100, message = "Description must not exceed 100 characters")
    private String description;

    @Min(value = 1, message = "Weight must be greater than or equal to 1")
    private int weight;

    private long categoryId;
}
