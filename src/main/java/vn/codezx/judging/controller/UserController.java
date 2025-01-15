package vn.codezx.judging.controller;

import org.springframework.web.bind.annotation.*;
import vn.codezx.judging.dtos.UserDTO;
import vn.codezx.judging.dtos.UserLoginDTO;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.entity.User;
import vn.codezx.judging.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result)
    {
        // Kiểm tra nếu có lỗi trong dữ liệu đầu vào
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessages);
        }

        // Kiểm tra mật khẩu và nhập lại mật khẩu
        if(!userDTO.getPwd().equals(userDTO.getRetype_pwd())){
            throw new IllegalArgumentException("sai mật khẩu");
        }

        User NewUser = userService.createUser(userDTO);
        return ResponseEntity.ok(NewUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ){
        String token;
        try{
            token = userService.login(userLoginDTO.getAccount(), userLoginDTO.getPwd());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
        User existingUser = userService.getUserById(id);
        return ResponseEntity.ok(existingUser);
    }
}
