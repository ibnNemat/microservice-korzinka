package uz.nt.cashbackservice.unit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.feign.UserClient;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CashbackServceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserClient userClient;

    private static String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

    @Test
    @Order(0)
    public void getCashbackCardId() {

    }
// TODO ishledigon method yog'akan


}
