package uz.nt.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import uz.nt.userservice.entity.CheckAttempt;

@Component
public interface CheckAttemptRepository extends CrudRepository<CheckAttempt,String> {
}
