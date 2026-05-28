package ntu.PhamGiaKhiem.TourBooking.models;



import jakarta.persistence.*;


@Entity
@Table(name = "tour_itineraries")

public class TourItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "image_url")
    private String imageUrl; 

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

	public TourItinerary(Long id, Integer dayNumber, String title, String content, String imageUrl, Tour tour) {
		super();
		this.id = id;
		this.dayNumber = dayNumber;
		this.title = title;
		this.content = content;
		this.imageUrl = imageUrl;
		this.tour = tour;
	}
	public TourItinerary() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
    
    
}
