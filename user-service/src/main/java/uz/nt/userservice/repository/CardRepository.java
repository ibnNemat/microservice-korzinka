package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.entity.Card;

import java.util.List;
@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
}
