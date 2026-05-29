package ntu.PhamGiaKhiem.TourBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ntu.PhamGiaKhiem.TourBooking.models.Tour;
import ntu.PhamGiaKhiem.TourBooking.services.LocationService;
import ntu.PhamGiaKhiem.TourBooking.services.TourService;

@Controller
@RequestMapping("/admin/tours")

public class AdminTourViewController {

	@Autowired
    private  TourService tourService;
	@Autowired
    private  LocationService locationService;

    // 1. Xem danh sách tất cả các Tour
    @GetMapping
    public String listTours(Model model) {
        model.addAttribute("tours", tourService.getAllTours());
        return "admin/tour/list";
    }

    // 2. Hiển thị form thêm mới Tour
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("tour", new Tour());
        model.addAttribute("locations", locationService.getAllLocations()); // Phục vụ thẻ chọn <select>
        return "admin/tour/create";
    }

    // 3. Xử lý lưu Tour mới
    @PostMapping("/create")
    public String createTour(
            @ModelAttribute("tour") Tour tour,
            @RequestParam Long departureLocationId,
            @RequestParam Long destinationLocationId) {
        tourService.createTour(tour, departureLocationId, destinationLocationId);
        return "redirect:/admin/tours";
    }

    // 4. Xử lý xóa Tour (Tự động cascade xóa lịch trình và ngày đi đi kèm)
    @GetMapping("/delete/{id}")
    public String deleteTour(@NonNull @PathVariable Long id) {
        tourService.deleteTour(id);
        return "redirect:/admin/tours";
    }
}
