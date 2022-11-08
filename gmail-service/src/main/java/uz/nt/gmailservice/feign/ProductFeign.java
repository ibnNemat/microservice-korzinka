package uz.nt.gmailservice.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "localhost:8002/product-api/")
public class ProductFeign {
}
