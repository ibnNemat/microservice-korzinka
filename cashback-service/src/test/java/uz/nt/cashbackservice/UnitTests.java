package uz.nt.cashbackservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackCard;
import uz.nt.cashbackservice.service.Impl.CashbackCardServiceImpl;

@SpringBootTest
public class UnitTests {
    @Autowired
    private CashbackCardServiceImpl cashbackCardService;

    @Test
    void getCashbackByIdTest(){
        ResponseDto<CashbackCard> responseDto=cashbackCardService.getCashbackById(-2);
        Assertions.assertEquals(responseDto.getSuccess(),false);


    }
}
