package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.userservice.dto.LoginDto;
import uz.nt.userservice.service.UserService;
import uz.nt.userservice.service.impl.UserDetailServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseDto getById(@PathVariable Integer id){
        return userService.getUserById(id);
    }
    @PostMapping
    public ResponseDto<UserDto> addUser(@RequestBody UserDto userDto, HttpServletRequest request){
        return userService.addUser(userDto, request);
    }

    @PutMapping
    public ResponseDto updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<UserDto> deleteUser(@PathVariable Integer id){
        return userService.deleteUserById(id);
    }

    @PostMapping("/login")
    public ResponseDto<JWTResponseDto> login(@RequestBody LoginDto loginDto){
        return userDetailService.login(loginDto);
    }

    @GetMapping("/excel")
    public void export(HttpServletRequest request, HttpServletResponse response){
        userService.export(request, response);
    }
}
