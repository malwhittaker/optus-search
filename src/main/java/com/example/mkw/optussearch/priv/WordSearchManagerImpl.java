package com.example.mkw.optussearch.priv;

import com.example.mkw.optussearch.WordSearchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mal on 18/06/16.
 */
@Service
public class WordSearchManagerImpl implements WordSearchManager {

    private static final Logger _logger = LoggerFactory.getLogger(WordSearchManagerImpl.class);

    private Map<String,Integer> _wordCountMap;

    @Override
    public Map<String, Integer> search(List<String> targetList) {
        if (_wordCountMap == null) {
            throw new RuntimeException("No content provided.");
        }

        Map<String,Integer> map = new HashMap<String, Integer>();
        for (String target : targetList) {
            Integer count = _wordCountMap.get(target.toLowerCase());
            if (count == null) {
                count = 0;
            }
            // Use requested word in response (may be mixed case), rather than word from map (always lowercase) - I don't like this, but it matches given tests
            map.put(target, count);
        }
        return map;
    }

    @Override
    public void analyze(String content) {
        Scanner scanner = new Scanner(content);
        scanner.useDelimiter("[\\s.,]+");// Treat multiple consecutive delimeters as one

        Map<String,Integer> map = new HashMap<String, Integer>();

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            addWordToMap(map, word);
            //_logger.debug("analyze(): add, word={}", word);
        }
        _wordCountMap = map;
    }

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
