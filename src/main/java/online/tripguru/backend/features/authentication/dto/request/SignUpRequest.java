package online.tripguru.backend.features.authentication.dto.request;

public record SignUpRequest(
        String phoneNumber,
        String fullName,
        String email,
        String password
) {
}
