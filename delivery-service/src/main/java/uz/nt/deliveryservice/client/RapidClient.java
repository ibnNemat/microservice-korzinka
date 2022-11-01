package uz.nt.deliveryservice.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.DistanceResponse;

@FeignClient(value = "rapid-service", url = "https://distance-calculator1.p.rapidapi.com/v1")
public interface RapidClient {

    @GetMapping("/getdistance")
    DistanceResponse getResponseBetweenTwoPoints(@RequestParam(value = "start_lat") double startLat,
                                                 @RequestParam(value = "start_lng") double startLng,
                                                 @RequestParam(value = "end_lat") double endLat,
                                                 @RequestParam(value = "end_lng") double endLng,
                                                 @RequestParam(value = "unit", defaultValue = "kilometers") String unit,
                                                 @RequestHeader HttpHeaders headers);
}
