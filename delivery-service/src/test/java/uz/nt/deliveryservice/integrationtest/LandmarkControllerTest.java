//package uz.nt.deliveryservice.integrationtest;
//
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import shared.libs.dto.JWTResponseDto;
//import shared.libs.dto.ResponseDto;
//import uz.nt.deliveryservice.client.UserClient;
//import uz.nt.userservice.dto.LoginDto;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class LandmarkControllerTest {
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
//    public void getAllByCityId() throws Exception {
//        int id = 2;
//
//        mockMvc.perform(get("/landmark/{id}", id)
//                        .header("Authorization", "Bearer "+token))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//}
