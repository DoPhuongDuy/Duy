package vn.codezx.judging.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO {

    @NotEmpty(message = "Group name cannot be empty")
    @Size(max = 50, message = "Group name must not exceed 50 characters")
    private String groupName;

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 20, message = "Title must not exceed 20 characters")
    private String title;

    @NotEmpty(message = "Client cannot be empty")
    @Size(max = 20, message = "Client must not exceed 20 characters")
    private String client;

    @NotNull(message = "Event ID cannot be null")
    @Min(value = 1, message = "Event ID must be greater than or equal to 1")
    private long eventId; // Chỉ lưu ID của Event

    @Size(max = 512, message = "Description must not exceed 512 characters")
    private String description;
}
