package uz.nt.orderservice.repository.helperRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import uz.nt.orderservice.client.ProductClient;
import shared.libs.dto.OrderedProductsDetail;
import uz.nt.orderservice.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
@EnableScheduling
public class OrderProductRepositoryHelper {
    private final EntityManager entityManager;
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public List<OrderedProductsDetail> getOrderedProductDetails(Integer orderId){
        Query query = entityManager.createNativeQuery(
                "select op.productId as productId, price, amount" +
                        " from orderProducts op inner join product p on p.id = op.productId" +
                        " where op.orderId =:orderId", OrderedProductsDetail.class);
        query.setParameter("orderId", orderId);

        List<OrderedProductsDetail> productsDetailList = query.getResultList();
        return productsDetailList;
    }

//    @Transactional
//    @Scheduled(cron = "30 45 23 * * *")
//    public void removeAllNonOrderedProducts(){
//        List<Integer> orderIdList = orderRepository.getAllOrdersIdIsPayedFalse();
//
//        if (!orderIdList.isEmpty()) {
//            for (Integer orderId : orderIdList) {
//                List<OrderedProductsDetail> unpaidOrderedProducts = getOrderedProductDetails(orderId);
//                for (OrderedProductsDetail productsDetail : unpaidOrderedProducts) {
//                    productClient.setProductAmount(productsDetail.getAmount(), productsDetail.getProductId());
//                }
//                String stringQuery = "delete from OrderProducts op where op.orderId = " + orderId;
//                Query query = entityManager.createQuery(stringQuery);
//                query.executeUpdate();
//            }
//        }
//    }
}
