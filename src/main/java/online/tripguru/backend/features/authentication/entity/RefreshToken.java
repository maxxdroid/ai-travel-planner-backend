package online.tripguru.backend.features.authentication.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "refresh_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String id;

    @Indexed(unique = true)
    private String token;

    private String userId;

    private boolean revoked;

    @Indexed(expireAfter = "0s")
    private Instant expiryDate;

    private String deviceId; /// optional (mobile tracking)
}