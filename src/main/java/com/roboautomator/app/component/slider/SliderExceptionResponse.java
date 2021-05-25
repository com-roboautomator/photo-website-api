package com.roboautomator.app.component.slider;

import com.roboautomator.app.component.util.DefaultExceptionResponse;
import com.roboautomator.app.component.util.ValidationError;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class SliderExceptionResponse extends DefaultExceptionResponse<ValidationError> {

}
