package uz.nt.userservice.service;

import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.userservice.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    ResponseDto<List<UserDto>> getAllUser();

    ResponseDto<UserDto> getUserById(Integer id);

    ResponseDto<UserDto> deleteUserById(Integer id);

    ResponseDto<Integer> deleteUserByUsername(String username);

    ResponseDto<String> updateUser(UserDto userDto);

    ResponseDto<UserDto> addUser(UserDto userDto,HttpServletRequest request);
    ResponseDto<JWTResponseDto> login(LoginDto loginDto);
    ResponseDto<UserDto> checkToken(String token);

    void export(HttpServletRequest request, HttpServletResponse response);

    ResponseDto<String> checkVerifyCode(Integer code,HttpServletRequest request);          //Verify gmail
    ResponseDto<String> sendToGmail(UserDto userDto,String code);
}
