package uz.nt.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.distance.DistanceInfo;

@FeignClient(value = "distance-service", url = "https://api.distancematrix.ai/maps/api")
public interface DistanceClient {

    @GetMapping("/distancematrix/json")
    DistanceInfo getResponseBetweenTwoPoints(@RequestParam(value = "origins") String origins,
                                             @RequestParam(value = "destinations") String destinations,
                                             @RequestParam(value = "departure_time") String departure_time,
                                             @RequestParam(value = "key") String key);
}
