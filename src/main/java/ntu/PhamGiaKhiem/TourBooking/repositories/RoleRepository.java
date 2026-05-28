package ntu.PhamGiaKhiem.TourBooking.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import ntu.PhamGiaKhiem.TourBooking.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
