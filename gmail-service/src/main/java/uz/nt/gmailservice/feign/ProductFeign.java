package uz.nt.gmailservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import shared.libs.dto.ProductDto;

import java.util.List;

@FeignClient(url = "localhost:8002/product-api/")
public interface ProductFeign {
    @GetMapping()
    public List<ProductDto> getLiked();
}
