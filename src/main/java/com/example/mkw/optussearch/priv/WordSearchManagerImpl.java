package com.example.mkw.optussearch.priv;

import com.example.mkw.optussearch.WordSearchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementation of the WordSearchManager.
 *
 * Analyzes given text, and then supports subsequent queries related to word frequency.
 */
@Service
public class WordSearchManagerImpl implements WordSearchManager {

    private static final Logger _logger = LoggerFactory.getLogger(WordSearchManagerImpl.class);

    private Map<String,Integer> _wordCountMap;

    @Override
    public Integer lookupCount(String word) {

        if (_wordCountMap == null) {
            throw new RuntimeException("No content provided.");
        }

        Integer count = _wordCountMap.get(word.toLowerCase());
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public void analyze(String content) {

        Scanner scanner = new Scanner(content);
        scanner.useDelimiter("[\\s.,]+");// Treat multiple consecutive delimiters as one

        Map<String,Integer> map = new HashMap<String, Integer>();

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            addWordToMap(map, word);
            //_logger.debug("analyze(): add, word={}", word);
        }
        _wordCountMap = map;
    }

    // ---------------------------------------- Private methods ------------------------------------------------------

    private void addWordToMap(Map<String,Integer> map, String word) {

        Integer count = map.get(word);
        if (count == null) {
            count = 1;
        }
        else {
            count = count + 1;
        }
        map.put(word, count);
    }
}
