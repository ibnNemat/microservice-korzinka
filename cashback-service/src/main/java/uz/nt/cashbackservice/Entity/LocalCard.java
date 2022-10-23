package uz.nt.cashbackservice.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "cash_card")
public class LocalCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cash_card_gen")
    @SequenceGenerator(name = "cash_card_gen", sequenceName = "cash_card_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name = "Local card";

    @Column(name = "total_month_buy")
    private Double totalMonthBuy;

    @Column(name = "total_quarter_buy")
    private Double totalQuarterBuy;

    @Column(name = "percent_cashback")
    private Integer percentCashback;

    @Column(name = "amount")
    private Double amount;


    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    @Column(name = "is_locked")
    private Boolean isLocked;

}
