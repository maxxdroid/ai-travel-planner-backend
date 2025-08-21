package online.tripguru.backend.user.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
    private String phone;
}
