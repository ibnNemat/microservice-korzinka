package uz.nt.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import shared.libs.dto.UserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 15)            // foydalanuvchini 15 minutga ban qilish
public class BanIp {
    private String id;           //IpAddress
    private UserDto userDto;
}
