package online.tripguru.backend.user.utility;

import online.tripguru.backend.user.dto.response.UserResponse;
import online.tripguru.backend.user.entity.GuruUser;

public class UserFactory {

    public static UserResponse toResponse(GuruUser guruUser) {
        return UserResponse
                .builder()
                .id(guruUser.getId())
                .email(guruUser.getEmail())
                .name(guruUser.getName())
                .profileUrl(guruUser.getProfileUrl())
                .username(guruUser.getUsername())
                .phone(guruUser.getPhone())
                .build();
    }

}
