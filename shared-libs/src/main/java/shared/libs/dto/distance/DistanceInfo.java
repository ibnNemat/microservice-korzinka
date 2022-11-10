package shared.libs.dto.distance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceInfo {
    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<Rows> rows;
    private String status;

    private String error_message;
}
