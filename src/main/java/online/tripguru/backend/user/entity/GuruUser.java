package online.tripguru.backend.user.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Document()
public class GuruUser {
    @MongoId
    private String id;
    private String email;
    private String name;
    private String username;
    private String profileUrl;
    private String phone;
    private String password;
    private String refreshToken; //TODO: encrypt this at rest
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
