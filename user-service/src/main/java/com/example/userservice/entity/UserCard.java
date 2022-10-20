package com.example.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_card_id_seq")
    @SequenceGenerator(name = "user_card_id_seq", sequenceName = "user_card_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;


}
