//package uz.nt.deliveryservice;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import shared.libs.dto.DistanceResponse;
//import uz.nt.deliveryservice.client.RapidClient;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//@SpringBootTest
//class DeliveryServiceApplicationTests {
//
//    @Autowired
//    private RapidClient rapidClient;
//
//    @Test
//    void checkRapidAPITwoPointsDistance() {
//        double startLat = 41.263018D;
//        double startLng = 69.222865;
//        double endLat = 41.274143;
//        double endLng = 69.20499;
//        String unit = "kilometers";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-RapidAPI-Key", "8ecd8a14d1mshc3743e65d6fee2ep1b1d00jsnd346fdad2f20");
//        headers.add("X-RapidAPI-Host", "distance-calculator1.p.rapidapi.com");
//        headers.add("x-rapidapi-ua", "RapidAPI-Playground");
//        headers.add("sec-fetch-mode", "cors");
//        headers.add("origin", "https://rapidapi.com");
//
//        DistanceResponse response = rapidClient.getResponseBetweenTwoPoints(startLat, startLng, endLat, endLng, unit, headers);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(response.getUnit(), "kilometers");
//
//        System.out.println(response);
//    }
//
//    @Test
//    public void distance() throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://distance-calculator1.p.rapidapi.com/v1/getdistance?start_lat=41.263018&start_lng=69.222865&end_lat=41.274143&end_lng=69.20499&unit=kilometers"))
//                .header("X-RapidAPI-Key", "8ecd8a14d1mshc3743e65d6fee2ep1b1d00jsnd346fdad2f20")
//                .header("X-RapidAPI-Host", "distance-calculator1.p.rapidapi.com")
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//    }

//}
