package uz.nt.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "distance",url = "https://distance-calculator1.p.rapidapi.com/v1")
public interface MapClient {
//    @GetMapping
//
}
