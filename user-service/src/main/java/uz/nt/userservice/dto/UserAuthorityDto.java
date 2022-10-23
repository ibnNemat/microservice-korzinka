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
    private Integer user_id;
    private Integer authority_id;
}
