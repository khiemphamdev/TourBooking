package ntu.PhamGiaKhiem.TourBooking.models;

package com.example.tourbooking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tours")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Sử dụng BigDecimal trong Java để đồng bộ với DECIMAL dưới DB cho chuẩn xác
    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "image_url")
    private String imageUrl; // Ảnh đại diện chính của Tour

    @ManyToOne
    @JoinColumn(name = "departure_location_id", nullable = false)
    private Location departureLocation;

    @ManyToOne
    @JoinColumn(name = "destination_location_id", nullable = false)
    private Location destinationLocation;

    // CascadeType.ALL + orphanRemoval giúp tự động xóa lịch trình khi xóa Tour
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dayNumber ASC") // Sắp xếp sẵn thứ tự tăng dần từ ngày 1, 2, 3...
    private List<TourItinerary> itineraries = new ArrayList<>();

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourSchedule> schedules = new ArrayList<>();
}
