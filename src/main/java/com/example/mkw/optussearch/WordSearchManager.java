package com.example.mkw.optussearch;

import java.util.List;
import java.util.Map;

/**
 * Created by mal on 18/06/16.
 */
public interface WordSearchManager {

    //Map<String,Integer> search(List<String> targetList);

    /**
     * Return the
     * @param number
     * @return
     */
    //Map<String,Integer> topResults(int number);

    /**
     * Lookup the number of times a word has occurred in the analyzed text (case is not significant).
     * If the given word is not present, return 0.
     *
     * @param word Target for lookup.
     * @return number of time word found in analyzed text.
     */
    Integer lookupCount(String word);

    void analyze(String content);

}
