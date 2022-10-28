package uz.nt.cashbackservice.service.Impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.service.Main.CashbackCardService;

@SpringBootTest
class CashbackCardServiceImplTest {


    @Autowired
    private CashbackCardService cashbackCardService;

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


    @DisplayName(value = "Negative. Check get cashback by id")
    @Test
    void secondGetCashbackById() {
        MockHttpServletRequest requestEnglish = new MockHttpServletRequest();
        requestEnglish.addHeader("Accept-Language", "uz");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.getCashbackById(null, requestEnglish);
        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("Ma`lumot topilmadi", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());

    }


    @DisplayName(value = "Positive. Check get cashback by cardId")
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

    @DisplayName(value = "Negative. Check get cashback by cardId")
    @Test
    void secondGetCashbackCardByUserId() {
        Integer userId = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "ru");

        ResponseDto<CashbackCardDto> responseDto = cashbackCardService.getCashbackCardByUserId(userId, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertEquals("Informatsiya ne naydeno", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());
    }



    @DisplayName(value = "Positive. Check increase cashback of user")
    @Test
    void firstIncreaseCashbackForUser() {
        Integer userId = 1;
        Double cashback = 45000.0;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en");

        ResponseDto<Boolean> responseDto = cashbackCardService.subtractUserCashback(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertTrue(responseDto.getResponseData());
        Assertions.assertEquals("Operation done successfully", responseDto.getMessage());
        Assertions.assertEquals(200, responseDto.getCode());
    }



    @DisplayName(value = "Negative. Check increase cashback of user")
    @Test
    void secondIncreaseCashbackForUser() {
        Integer userId = null;
        Double cashback = 20000.0;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en");

        ResponseDto<Boolean> responseDto = cashbackCardService.subtractUserCashback(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertFalse(responseDto.getResponseData());
        Assertions.assertEquals("An error has occurred", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());
    }



    @DisplayName(value = "Positive. Check subtraction cashback of user")
    @Test
    void firstSubtractUserCashback() {
        Integer userId = 1;
        Double cashback = 15000.0;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "uz");

        ResponseDto<Boolean> responseDto = cashbackCardService.subtractUserCashback(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertTrue(responseDto.getResponseData());
        Assertions.assertEquals("Operatsiya muvaffaqiyatli bajarildi", responseDto.getMessage());
        Assertions.assertEquals(200, responseDto.getCode());
    }


    @DisplayName(value = "Negative. Check subtraction cashback of user")
    @Test
    void secondSubtractUserCashback() {
        Integer userId = 34;
        Double cashback = 20000.0;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "uz");

        ResponseDto<Boolean> responseDto = cashbackCardService.subtractUserCashback(userId, cashback, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertFalse(responseDto.getSuccess());
        Assertions.assertFalse(responseDto.getResponseData());
        Assertions.assertEquals("Xatolik yuz berdi", responseDto.getMessage());
        Assertions.assertEquals(-1, responseDto.getCode());
    }


    @DisplayName(value = "Positive. Delete cashback by card id")
    @Test
    void firstDeleteCashBackCardById() {
        Integer cardId = 1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-language", "ru");

        ResponseDto<Boolean> responseDto = cashbackCardService.deleteCashBackCardById(cardId, request);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.getResponseData());
        Assertions.assertTrue(responseDto.getSuccess());
        Assertions.assertEquals(200, responseDto.getCode());
        Assertions.assertEquals("Operatsiya vipolnena uspeshno", responseDto.getMessage());
    }


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
        Assertions.assertEquals("Informatsiya ne naydeno", responseDto.getMessage());
    }

    @Test
    void addCashback() {
    }

    @Test
    void deleteCashbackCardIdByUserId() {
    }

    @Test
    void increaseCashbackForMoreBought() {
    }
}