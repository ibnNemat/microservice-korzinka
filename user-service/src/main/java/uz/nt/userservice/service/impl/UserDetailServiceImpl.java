package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shared.libs.configuration.Config;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.security.JwtService;
import uz.nt.userservice.dto.LoginDto;
import uz.nt.userservice.dto.UserDto;
import shared.libs.utils.DateUtil;
import uz.nt.userservice.entity.User;
import uz.nt.userservice.repository.UserRepository;
import uz.nt.userservice.service.UserService;
import uz.nt.userservice.service.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public static Map<Integer, UserDto> usersMap = new HashMap<>();
    private final PasswordEncoder passwordEncoder = Config.passwordEncoder();
    private final JwtService jwtService = JwtService.getInstance();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findFirstByUsername(username);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Username is not found");
        }
        User user = optionalUser.get();
        return userMapper.toDto(user);
    }

    @Override
    public ResponseDto addUser(UserDto userDto) {
        try{
            User user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

            return ResponseDto.builder()
                    .code(200)
                    .success(true)
                    .message("Successfully saved")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .code(500)
                    .message("Error while adding new user to DB")
                    .build();
        }
    }
    @Override
    public ResponseDto<JWTResponseDto> login(LoginDto loginDto){
        User user = userRepository.findFirstByUsername(loginDto.getUsername()).orElseThrow(
                () ->
                        new UsernameNotFoundException(String.format("User with username %s not found",
                                loginDto.getUsername()))
        );

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Password is incorrect!");
        }

        try{
            usersMap.put(user.getId(), userMapper.toDto(user));

            String token = jwtService.generateToken(String.valueOf(user.getId()));

            return ResponseDto.<JWTResponseDto>builder()
                    .code(200)
                    .success(true)
                    .message("OK")
                    .responseData(new JWTResponseDto(token, DateUtil.expirationTimeToken(), null))
                    .build();

        }catch (Exception e){
            return ResponseDto.<JWTResponseDto>builder()
                    .code(-12)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> checkToken(String token){
        Integer subject = Integer.valueOf(String.valueOf(jwtService.getClaim(token, "sub")));

        return ResponseDto.<UserDto>builder()
                .code(200)
                .success(true)
                .message("OK")
                .responseData(usersMap.get(subject))
                .build();
    }

    @Override
    public ResponseDto<List<UserDto>> getAllUser() {
        List<UserDto> list =userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
        return ResponseDto.<List<UserDto>>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .responseData(list)
                .build();
    }

    @Override
    public ResponseDto<UserDto> getUserById(Integer id) {
        User user = userRepository.findById(id).get();
        return ResponseDto.<UserDto>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .responseData(userMapper.toDto(user))
                .build();
    }

    @Override
    public ResponseDto deleteUserById(Integer id) {
        userRepository.deleteById(id);
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto updateUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }

}
