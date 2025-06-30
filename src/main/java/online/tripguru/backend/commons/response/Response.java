package online.tripguru.backend.commons.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response <T> {
    private int status;
    private String message;
    private T data;
    private int total;
}
