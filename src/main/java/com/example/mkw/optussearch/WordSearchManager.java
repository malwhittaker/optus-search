package com.example.mkw.optussearch;

/**
 * Define the interface for the underlying business logic related to the given spec
 */
public interface WordSearchManager {

    /**
     * Return the most frequently occurring words in the analyzed text.
     *
     * @param count The number of words requested (eg 5 means, return top 5 words).
     * @return List of most frequently occurring words and their counts.
     */
    WordCountList lookupMostFrequentWords(Integer count);

    /**
     * Lookup the number of times a word has occurred in the analyzed text (case is not significant).
     * If the given word is not present, return 0.
     *
     * @param word Target for lookup.
     * @return number of time word found in analyzed text.
     */
    Integer lookupCount(String word);

    /**
     * Analyze the given text to support subsequent queries.
     *
     * @param content The text content to be analyzed.
     */
    void analyze(String content);

}
