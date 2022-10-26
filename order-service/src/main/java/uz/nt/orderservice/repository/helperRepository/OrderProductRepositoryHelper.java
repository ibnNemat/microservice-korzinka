package uz.nt.orderservice.repository.helperRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
@EnableScheduling
public class OrderProductRepositoryHelper {
    private final EntityManager entityManager;
    private final OrderRepository orderRepository;

    public List<OrderedProductsDetail> getOrderedProductDetails(Integer order_id){
        Query query = entityManager.createNativeQuery(
                "select op.product_id as product_id, sum(p.price) as price, sum(op.amount) as amount" +
                        " from order_products op inner join product p on p.id = op.product_id" +
                        " where op.order_id =:orderId group by op.product_id", OrderedProductsDetail.class);
        query.setParameter("orderId", order_id);

        List<OrderedProductsDetail> productsDetailList = query.getResultList();
        return productsDetailList;
    }

    @Scheduled(cron = "30 45 23 * * *")
    public void removeAllNonOrderedProducts(){
        List<Integer> list = orderRepository.getIdByPayedFalse();

//        for (Integer order_id: list){
//            List<OrderedProductsDetail> nonOrderedProducts = getOrderedProductDetails(order_id);
//            for (OrderedProductsDetail productsDetail: nonOrderedProducts){
//                productRepository.addProductAmount(productsDetail.getAmount(), productsDetail.getProduct_id());
//            }
//            String hql = "delete from OrderProducts op where op.order_id = " + order_id;
//            Query query = entityManager.createQuery(hql);
//            query.executeUpdate();
//        }
    }
}
