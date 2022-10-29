package uz.nt.deliveryservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.libs.dto.distance.DistanceInfo;
import uz.nt.deliveryservice.client.DistanceClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
class DeliveryServiceApplicationTests {

    @Autowired
    private DistanceClient rapidClient;

    @Test
    void checkRapidAPITwoPointsDistance() {
        String origins = "41.3223951,69.1763972";
        String destinations = "41.2858806,69.2035627";
//        41.3043857,69.1974312
        String departure_time = "now";
        String key = "HqwFc7vSeUYvF7vvdgDkWCA0Xzpuc";

        try {
            DistanceInfo distanceInfo = rapidClient.getResponseBetweenTwoPoints(
                            origins,
                            destinations,
                            departure_time,
                            key
                    );
            Assertions.assertNotNull(distanceInfo);
            System.out.println(distanceInfo.getDestination_addresses().toString());
            System.out.println(distanceInfo.getOrigin_addresses().toString());
            System.out.println(distanceInfo.getRows().get(0).getElements().get(0).getDistance().toString());
            System.out.println(distanceInfo.getRows().get(0).getElements().get(0).getDuration().toString());
            System.out.println(distanceInfo.getRows().get(0).getElements().get(0).getDuration_in_traffic().toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void distance() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://distance-calculator1.p.rapidapi.com/v1/getdistance?start_lat=41.263018&start_lng=69.222865&end_lat=41.274143&end_lng=69.20499&unit=kilometers"))
                .header("X-RapidAPI-Key", "8ecd8a14d1mshc3743e65d6fee2ep1b1d00jsnd346fdad2f20")
                .header("X-RapidAPI-Host", "distance-calculator1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

}
