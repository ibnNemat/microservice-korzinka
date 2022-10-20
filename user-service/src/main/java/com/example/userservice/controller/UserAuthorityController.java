package com.example.userservice.controller;

import com.example.userservice.service.UserAuthorityService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserAuthorityDto;

import java.util.List;

@RestController
@RequestMapping("/user_authority")
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
