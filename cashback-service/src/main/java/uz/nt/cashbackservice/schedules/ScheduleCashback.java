package uz.nt.cashbackservice.schedules;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.feign.OrderClient;
import uz.nt.cashbackservice.feign.UserClient;
//import uz.nt.cashbackservice.service.Main.CashbackCardService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ScheduleCashback {

    private final OrderClient orderClient;
    private final UserClient userClient;

//    private final CashbackCardService cashbackCardService;

    @Scheduled(cron = "0 0 4 1 * *")
    public void increaseCashbackMoreThanMillion(){
         ResponseDto<HashMap<Integer, Double>> responseDto = orderClient.getUsersBoughtMoreMillion(new Date());
         //user va xarid qilingan miqdor
         HashMap<Integer, Double> usersAndMoneys = responseDto.getResponseData();
         //userlarning idsi
         List<Integer> usersId = responseDto.getResponseData().keySet().stream().toList();
         //carta va user idlari. Key carta id - Value user id
         HashMap<Integer, Integer> cardsAndUsersId = userClient.getListCashbackCardId(usersId).getResponseData();

         cardsAndUsersId.forEach((k, v) -> {
             Double money = usersAndMoneys.get(v);
             Integer cardId = k;
//             cashbackCardService.increaseCashbackForMoreBought(cardId, money);
         });

    }


    @Scheduled(cron = "0 0 4 1 */3 *")
    public void increaseCashbackMoreThanThreeMillion(){
        ResponseDto<HashMap<Integer, Double>> responseDto = orderClient.getUsersBoughtMoreMillion(new Date());

        HashMap<Integer, Double> usersAndMoneys = responseDto.getResponseData();

        List<Integer> usersId = responseDto.getResponseData().keySet().stream().toList();

        HashMap<Integer, Integer> cardsAndUsersId = userClient.getListCashbackCardId(usersId).getResponseData();

        cardsAndUsersId.forEach((k, v) -> {
            Double money = usersAndMoneys.get(v);
            Integer cardId = k;
//            cashbackCardService.increaseCashbackForMoreBought(cardId, money);
        });
    }

}
