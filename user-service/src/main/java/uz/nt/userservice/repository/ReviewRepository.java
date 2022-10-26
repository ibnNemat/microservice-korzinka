package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}