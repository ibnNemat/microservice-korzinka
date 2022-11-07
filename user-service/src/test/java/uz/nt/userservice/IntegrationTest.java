package uz.nt.userservice;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.LoginDto;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static String token;

    @Order(1)
    @Test
    void getToken() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String content = objectMapper.writeValueAsString(LoginDto.builder()
                .username("sardorbroo")
                .password("password")
                .build());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType("application/json")
                .content(content);

        String res = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonMapper jsonMapper = new JsonMapper();

        ObjectReader objectReader = jsonMapper.readerFor(new TypeReference<ResponseDto<JWTResponseDto>>(){});

        ResponseDto<JWTResponseDto> responseDto = objectReader.readValue(res);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertInstanceOf(responseDto.getResponseData().getClass(),new JWTResponseDto());

        JWTResponseDto jwtResponseDto = responseDto.getResponseData();

        token = jwtResponseDto.getToken();

    }





}
