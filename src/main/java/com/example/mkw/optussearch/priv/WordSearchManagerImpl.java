package com.example.mkw.optussearch.priv;

import com.example.mkw.optussearch.WordCount;
import com.example.mkw.optussearch.WordCountList;
import com.example.mkw.optussearch.WordSearchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementation of the WordSearchManager.
 *
 * Analyzes given text, and then supports subsequent queries related to word frequency.
 */
@Service
class WordSearchManagerImpl implements WordSearchManager {

    private static final Logger _logger = LoggerFactory.getLogger(WordSearchManagerImpl.class);

    private Map<String,Integer> _wordCountMap;
    private List<WordCount> _wordCountList;

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
    public WordCountList lookupMostFrequentWords(Integer count) {

        if (_wordCountMap == null) {
            throw new RuntimeException("No content provided.");
        }

        WordCountList wordCountList = new WordCountList();
        // Limit count to number of words in list
        for (int i = 0; i < Math.min(count, _wordCountList.size()); i++) {
            wordCountList.addItem(_wordCountList.get(i));
        }
        return wordCountList;
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

        // Beware possible race condition here
        _wordCountList = composeListByFrequency(map);
        _wordCountMap = map;
        _logger.debug("analyze(): completed, words={}", _wordCountList.size());
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

    private List<WordCount> composeListByFrequency(Map<String,Integer> map) {
        List<WordCount> list = new ArrayList<WordCount>();
        for (String word : map.keySet()) {
            list.add(new WordCount(word, map.get(word)));
        }

        // Note that comparator is embedded here rather than on WordCount, because some other use might require the list in alpha order (for example)
        Collections.sort(list, new Comparator<WordCount>() {
            public int compare(WordCount wc1, WordCount wc2) {
                int countCompare = wc2.getCount() - wc1.getCount();
                if (countCompare != 0) {
                    return countCompare;
                }
                else {
                    return wc1.getWord().compareToIgnoreCase(wc2.getWord());
                }
            }
        });
        return list;
    }
}
