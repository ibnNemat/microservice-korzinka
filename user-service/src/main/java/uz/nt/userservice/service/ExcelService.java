package uz.nt.userservice.service;

import shared.libs.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

public interface ExcelService {

    void export(Stream<UserDto> userDtos, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
