package uz.nt.userservice.service;

import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.LoginDto;
import shared.libs.dto.UserDto;

import java.util.List;

public interface UserService {
    ResponseDto<List<UserDto>> getAllUser();

    ResponseDto<UserDto> getUserById(Integer id);

    ResponseDto<String> deleteUserById(Integer id);

    ResponseDto<String> updateUser(UserDto userDto);

    ResponseDto<String> addUser(UserDto userDto);
    ResponseDto<JWTResponseDto> login(LoginDto loginDto);
    ResponseDto<UserDto> checkToken(String token);

    ResponseDto<String> checkVerifyCode(Integer code);          //Verify gmail
}
