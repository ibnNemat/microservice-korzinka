package uz.nt.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import shared.libs.dto.UserDto;

@RedisHash(timeToLive = 60 * 60 * 3)          // verify uchun 3 minut kutiw
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAttempt {
    private String id;  // IpAddress
    private UserDto userDto;
}
