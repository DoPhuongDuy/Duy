package vn.codezx.judging.service.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.codezx.judging.Exception.AlreadyExistsException;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.Exception.DataSaveException;
import vn.codezx.judging.components.JwtTokenUtil;
import vn.codezx.judging.dtos.UserDTO;
import vn.codezx.judging.entity.Role;
import vn.codezx.judging.entity.User;
import vn.codezx.judging.mapper.UserMapper;
import vn.codezx.judging.mapper.UserPackageMappingMapper;
import vn.codezx.judging.repository.PackagePlanRepository;
import vn.codezx.judging.repository.RoleRepository;
import vn.codezx.judging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.codezx.judging.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final PackagePlanRepository packagePlanRepository;
    private final UserPackageMappingMapper userPackageMappingMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserDTO userDTO) {
        //trung Email, phone
        if (userRepository.existsByAccount(userDTO.getAccount())) {
            throw new AlreadyExistsException("Account already exists");
        }
        if (userRepository.existsByPhone(userDTO.getPhone())) {
            throw new AlreadyExistsException("Phone already exists");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }
        List<Role> roles = new ArrayList<>();
        roles.add(findRoleByName(Role.USER));

        User newUser = userMapper.toEntity(userDTO);

        newUser.setRoles(roles);

        //Mã hóa mật khẩu User (có thể không cần)
        String pwd = userDTO.getPwd();
        String encodedPassword = passwordEncoder.encode(pwd);
        newUser.setPwd(encodedPassword);

        //save User
        newUser = userRepository.save(newUser);
        if (newUser.getId() == 0) {
            throw new DataSaveException("Failed to save user.");
        }
        return newUser;
    }

    @Override
    public User getUserById(long id){
        return  userRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("User Not Found with id "+id));
    }

    @Override
    public String login(String account, String pwd) {
        Optional<User> optionalUser = userRepository.findByAccount(account);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid account / password");
        }
        //trả về token
        User existingUser = optionalUser.get();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                account,pwd,existingUser.getAuthorities()
        );
        //xác thực người dùng với sping secuiry
        authenticationManager.authenticate(authenticationToken);

        return jwtTokenUtil.generateToken(optionalUser.get());
    }

    private Role findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new DataNotFoundException("Role not found with name: " + roleName));
    }
}
