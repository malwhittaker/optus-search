package com.example.mkw.optussearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mal on 18/06/16.
 *
 Result in JSON:
 > {"counts": [{"Duis": 11}, {"Sed": 16}, {"Donec": 8}, {"Augue": 7}, {"Pellentesque": 6}, {"123": 0}]}
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/counter-api")
@ComponentScan
public class CounterController {

    @Autowired
    private WordSearchManager _wordSearchManager;

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public ResponseEntity<SearchCountList> search(@RequestBody SearchList searchList) {
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        map.put("Duis", 11);
//        map.put("Sed", 16);
//        SearchCountList searchCountList = new SearchCountList(map);
        SearchCountList searchCountList = new SearchCountList();
        for (String word : searchList.getSearchText()) {
            searchCountList.addItem(new SearchCountItem(word, _wordSearchManager.lookupCount(word)));
        }
        return new ResponseEntity<SearchCountList>(searchCountList, HttpStatus.OK);
    }

    @RequestMapping(value = "/analyze", method = RequestMethod.POST, consumes = "application/text; charset=UTF-8")
    public void analyze(@RequestBody String content) {
        _wordSearchManager.analyze(content);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CounterController.class, args);
    }

}