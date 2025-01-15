package vn.codezx.judging.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotEmpty(message = "Account cannot be empty")
    @Size(max = 64, message = "Account must not exceed 64 characters")
    private String account; // Tương ứng với varchar(64)

    @NotEmpty(message = "Password cannot be empty")
    @Size(max = 64, message = "Password must not exceed 64 characters")
    private String pwd; // Tương ứng với varchar(64)
}
