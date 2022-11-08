package uz.nt.cashbackservice.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.jdbc.Sql;
import shared.libs.dto.CashbackHistoryDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackHistory;
import uz.nt.cashbackservice.service.Main.CashbackHistoryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//todo| Test databaza ochib, 'SQL-script'-dagi sessionni ana o'sha databazaga ulash kerak
//todo| Va test-databazaning nomi 'application-test.properties' dagi nom bilan bir-xil bo'lsin

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@PropertySource("classpath:/application-test.properties")
class CashbackHistoryUnitTest {

    @Autowired
    private CashbackHistoryService cashbackHistoryService;

    @Order(1)
    @DisplayName("Add cashback into history")
    @Test
    @Sql(value = "classpath:/cashback-history-unit.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void addCashbackHistory() throws ParseException {

        Date firstDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-10-2022");
        Date secondDate = new SimpleDateFormat("dd-MM-yyyy").parse("21-10-2022");
        Date thirdDate = new SimpleDateFormat("dd-MM-yyyy").parse("30-10-2022");
        Date fourthDate = new SimpleDateFormat("dd-MM-yyyy").parse("03-11-2023");

        CashbackHistory first = CashbackHistory.builder().cardId(1).beforeTransaction(5000D)
                      .transactionAmount(5000D).afterTransaction(10000D).transactionDate(firstDate).build();
        CashbackHistory second = CashbackHistory.builder().cardId(1).beforeTransaction(20000D)
                .transactionAmount(2300D).afterTransaction(23000D).transactionDate(secondDate).build();
        CashbackHistory third = CashbackHistory.builder().cardId(1).beforeTransaction(12000D)
                .transactionAmount(3000D).afterTransaction(9000D).transactionDate(thirdDate).build();
        CashbackHistory fourth = CashbackHistory.builder().cardId(1).beforeTransaction(7000D)
                .transactionAmount(5000D).afterTransaction(2000D).transactionDate(fourthDate).build();

        CashbackHistory one = cashbackHistoryService.addCashbackHistory(first);
        CashbackHistory two = cashbackHistoryService.addCashbackHistory(second);
        CashbackHistory three = cashbackHistoryService.addCashbackHistory(third);
        CashbackHistory four = cashbackHistoryService.addCashbackHistory(fourth);

        Assertions.assertEquals(first, one);
        Assertions.assertEquals(second, two);
        Assertions.assertEquals(third, three);
        Assertions.assertEquals(fourth, four);

    }


    @DisplayName("Get cashback by card id")
    @Order(2)
    @Test
    void getCashbackHistoryByCardId() throws ParseException {
        Date secondDate = new SimpleDateFormat("dd-MM-yyyy").parse("21-10-2022");

        CashbackHistoryDto expected = CashbackHistoryDto.builder().id(2).cardId(1).beforeTransaction(20000D)
                .transactionAmount(2300D).afterTransaction(23000D).transactionDate(secondDate).build();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("accept-language", "en");

        ResponseDto<List<CashbackHistoryDto>> one = cashbackHistoryService.getCashbackHistoryByCardId(1, request);

        Assertions.assertEquals(expected.getAfterTransaction(), one.getResponseData().get(1).getAfterTransaction());
        Assertions.assertEquals(4, one.getResponseData().size());
        Assertions.assertEquals(expected.getBeforeTransaction(), one.getResponseData().get(1).getBeforeTransaction());
        Assertions.assertEquals(expected.getTransactionAmount(), one.getResponseData().get(1).getTransactionAmount());
        Assertions.assertEquals("Data found", one.getMessage());

    }


    @Order(3)
    @DisplayName("Get cashback by card id and between two date")
    @Test
    void getCashbackHistoryBetween() throws ParseException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("accept-language", "ru");

        Date secondDate = new SimpleDateFormat("dd-MM-yyyy").parse("21-10-2022");
        Date thirdDate = new SimpleDateFormat("dd-MM-yyyy").parse("30-10-2022");

        CashbackHistory second = CashbackHistory.builder().cardId(1).beforeTransaction(20000D)
                .transactionAmount(2300D).afterTransaction(23000D).transactionDate(secondDate).build();
        CashbackHistory third = CashbackHistory.builder().cardId(1).beforeTransaction(12000D)
                .transactionAmount(3000D).afterTransaction(9000D).transactionDate(thirdDate).build();

        Date from = new SimpleDateFormat("dd-MM-yyyy").parse("21-10-2022");
        ResponseDto<List<CashbackHistoryDto>> dto = cashbackHistoryService.getCashbackHistoryBetween(1, from, request);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getResponseData());
        Assertions.assertEquals(2, dto.getResponseData().size());
        Assertions.assertEquals("Informatsiya naydeno", dto.getMessage());
        Assertions.assertEquals(second.getAfterTransaction(), dto.getResponseData().get(0).getAfterTransaction());
        Assertions.assertEquals(third.getAfterTransaction(), dto.getResponseData().get(1).getAfterTransaction());
    }


    @Order(4)
    @DisplayName("Delete history by cardId")
    @Test
    void deleteCashbackHistoryByCardId() {
        Integer cardId = 1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("accept-language", "ru");

        ResponseDto<Boolean> dto = cashbackHistoryService.deleteCashbackHistoryByCardId(cardId, request);

        Assertions.assertNotNull(dto);
        Assertions.assertTrue(dto.getResponseData());
        Assertions.assertTrue(dto.getSuccess());
        Assertions.assertEquals("Operatsiya vipolnena uspeshno", dto.getMessage());
    }


}


