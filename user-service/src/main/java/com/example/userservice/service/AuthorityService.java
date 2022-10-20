package com.example.userservice.service;

import shared.libs.dto.AuthorityDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserAuthorityDto;

import java.util.List;

public interface AuthorityService {
    ResponseDto<List<AuthorityDto>> getAllAuthority();

    ResponseDto<AuthorityDto> getAuthorityById(Integer id);

    ResponseDto deleteAuthorityById(Integer id);

    ResponseDto updateAuthority(AuthorityDto authorityDto);

    ResponseDto addAuthority(AuthorityDto authorityDto);
}
