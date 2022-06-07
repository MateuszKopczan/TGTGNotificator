package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class ListOfferRequest implements Request{

    @JsonProperty("user_id")
    private String userId;

    private Origin origin;

    private String radius;

    @JsonProperty("page_size")
    private String pageSize;

    private String page;

    @JsonProperty("favorites_only")
    private boolean favoritesOnly;

    @Data
    @Jacksonized
    @Builder
    public static class Origin{

        private String latitude;
        private String longitude;

    }
}
