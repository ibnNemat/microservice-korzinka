package uz.nt.userservice;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.userservice.dto.LoginDto;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;
    private String username = "fotima";
    private String password = "Aa12345";

    @Order(0)
    @Test
    public void addUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = new UserDto();
        userDto.setFirstname("Fotima");
        userDto.setLastname("Qosimov");
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setEmail("abdullohqwe@gmail.com");
        userDto.setCreated_at(new Date());
        userDto.setIsActive(false);

        String user = objectMapper.writeValueAsString(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user);

        String responseBody = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader objectReader = objectMapper.readerFor(new TypeReference<ResponseDto<UserDto>>() {});
        ResponseDto<UserDto> responseDto = objectReader.readValue(responseBody);
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertNotNull(responseDto.getResponseData());
    }

    @Order(1)
    @Test
    public void getToken() throws Exception {
        LoginDto loginDto = new LoginDto(username,password);

        String content = new ObjectMapper().writeValueAsString(loginDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        String responseBody = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = new ObjectMapper().readerFor(new TypeReference<ResponseDto<JWTResponseDto>>() {
        });

        ResponseDto<JWTResponseDto> responseDto = reader.readValue(responseBody);

        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertNotNull(responseDto.getResponseData());
        token = responseDto.getResponseData().getToken();
    }

    @Order(3)
    @Test
    public void deleteUser() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete")
                .param("username",username).header("Authorization","Bearer ".concat(token));

        String response = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader objectReader = new ObjectMapper().readerFor(new TypeReference<ResponseDto<Integer>>() {
        });
        ResponseDto<Integer> responseDto = objectReader.readValue(response);
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertEquals(responseDto.getResponseData(),1);
    }

}
