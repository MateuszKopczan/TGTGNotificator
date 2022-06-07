package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class RefreshTokenRequest implements Request{

    @JsonProperty("refresh_token")
    private String refreshToken;
}
