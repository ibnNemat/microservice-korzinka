package shared.libs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import shared.libs.entity.UserSession;

@Component
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
