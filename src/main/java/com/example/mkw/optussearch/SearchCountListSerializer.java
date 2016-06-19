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
public class SearchCountListSerializer  extends JsonSerializer<SearchCountList> {

    @Override
    public void serialize(SearchCountList searchCountList, JsonGenerator jgen,
                          SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        Map<String,Integer> contentMap = searchCountList.getContentMap();

        jgen.writeStartObject();
        jgen.writeFieldName("counts");
        jgen.writeStartArray();
        for (String key : searchCountList.getRequestList()) {
            jgen.writeStartObject();
            jgen.writeFieldName(key);
            jgen.writeNumber(contentMap.get(key));
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}

