//package uz.nt.deliveryservice.integrationtest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.annotation.Order;
//import org.springframework.test.web.servlet.MockMvc;
//import shared.libs.dto.JWTResponseDto;
//import shared.libs.dto.ResponseDto;
//import uz.nt.deliveryservice.client.UserClient;
//import uz.nt.deliveryservice.dto.RegionDto;
//import uz.nt.userservice.dto.LoginDto;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class RegionControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserClient userClient;
//
//    private static String token;
//
//    @Test
//    @Order(0)
//    public void getToken() {
//        LoginDto loginDto = LoginDto.builder().username("nematovsr").password("nematovsr").build();
//
//        ResponseDto<JWTResponseDto> responseDto = userClient.getToken(loginDto);
//
//        token = responseDto.getResponseData().getToken();
//    }
//
//    @Test
//    @Order(1)
//    public void getAll() throws Exception {
//        mockMvc.perform(get("/region")
//                        .header("Authorization", "Bearer "+token))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(2)
//    public void getById() throws Exception {
//        int id = 1;
//
//        mockMvc.perform(get("/region/{id}", id)
//                        .header("Authorization", "Bearer "+token))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(3)
//    public void add() throws Exception {
//        RegionDto regionDto = RegionDto.builder().name("Sirdaryo").build();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String content = objectMapper.writeValueAsString(regionDto);
//
//        mockMvc.perform(post("/region")
//                        .content(content)
//                        .contentType("application/json")
//                        .header("Authorization", "Bearer "+token))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(4)
//    public void update() throws Exception {
//        RegionDto regionDto = RegionDto.builder().id(5).name("Namangan").build();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String content = objectMapper.writeValueAsString(regionDto);
//
//        mockMvc.perform(patch("/region")
//                        .content(content)
//                        .contentType("application/json")
//                        .header("Authorization", "Bearer "+token))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(5)
//    public void deleteById() throws Exception {
//        int id = 6;
//
//        mockMvc.perform(delete("/region/{id}", id)
//                        .header("Authorization", "Bearer "+token))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//}
