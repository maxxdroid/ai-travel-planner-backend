package online.tripguru.backend.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class DropDb {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void dropDatabase() {
        mongoTemplate.getDb().drop();
    }

}
