CREATE TABLE image_collections (
    collection_id UUID NOT NULL,
    image_id UUID NOT NULL,
    CONSTRAINT image_collections_pk PRIMARY KEY (image_id, collection_id),
    CONSTRAINT FK_image FOREIGN KEY (image_id) REFERENCES image (id),
    CONSTRAINT FK_collection FOREIGN KEY (collection_id) REFERENCES collection (id)
);