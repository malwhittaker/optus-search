package com.example.mkw.optussearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity containing result of a search.
 *
 * The desired encoding is not quite a map, its a list of elements each containing a name and value.
 * The order is significant, allowing search results to be returned in the same order as requested, and retaining
 * order for /top/N requests.
 *
 * Sample JSON encoding:
 * {"counts": [{"Duis": 11}, {"Sed": 16}, {"Donec": 8}, {"Augue": 7}, {"Pellentesque": 6}, {"123": 0}]}
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WordCountList {

    private List<WordCount> _itemList;

    public WordCountList() {
        _itemList = new ArrayList<WordCount>();
    }

    public WordCountList(List<WordCount> itemList) {
        _itemList = itemList;
    }

    @JsonProperty("counts")
    public List<WordCount> getItemList() {
        return _itemList;
    }

    public void setItemList(List<WordCount> requestList) {
        _itemList = requestList;
    }

    public void addItem(WordCount wordCount) {
        _itemList.add(wordCount);
    }

}
