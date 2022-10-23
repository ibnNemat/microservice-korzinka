package uz.nt.cashbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.cashbackservice.Entity.LocalCard;

@Repository
public interface LocalCardRepository extends JpaRepository<LocalCard, Integer> {

}
