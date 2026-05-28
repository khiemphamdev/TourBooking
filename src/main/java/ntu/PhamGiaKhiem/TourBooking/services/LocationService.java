package ntu.PhamGiaKhiem.TourBooking.services;

import org.springframework.stereotype.Service;

import ntu.PhamGiaKhiem.TourBooking.models.Location;
import ntu.PhamGiaKhiem.TourBooking.repositories.LocationRepository;

import java.util.List;

@Service

public class LocationService {

    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa điểm với ID: " + id));
    }

    public Location createLocation(Location location) {
        if (locationRepository.findByName(location.getName()).isPresent()) {
            throw new RuntimeException("Tên địa điểm này đã tồn tại!");
        }
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, Location locationDetails) {
        Location location = getLocationById(id);
        location.setName(locationDetails.getName());
        location.setDescription(locationDetails.getDescription());
        return locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        Location location = getLocationById(id);
        locationRepository.delete(location);
    }
}