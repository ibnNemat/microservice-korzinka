package uz.nt.orderservice.client;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.CardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "http://localhost:8001/user-api", name = "user-service", configuration = FeignConfiguration.class)
public interface UserCardClient {
    @GetMapping("/cards/by-id/{cardId}")
    ResponseDto<CardDto> getCardById(@PathVariable Integer cardId);

    @PutMapping("/cards")
    ResponseDto updateCard(@RequestBody CardDto cardDto);
}
