package online.tripguru.backend.features.trips.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document()
public class Trip {
    @MongoId
    private String id;
    private String title;
    private String city;
    private Location baseLocation;
    private String country;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean aiGenerated;
    private String aiModel;
    private List<TripGuruUser> tripGoers;
    private List<TripDay> days;
    private TripGuruUser planner;
    private TripStatus tripStatus;
    private Double estimatedTotalCost;
    private String currency;
    private List<String> tags;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
