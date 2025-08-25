package online.tripguru.backend.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import online.tripguru.backend.common.exception.ApiException;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(ApiException ex) {
        this.status = ex.getHttpStatus().value();
        this.message = ex.getMessage();
        this.timestamp = LocalDateTime.now();
    }

}
