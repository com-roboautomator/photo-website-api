package com.roboautomator.app.component.collection;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionRepository collectionRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UUID createCollection(@Valid @RequestBody CollectionUpdate update) {
        log.info("Creating new collection");
        UUID id = UUID.randomUUID();
        collectionRepository.save(CollectionEntity.builder().id(id).build().update(update));
        return id;
    }

    @GetMapping(value = "/{collectionId}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionEntity getCollection(@PathVariable String collectionId) {
        return getEntity(collectionId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CollectionEntity> getCollections(){
        return collectionRepository.findAll();
    }


    @PutMapping(value = "/{collectionId}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionEntity updateCollection(@PathVariable String collectionId,
            @Valid @RequestBody CollectionUpdate collectionUpdate) {
        var entity = getEntity(collectionId);

        log.info("Updating collection with id: " + collectionId);
        collectionRepository.save(entity.update(collectionUpdate));
        return entity;
    }

    @DeleteMapping(value = "/{collectionId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCollection(@PathVariable String collectionId) {
        var entity = getEntity(collectionId);
        collectionRepository.deleteById(entity.getId());
    }

    private CollectionEntity getEntity(String collectionId) {

        checkIfValidUUID(collectionId);

        var maybeCollection = collectionRepository.findById(UUID.fromString(collectionId));

        if (maybeCollection.isPresent()) {
            return maybeCollection.get();
        } else {
            log.info("Collection with id \"" + collectionId + "\" not found");
            throw new CollectionControllerEntityNotFoundException(
                    "Collection with id \"" + collectionId + "\" not found");
        }

    }

    private void checkIfValidUUID(String uuid) {
        if (!uuid.matches("^\\{?[A-F0-9a-f]{8}-[A-F0-9a-f]{4}-[A-F0-9a-f]{4}-[A-F0-9a-f]{4}-[A-F0-9a-f]{12}\\}?$")) {
            String errorMessage = uuid + " is not a valid UUID";
            log.info(errorMessage);
            throw new CollectionControllerValidationException(errorMessage, "collectionId");
        }
    }

}
