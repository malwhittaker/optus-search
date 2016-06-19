package com.example.mkw.optussearch;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to hold incoming search specification.
 *
 * Sample JSON encoding:
 *
 * {“searchText”:[“Duis”, “Sed”, “Donec”, “Augue”, “Pellentesque”, “123”]}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchSpec {

    private List<String> _wordList;

    /**
     * Default constructor - create empty searchList so empty list is shown as {"searchText":[]}  rather than {}
     */
    public SearchSpec() {
        _wordList = new ArrayList<String>();
    }

    /**
     * Construct with list - probably only used within tests
     * @param wordList
     */
    public SearchSpec(List<String> wordList) {
        _wordList = wordList;
    }

    public List<String> getWordList() {
        return _wordList;
    }

    public void setWordList(List<String> wordList) {
        _wordList = wordList;
    }

}
