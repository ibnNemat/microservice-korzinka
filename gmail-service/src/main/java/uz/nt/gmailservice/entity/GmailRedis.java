package uz.nt.gmailservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Data
@Builder
public class GmailRedis {
    private Long id;
    private String gmail;
    private Integer code;
}
