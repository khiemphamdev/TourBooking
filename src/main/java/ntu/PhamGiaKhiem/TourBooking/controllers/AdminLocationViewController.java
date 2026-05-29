package ntu.PhamGiaKhiem.TourBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ntu.PhamGiaKhiem.TourBooking.models.Location;
import ntu.PhamGiaKhiem.TourBooking.services.LocationService;

@Controller
@RequestMapping("/admin/locations")

public class AdminLocationViewController {

	@Autowired
    private LocationService locationService;

    // 1. Xem danh sách địa điểm
    @GetMapping
    public String listLocations(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        return "admin/location/list";
    }

    // 2. Hiển thị form thêm mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("location", new Location());
        return "admin/location/create";
    }

    // 3. Xử lý lưu địa điểm mới
    @PostMapping("/create")
    public String createLocation(@ModelAttribute("location") Location location) {
        locationService.createLocation(location);
        return "redirect:/admin/locations";
    }

    // 4. Hiển thị form chỉnh sửa địa điểm
    @GetMapping("/edit/{id}")
    public String showEditForm(@NonNull @PathVariable Long id, Model model) {
        model.addAttribute("location", locationService.getLocationById(id));
        return "admin/location/edit";
    }

    // 5. Xử lý cập nhật thông tin địa điểm
    @PostMapping("/edit/{id}")
    public String updateLocation(@NonNull @PathVariable Long id, @ModelAttribute("location") Location location) {
        locationService.updateLocation(id, location);
        return "redirect:/admin/locations";
    }

    // 6. Xử lý xóa địa điểm
    @GetMapping("/delete/{id}")
    public String deleteLocation(@NonNull @PathVariable Long id) {
        locationService.deleteLocation(id);
        return "redirect:/admin/locations";
    }
}
