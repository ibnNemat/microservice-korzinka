package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
