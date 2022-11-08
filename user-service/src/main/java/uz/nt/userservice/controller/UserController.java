package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.userservice.dto.LoginDto;
import uz.nt.userservice.service.UserService;
import uz.nt.userservice.service.impl.UserDetailServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDetailServiceImpl userDetailService;

    @GetMapping
    public ResponseDto<List<UserDto>> getAll(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseDto<UserDto> getById(@PathVariable Integer id){
        return userService.getUserById(id);
    }
    @PostMapping
    public ResponseDto<UserDto> addUser(@RequestBody UserDto userDto,HttpServletRequest request){
        return userService.addUser(userDto,request);
    }

    @PutMapping
    public ResponseDto<String> updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseDto<Integer> deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<UserDto> deleteUser(@PathVariable Integer id){
        return userService.deleteUserById(id);
    }

    @PostMapping("/login")
    public ResponseDto<JWTResponseDto> login(@RequestBody LoginDto loginDto){
        return userDetailService.login(loginDto);
    }
    @PostMapping("/verify/{code}")
    public ResponseDto<String> checkVerifyCode(@PathVariable Integer code,HttpServletRequest request) {
        return userService.checkVerifyCode(code,request);
    }


}
