package ntu.PhamGiaKhiem.TourBooking.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ntu.PhamGiaKhiem.TourBooking.models.Tour;
import ntu.PhamGiaKhiem.TourBooking.models.TourItinerary;
import ntu.PhamGiaKhiem.TourBooking.models.TourSchedule;
import ntu.PhamGiaKhiem.TourBooking.services.TourItineraryService;
import ntu.PhamGiaKhiem.TourBooking.services.TourScheduleService;
import ntu.PhamGiaKhiem.TourBooking.services.TourService;

import java.util.List;

@Controller

public class TourDetailController {

	@Autowired
    private  TourService tourService;
	@Autowired
    private  TourItineraryService itineraryService;
	@Autowired
    private TourScheduleService scheduleService;

    @GetMapping("/tours/{id}")
    public String viewTourDetail(@NonNull @PathVariable Long id, Model model) {
        // 1. Lấy thông tin Tour chính
        Tour tour = tourService.getTourById(id);
        model.addAttribute("tour", tour);

        // 2. Lấy danh sách lịch trình chi tiết từng ngày (Sắp xếp theo thứ tự số ngày tăng dần)
        List<TourItinerary> itineraries = itineraryService.getItinerariesByTourId(id);
        model.addAttribute("itineraries", itineraries);

        // 3. Lấy danh sách các ngày khởi hành khả dụng (availableSeats > 0)
        List<TourSchedule> schedules = scheduleService.getSchedulesByTourId(id);
        model.addAttribute("schedules", schedules);

        return "tour-detail"; // Trả về file tour-detail.html
    }
}
