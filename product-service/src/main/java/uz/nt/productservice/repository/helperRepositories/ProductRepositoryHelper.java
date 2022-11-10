package uz.nt.productservice.repository.helperRepositories;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import shared.libs.dto.ProductDto;
import uz.nt.productservice.dto.ProductSearchDto;
import uz.nt.productservice.entity.Product;
import uz.nt.productservice.service.mapper.impl.ProductMapperImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.DateFormatter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<String, Object> searchByParams(MultiValueMap<String, String> map, ProductSearchDto dto){
        StringBuilder builder = new StringBuilder("SELECT * FROM product WHERE 1=1 ");
        StringBuilder count = new StringBuilder("SELECT count(1) FROM product WHERE 1=1");
        createQuery(builder, map);
        createQuery(count, map);

        Query query = entityManager.createNativeQuery(builder.toString(), Product.class);
        Query summary = entityManager.createNativeQuery(count.toString(), Integer.class);

        setValues(query, map);
        setValues(summary, map);

        List<ProductDto> products = query.getResultList().stream()
                .map(p -> {
                    Product product = (Product) p;
                    return ProductMapperImpl.toDtoWithoutType(product);
                }).toList();
        int number = summary.getFirstResult();

        Map<String, Object> results = new HashMap<>();
        results.put("Count", number);
        results.put("Data", products);

        return results;
    }

    private void createQuery(StringBuilder builder, MultiValueMap<String, String> map){
        if(map.containsKey("id")){
            builder.append("AND id = :id ");
        }
        if(map.containsKey("name")){
            builder.append("AND name like '%:id%' ");
        }
        if(map.containsKey("amount")){
            builder.append("AND amount >= :amount ");
        }
        if(map.containsKey("price")){
            builder.append("AND price >= :price ");
        }
        if(map.containsKey("active")){
            builder.append("AND active = :active ");
        }
        if(map.containsKey("createdAt")){
            builder.append("AND created_at = :createdAt ");
        }
        if(map.containsKey("storageLife")){
            builder.append("AND storage_life = :storageLife ");
        }
    }

    private void setValues(Query query, MultiValueMap<String, String> map){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = format.parse(map.getFirst("createdAt"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(map.containsKey("id")){
            query.setParameter("id", Integer.parseInt(map.getFirst("id")));
        }
        if(map.containsKey("name")){
            query.setParameter("name", map.getFirst("name"));
        }
        if(map.containsKey("amount")){
            query.setParameter("amount", Double.parseDouble(map.getFirst("amount")));
        }
        if(map.containsKey("price")){
            query.setParameter("price", Double.parseDouble(map.getFirst("price")));
        }
        if(map.containsKey("active")){
            query.setParameter("active", map.getFirst("active").equals("true")? true: false);
        }
        if(map.containsKey("createdAt")){
            query.setParameter("createdAt", date);
        }
    }

    private void createSqlQuery(StringBuilder builder, ProductSearchDto searchDto) {
        Integer id = searchDto.getId();
        String name = searchDto.getName();
        Double amount = searchDto.getAmount();
        String balance = searchDto.getBalance();
        Double startingPrice = searchDto.getStartingPrice();
        Double endingPrice = searchDto.getEndingPrice();
        Boolean active = searchDto.getActive();
        Double startingDiscount = searchDto.getStartingDiscount();
        Double endingDiscount = searchDto.getEndingDiscount();
        Date startingDate = searchDto.getStartingDate();
        Date endingDate = searchDto.getEndingDate();
        Date currentDate = searchDto.getCurrentDate();

        if (id != null && !(id <= 0)) {
            builder.append("AND id = :id ");
        }
        if (name != null && !(name.equals(" ") || name.equals(""))) {
            builder.append("AND name like '%:name%' ");
        }
        if (amount != null && !(amount < 0)) {
            if (balance == null || balance.equals("EQUAL")) {
                builder.append("AND amount = :amount ");
            } else if (balance.equals("GREATER")) {
                builder.append("AND amount <= :amount ");
            } else if (balance.equals("LOWER")) {
                builder.append("AND amount >= :amount ");
            }
        }
        if (startingPrice != null && endingPrice != null) {
            builder.append("AND price BETWEEN :start AND :end ");
        } else if (startingPrice == null || endingPrice == null) {
            if (startingPrice == null) {
                builder.append("AND price <= :end");
            } else {
                builder.append("AND price >= start");
            }
        }
    }
}
