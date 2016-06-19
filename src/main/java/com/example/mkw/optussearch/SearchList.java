package com.example.mkw.optussearch;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to hold incoming search specification.
 *
 * Sample JSON encoding"
 *
 * {“searchText”:[“Duis”, “Sed”, “Donec”, “Augue”, “Pellentesque”, “123”]}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchList {

    private List<String> _searchText;

    /**
     * Default constructor - create empty searchList so empty list is shown as {"searchText":[]}  rather than {}
     */
    public SearchList() {
        _searchText = new ArrayList<String>();
    }

    /**
     * Construct with list - probably only used within tests
     * @param searchList
     */
    public SearchList(List<String> searchList) {
        _searchText = searchList;
    }

    public List<String> getSearchText() {
        return _searchText;
    }

    public void setSearchText(List<String> searchText) {
        _searchText = searchText;
    }

}
