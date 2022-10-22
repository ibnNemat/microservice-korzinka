package uz.nt.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.libs.dto.CardTypeDto;
import shared.libs.dto.UserDto;

import javax.persistence.*;
import java.sql.Date;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cards_id_seq")
    @SequenceGenerator(name = "cards_id_seq", sequenceName = "cards_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne
    private User user;
    private CardType cardType;
    private String cardNumber;
    private Date validDate;
    private Double account;

}
