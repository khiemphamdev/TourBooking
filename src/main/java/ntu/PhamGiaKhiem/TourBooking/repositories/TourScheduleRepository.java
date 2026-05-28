package ntu.PhamGiaKhiem.TourBooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.PhamGiaKhiem.TourBooking.models.TourSchedule;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, Long> {
    // Lấy danh sách lịch khởi hành của một tour cụ thể
    List<TourSchedule> findByTourId(Long tourId);

    // Lọc các lịch trình khởi hành từ ngày hiện tại trở về sau (bỏ qua các tour đã quá ngày)
    List<TourSchedule> findByTourIdAndDepartureDateAfter(Long tourId, LocalDate date);
}
