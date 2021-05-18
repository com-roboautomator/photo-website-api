package com.roboautomator.app.component.image;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.roboautomator.app.component.util.DefaultEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "image")
@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class ImageEntity extends DefaultEntity {
    
    private String title;
    private String url;
    private Integer index;
    private String description;

}
