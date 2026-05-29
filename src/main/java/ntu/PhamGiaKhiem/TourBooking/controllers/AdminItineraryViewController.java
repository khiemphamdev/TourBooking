package ntu.PhamGiaKhiem.TourBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ntu.PhamGiaKhiem.TourBooking.models.TourItinerary;
import ntu.PhamGiaKhiem.TourBooking.services.TourItineraryService;
import ntu.PhamGiaKhiem.TourBooking.services.TourService;

@Controller
@RequestMapping("/admin/itineraries")

public class AdminItineraryViewController {

	@Autowired
    private  TourItineraryService itineraryService;
	@Autowired
    private  TourService tourService;

    // 1. Xem lịch trình chi tiết của một Tour cụ thể
    @GetMapping("/tour/{tourId}")
    public String viewTourItineraries(@NonNull @PathVariable Long tourId, Model model) {
        model.addAttribute("tour", tourService.getTourById(tourId));
        model.addAttribute("itineraries", itineraryService.getItinerariesByTourId(tourId));
        return "admin/itinerary/list";
    }

    // 2. Hiển thị form thêm một ngày mới vào Tour
    @GetMapping("/tour/{tourId}/create")
    public String showCreateForm(@NonNull @PathVariable Long tourId, Model model) {
        model.addAttribute("itinerary", new TourItinerary());
        model.addAttribute("tourId", tourId);
        return "admin/itinerary/create";
    }

 // 3. Xử lý lưu ngày lịch trình mới
    @PostMapping("/tour/{tourId}/create")
    public String createItinerary(
            @NonNull @PathVariable Long tourId, 
            @ModelAttribute("itinerary") TourItinerary itinerary,
            @RequestParam(value = "imageFile", required = false) MultipartFile file) { 
        
        // SỬA TẠI ĐÂY: Truyền thêm tham số file vào hàm của Service
        itineraryService.addItineraryToTour(tourId, itinerary, file);
        
        return "redirect:/admin/itineraries/tour/" + tourId;
    }

 // 4. Hiển thị form sửa thông tin/hình ảnh một ngày đi
    @GetMapping("/edit/{id}")
    public String showEditForm(@NonNull @PathVariable Long id, Model model) {
        // SỬA TẠI ĐÂY: Tìm lịch trình cũ dựa vào ID để đẩy ra hiển thị trên Form sửa
        TourItinerary itinerary = itineraryService.getItineraryById(id); 
        model.addAttribute("itinerary", itinerary);
        return "admin/itinerary/edit";
    }

    // 5. Xử lý cập nhật thông tin ngày đi
    @PostMapping("/edit/{id}")
    public String updateItinerary(
            @NonNull @PathVariable Long id, 
            @ModelAttribute("itinerary") TourItinerary itinerary,
            @RequestParam(value = "imageFile", required = false) MultipartFile file) { // THÊM THAM SỐ NÀY
        
        // SỬA TẠI ĐÂY: Truyền thêm tham số file vào hàm update của Service
        TourItinerary updated = itineraryService.updateItinerary(id, itinerary, file);
        
        return "redirect:/admin/itineraries/tour/" + updated.getTour().getId();
    }

    // 6. Xử lý xóa một ngày trong lịch trình
    @GetMapping("/delete/{id}")
    public String deleteItinerary(@NonNull @PathVariable Long id) {
  
        TourItinerary itinerary = itineraryService.getItineraryById(id);
        Long tourId = itinerary.getTour().getId();
 
        itineraryService.deleteItinerary(id);

        return "redirect:/admin/itineraries/tour/" + tourId;
    }
}
