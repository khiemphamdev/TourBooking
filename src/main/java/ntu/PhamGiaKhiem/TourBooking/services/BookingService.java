package ntu.PhamGiaKhiem.TourBooking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ntu.PhamGiaKhiem.TourBooking.models.Booking;
import ntu.PhamGiaKhiem.TourBooking.models.BookingStatus;
import ntu.PhamGiaKhiem.TourBooking.models.TourSchedule;
import ntu.PhamGiaKhiem.TourBooking.models.User;
import ntu.PhamGiaKhiem.TourBooking.repositories.BookingRepository;
import ntu.PhamGiaKhiem.TourBooking.repositories.TourScheduleRepository;
import ntu.PhamGiaKhiem.TourBooking.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {

	@Autowired
    private  BookingRepository bookingRepository;
	@Autowired
    private  TourScheduleRepository tourScheduleRepository;
	@Autowired
    private  UserRepository userRepository;

    @Transactional
    public Booking createBooking(Long scheduleId, String username, int adults, int children) {
        // 1. Kiểm tra tài khoản người dùng
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Tài khoản người dùng không tồn tại"));

        // 2. Kiểm tra lịch khởi hành của tour
        TourSchedule schedule = tourScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Lịch khởi hành này không tồn tại"));

        // 3. Kiểm tra số lượng chỗ trống còn lại
        int totalSeatsRequested = adults + children;
        if (schedule.getAvailableSeats() < totalSeatsRequested) {
            throw new RuntimeException("Số lượng chỗ trống không đủ! Chỉ còn " + schedule.getAvailableSeats() + " chỗ.");
        }

        // 4. Tính toán tổng tiền chính xác bằng BigDecimal
        // Ví dụ: Vé trẻ em = 70% giá vé người lớn
        BigDecimal adultCount = new BigDecimal(adults);
        BigDecimal childCount = new BigDecimal(children);
        BigDecimal childRate = new BigDecimal("0.7");

        BigDecimal totalPriceForAdults = schedule.getPrice().multiply(adultCount);
        BigDecimal totalPriceForChildren = schedule.getPrice().multiply(childRate).multiply(childCount);
        BigDecimal totalOrderPrice = totalPriceForAdults.add(totalPriceForChildren);

        // 5. Trừ bớt số ghế trống của lịch khởi hành đó
        schedule.setAvailableSeats(schedule.getAvailableSeats() - totalSeatsRequested);
        tourScheduleRepository.save(schedule);

        // 6. Khởi tạo đơn hàng mới
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTourSchedule(schedule);
        booking.setNumberOfAdults(adults);
        booking.setNumberOfChildren(children);
        booking.setTotalPrice(totalOrderPrice);
        booking.setStatus(BookingStatus.PENDING); // Đặt trạng thái mặc định: Chờ thanh toán

        return bookingRepository.save(booking);
    }

    // Lịch sử đặt tour dành riêng cho từng khách hàng
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserIdOrderByBookingDateDesc(userId);
    }

    // Danh sách toàn bộ đơn đặt tour dành cho Admin quản lý
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đặt tour"));

        // Logic đặc biệt: Nếu đơn hàng bị HUỶ (CANCELLED), hoàn trả lại số ghế trống cho Tour
        if (status == BookingStatus.CANCELLED && booking.getStatus() != BookingStatus.CANCELLED) {
            TourSchedule schedule = booking.getTourSchedule();
            int seatsToRefund = booking.getNumberOfAdults() + booking.getNumberOfChildren();
            schedule.setAvailableSeats(schedule.getAvailableSeats() + seatsToRefund);
            tourScheduleRepository.save(schedule);
        }

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}
