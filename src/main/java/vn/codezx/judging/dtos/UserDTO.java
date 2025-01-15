package vn.codezx.judging.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotEmpty(message = "Account cannot be empty")
    @Size(max = 64, message = "Account must not exceed 64 characters")
    private String account; // Tương ứng với varchar(64)

    @NotEmpty(message = "Password cannot be empty")
    @Size(max = 64, message = "Password must not exceed 64 characters")
    private String pwd; // Tương ứng với varchar(64)

    @NotEmpty(message = "Password cannot be empty")
    @Size(max = 64, message = "Retyp password must not exceed 64 characters")
    private String retype_pwd; // Tương ứng với varchar(64)

    @NotEmpty(message = "First name cannot be empty")
    @Size(max = 64, message = "First name must not exceed 64 characters")
    private String firstName; // Tương ứng với varchar(64)

    @NotEmpty(message = "Last name cannot be empty")
    @Size(max = 64, message = "Last name must not exceed 64 characters")
    private String lastName; // Tương ứng với varchar(64)

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Size(max = 64, message = "Email must not exceed 64 characters")
    private String email; // Tương ứng với varchar(64)

    @NotEmpty(message = "Phone cannot be empty")
    @Size(max = 64, message = "Phone must not exceed 64 characters")
    private String phone; // Tương ứng với varchar(64)

    @Size(max = 256, message = "Description must not exceed 256 characters")
    private String description; // Tương ứng với varchar(256)

    private long packagePlan;
}
