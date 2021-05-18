package com.roboautomator.app.component.collection;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<CollectionEntity, UUID>{
    Optional<CollectionEntity> findById(UUID id);
    Optional<CollectionEntity> findByTitle(String title);
}
