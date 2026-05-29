package ntu.PhamGiaKhiem.TourBooking.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ntu.PhamGiaKhiem.TourBooking.models.Tour;
import ntu.PhamGiaKhiem.TourBooking.services.LocationService;
import ntu.PhamGiaKhiem.TourBooking.services.TourService;

@Controller

public class HomeController {

	@Autowired
    private  TourService tourService;
	@Autowired
    private  LocationService locationService;

    // Khi người dùng gõ vào địa chỉ IP gốc (http://localhost:8080/)
    @GetMapping("/")
    public String displayHomepage(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "departureId", required = false) Long departureId,
            @RequestParam(name = "destinationId", required = false) Long destinationId,
            Model model) {

        // 1. Tìm kiếm theo 3 tham số còn lại
        List<Tour> filteredTours = tourService.searchTours(keyword, departureId, destinationId);
        model.addAttribute("tours", filteredTours);

        // 2. Giữ lại giá trị cũ hiển thị trên form
        model.addAttribute("selectedKeyword", keyword);
        model.addAttribute("selectedDepartureId", departureId);
        model.addAttribute("selectedDestinationId", destinationId);

        // 3. Nạp danh sách địa điểm cho các thẻ select
        model.addAttribute("locations", locationService.getAllLocations());

        return "index";
    }
}
