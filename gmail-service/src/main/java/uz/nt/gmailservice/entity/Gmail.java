package uz.nt.gmailservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Gmail {
    @Id
    private Integer id;
    private String gmail;
    private Boolean success;
    private Integer userId;
}
