package com.roboautomator.app.component.image;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;

    @PutMapping(value = "/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    public ImageEntity updateImage(@PathVariable String imageId, @Valid @RequestBody ImageUpdate imageUpdate) {
        var entity = getEntity(imageId);

        imageRepository.save(entity.update(imageUpdate));
        return entity;
    }

    @GetMapping(value = "/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    public ImageEntity getImage(@PathVariable String imageId){
        return getEntity(imageId);
    }

    private ImageEntity getEntity(String uuid) {

        //checkIfValidUUID(uuid);

        var maybeImage = imageRepository.findById(UUID.fromString(uuid));
        if (maybeImage.isPresent()) {
            return maybeImage.get();
        } else {
            return null;
        }

    }

    private void checkIfValidUUID(String uuid) {
        if (!uuid.matches("^\\{?[A-F0-9a-f]{8}-[A-F0-9a-f]{4}-[A-F0-9a-f]{4}-[A-F0-9a-f]{4}-[A-F0-9a-f]{12}\\}?$")) {
            String errorMessage = uuid + " is not a valid UUID";
            log.info(errorMessage);
            throw new ImageControllerValidationException(errorMessage, "imageId");
        }
    }

}
