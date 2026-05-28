package ntu.PhamGiaKhiem.TourBooking.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.PhamGiaKhiem.TourBooking.models.Booking;
import ntu.PhamGiaKhiem.TourBooking.models.BookingStatus;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Lấy danh sách lịch sử đặt tour của một User cụ thể (để hiển thị ở trang cá nhân khách hàng)
    List<Booking> findByUserIdOrderByBookingDateDesc(Long userId);

    // Lấy danh sách các đơn hàng theo trạng thái (Dành cho Admin duyệt đơn)
    List<Booking> findByStatus(BookingStatus status);
    
}
