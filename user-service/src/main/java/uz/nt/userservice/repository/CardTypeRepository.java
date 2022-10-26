package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.entity.CardType;
@Repository
public interface CardTypeRepository extends JpaRepository<CardType, Integer> {
}
