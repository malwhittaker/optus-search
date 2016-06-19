package com.example.mkw.optussearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for "counter-api" endpoints.
 *
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/counter-api")
@ComponentScan
@SuppressWarnings("unused")
public class CounterController {

    private static final String CSV_SEPARATOR = "|";

    @Autowired
    private WordSearchManager _wordSearchManager;

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public ResponseEntity<WordCountList> search(@RequestBody SearchSpec searchSpec) {

        WordCountList wordCountList = new WordCountList();
        for (String word : searchSpec.getWordList()) {
            wordCountList.addItem(new WordCount(word, _wordSearchManager.lookupCount(word)));
        }
        return new ResponseEntity<WordCountList>(wordCountList, HttpStatus.OK);
    }

    @RequestMapping(value = "/top/{number}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ResponseEntity<WordCountList> findMostFrequentAsJson(@PathVariable Integer number) {

        WordCountList wordCountList = _wordSearchManager.lookupMostFrequentWords(number);
        return new ResponseEntity<WordCountList>(wordCountList, HttpStatus.OK);
    }

    @RequestMapping(value = "/top/{number}", method = RequestMethod.GET, produces = "text/csv; charset=UTF-8")
    public ResponseEntity<String> findMostFrequentAsCsv(@PathVariable Integer number) {

        WordCountList wordCountList = _wordSearchManager.lookupMostFrequentWords(number);
        String textList = formatListAsCsv(wordCountList);
        return new ResponseEntity<String>(textList, HttpStatus.OK);
    }

    @RequestMapping(value = "/analyze", method = RequestMethod.POST, consumes = "text/plain; charset=UTF-8")
    public void analyze(@RequestBody String content) {

        _wordSearchManager.analyze(content);
    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(CounterController.class, args);
    }

    //------------------------------------- Private methods ------------------------------------------------------

    private static String formatListAsCsv(WordCountList wordCountList) {

        StringBuilder buffer = new StringBuilder();
        for (WordCount wordCount : wordCountList.getItemList()) {
            buffer.append(wordCount.getWord());
            buffer.append(CSV_SEPARATOR);
            buffer.append(wordCount.getCount());
            buffer.append("\n");
        }
        return buffer.toString();
    }
}