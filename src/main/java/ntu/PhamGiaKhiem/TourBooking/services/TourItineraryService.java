package ntu.PhamGiaKhiem.TourBooking.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ntu.PhamGiaKhiem.TourBooking.models.Tour;
import ntu.PhamGiaKhiem.TourBooking.models.TourItinerary;
import ntu.PhamGiaKhiem.TourBooking.repositories.TourItineraryRepository;
import ntu.PhamGiaKhiem.TourBooking.repositories.TourRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service

public class TourItineraryService {

	private final String UPLOAD_DIR = "C:/lap trinh/TourBooking/uploads/images/";
    private TourItineraryRepository itineraryRepository;
    private TourRepository tourRepository;

    public List<TourItinerary> getItinerariesByTourId(Long tourId) {
        if (!tourRepository.existsById(tourId)) {
            throw new RuntimeException("Không tìm thấy tour với ID: " + tourId);
        }
        return itineraryRepository.findByTourIdOrderByDayNumberAsc(tourId);
    }

    @Transactional
    public TourItinerary addItineraryToTour(Long tourId, TourItinerary itinerary, MultipartFile file) {
    	Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tour với ID: " + tourId));
        
        if (file != null && !file.isEmpty()) {
            String fileName = saveImageToDisk(file);
            itinerary.setImageUrl("/uploads/images/" + fileName);
        }
        
        itinerary.setTour(tour);
        return itineraryRepository.save(itinerary);
    }

   
    @Transactional
    public TourItinerary updateItinerary(Long itineraryId, TourItinerary details, MultipartFile file) {
        TourItinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết lịch trình với ID: " + itineraryId));

        itinerary.setDayNumber(details.getDayNumber());
        itinerary.setTitle(details.getTitle());
        itinerary.setContent(details.getContent());

   
        if (file != null && !file.isEmpty()) {
            deleteImageFromDisk(itinerary.getImageUrl());
            String fileName = saveImageToDisk(file);
            itinerary.setImageUrl("/uploads/images/" + fileName);
        }

        return itineraryRepository.save(itinerary);
    }

    @Transactional
    public void deleteItinerary(Long itineraryId) {
        TourItinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết lịch trình với ID: " + itineraryId));

        deleteImageFromDisk(itinerary.getImageUrl());
        
        itineraryRepository.delete(itinerary);
    }

    // ==================== CÁC HÀM PHỤ XỬ LÝ LƯU/XÓA FILE VẬT LÝ ====================

    // Hàm lưu file ảnh vào ổ đĩa vật lý
    private String saveImageToDisk(MultipartFile file) {
        try {
            // Tạo chuỗi tên file ngẫu nhiên để tránh trùng lặp đè file cũ
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Tự tạo thư mục nếu chưa tồn tại
            }

            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi trong quá trình ghi file ảnh lộ trình: " + e.getMessage());
        }
    }

    // Hàm xóa file ảnh khỏi ổ đĩa vật lý
    private void deleteImageFromDisk(String imageUrl) {
        if (imageUrl != null && imageUrl.startsWith("/uploads/images/")) {
            try {
                // Trích xuất lại tên file thực tế từ chuỗi URL ảo
                String fileName = imageUrl.replace("/uploads/images/", "");
                Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log cảnh báo hoặc bỏ qua nếu file đã bị xóa thủ công trước đó
                System.err.println("Không thể xóa file ảnh cũ: " + e.getMessage());
            }
        }
    }
}
