package ntu.PhamGiaKhiem.TourBooking.models;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")

public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Liên kết đảo ngược (Optional - giúp gọi từ location ra danh sách các tour nếu cần)
    @OneToMany(mappedBy = "departureLocation")
    private List<Tour> departureTours;

    @OneToMany(mappedBy = "destinationLocation")
    private List<Tour> destinationTours;

    public Location() {}
	public Location(Long id, String name, String description, List<Tour> departureTours, List<Tour> destinationTours) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.departureTours = departureTours;
		this.destinationTours = destinationTours;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Tour> getDepartureTours() {
		return departureTours;
	}

	public void setDepartureTours(List<Tour> departureTours) {
		this.departureTours = departureTours;
	}

	public List<Tour> getDestinationTours() {
		return destinationTours;
	}

	public void setDestinationTours(List<Tour> destinationTours) {
		this.destinationTours = destinationTours;
	}
    
}
