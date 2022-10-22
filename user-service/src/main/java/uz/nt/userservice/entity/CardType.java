package uz.nt.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_types")
public class CardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "card_type_id_seq")
    @SequenceGenerator(name = "card_type_id_seq", sequenceName = "card_type_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
}
