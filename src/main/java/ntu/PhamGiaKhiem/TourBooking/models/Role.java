package ntu.PhamGiaKhiem.TourBooking.models;


import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;  // Ví dụ: ROLE_USER, ROLE_ADMIN
//getter, setter, contrstructor
}
