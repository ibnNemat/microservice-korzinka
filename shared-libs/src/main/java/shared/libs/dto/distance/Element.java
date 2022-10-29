package shared.libs.dto.distance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element {
    private Distance distance;
    private Duration duration;
    private Duration duration_in_traffic;
    private String status;
}
