package com.project.entity.business;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ImageSerializer extends StdSerializer<Image> {


    public ImageSerializer() {
        this(null);
    }

    public ImageSerializer(Class<Image> t) {
        super(t);
    }

    @Override
    public void serialize(Image image, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", image.getId());
       // gen.writeStringField("url", image.getUrl()); // Örnek alan
        gen.writeEndObject();
    }
}
