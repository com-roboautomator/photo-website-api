package com.roboautomator.app.component.slider;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/slider")
@RequiredArgsConstructor
public class SliderController {

    private final SliderRepository sliderRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UUID createSlider(@Valid @RequestBody SliderUpdate update){
        log.info("Creating new slider");
        UUID id = UUID.randomUUID();
        sliderRepository.save(SliderEntity.builder().id(id).build().update(update));
        return id;
    }

    @GetMapping(value = "/{sliderId}")
    @ResponseStatus(HttpStatus.OK)
    public SliderEntity getSlider(@PathVariable String sliderId){
        return getEntity(sliderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SliderEntity> getSliders(){
        return sliderRepository.findAll();
    }

    @PutMapping(value = "/{sliderId}")
    @ResponseStatus(HttpStatus.OK)
    public SliderEntity updateSlider(@PathVariable String sliderId, @Valid @RequestBody SliderUpdate sliderUpdate){
        var entity = getEntity(sliderId);

        log.info("Updating slider with id: " + sliderId);
        sliderRepository.save(entity.update(sliderUpdate));
        return entity;
    }

    @DeleteMapping(value = "/{sliderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSlider(@PathVariable String sliderId){
        var entity = getEntity(sliderId);
        sliderRepository.deleteById(entity.getId());
    }

    private SliderEntity getEntity(String sliderId) {
        checkIfValidUUID(sliderId);

        var maybeSlider = sliderRepository.findById(UUID.fromString(sliderId));

        if (maybeSlider.isPresent()) {
            return maybeSlider.get();
        } else {
            String info = "Slider with id \"" + sliderId + "\" not found";
            log.info(info);
            throw new SliderControllerEntityNotFoundException(info);
        }
    }

    private void checkIfValidUUID(String uuid) {
        if (!uuid.matches("^\\{?[A-F0-9a-f]{8}-[A-F0-9a-f]{4}-[A-F0-9a-f]{4}-[A-F0-9a-f]{4}-[A-F0-9a-f]{12}\\}?$")) {
            String errorMessage = uuid + " is not a valid UUID";
            log.info(errorMessage);
            throw new SliderControllerValidationException(errorMessage, "sliderId");
        }
    }

}
