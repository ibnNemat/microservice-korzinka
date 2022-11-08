package uz.nt.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity @Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Review {
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "review_id_seq")
    @Id
    private Integer id;
    private String message;
    private Integer ball;
    private Integer orderId;
    private Integer deliveryId;     // aniqmas
}
