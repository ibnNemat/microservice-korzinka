package uz.nt.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "cardType")
    @Fetch(FetchMode.SUBSELECT)
    private List<Card> cards;
}
