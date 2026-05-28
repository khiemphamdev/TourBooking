package ntu.PhamGiaKhiem.TourBooking.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ntu.PhamGiaKhiem.TourBooking.models.Tour;
import ntu.PhamGiaKhiem.TourBooking.models.TourSchedule;
import ntu.PhamGiaKhiem.TourBooking.repositories.TourRepository;
import ntu.PhamGiaKhiem.TourBooking.repositories.TourScheduleRepository;

import java.time.LocalDate;
import java.util.List;

@Service

public class TourScheduleService {

    private  TourScheduleRepository scheduleRepository;
    private  TourRepository tourRepository;

    // Lấy tất cả lịch khởi hành của một tour (Cả trong quá khứ lẫn tương lai)
    public List<TourSchedule> getSchedulesByTourId(Long tourId) {
        return scheduleRepository.findByTourId(tourId);
    }

    // Lọc danh sách lịch khởi hành khả dụng (Chỉ lấy các ngày lớn hơn ngày hiện tại)
    public List<TourSchedule> getAvailableSchedules(Long tourId) {
        return scheduleRepository.findByTourIdAndDepartureDateAfter(tourId, LocalDate.now());
    }

    // Thêm một ngày khởi hành mới cho Tour
    @Transactional
    public TourSchedule createSchedule(Long tourId, TourSchedule schedule) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tour với ID: " + tourId));

        // Kiểm tra logic: Không cho phép tạo ngày khởi hành trong quá khứ
        if (schedule.getDepartureDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngày khởi hành không được phép nằm trong quá khứ!");
        }

        schedule.setTour(tour);
        return scheduleRepository.save(schedule);
    }

    // Cập nhật số ghế trống hoặc điều chỉnh giá tour cho một ngày cụ thể
    @Transactional
    public TourSchedule updateSchedule(Long scheduleId, TourSchedule details) {
        TourSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với ID: " + scheduleId));

        schedule.setDepartureDate(details.getDepartureDate());
        schedule.setAvailableSeats(details.getAvailableSeats());
        schedule.setPrice(details.getPrice()); // Điều chỉnh tăng/giảm giá theo thời điểm

        return scheduleRepository.save(schedule);
    }

    // Xóa một lịch khởi hành
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        TourSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với ID: " + scheduleId));
        scheduleRepository.delete(schedule);
    }
}