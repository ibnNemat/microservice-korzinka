package uz.nt.gmailservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import shared.libs.dto.ProductDto;
import uz.nt.gmailservice.config.FeignConfig;

import java.util.List;

@FeignClient(value = "product-service",url = "localhost:8002/product-api/",configuration = FeignConfig.class)
public interface ProductFeign {
    @GetMapping()
    public List<ProductDto> getLiked();
}
