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
	public Tour(Long id, String title, String description, BigDecimal basePrice, String imageUrl,
			Location departureLocation, Location destinationLocation, List<TourItinerary> itineraries,
			List<TourSchedule> schedules) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.basePrice = basePrice;
		this.imageUrl = imageUrl;
		this.departureLocation = departureLocation;
		this.destinationLocation = destinationLocation;
		this.itineraries = itineraries;
		this.schedules = schedules;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Location getDepartureLocation() {
		return departureLocation;
	}
	public void setDepartureLocation(Location departureLocation) {
		this.departureLocation = departureLocation;
	}
	public Location getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(Location destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public List<TourItinerary> getItineraries() {
		return itineraries;
	}
	public void setItineraries(List<TourItinerary> itineraries) {
		this.itineraries = itineraries;
	}
	public List<TourSchedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<TourSchedule> schedules) {
		this.schedules = schedules;
	}
    
}
