package online.tripguru.backend.common.factories;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResponseFactory {

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

    public static Response<?> tokenResponse(String token) {
        return Response.builder()
                .status(200)
                .token(token)
                .message("Login Successful")
                .build();
    }

    public static Response<?> tokenResponse(Map<String, String> token) {
        return Response.builder()
                .status(200)
                .data(token)
                .message("Login Successful")
                .build();
    }

    public static ResponseEntity<Response<?>> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Response.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(message)
                        .build());
    }

    public static <T> Response<T> creationsSuccess() {
        return Response.<T>builder()
                .status(201)
                .message("Success")
                .build();
    }

    public static <T> Response<T> creationsSuccess(String token) {
        return Response.<T>builder()
                .status(201)
                .token(token)
                .message("Success")
                .build();
    }

    public static <T> Response<T> creationsSuccess(T token) {
        return Response.<T>builder()
                .status(201)
                .data(token)
                .message("Success")
                .build();
    }

}
