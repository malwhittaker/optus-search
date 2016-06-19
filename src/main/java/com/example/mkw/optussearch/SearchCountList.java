package com.example.mkw.optussearch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Entity containing result of a search.
 *
 * The desired encoding is not quite a map, its a list of elements each containing a name and value.
 * I don't know if the order is significant - does the client want the results back in the same order of the request?
 * If it is, we'll need to preserve that separately, we cannot rely on the order of keys in the map.
 *
 * Sample JSON encoding:
 * {"counts": [{"Duis": 11}, {"Sed": 16}, {"Donec": 8}, {"Augue": 7}, {"Pellentesque": 6}, {"123": 0}]}
 *
 * This is *NOT* the way I would normally map JSON entities.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = SearchCountListSerializer.class)
public class SearchCountList {

    private Map<String,Integer> _contentMap;
    private List<String> _requestList;

//    public SearchCountList(Map<String,Integer> contentMap) {
//        _contentMap = contentMap;
//    }

    public SearchCountList(List<String> requestList, Map<String,Integer> contentMap) {
        _requestList = requestList;
        _contentMap = contentMap;
    }

    @JsonIgnore
    public Map<String,Integer> getContentMap() {
        return _contentMap;
    }

    @JsonIgnore
    public List<String> getRequestList() {
        return _requestList;
    }

}
