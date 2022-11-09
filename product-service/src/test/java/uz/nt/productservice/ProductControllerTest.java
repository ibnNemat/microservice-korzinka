//package uz.nt.productservice;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.json.JsonMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import shared.libs.dto.JWTResponseDto;
//import shared.libs.dto.ProductDto;
//import shared.libs.dto.ResponseDto;
//import uz.nt.productservice.dto.LoginDto;
//import uz.nt.productservice.feign.UserFeign;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
////@Slf4j
////@SpringBootTestgBootTest
////@AutoConfigureMockMvc
////@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ProductControllerTest {
//
////    @Autowired
//    private MockMvc mvc;
//
////    @Autowired
//    private UserFeign feign;
//
//    private static String token;
//
//    private ObjectMapper objectMapper;
//
//    private JsonMapper jsonMapper;
//
////    @PostConstruct
//    public void injecting(){
//        objectMapper = new ObjectMapper();
//        jsonMapper = new JsonMapper();
//    }
//
////    @Test
////    @Order(1)
//    public void unauthorized(){
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/1");
//        try {
//            mvc.perform(requestBuilder)
//                    .andExpect(MockMvcResultMatchers.status().isForbidden());
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
////    @Test
////    @Order(2)
//    public void getToken(){
//        LoginDto loginDto = LoginDto.builder()
//                .username("sardorbroo").password("password").build();
//
//        ResponseDto<JWTResponseDto> response = feign.token(loginDto);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(true, response.getSuccess());
//        Assertions.assertNotNull(response.getResponseData());
//        Assertions.assertInstanceOf(JWTResponseDto.class, response.getResponseData());
//        Assertions.assertTrue(response.getResponseData().toString().contains("token"));
//
//        token = response.getResponseData().getToken();
//    }
//
////    @Test
////    @Order(3)
//    public void dataIsNotFound(){
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8003/product/8")
//                .contentType("application/json")
//                .accept("application/json")
//                .header("Authorization", "Bearer " + token);
//        String response = null;
//        try {
//            response = mvc.perform(requestBuilder)
//                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                    .andReturn()
//                    .getResponse()
//                    .getContentAsString();
//
//            ResponseDto responseDto = jsonMapper.readValue(response, ResponseDto.class);
//
//            Assertions.assertNotNull(responseDto);
//            Assertions.assertFalse(responseDto.getSuccess());
//            Assertions.assertEquals("Data is not found!", responseDto.getMessage());
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
////    @Test
////    @Order(4)
//    public void addNewProductTest(){
//        SimpleDateFormat format = new SimpleDateFormat();
//        Date date = null;
//        try {
//            date = format.parse("2022-10-31");
//
//
//            ProductDto dto = ProductDto.builder()
//                    .name("Coca Cola")
//                    .amount(72.0)
//                    .price(12000.0)
//                    .active(true)
//                    .caption("This is Coca Cola's caption.")
//                    .createdAt(date)
//                    .build();
//
////        JsonMapper jsonMapper = new JsonMapper();
//            String content = null;
//
//            content = jsonMapper.writeValueAsString(dto);
//
//
//            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/")
//                            .contentType("application/json")
//                            .accept("application/json")
//                            .header("Authorization", "Bearer " + token)
//                            .content(content);
//
//            String response = null;
//                response = mvc
//                        .perform(requestBuilder)
//                        .andExpect(MockMvcResultMatchers.status().isConflict())
//                        .andReturn()
//                        .getResponse()
//                        .getContentAsString();
//
//
//
//            ResponseDto<ProductDto> responseDto =
//                    jsonMapper.readValue(response, new TypeReference<ResponseDto<ProductDto>>() {});
//
//            Assertions.assertNotNull(responseDto);
//            Assertions.assertEquals(false, responseDto.getSuccess());
//            Assertions.assertNull(responseDto.getResponseData());
//
//        } catch (ParseException e) {
//            log.error("[Error while parsing String to Date] Current thread:{} | Current time: {} | Exception message: {} | Cause: {}",
//                    Thread.currentThread().getName(), System.currentTimeMillis(),e.getMessage(), e.getCause());
//        } catch (JsonProcessingException e) {
//            log.error("[Error while parsing Object to String] Current thread:{} | Current time: {} | Exception message: {} | Cause: {}",
//                    Thread.currentThread().getName(), System.currentTimeMillis(),e.getMessage(), e.getCause());
//        } catch (Exception e) {
//            log.error("[Exception] Current thread:{} | Current time: {} | Exception message: {} | Cause: {}",
//                    Thread.currentThread().getName(), System.currentTimeMillis(), e.getMessage(), e.getCause());
//        }finally{
//            log.info("[Test was successfully]");
//        }
//    }
//}