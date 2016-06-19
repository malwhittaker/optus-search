package com.example.mkw.optussearch;

import java.util.List;
import java.util.Map;

/**
 * Created by mal on 18/06/16.
 */
public interface WordSearchManager {

    Map<String,Integer> search(List<String> targetList);

    /**
     * Return the
     * @param number
     * @return
     */
    Map<String,Integer> topResults(int number);

    //Integer search(String target);

    void analyze(String content);

}
