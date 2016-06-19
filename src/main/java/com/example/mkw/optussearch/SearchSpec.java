package com.example.mkw.optussearch;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to hold incoming search specification.
 * Essentially it is a simple list of words with a wrapper to name the list "searchText".
 *
 * Sample JSON encoding:
 *
 * {“searchText”:[“Duis”, “Sed”, “Donec”, “Augue”, “Pellentesque”, “123”]}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
class SearchSpec {

    private List<String> _wordList;

    /**
     * Default constructor - create empty searchList so empty list is shown as {"searchText":[]}  rather than {}
     */
    public SearchSpec() {
        _wordList = new ArrayList<String>();
    }

    /**
     * Construct with list - probably only used within tests
     *
     * @param wordList This list of words to be searched.
     */
    public SearchSpec(List<String> wordList) {
        _wordList = wordList;
    }

    /**
     * Return the contained list of words
     * @return
     */
    public List<String> getWordList() {
        return _wordList;
    }

    public void setWordList(List<String> wordList) {
        _wordList = wordList;
    }

}
