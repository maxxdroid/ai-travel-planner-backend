package online.tripguru.backend.features.authentication.dto.request;

public record LoginRequest(
        String phoneNumber,
        String email,
        String password
) {
}
