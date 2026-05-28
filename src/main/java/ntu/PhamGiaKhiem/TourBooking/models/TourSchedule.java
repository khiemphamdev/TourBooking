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

	public TourSchedule(Long id, LocalDate departureDate, Integer availableSeats, BigDecimal price, Tour tour) {
		super();
		this.id = id;
		this.departureDate = departureDate;
		this.availableSeats = availableSeats;
		this.price = price;
		this.tour = tour;
	}

	public TourSchedule() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
    
    
}
