package shared.libs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import shared.libs.entity.UserSession;

@Component
public interface UserSessionRepository extends CrudRepository<UserSession, Integer> {
}
