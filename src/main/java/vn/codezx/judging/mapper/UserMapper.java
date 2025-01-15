package vn.codezx.judging.mapper;

import lombok.RequiredArgsConstructor;
import vn.codezx.judging.dtos.UserDTO;
import vn.codezx.judging.entity.User;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class UserMapper {
    // Convert DTO to Entity
    public User toEntity(UserDTO userDTO) {
        User user = User.builder()
                .account(userDTO.getAccount())
                .pwd(userDTO.getPwd())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .description(userDTO.getDescription())
                .build();
        return user;
    }

    // Convert Entity to DTO
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .account(user.getAccount())
                .pwd(user.getPwd())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .description(user.getDescription())
                .build();
    }
}
