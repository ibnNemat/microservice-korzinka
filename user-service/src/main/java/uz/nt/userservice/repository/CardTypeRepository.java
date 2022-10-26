package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.userservice.entity.CardType;

public interface CardTypeRepository extends JpaRepository<CardType, Integer> {
}
