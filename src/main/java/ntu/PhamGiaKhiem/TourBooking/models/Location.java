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
}
