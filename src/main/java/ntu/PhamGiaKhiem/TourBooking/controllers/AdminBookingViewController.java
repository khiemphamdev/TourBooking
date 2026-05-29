package ntu.PhamGiaKhiem.TourBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ntu.PhamGiaKhiem.TourBooking.models.BookingStatus;
import ntu.PhamGiaKhiem.TourBooking.services.BookingService;

@Controller
@RequestMapping("/admin/bookings")

public class AdminBookingViewController {

	@Autowired
    private BookingService bookingService;

    // 1. Hiển thị toàn bộ hóa đơn đặt tour hiện có trên hệ thống
    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/booking/list";
    }

    // 2. Thay đổi trạng thái đơn hàng (Ví dụ: xác nhận đã nhận tiền hoặc hủy đơn hoàn ghế)
    @GetMapping("/{id}/update-status")
    public String updateStatus(@NonNull @PathVariable Long id, @RequestParam BookingStatus status) {
        bookingService.updateBookingStatus(id, status);
        return "redirect:/admin/bookings";
    }
}
