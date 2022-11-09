package uz.nt.cashbackservice.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shared.libs.dto.CashbackHistoryDto;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@PropertySource(value = "classpath:/application-test.properties")
public class CashbackHistoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserClient userClient;

    private static String token;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Sql(value = "classpath:/cashback-history-integration.sql")
    @DisplayName(value = "Get history by card id")
    @Test
    @Order(1)
    void getByCardId() throws Exception {
        int cardId = 1;
        LoginDto loginDto = LoginDto.builder().username("abdumalik").password("abdumalik").build();

        token = userClient.login(loginDto).getResponseData().getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.GET, "/history/get-by-card-id")
                .header("Authorization","Bearer ".concat(token))
                .param("cardId", Integer.toString(cardId));

        String response = mockMvc.perform(requestBuilder)
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();


        ObjectReader reader = mapper.readerFor(ResponseDto.class);

        ResponseDto<List<CashbackHistoryDto>> listResponseDto = reader.readValue(response);

        Assertions.assertNotNull(listResponseDto);
        Assertions.assertNotNull(listResponseDto.getResponseData());
        Assertions.assertEquals(6, listResponseDto.getResponseData().size());
        Assertions.assertTrue(listResponseDto.getSuccess());

    }



    @DisplayName(value = "Get history by card id and between")
    @Test
    @Order(2)
    void getBetween() throws Exception {

        int cardId = 1;
        String from = "2022-01-15";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.GET, "/history/get-between")
                .header("Authorization","Bearer ".concat(token))
                .param("cardId", String.valueOf(cardId))
                .param("date", from);

        String response = mockMvc.perform(requestBuilder)
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

        ResponseDto<List<CashbackHistoryDto>> responseDto = mapper.readerFor(ResponseDto.class).readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals(3, responseDto.getResponseData().size());

    }


    @Test
    @DisplayName(value = "Delete history by card id")
    @Order(3)
    void deleteByCardId() throws Exception {
        int cardId = 1;

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.DELETE, "/history/delete-by-card-id")
                .header("Authorization", "Bearer ".concat(token))
                .param("cardId", Integer.toString(cardId));

        String response = mockMvc.perform(requestBuilder)
                                    .andDo(print())
                                    .andExpect(status().isOk())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        ResponseDto<Boolean> responseDto = mapper.readerFor(ResponseDto.class).readValue(response);

        Assertions.assertNotNull(responseDto);
        Assertions.assertTrue(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
    }



}
