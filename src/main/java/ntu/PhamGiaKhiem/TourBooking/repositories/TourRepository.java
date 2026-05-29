package ntu.PhamGiaKhiem.TourBooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ntu.PhamGiaKhiem.TourBooking.models.Tour;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByDestinationLocationId(Long destinationId);

    List<Tour> findByTitleContainingIgnoreCase(String keyword);
    
    @Query("SELECT t FROM Tour t WHERE " +
            "(:keyword IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:departureId IS NULL OR t.departureLocation.id = :departureId) " +
            "AND (:destinationId IS NULL OR t.destinationLocation.id = :destinationId)")
     List<Tour> searchTours(
             @Param("keyword") String keyword,
             @Param("departureId") Long departureId,
             @Param("destinationId") Long destinationId
     );
}
