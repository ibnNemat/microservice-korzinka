package shared.libs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shared.libs.dto.UserDto;
import shared.libs.entity.UserSession;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, Integer> {
}
