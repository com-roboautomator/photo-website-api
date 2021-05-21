package com.roboautomator.app.component.image;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
    Optional<ImageEntity> findById(UUID id);
}
