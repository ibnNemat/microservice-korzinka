package uz.nt.cashbackservice.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.jdbc.Sql;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.service.Main.CashbackCardService;

//todo| Test databaza ochib, 'SQL-script'-dagi sessionni ana o'sha databazaga ulash kerak
//todo| Va test-databazaning nomi 'application-test.properties' dagi nom bilan bir-xil bo'lsin
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@PropertySource(value = {"classpath:/application-test.properties"})
@Sql(value = "classpath:/cashback-card-unit.sql")
class CardUnitTest {


    @Autowired
    private CashbackCardService cashbackCardService;


    @Order(1)
    @DisplayName(value = "Positive. Check get cashback by id")
    @Test
    void firstGetCashbackById() {
        Integer cardId = 1;
        MockHttpServletRequest requestEnglish = new MockHttpServletRequest();
        requestEnglish.addHeader("Accept-Language", "ru");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.getCashbackById(cardId, requestEnglish);
        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals("Informatsiya naydeno", responseDto.getMessage());
        Assertions.assertEquals(200, responseDto.getCode());
    }




    @Order(2)
    @DisplayName(value = "Negative. Check get cashback by id")
    @Test
    void secondGetCashbackById() {
        MockHttpServletRequest requestEnglish = new MockHttpServletRequest();
        requestEnglish.addHeader("Accept-Language", "uz");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.getCashbackById(null, requestEnglish);
        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("Xatolik yuz berdi", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());

    }


    @Order(3)
    @DisplayName(value = "Positive. Check get cashback by user Id")
    @Test
    void firstGetCashbackCardByUserId() {
        Integer userId = 1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.getCashbackCardByUserId(userId, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals("Data found", responseDto.getMessage());
        Assertions.assertEquals(200, responseDto.getCode());
    }

    @Order(4)
    @DisplayName(value = "Negative. Check get cashback by user Id")
    @Test
    void secondGetCashbackCardByUserId() {
        Integer userId = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "ru");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.getCashbackCardByUserId(userId, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("Proizoshla oshibka", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());
    }



    @Order(5)
    @DisplayName(value = "Positive. Check increase cashback of user")
    @Test
    void firstIncreaseCashbackForUser() {
        Integer userId = 1;
        Double cashback = 450000D;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.increaseCashbackForUser(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals( 104500D, responseDto.getResponseData().getAmount());
        Assertions.assertEquals("Operation done successfully", responseDto.getMessage());
        Assertions.assertEquals(200, responseDto.getCode());
    }



    @Order(6)
    @DisplayName(value = "Negative. Check increase cashback of user")
    @Test
    void secondIncreaseCashbackForUser() {
        Integer userId = null;
        Double cashback = 20000D;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.increaseCashbackForUser(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("An error has occurred", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());
    }


    @Order(7)
    @DisplayName(value = "Positive. Check subtraction cashback of user")
    @Test
    void firstSubtractUserCashback() {
        Integer userId = 1;
        Double cashback = 15000D;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "uz");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.subtractUserCashback(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals( 85000D, responseDto.getResponseData().getAmount());
        Assertions.assertEquals("Operatsiya muvaffaqiyatli bajarildi", responseDto.getMessage());
        Assertions.assertEquals(200, responseDto.getCode());
    }


    @Order(8)
    @DisplayName(value = "Negative. Check subtraction cashback of user")
    @Test
    void secondSubtractUserCashback() {
        Integer userId = null;
        Double cashback = 20000D;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "uz");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.subtractUserCashback(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("Xatolik yuz berdi", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());
    }

    @Order(9)
    @DisplayName(value = "Positive. Increase cashback by card id")
    @Test
    void firstIncreaseCashbackForMoreBought() {
        Integer userId = 1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "uz");

        cashbackCardService.increaseCashbackForMoreBought(userId, 400000D);
        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.getCashbackById(1, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals( 104000D, responseDto.getResponseData().getAmount());
        Assertions.assertEquals("Ma`lumot topildi", responseDto.getMessage());
        Assertions.assertEquals(200, responseDto.getCode());

    }





    @Order(10)
    @DisplayName(value = "Positive. Delete cashback by user id")
    @Test
    void firstDeleteCashbackCardIdByUserId() {
        Integer userId = 2;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-language", "uz");

        ResponseDto<Boolean> responseDto = cashbackCardService.deleteCashbackCardIdByUserId(userId, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals(200, responseDto.getCode());
        Assertions.assertEquals("Operatsiya muvaffaqiyatli bajarildi", responseDto.getMessage());
    }

    @Order(11)
    @DisplayName(value = "Negative. Delete cashback by user id")
    @Test
    void secondDeleteCashbackCardIdByUserId() {

        Integer userId = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-language", "uz");

        ResponseDto<Boolean> responseDto = cashbackCardService.deleteCashBackCardById(userId, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals(-1, responseDto.getCode());
        Assertions.assertEquals("Xatolik yuz berdi", responseDto.getMessage());

    }



    @Order(12)
    @DisplayName(value = "Negative. Delete cashback by card id")
    @Test
    void secondDeleteCashBackCardById() {
        Integer cardId = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-language", "ru");

        ResponseDto<Boolean> responseDto = cashbackCardService.deleteCashBackCardById(cardId, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals(-1, responseDto.getCode());
        Assertions.assertEquals("Proizoshla oshibka", responseDto.getMessage());
    }



    @Order(13)
    @DisplayName(value = "Positive . Delete cashback by card id")
    @Test
    void firstDeleteCashBackCardById() {
        Integer firstCardId = 1;
        Integer secondCardId = 2;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-language", "ru");

        ResponseDto<Boolean> firstResponseDto = cashbackCardService.deleteCashBackCardById(firstCardId, request);
        ResponseDto<Boolean> secondResponseDto = cashbackCardService.deleteCashBackCardById(secondCardId, request);

        Assertions.assertNotNull(firstResponseDto);
        Assertions.assertNotNull(firstResponseDto.getResponseData());
        Assertions.assertTrue(firstResponseDto.getSuccess());
        Assertions.assertEquals(200, firstResponseDto.getCode());
        Assertions.assertEquals("Operatsiya vipolnena uspeshno", firstResponseDto.getMessage());


        Assertions.assertNotNull(secondResponseDto);
        Assertions.assertNotNull(secondResponseDto.getResponseData());
        Assertions.assertTrue(secondResponseDto.getSuccess());

    }





}
