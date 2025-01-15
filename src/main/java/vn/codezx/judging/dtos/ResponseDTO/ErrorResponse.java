package vn.codezx.judging.dtos.ResponseDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int status;
    private String error;
}
