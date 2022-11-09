package uz.nt.cashbackservice.schedules;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.feign.OrderClient;
import uz.nt.cashbackservice.service.Main.CashbackCardService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@EnableScheduling
@Configuration



@RequiredArgsConstructor
public class ScheduleCashback {

    private final OrderClient orderClient;
    private final CashbackCardService cashbackCardService;

    @Scheduled(cron = "0 0 4 1 * *")
    public void increaseCashbackMoreThanMillion(){
         ResponseDto<HashMap<Integer, Double>> responseDto = orderClient.getUsersBoughtMoreMillion();
         HashMap<Integer, Double> usersAndMoneys = responseDto.getResponseData();

         usersAndMoneys.forEach(cashbackCardService::increaseCashbackForMoreBought);
    }


    @Scheduled(cron = "0 0 4 1 */3 *")
    public void increaseCashbackMoreThanThreeMillion(){
        ResponseDto<HashMap<Integer, Double>> responseDto = orderClient.getUsersBoughtMoreMillion();
        HashMap<Integer, Double> usersAndTheirMoneys = responseDto.getResponseData();

        usersAndTheirMoneys.forEach(cashbackCardService::increaseCashbackForMoreBought);
    }

}
