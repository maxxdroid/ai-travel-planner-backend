package online.tripguru.backend.features.authentication.dto.request;

public record GoogleAuthRequest(
        String idToken,
        String authCode
) {
}
