package ntu.PhamGiaKhiem.TourBooking.models;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tours")

public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

 
    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "departure_location_id", nullable = false)
    private Location departureLocation;

    @ManyToOne
    @JoinColumn(name = "destination_location_id", nullable = false)
    private Location destinationLocation;

 
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dayNumber ASC") 
    private List<TourItinerary> itineraries = new ArrayList<>();
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourSchedule> schedules = new ArrayList<>();
}
