package uz.nt.deliveryservice.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.DistanceResponse;
import shared.libs.dto.distance.DistanceInfo;

@FeignClient(value = "distance-service", url = "https://api.distancematrix.ai/maps/api")
public interface RapidClient {

    @GetMapping("/distancematrix/json")
    DistanceInfo getResponseBetweenTwoPoints(@RequestParam(value = "origins") String origins,
                                             @RequestParam(value = "destinations") String destinations,
                                             @RequestParam(value = "departure_time") String departure_time,
                                             @RequestParam(value = "key") String key);
}
