package ntu.PhamGiaKhiem.TourBooking.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import ntu.PhamGiaKhiem.TourBooking.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUserName(String username);
}
