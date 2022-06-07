package response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorsResponse {

    private List<Code> errors;

    @Data
    public static class Code{
        private String code;
    }
}
