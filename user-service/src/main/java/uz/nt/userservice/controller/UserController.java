package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
//    private final UserService userService;
//
//    @GetMapping
//    public ResponseDto<List<UserDto>> getAll(){
//        return userService.getAllUser();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseDto getById(@PathVariable Integer id){
//        return userService.getUserById(id);
//    }
//    @PostMapping
//    public ResponseDto addUser(@RequestBody UserDto userDto){
//        return userService.addUser(userDto);
//    }
//
//    @PutMapping
//    public ResponseDto updateUser(@RequestBody UserDto userDto){
//        return userService.updateUser(userDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseDto deleteUser(@PathVariable Integer id){
//        return userService.deleteUserById(id);
//    }
}
