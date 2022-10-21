package uz.nt.userservice.entity;

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
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_authority_id_seq")
    @SequenceGenerator(name = "user_authority_id_seq", sequenceName = "user_authority_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    private Integer user_id;
    private Integer authority_id;
}
