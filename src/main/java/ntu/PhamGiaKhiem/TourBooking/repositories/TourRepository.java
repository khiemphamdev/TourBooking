package ntu.PhamGiaKhiem.TourBooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.PhamGiaKhiem.TourBooking.models.Tour;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByDestinationLocationId(Long destinationId);

    List<Tour> findByTitleContainingIgnoreCase(String keyword);
}
