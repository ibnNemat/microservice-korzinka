package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.entity.User;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByUsername(String username);
    Boolean existsByUsername(String username);

    Stream<User> findAllByIdLessThan(Integer  id);
    Integer deleteByUsername(String username);

}
