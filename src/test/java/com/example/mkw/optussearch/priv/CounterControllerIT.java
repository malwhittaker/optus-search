package com.example.mkw.optussearch.priv;

import com.example.mkw.optussearch.CounterController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.misc.IOUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CounterController.class)
@WebIntegrationTest
public class CounterControllerIT {

    private static final Logger _logger = LoggerFactory.getLogger(CounterControllerIT.class);

    @InjectMocks
    CounterController controller;

    private MockMvc mvc;

    @Autowired
    WebApplicationContext context;

//    @Resource
//    @SuppressWarnings("unused")
//    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .addFilter(springSecurityFilterChain)
                .build();

        try {
            analyzeStandardText();
        }
        catch (Exception e) {
            _logger.error("Failed to load standard text", e);
        }
    }

    // ------------------------------------- Tests --------------------------------------------------------------------

    @Test
    public void testSearch() throws Exception {
        // @formatter:off
        MvcResult result = mvc.perform(post("/counter-api/search")
                .content("{\"wordList\":[\"Duis\", \"Sed\", \"Donec\", \"Augue\", \"Pellentesque\", \"123\"]}\n")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on
    }

    @Test
    public void testTop5AsJson() throws Exception {
        // @formatter:off
        MvcResult result = mvc.perform(get("/counter-api/top/5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        assertEquals("{\"counts\":[{\"eget\":17},{\"vel\":17},{\"sed\":16},{\"in\":15},{\"et\":14}]}", result.getResponse().getContentAsString());
    }

    @Test
    public void testTop5AsText() throws Exception {
        // @formatter:off
        MvcResult result = mvc.perform(get("/counter-api/top/5")
                .accept("text/csv"))
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        assertEquals("eget|17\nvel|17\nsed|16\nin|15\net|14\n", result.getResponse().getContentAsString());

    }

//
//    @Resource
//    @SuppressWarnings("unused")
//    private FilterChainProxy springSecurityFilterChain;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .addFilter(springSecurityFilterChain)
//                .build();
//    }
//
//    @Test
//    public void listUsers() throws Exception {
//        // @formatter:off
//        mvc.perform(get("/users")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
//        // @formatter:on
//    }

    // ------------------------------Supporting methods --------------------------------------------------------------

    private void analyzeStandardText() throws Exception {

        // Read test content
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testdata.txt").getFile());
        _logger.debug("setUp(): readStream, file={}", file);

        InputStream stream = new FileInputStream(file);
        _logger.debug("setUp(): readStream, stream={}", stream);

        byte[] bytes = IOUtils.readFully(stream, -1, false);
        String content = new String(bytes);

        mvc.perform(post("/counter-api/analyze")
                .content(content)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

}