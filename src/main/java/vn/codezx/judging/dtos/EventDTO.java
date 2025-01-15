package vn.codezx.judging.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO{

    @NotEmpty(message = "Event name cannot be empty")
    @Size(max = 50, message = "Event name must not exceed 50 characters")
    private String eventName; // Tương ứng với cột "event_name"

    private LocalDate startDate; // Tương ứng với cột "start_date"

    private LocalDate endDate; // Tương ứng với cột "end_date"

    @Size(max = 256, message = "Description must not exceed 256 characters")
    private String description; // Tương ứng với cột "description"

    @Min(value = 0, message = "organizerId must be greater than or equal to 0")
    private long organizerId; // Tương ứng với cột "organizer_id"

    private long planid;

    private long schoolId; // Tương ứng với cột "school_id"
}
