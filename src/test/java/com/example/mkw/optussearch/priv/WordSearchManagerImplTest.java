package com.example.mkw.optussearch.priv;

import com.example.mkw.optussearch.WordSearchManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by mal on 18/06/16.
 */
public class WordSearchManagerImplTest {

    private static final Logger _logger = LoggerFactory.getLogger(WordSearchManagerImplTest.class);

    private WordSearchManager _wordSearchManager = new WordSearchManagerImpl();

    private List<String> _searchTargets;
    private Map<String,Integer> _expectedSearchResults;

    @Before
    public void setUp() throws Exception {
        _logger.debug("setUp(): enter");

        // Read test content
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testdata.txt").getFile());
        _logger.debug("setUp(): readStream, file={}", file);

        InputStream stream = new FileInputStream(file);
        _logger.debug("setUp(): readStream, stream={}", stream);

        byte[] bytes = IOUtils.readFully(stream, -1, false);
        String content = new String(bytes);

        _wordSearchManager.analyze(content);

        List<String> list = new ArrayList<String>();
        list.add("Duis");
        list.add("Sed");
        list.add("Donec");
        list.add("Augue");
        list.add("Pellentesque");
        list.add("123");
        _searchTargets = list;

        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("Duis", 11);
        map.put("Sed", 16);
        map.put("Donec", 8);
        map.put("Augue", 7);
        map.put("Pellentesque", 6);
        map.put("123", 0);
        _expectedSearchResults = map;

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSearch() {
        _logger.debug("testSearch(): enter");
        Map<String,Integer> searchResult = _wordSearchManager.search(_searchTargets);
        _logger.debug("testSearch(): mid, result={}", searchResult);
        assertEquals("Result must be as expected", _expectedSearchResults, searchResult);
    }
}