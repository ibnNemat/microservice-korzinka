package uz.nt.orderservice.repository.helperRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import uz.nt.orderservice.dto.OrderedProductsDetail;
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

//    public List<OrderedProductsDetail> getOrderedProductDetails(Integer order_id){
//        Query query = entityManager.createNativeQuery(
//                "select op.product_id as product_id, price, amount" +
//                        " from order_products op inner join product p on p.id = op.product_id" +
//                        " where op.order_id =:orderId", OrderedProductsDetail.class);
//        query.setParameter("orderId", order_id);
//
//        List<OrderedProductsDetail> productsDetailList = query.getResultList();
//        return productsDetailList;
//    }

//    @Transactional
//    @Scheduled(cron = "30 45 23 * * *")
//    public void removeAllNonOrderedProducts(){
//        List<Integer> orderIdList = orderRepository.getAllOrdersIdIsPayedFalse();
//
//        for (Integer order_id: orderIdList){
//            List<OrderedProductsDetail> unpaidOrderedProducts = getOrderedProductDetails(order_id);
//            for (OrderedProductsDetail productsDetail: unpaidOrderedProducts){
//                //productRepository.addProductAmount(productsDetail.getAmount(), productsDetail.getProduct_id());
//            }
//            String stringQuery = "delete from OrderProducts op where op.orderId = " + order_id;
//            Query query = entityManager.createQuery(stringQuery);
//            query.executeUpdate();
//        }
//    }
}
