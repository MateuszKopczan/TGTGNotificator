package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class LoginRequest implements Request {

    @JsonProperty("device_type")
    private String deviceType;

    private String email;
}
