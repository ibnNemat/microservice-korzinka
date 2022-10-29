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
import shared.libs.entity.UserSession;
import shared.libs.repository.UserSessionRepository;
import shared.libs.security.JwtService;
import uz.nt.userservice.client.GmailPlaceHolder;
import uz.nt.userservice.dto.LoginDto;
import shared.libs.dto.UserDto;
import shared.libs.utils.DateUtil;
import uz.nt.userservice.entity.User;
import uz.nt.userservice.repository.UserRepository;
import uz.nt.userservice.service.UserService;
import uz.nt.userservice.service.mapper.UserMapper;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = Config.passwordEncoder();
    private final JwtService jwtService;

    private final UserSessionRepository userSessionRepository;
    private final GmailPlaceHolder gmailPlaceHolder;
    private Integer verifyCode;

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
    public ResponseDto<String> addUser(UserDto userDto) {
        try{
            User user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            ResponseDto<Integer> responseDto = gmailPlaceHolder.sendToGmailAndGetVerifyCode(userDto.getEmail());
            if(responseDto.getSuccess()) {
                verifyCode = responseDto.getResponseData();
                return ResponseDto.<String>builder()
                        .code(200)
                        .success(true)
                        .message("Ok")
                        .responseData("Successfully saved")
                        .build();
            }
            return ResponseDto.<String>builder()
                    .code(500)
                    .message("Error")
                    .responseData("Error while adding new user to DB")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .code(500)
                    .message("Error")
                    .responseData("Error while adding new user to DB")
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
            UserSession userSession = new UserSession(sysGuid(), userMapper.toDto(user));
            userSessionRepository.save(userSession);

            String token = jwtService.generateToken(userSession.getId());

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
                .build();
    }

    @Override
    public ResponseDto<String> checkVerifyCode(Integer code) {
        if(verifyCode != null && verifyCode == code) {
            return ResponseDto.<String>builder()
                    .code(0)
                    .message("Ok")
                    .success(true)
                    .responseData("Access Verify").build();
        }
        return ResponseDto.<String>builder()
                .code(-10)
                .message("failed")
                .success(false)
                .responseData("Verify code is incorrect").build();
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
    public ResponseDto<String> deleteUserById(Integer id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseDto.<String>builder()
                    .code(0)
                    .success(true)
                    .message("Ok")
                    .responseData("Success delete")
                    .build();
        }
        return ResponseDto.<String>builder()
                .code(-3)
                .success(false)
                .message("Failed")
                .responseData("User Id dont found")
                .build();
    }

    @Override
    public ResponseDto<String> updateUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
        return ResponseDto.<String>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }

    private String sysGuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
