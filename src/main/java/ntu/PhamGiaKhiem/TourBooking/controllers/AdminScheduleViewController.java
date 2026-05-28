package ntu.PhamGiaKhiem.TourBooking.controllers;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ntu.PhamGiaKhiem.TourBooking.models.TourSchedule;
import ntu.PhamGiaKhiem.TourBooking.services.TourScheduleService;
import ntu.PhamGiaKhiem.TourBooking.services.TourService;

@Controller
@RequestMapping("/admin/schedules")
public class AdminScheduleViewController {

    private TourScheduleService scheduleService;
    private TourService tourService;

    // 1. Quản lý danh sách ngày khởi hành của một Tour
    @GetMapping("/tour/{tourId}")
    public String listSchedules(@NonNull @PathVariable Long tourId, Model model) {
        model.addAttribute("tour", tourService.getTourById(tourId));
        model.addAttribute("schedules", scheduleService.getSchedulesByTourId(tourId));
        return "admin/schedule/list";
    }

    // 2. Hiển thị form tạo ngày khởi hành mới
    @GetMapping("/tour/{tourId}/create")
    public String showCreateForm(@NonNull @PathVariable Long tourId, Model model) {
        model.addAttribute("schedule", new TourSchedule());
        model.addAttribute("tourId", tourId);
        return "admin/schedule/create";
    }

    // 3. Xử lý lưu lịch khởi hành mới
    @PostMapping("/tour/{tourId}/create")
    public String createSchedule(@NonNull @PathVariable Long tourId, @ModelAttribute("schedule") TourSchedule schedule) {
        scheduleService.createSchedule(tourId, schedule);
        return "redirect:/admin/schedules/tour/" + tourId;
    }

    // 4. Xử lý xóa lịch khởi hành
    @GetMapping("/delete/{id}")
    public String deleteSchedule(@NonNull @PathVariable Long id) {
        TourSchedule schedule = scheduleService.updateSchedule(id, new TourSchedule());
        Long tourId = schedule.getTour().getId();
        
        scheduleService.deleteSchedule(id);
        return "redirect:/admin/schedules/tour/" + tourId;
    }
}
