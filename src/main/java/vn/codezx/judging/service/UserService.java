package vn.codezx.judging.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import vn.codezx.judging.dtos.UserDTO;
import vn.codezx.judging.entity.User;
import vn.codezx.judging.mapper.UserMapper;
import vn.codezx.judging.repository.UserRepository;

public interface UserService {
    User createUser(UserDTO userDTO);

    public User getUserById(long id);



    String login(String account, String pwd);
}