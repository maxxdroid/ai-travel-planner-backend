package online.tripguru.backend.commons.util;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.commons.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseUtil {

    public static <T> ResponseEntity<Response<T>> success(T data) {
        return ResponseEntity.ok(Response.<T>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build());
    }

    public static ResponseEntity<Response<?>> success(Response<?> response) {
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Response<?>> success() {
        return ResponseEntity.ok(Response.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build());
    }

    public static <T> ResponseEntity<Response<T>> success(T data, int total) {
        return ResponseEntity.ok(Response.<T>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .total(total)
                .build());
    }

    public static ResponseEntity<Response<?>> serverError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(message)
                        .build());
    }

    public static ResponseEntity<Response<?>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(message)
                        .build());
    }

    public static ResponseEntity<Response<?>> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .build());
    }

    public static ResponseEntity<Response<?>> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(message)
                        .build());
    }

    public static ResponseEntity<Response<?>> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Response.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(message)
                        .build());
    }

}
