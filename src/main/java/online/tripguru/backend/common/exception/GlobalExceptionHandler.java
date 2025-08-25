package online.tripguru.backend.common.exception;

import online.tripguru.backend.common.logs.LogHandler;
import online.tripguru.backend.common.response.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiExceptions(ApiException ex) {
        LogHandler.log(ex);
        return new ResponseEntity<>(new ApiError(ex), ex.getHttpStatus());
    }

}
