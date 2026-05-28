package ntu.PhamGiaKhiem.TourBooking.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.PhamGiaKhiem.TourBooking.models.TourItinerary;

import java.util.List;

@Repository
public interface TourItineraryRepository extends JpaRepository<TourItinerary, Long> {
    // Lấy toàn bộ lịch trình của một tour và sắp xếp theo thứ tự số ngày tăng dần
    List<TourItinerary> findByTourIdOrderByDayNumberAsc(Long tourId);
}
