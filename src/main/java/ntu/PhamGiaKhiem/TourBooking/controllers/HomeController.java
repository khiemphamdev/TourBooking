package ntu.PhamGiaKhiem.TourBooking.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String displayHomepage(Model model) {
        // 1. Gửi toàn bộ danh sách Tour du lịch hiện có ra trang chủ
        model.addAttribute("tours", tourService.getAllTours());
        
        // 2. Gửi danh sách Địa danh để đổ dữ liệu vào 2 thẻ <select> trên thanh tìm kiếm nhanh
        model.addAttribute("locations", locationService.getAllLocations());
        
        return "index"; // Trả về tệp tin index.html nằm ở thư mục gốc của templates
    }
}
