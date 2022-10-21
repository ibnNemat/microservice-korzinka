package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
