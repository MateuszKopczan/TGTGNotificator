package response;

import response.shared.StartupData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollingResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_ttl_seconds")
    private int accessTokenTTLSeconds;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("startup_data")
    private StartupData startupData;
}
