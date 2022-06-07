package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Data
@Jacksonized
@Builder
public class RegisterRequest implements Request {

    @JsonProperty("country_id")
    private String countryId;

    @JsonProperty("device_type")
    private String deviceType;

    private String email;

    private String name;

    @JsonProperty("newsletter_opt_in")
    private boolean newsletter;

    @JsonProperty("push_notification_opt_in")
    private boolean pushNotification;
}
