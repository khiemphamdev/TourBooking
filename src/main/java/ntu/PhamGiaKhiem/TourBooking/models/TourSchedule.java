package ntu.PhamGiaKhiem.TourBooking.models;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tour_schedules")
public class TourSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(nullable = false)
    private BigDecimal price; 

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;
}
