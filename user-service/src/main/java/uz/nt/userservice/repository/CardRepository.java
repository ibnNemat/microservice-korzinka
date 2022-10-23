package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.userservice.entity.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
