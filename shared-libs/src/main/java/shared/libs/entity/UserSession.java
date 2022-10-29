package shared.libs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import shared.libs.dto.UserDto;

@RedisHash(timeToLive = 60 * 60 * 3)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    private String id;
    private UserDto userDto;
}
