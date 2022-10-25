package shared.libs.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import shared.libs.dto.UserDto;

@RedisHash(timeToLive = 60 * 60 * 3)
@Data
public class UserSession {

    private Integer id;
    private UserDto userDto;

}
