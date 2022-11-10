package uz.nt.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorityDto {
    private Integer id;
    private Integer userId;
    private Integer authorityId;
}
