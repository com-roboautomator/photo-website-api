CREATE TABLE collection_sliders (
    slider_id UUID NOT NULL, 
    collection_id UUID NOT NULL,
    CONSTRAINT collection_sliders_pk PRIMARY KEY (collection_id, slider_id),
    CONSTRAINT FK_collection FOREIGN KEY (collection_id) REFERENCES collection (id),
    CONSTRAINT FK_slider FOREIGN KEY (slider_id) REFERENCES slider (id)
);