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
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq_id")
    @SequenceGenerator(name = "authority_seq_id", sequenceName = "authority_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String permission;
}
