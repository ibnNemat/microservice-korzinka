package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.userservice.client.GmailPlaceHolder;
import uz.nt.userservice.dto.LoginDto;
import shared.libs.utils.MyDateUtil;
import shared.libs.dto.UserDto;
import shared.libs.configuration.Config;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.entity.UserSession;
import shared.libs.repository.UserSessionRepository;
import shared.libs.security.JwtService;
import uz.nt.userservice.entity.BanIp;
import uz.nt.userservice.entity.CheckAttempt;
import uz.nt.userservice.entity.User;
import uz.nt.userservice.repository.BanIpRepository;
import uz.nt.userservice.repository.CheckAttemptRepository;
import uz.nt.userservice.repository.UserRepository;
import uz.nt.userservice.service.UserService;
import uz.nt.userservice.service.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ExcelServiceImpl excelService;
    public static Map<Integer, UserDto> usersMap = new HashMap<>();
    private final PasswordEncoder passwordEncoder = Config.passwordEncoder();
    private final JwtService jwtService;

    private final UserSessionRepository userSessionRepository;
    private final GmailPlaceHolder gmailPlaceHolder;
    private final CheckAttemptRepository checkAttemptRepository;
    private final BanIpRepository banIpRepository;


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
    public ResponseDto<UserDto> addUser(UserDto userDto,HttpServletRequest request) {
        
        try{
            User user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            ResponseDto<String> responseDto = sendToGmail(userDto,request.getRemoteAddr());
            if(responseDto.getSuccess()) {
                return ResponseDto.<UserDto>builder()
                        .code(200)
                        .success(true)
                        .message("Successfully saved")
                        .responseData(userMapper.toDto(user))
                        .build();
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<UserDto>builder()
                    .code(500)
                    .message("Error while adding new user to DB or gmail incorrect")
                    .build();
        }
        return ResponseDto.<UserDto>builder()
                .code(500)
                .message("Gmail incorrect")
                .build();
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
                    .responseData(new JWTResponseDto(token, MyDateUtil.expirationTimeToken(), null))
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
    public ResponseDto<UserDto> deleteUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .success(false)
                    .message("Failed")
                    .build();
        }
        userRepository.delete(user.get());
        return ResponseDto.<UserDto>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .responseData(userMapper.toDto(user.get()))
                .build();
    }

    @Override
    @Transactional
    public ResponseDto<Integer> deleteUserByUsername(String username) {
        if(userRepository.existsByUsername(username)) {
            Integer howToDelete = userRepository.deleteByUsername(username);
            return ResponseDto.<Integer>builder()
                    .code(0)
                    .message("Ok")
                    .success(true)
                    .responseData(howToDelete)
                    .build();
        }
        return ResponseDto.<Integer>builder()
                .code(-5)
                .message("Failed")
                .success(false)
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


    @Override
    public ResponseDto<String> checkVerifyCode(Integer code,HttpServletRequest request) {
        Optional<BanIp> optionalBanIp = banIpRepository.findById(request.getRemoteAddr());
        if(optionalBanIp.isEmpty()) {
            Optional<CheckAttempt> optionalCheckAttempt = checkAttemptRepository.findById(request.getRemoteAddr());
            if(optionalCheckAttempt.isPresent() && optionalCheckAttempt.get().getUserDto().getIncrement() >= 3 && !optionalCheckAttempt.get().getUserDto().getCode().equals(code)) {
                checkAttemptRepository.deleteById(request.getRemoteAddr());
                banIpRepository.save(new BanIp(request.getRemoteAddr(),optionalCheckAttempt.get().getUserDto()));
                return ResponseDto.<String>builder()
                        .code(-10)
                        .message("failed")
                        .success(false)
                        .responseData("Verify code is incorrect Your are banned 15 minute").build();
            } else if(optionalCheckAttempt.isPresent() && optionalCheckAttempt.get().getUserDto().getCode().equals(code)) {
                UserDto userDto = optionalCheckAttempt.get().getUserDto();
                userDto.setIsActive(true);
                userRepository.save(userMapper.toEntity(userDto));
                return ResponseDto.<String>builder()
                        .code(0)
                        .message("Ok")
                        .success(true)
                        .responseData("Verify is correct").build();
            } else {
                UserDto userDto = optionalCheckAttempt.get().getUserDto();
                userDto.setIncrement(userDto.getIncrement()+1);
                checkAttemptRepository.save(new CheckAttempt(request.getRemoteAddr(),userDto));
                return ResponseDto.<String>builder()
                        .code(-2)
                        .message("failed")
                        .success(false)
                        .responseData("Verify code is incorrect!").build();
            }
        }
        return ResponseDto.<String>builder()
                .code(-10)
                .message("failed")
                .success(false)
                .responseData("Verify code is incorrect Your are banned 15 minute").build();
    }

    @Override
    public ResponseDto<String> sendToGmail(UserDto userDto,String IpAddress) {
        Random random = new Random();
        int rand = random.nextInt(1000,9999);
        ResponseDto<String> responseDto = gmailPlaceHolder.sendToGmailAndGetVerifyCode(userDto.getEmail(),rand);      //send verify code and gmail to gmail-service
        if(responseDto.getSuccess()) {
            userDto.setCode(rand);
            userDto.setIncrement(0);
            CheckAttempt checkAttempt = new CheckAttempt(IpAddress,userDto);
            checkAttemptRepository.save(checkAttempt);          // redis save
            return ResponseDto.<String>builder().code(0).message("Ok").success(true).responseData("Please verify your code from gmail").build();
        }
        return ResponseDto.<String>builder().code(-3).message("Failed").success(false).responseData("Error gmail").build();
    }


    @Transactional
    @Override
    public void export (HttpServletRequest request, HttpServletResponse response){
        Stream<User> users = userRepository.findAllByIdLessThan(1_000_000);
        Stream<UserDto> userDtos = users.map(userMapper::toDto);

        try {
            excelService.export(userDtos, request, response);
        } catch (IOException e) {
            log.error("Excel exprot error " + e.getMessage());
        }
    }

    private String sysGuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
