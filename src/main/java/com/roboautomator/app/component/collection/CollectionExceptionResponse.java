package com.roboautomator.app.component.collection;

import com.roboautomator.app.DefaultExceptionResponse;
import com.roboautomator.app.component.util.ValidationError;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class CollectionExceptionResponse extends DefaultExceptionResponse<ValidationError> {
}
