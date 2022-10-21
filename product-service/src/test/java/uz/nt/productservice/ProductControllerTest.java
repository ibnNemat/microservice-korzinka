package uz.nt.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shared.libs.dto.ProductDto;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create_new_product_and_check_result(){
        ProductDto productDto = ProductDto.builder()
                .build();

//        RequestBuilder request = MockMvcRequestBuilders.request(HttpMethod.POST, "/product")
//                .contentType("application/json")
//                .content();
//
//        mockMvc.perform()
    }

}