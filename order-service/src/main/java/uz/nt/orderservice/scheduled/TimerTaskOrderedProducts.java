package uz.nt.orderservice.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import shared.libs.dto.UserDto;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.OrderedProductsRedis;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.repository.OrderedProductsRedisRepository;

import java.util.*;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class TimerTaskOrderedProducts {
    private final OrderRepository orderRepository;
    private final OrderedProductsRedisRepository redis;
    private final ProductClient productClient;

    public void holdingTheOrderForFifteenMinutes(Integer orderId){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto userDto) {
                    Integer userId = userDto.getId();
                    Optional<Integer> optional = orderRepository.findByIdAndUserIdAndPayedIsFalse(orderId, userId);

                    if (optional.isPresent()) {
                        orderRepository.deleteById(orderId);

                        Optional<OrderedProductsRedis> optionalRedis = redis.findById(orderId);
                        if (optionalRedis.isPresent()) {
                            OrderedProductsRedis orderedProductsRedis = optionalRedis.get();
                            // TODO: Sardor ProductControllerda shunaqa endPoint
                            //  ochib productAmountni qaytatib olishi kerak
                            productClient.addProductAmountBackWard(orderedProductsRedis.getOrderedProductsList());
                            redis.deleteById(orderId);
                        }
                    }

                    timer.cancel();
                    timer.purge();
                }
            }
        };

        Date date = new Date(System.currentTimeMillis() + 10 * 60 * 60 * 15);
        timer.schedule(timerTask, date);

    }
}
