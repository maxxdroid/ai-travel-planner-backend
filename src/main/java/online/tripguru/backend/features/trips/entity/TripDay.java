package online.tripguru.backend.features.trips.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDay {

    private LocalDate date;

    private List<Activity> activities;

    /// Where user stays that night (optional)
    private Stay stay;

    private String notes; // AI tips, reminders, etc.
}
