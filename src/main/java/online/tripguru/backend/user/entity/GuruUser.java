package online.tripguru.backend.user.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@ToString
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
    private String refreshToken; // encrypt this at rest
    private Instant createdAt;
    private Instant updatedAt;
}
