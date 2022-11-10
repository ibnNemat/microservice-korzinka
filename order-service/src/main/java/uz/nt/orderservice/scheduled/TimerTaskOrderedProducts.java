package uz.nt.orderservice.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.entity.OrderedProductsRedis;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.repository.OrderedProductsRedisRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class TimerTaskOrderedProducts {
    private final OrderRepository orderRepository;
    private final OrderedProductsRedisRepository redis;
    private final ProductClient productClient;

    public void holdingTheOrderForFifteenMinutes(Integer orderId, Integer userId){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto userDto) {
//                    Integer userId = userDto.getId();
                //todo exists bilan tekshirish
                    Optional<Integer> optional = orderRepository.findByIdAndUserIdAndPayedIsFalse(orderId, userId);

                    if (optional.isPresent()) {
                        orderRepository.deleteById(orderId);

                        Optional<OrderedProductsRedis> optionalRedis = redis.findById(orderId);
                        if (optionalRedis.isPresent()) {
                            OrderedProductsRedis orderedProductsRedis = optionalRedis.get();
                            // TODO: Sardor ProductControllerda shunaqa endPoint
                            //  ochib productAmountni qaytatib olishi kerak
//                            productClient.addProductAmountBackWard(orderedProductsRedis.getOrderedProductsList());
                            redis.deleteById(orderId);
                        }
                    }

                    timer.cancel();
                    timer.purge();
//                }
            }
        };

        Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 15);
        timer.schedule(timerTask, date);

    }
}
