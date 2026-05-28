package ntu.PhamGiaKhiem.TourBooking.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ntu.PhamGiaKhiem.TourBooking.models.Location;
import ntu.PhamGiaKhiem.TourBooking.models.Tour;
import ntu.PhamGiaKhiem.TourBooking.repositories.LocationRepository;
import ntu.PhamGiaKhiem.TourBooking.repositories.TourRepository;

import java.util.List;

@Service

public class TourService {

    private  TourRepository tourRepository;
    private  LocationRepository locationRepository;

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tour với ID: " + id));
    }

    public List<Tour> searchToursByTitle(String keyword) {
        return tourRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Transactional
    public Tour createTour(Tour tour, Long departureLocationId, Long destinationLocationId) {
        Location departure = locationRepository.findById(departureLocationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa điểm đi"));
        Location destination = locationRepository.findById(destinationLocationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa điểm đến"));

        tour.setDepartureLocation(departure);
        tour.setDestinationLocation(destination);

        // Ràng buộc mối quan hệ hai chiều (Bắt buộc đối với JPA khi dùng Cascade)
        if (tour.getItineraries() != null) {
            tour.getItineraries().forEach(itinerary -> itinerary.setTour(tour));
        }
        if (tour.getSchedules() != null) {
            tour.getSchedules().forEach(schedule -> schedule.setTour(tour));
        }

        return tourRepository.save(tour);
    }
    
    @Transactional
    public Tour updateTour(Long id, Tour updatedTourData, Long departureLocationId, Long destinationLocationId) {
        // 1. Kiểm tra xem Tour cần sửa có tồn tại không
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tour với ID: " + id));

        // 2. Tìm và cập nhật lại địa điểm đi / điểm đến mới (nếu có thay đổi)
        Location departure = locationRepository.findById(departureLocationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa điểm đi"));
        Location destination = locationRepository.findById(destinationLocationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa điểm đến"));

        // 3. Cập nhật các thông tin cơ bản của Tour
        existingTour.setTitle(updatedTourData.getTitle());
        existingTour.setDescription(updatedTourData.getDescription());
        existingTour.setBasePrice(updatedTourData.getBasePrice());
        existingTour.setImageUrl(updatedTourData.getImageUrl());
        existingTour.setDepartureLocation(departure);
        existingTour.setDestinationLocation(destination);
  
        // 6. Lưu lại vào Database (JPA sẽ tự động phát hiện thay đổi và sinh câu lệnh UPDATE)
        return tourRepository.save(existingTour);
    }

    @Transactional
    public void deleteTour(Long id) {
        Tour tour = getTourById(id);
        tourRepository.delete(tour); // Nhờ ON DELETE CASCADE ở DB, các bảng con sẽ tự động xóa theo
    }
}