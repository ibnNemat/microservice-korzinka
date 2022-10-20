package com.example.userservice.controller;

import com.example.userservice.service.AuthorityService;
import com.example.userservice.service.UserAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.AuthorityDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserAuthorityDto;

import java.util.List;

@RestController
@RequestMapping("/user_authority")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService authorityService;


    @GetMapping
    public ResponseDto<List<AuthorityDto>> getAll(){
        return authorityService.getAllAuthority();
    }

    @GetMapping("/{id}")
    public ResponseDto<AuthorityDto> getById(@PathVariable Integer id){
        return authorityService.getAuthorityById(id);
    }

    @PostMapping
    public ResponseDto addAuthority(@RequestBody AuthorityDto authorityDto){
        return authorityService.addAuthority(authorityDto);
    }

    @PutMapping
    public ResponseDto updateAuthority(@RequestBody AuthorityDto authorityDto){
        return authorityService.updateAuthority(authorityDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteAuthority(@PathVariable Integer id){
        return authorityService.deleteAuthorityById(id);
    }
}
