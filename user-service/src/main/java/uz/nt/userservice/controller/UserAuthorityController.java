package uz.nt.userservice.controller;

import uz.nt.userservice.service.UserAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.UserAuthorityDto;

import java.util.List;

@RestController
@RequestMapping("/user-authority")
@RequiredArgsConstructor
public class UserAuthorityController {
    private final UserAuthorityService authorityService;

    @GetMapping
    public ResponseDto<List<UserAuthorityDto>> getAll(){
        return authorityService.getAllUserAuthority();
    }

    @GetMapping("/{id}")
    public ResponseDto<UserAuthorityDto> getById(@PathVariable Integer id){
        return authorityService.getUserAuthorityById(id);
    }

    @PostMapping
    public ResponseDto addUserAuthority(@RequestBody UserAuthorityDto userAuthorityDto){
        return authorityService.addUserAuthority(userAuthorityDto);
    }

    @PutMapping
    public ResponseDto updateUserAuthority(@RequestBody UserAuthorityDto userAuthorityDto){
        return authorityService.updateUserAuthority(userAuthorityDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteUserAuthority(@PathVariable Integer id){
        return authorityService.deleteUserAuthorityById(id);
    }
}
