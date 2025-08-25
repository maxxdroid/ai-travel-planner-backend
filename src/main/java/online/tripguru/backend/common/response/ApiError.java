package online.tripguru.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.tripguru.backend.common.exception.ApiException;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiError(ApiException ex) {
        this.status = ex.getHttpStatus().value();
        this.message = ex.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
