package com.example.mkw.optussearch;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Serializer to present the search response {@see WordCountList} in the specified format, as illustrated by the
 * following sample:
 *
 * {"counts": [{"Duis": 11}, {"Sed": 16}, {"Donec": 8}, {"Augue": 7}, {"Pellentesque": 6}, {"123": 0}]}
 */
public class WordCountSerializer extends JsonSerializer<WordCount> {

    @Override
    public void serialize(WordCount wordCount, JsonGenerator jgen, SerializerProvider provider) throws IOException {

            jgen.writeStartObject();
            jgen.writeFieldName(wordCount.getWord());
            jgen.writeNumber(wordCount.getCount());
            jgen.writeEndObject();
    }
}
