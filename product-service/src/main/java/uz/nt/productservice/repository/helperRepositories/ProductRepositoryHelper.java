package uz.nt.productservice.repository.helperRepositories;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductRepositoryHelper {
    private final EntityManager entityManager;

    public ProductRepositoryHelper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Product> getTopOrderedProducts(){
        Query query = entityManager.createNativeQuery("select p.id, p.name, p.type_id, p.amount, p.price from product p inner join " +
                "(select op.product_id, sum(op.amount) as amount from order_products op " +
                "group by op.product_id order by amount desc) op on op.product_id = p.id limit 100", Product.class);
        return query.getResultList();
    }

    public void updateProduct(){
        String sql = "UPDATE product SET is_active = false WHERE amount = 0" +
                " OR storage_life <= now() - created_at";

        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }


}
