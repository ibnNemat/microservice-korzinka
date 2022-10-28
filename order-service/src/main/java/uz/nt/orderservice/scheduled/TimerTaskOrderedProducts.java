package uz.nt.orderservice.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.repository.OrderedProductsRedisRepository;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TimerTaskOrderedProducts {

    private final OrderRepository orderRepository;
    private final OrderedProductsRedisRepository redis;
    private final ProductClient productClient;

    public void holdingTheOrderForFifteenMinutes(Integer orderId, Integer userId){

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Optional<Integer> optional = orderRepository.findByIdAndUserIdAndPayedIsFalse(orderId, userId);
                if (optional.isPresent()){
                    orderRepository.deleteById(orderId);

                    Optional<List<OrderedProductsDetail>> optionalList = redis.findById(orderId);
                    if (optionalList.isPresent()) {
                        productClient.addProductAmountBackWard(optionalList.get());
                        redis.deleteById(orderId);
                    }
                }

                timer.cancel();
                timer.purge();
            }
        };

        Date date = new Date(System.currentTimeMillis() + 10 * 60 * 60 * 15);
        timer.schedule(timerTask, date);

    }

}
