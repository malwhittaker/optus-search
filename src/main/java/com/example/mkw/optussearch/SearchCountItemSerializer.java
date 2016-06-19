package com.example.mkw.optussearch;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

/**
 * Serializer to present the search response {@see SearchCountList} in the specified format, as illustrated by the
 * following sample:
 *
 * {"counts": [{"Duis": 11}, {"Sed": 16}, {"Donec": 8}, {"Augue": 7}, {"Pellentesque": 6}, {"123": 0}]}
 */
public class SearchCountItemSerializer extends JsonSerializer<SearchCountItem> {

    @Override
    public void serialize(SearchCountItem searchCountItem, JsonGenerator jgen,
                          SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
            jgen.writeStartObject();
            jgen.writeFieldName(searchCountItem.getWord());
            jgen.writeNumber(searchCountItem.getCount());
            jgen.writeEndObject();
    }
}
