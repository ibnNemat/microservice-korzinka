package uz.nt.cashbackservice.service.Impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.LoginDto;
import uz.nt.cashbackservice.feign.UserClient;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//todo| Bu testni ishlatish uchun Cloud-service va User-service ishlab turishi kerak.
//todo| Test databaza ochib, 'SQL-script'-dagi sessionni ana o'sha databazaga ulash kerak
//todo| Va test-databazaning nomi 'application-test.properties' dagi nom bilan bir-xil bo'lsin

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserClient userClient;

    private static String token;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Sql(value = "classpath:/cashback-card-integration.sql")
    @DisplayName(value = "Add cashback card")
    @Test
    @Order(1)
    void firstAddCashback() throws Exception {

        LoginDto loginDto = LoginDto.builder().username("abdumalik").password("abdumalik").build();
        ResponseDto<JWTResponseDto> responseDto = userClient.login(loginDto);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertInstanceOf(responseDto.getResponseData().getClass(), new JWTResponseDto());

        token = responseDto.getResponseData().getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cashback")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer ".concat(token))
                                .header("Accept-language", "uz");

        String response = mockMvc.perform(requestBuilder)
                                    .andDo(print())
                                    .andExpect(status().is(200))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();



        ObjectReader reader = mapper.readerFor(ResponseDto.class);

        ResponseDto<CashbackCardDto> responseCashback = reader.readValue(response);

        Assertions.assertNotNull(responseCashback);
        Assertions.assertNotNull(responseCashback.getResponseData());
        Assertions.assertTrue(responseCashback.getSuccess());
        Assertions.assertEquals("Muvaffaqiyatli qo`shildi", responseCashback.getMessage());
    }



    @Test
    @Order(2)
    @DisplayName(value = "Positive. Get cashback by it's id")
    void firstGetCashbackById() throws Exception {

        Integer cashbackCardId = 1;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cashback/get-by-id")
                .param("cardId", String.valueOf(cashbackCardId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "en");

        String response = mockMvc.perform(requestBuilder)
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<CashbackCardDto> dtoResponseDto = reader.readValue(response);

        Assertions.assertNotNull(dtoResponseDto);
        Assertions.assertNotNull(dtoResponseDto.getResponseData());
        Assertions.assertTrue(dtoResponseDto.getSuccess());
        Assertions.assertEquals("Data found", dtoResponseDto.getMessage());

    }




    @Test
    @Order(3)
    @DisplayName(value = "Negative. Get cashback card by id")
    void secondGetCashbackById() throws Exception {
        Integer cashbackCardId = 2;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cashback/get-by-id")
                .param("cardId", String.valueOf(cashbackCardId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "ru");

        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<CashbackCardDto> dtoResponseDto = reader.readValue(response);

        Assertions.assertNotNull(dtoResponseDto);
        Assertions.assertNull(dtoResponseDto.getResponseData());
        Assertions.assertEquals(-2, dtoResponseDto.getCode());
        Assertions.assertFalse(dtoResponseDto.getSuccess());
        Assertions.assertEquals("Informatsiya ne naydeno", dtoResponseDto.getMessage());

    }



    @Test
    @Order(4)
    @DisplayName(value = "Positive. Get cashback card by user-id")
    void firstGetCashbackByUserId() throws Exception {
        Integer userId = 1;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cashback/get-by-id")
                .param("cardId", String.valueOf(userId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "ru");

        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<CashbackCardDto> dtoResponseDto = reader.readValue(response);

        Assertions.assertNotNull(dtoResponseDto);
        Assertions.assertNotNull(dtoResponseDto.getResponseData());
        Assertions.assertTrue(dtoResponseDto.getSuccess());
        Assertions.assertEquals("Informatsiya naydeno", dtoResponseDto.getMessage());

    }



    @Test
    @Order(5)
    @DisplayName(value = "Negative. Get cashback card by user-id")
    void secondGetCashbackByUserId() throws Exception {
        Integer cashbackCardId = 2;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cashback/get-by-id")
                .param("cardId", String.valueOf(cashbackCardId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "ru");

        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<CashbackCardDto> dtoResponseDto = reader.readValue(response);

        Assertions.assertNotNull(dtoResponseDto);
        Assertions.assertNull(dtoResponseDto.getResponseData());
        Assertions.assertEquals(-2, dtoResponseDto.getCode());
        Assertions.assertFalse(dtoResponseDto.getSuccess());
        Assertions.assertEquals("Informatsiya ne naydeno", dtoResponseDto.getMessage());

    }




    @Test
    @Order(6)
    @DisplayName(value = "Positive. Increase amount cashback")
    void firstIncreaseCashback() throws Exception {
        int userId = 1;
        double amount = 1000_000D;

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("userId", List.of(Integer.toString(userId)));
        multiValueMap.put("cashbackAmount", List.of(Double.toString(amount)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cashback/calculate-cashback")
                .params(multiValueMap)
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "en");



        String response = mockMvc.perform(requestBuilder)
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

        ObjectReader reader = mapper.readerFor(new TypeReference<ResponseDto<CashbackCardDto>>() {});
        ResponseDto<CashbackCardDto> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals("Operation done successfully", responseDto.getMessage());
        Assertions.assertEquals(15000D, responseDto.getResponseData().getAmount());

    }







    @Test
    @Order(7)
    @DisplayName(value = "Negative. Increase amount cashback")
    void secondIncreaseCashback() throws Exception {
        int userId = 2;
        double amount = 1000_000D;

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("userId", List.of(Integer.toString(userId)));
        multiValueMap.put("cashbackAmount", List.of(Double.toString(amount)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cashback/calculate-cashback")
                .params(multiValueMap)
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "en");



        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(new TypeReference<ResponseDto<CashbackCardDto>>() {});
        ResponseDto<CashbackCardDto> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("Data not found", responseDto.getMessage());

    }





    @Test
    @Order(8)
    @DisplayName(value = "Positive. Subtract amount cashback")
    void firstSubtractCashback() throws Exception {
        int userId = 1;
        double cashbackAmount = 3000D;

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("userId", List.of(Integer.toString(userId)));
        multiValueMap.put("cashbackAmount", List.of(Double.toString(cashbackAmount)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cashback/subtract")
                .params(multiValueMap)
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "uz");



        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(new TypeReference<ResponseDto<CashbackCardDto>>() {});
        ResponseDto<CashbackCardDto> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals("Operatsiya muvaffaqiyatli bajarildi", responseDto.getMessage());
        Assertions.assertEquals(12000D, responseDto.getResponseData().getAmount());

    }



    @Test
    @Order(9)
    @DisplayName(value = "Negative. Subtract amount cashback")
    void secondSubtractCashback() throws Exception {
        int userId = 2;
        double cashbackAmount = 3000D;

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("userId", List.of(Integer.toString(userId)));
        multiValueMap.put("cashbackAmount", List.of(Double.toString(cashbackAmount)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/cashback/subtract")
                .params(multiValueMap)
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "uz");



        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(new TypeReference<ResponseDto<CashbackCardDto>>() {});
        ResponseDto<CashbackCardDto> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("Ma`lumot topilmadi", responseDto.getMessage());
    }



    @Test
    @Order(10)
    @DisplayName(value = "Positive. Delete card by it's id")
    void firstDeleteCardById() throws Exception {
        int cardId = 1;

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.DELETE, "/cashback/del-by-id")
                .param("cardId", String.valueOf(cardId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "uz");

        String response = mockMvc.perform(requestBuilder)
                                .andDo(print())
                                .andExpect(status().is(200))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<Boolean> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertTrue(responseDto.getResponseData());
        Assertions.assertEquals("Operatsiya muvaffaqiyatli bajarildi", responseDto.getMessage());

        firstAddCashback();
    }




    @Test
    @Order(11)
    @DisplayName(value = "Negative. Delete card by it's id")
    void secondDeleteCardById() throws Exception {
        int cardId = 11;

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.DELETE, "/cashback/del-by-id")
                .param("cardId", String.valueOf(cardId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "uz");

        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<Boolean> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertFalse(responseDto.getResponseData());
        Assertions.assertEquals("Ma`lumot topilmadi", responseDto.getMessage());
        Assertions.assertEquals(-2, responseDto.getCode());

    }


    @Test
    @Order(12)
    @DisplayName(value = "Positive. Delete card by user id")
    void firstDeleteCardByUserId() throws Exception {
        int userId = 1;

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.DELETE, "/cashback/del-by-userid")
                .param("userId", String.valueOf(userId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "uz");

        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<Boolean> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertTrue(responseDto.getResponseData());
        Assertions.assertEquals("Operatsiya muvaffaqiyatli bajarildi", responseDto.getMessage());
    }




    @Test
    @Order(13)
    @DisplayName(value = "Negative. Delete card by user id")
    void secondDeleteCardByUserId() throws Exception {
        int userId = 120;

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.DELETE, "/cashback/del-by-userid")
                .param("userId", String.valueOf(userId))
                .header("Authorization", "Bearer ".concat(token))
                .header("Accept-language", "uz");

        String response = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = mapper.readerFor(ResponseDto.class);
        ResponseDto<Boolean> responseDto = reader.readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertFalse(responseDto.getResponseData());
        Assertions.assertEquals("Ma`lumot topilmadi", responseDto.getMessage());
        Assertions.assertEquals(-2, responseDto.getCode());
    }


}




















