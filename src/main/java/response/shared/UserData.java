package response.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    @JsonProperty("user_id")
    private String userId;

    private String name;

    @JsonProperty("country_id")
    private String countryId;

    private String email;
}
