package uz.nt.deliveryservice.service.impl;

import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.service.DeliveryService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final double startLat = 41.285834;
    private final double startLng = 69.2034906;
    @Override
    public String distanceCost(double endLat, double endLng) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://distance-calculator1.p.rapidapi.com/v1/getdistance?start_lat=41.285834&start_lng=69.2034906&end_lat=41.345036&end_lng=69.236289&unit=kilometers"))
            .header("X-RapidAPI-Key", "45f732ceebmsh9d6ce0365452961p1e92dcjsn0cb33c3d365d")
            .header("X-RapidAPI-Host", "distance-calculator1.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }

//    HttpRequest request = HttpRequest.newBuilder()
//            .uri(URI.create("https://distance-calculator1.p.rapidapi.com/v1/getdistance?start_lat=13.198989944266078&start_lng=77.70908188996312&end_lat=13.113908410795627&end_lng=77.5745906650984&unit=kilometers"))
//            .header("X-RapidAPI-Key", "45f732ceebmsh9d6ce0365452961p1e92dcjsn0cb33c3d365d")
//            .header("X-RapidAPI-Host", "distance-calculator1.p.rapidapi.com")
//            .method("GET", HttpRequest.BodyPublishers.noBody())
//            .build();
//    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//System.out.println(response.body());
}
