package uz.nt.productservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UnitDto;
import shared.libs.dto.UserDto;
import uz.nt.productservice.dto.LoginDto;
import uz.nt.productservice.feign.UserFeign;


@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitControllerTest {

//    @Autowired
    private MockMvc mvc;

//    @Autowired
    private UserFeign userFeign;

    private static String token;
    private static ObjectMapper objectMapper;
    private static JsonMapper jsonMapper;
    private static UserDto userDto;

//    @PostConstruct
    public void injecting(){
        objectMapper = new ObjectMapper();
        jsonMapper = new JsonMapper();

        log.info("Annotation \"PostConstruct\" is worked. Objects are injected.");
    }

//    @Test
//    @Order(1)
    public void addUser(){
        UserDto userDto = new UserDto();
        userDto.setEmail("test@gmail.com");
        userDto.setLastname("test");
        userDto.setFirstname("test");
        userDto.setPassword("password");
        userDto.setUsername("sardorbroo");

        ResponseDto<UserDto> response = userFeign.addUser(userDto);

        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getResponseData().getUsername(), userDto.getUsername());
        Assertions.assertEquals(response.getCode(), 200);

        UnitControllerTest.userDto = response.getResponseData();
    }

//    @Test
//    @Order(2)
    public void token(){
        LoginDto loginDto = LoginDto.builder()
                .username("sardorbroo").password("password").build();

        ResponseDto<JWTResponseDto> responseDto = userFeign.token(loginDto);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertEquals(true, responseDto.getSuccess());

        token = responseDto.getResponseData().getToken();
    }


//    @Test
//    @Order(4)
    public void addNewUnit(){
        UnitDto unitDto = UnitDto.builder()
                .name("Litr").shortName("L")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String entity;

        try {
            entity = objectMapper.writeValueAsString(unitDto);
        } catch (JsonProcessingException e) {
            log.error("[Error while converting DTO to String] Message: {} | Cause: {}",
                    e.getMessage(), e.getCause());

            throw new RuntimeException(e);
        }

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/unit")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .accept("application/json")
                .content(entity);
        String response;

        try {
            response = mvc
                    .perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("responseData")))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

        } catch (Exception e) {
            log.error("[Error while testing controller with MockMvc.perform()] Message: {} | Cause: {}",
                    e.getMessage(), e.getCause());

            throw new RuntimeException(e);
        }

        ObjectReader reader = jsonMapper.readerFor(new TypeReference<ResponseDto<UnitDto>>() {});

        ResponseDto<UnitDto> data;

        try {
            data = reader.readValue(response);
            Assertions.assertNotNull(data);
            Assertions.assertEquals(false, data.getSuccess());


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @Test
//    @Order(3)
//    public void checkPaginationController(){
//        Map<String, List<String>> map = Map.of(
//                "page", List.of(String.valueOf(0)),
//                "size", List.of(String.valueOf(10))
//        );
//
//        MultiValueMap<String, String> params = new MultiValueMapAdapter<>(map);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/unit/pagination")
//                                .params(params)
//                                .header("Authorization", "Bearer " + token)
//                                .accept("application/json");
//
//        String response;
//        try {
//            response = mvc
//                    .perform(requestBuilder)
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                    .andReturn()
//                    .getResponse()
//                    .getContentAsString();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        ObjectReader objectReader = jsonMapper.readerFor(new TypeReference<ResponseDto<ProductPage<UnitDto>>>() {});
//
//        ResponseDto<Page<UnitDto>> responseDto = null;
//
//        try {
//            responseDto = objectReader.readValue(response);
//
//            Assertions.assertNotNull(responseDto);
//            Assertions.assertNotNull(responseDto.getResponseData());
//            Assertions.assertEquals(true, responseDto.getSuccess());
//            Assertions.assertInstanceOf(PageRequest.class, responseDto.getResponseData());
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Order(5)
//    @Test
//    public void deleteInsertedUserAtTheEndOfTesting(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token);
//
//        ResponseDto<UserDto> deletedUser = userFeign.deleteUser(userDto.getId(), headers);
//
//        Assertions.assertTrue(deletedUser.getSuccess());
//        Assertions.assertNotNull(deletedUser.getResponseData());
//        Assertions.assertEquals(deletedUser.getResponseData().getUsername(), userDto.getUsername());
//    }
}
