package online.tripguru.backend.user.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String name;
    private String username;
    private String profileUrl;
    private String phone;
}
