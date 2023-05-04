package com.jacksonannotations.jsonserialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomSerialzer extends StdSerializer<JsonSerializePOJO >{

    public CustomSerialzer(Class <JsonSerializePOJO> t ) {
        super(t);
    }

    public CustomSerialzer(){
        this(null);
    }


    @Override
    public void serialize(JsonSerializePOJO jsonSerializePOJO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("first_Name",jsonSerializePOJO.getFirstname());
        jsonGenerator.writeStringField("last_Name",jsonSerializePOJO.getLastname());
        jsonGenerator.writeStringField("email" , jsonSerializePOJO.getEmail());
        jsonGenerator.writeEndObject();



    }
}
