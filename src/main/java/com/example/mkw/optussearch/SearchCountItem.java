package com.example.mkw.optussearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Holds elements of the the search result.
 *
 * Requires custom serialization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = SearchCountItemSerializer.class)
public class SearchCountItem {

    private String _word;
    private Integer _count;

    public SearchCountItem(String word, Integer count) {
        _word = word;
        _count = count;
    }

    /**
     * @return Return the count associated with this item.
     */
    public String getWord() {
        return _word;
    }

    public void setWord(String word) {
        _word = word;
    }

    /**
     * @return Return the count associated with this item.
     */
    public Integer getCount() {
        return _count;
    }

    public void setCount(Integer count) {
        _count = count;
    }

}
