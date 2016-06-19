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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;
import sun.misc.IOUtils;

import javax.annotation.Resource;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CounterController.class)
@WebIntegrationTest
@SuppressWarnings("unused")
public class CounterControllerIT {

    private static final Logger _logger = LoggerFactory.getLogger(CounterControllerIT.class);

    private static final String USER_0 = "mal";
    private static final String PASSWORD_0 = "secret";

    private static final String USER_1 = "optus";
    private static final String PASSWORD_1 = "candidates";

    // Test values
    private static final String SEARCH_SPEC_1 = "{\"wordList\":[\"Duis\", \"Sed\", \"Donec\", \"Augue\", \"Pellentesque\", \"123\"]}";
    private static final String SEARCH_RESULT_1 = "{\"counts\":[{\"Duis\":11},{\"Sed\":16},{\"Donec\":8},{\"Augue\":7},{\"Pellentesque\":6},{\"123\":0}]}";
    private static final String TOP_5_RESULT_JSON = "{\"counts\":[{\"eget\":17},{\"vel\":17},{\"sed\":16},{\"in\":15},{\"et\":14}]}";
    private static final String TOP_5_RESULT_TEXT = "eget|17\nvel|17\nsed|16\nin|15\net|14\n";

    @InjectMocks
    CounterController controller;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
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
    public void testUnauthorizedRequestRejected() throws Exception {

        mvc.perform(post("/counter-api/search")
                .content(SEARCH_SPEC_1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testSearch() throws Exception {

        MvcResult result = mvc.perform(post("/counter-api/search")
                .header(HttpHeaders.AUTHORIZATION, generateAuthHeader(USER_1, PASSWORD_1))
                .content(SEARCH_SPEC_1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(SEARCH_RESULT_1, result.getResponse().getContentAsString());

    }

    @Test
    public void testTop5AsJson() throws Exception {

        MvcResult result = mvc.perform(get("/counter-api/top/5")
                .header(HttpHeaders.AUTHORIZATION, generateAuthHeader(USER_1, PASSWORD_1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(TOP_5_RESULT_JSON, result.getResponse().getContentAsString());
    }

    @Test
    public void testTop5AsText() throws Exception {

        MvcResult result = mvc.perform(get("/counter-api/top/5")
                .header(HttpHeaders.AUTHORIZATION, generateAuthHeader(USER_1, PASSWORD_1))
                .accept("text/csv"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(TOP_5_RESULT_TEXT, result.getResponse().getContentAsString());

    }

    // ------------------------------Supporting methods --------------------------------------------------------------

    private void analyzeStandardText() throws Exception {

        // Read test content
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testdata.txt");
        _logger.debug("setUp(): readStream, stream={}", stream);

        byte[] bytes = IOUtils.readFully(stream, -1, false);
        String content = new String(bytes);

        mvc.perform(post("/counter-api/analyze")
                .header(HttpHeaders.AUTHORIZATION, generateAuthHeader(USER_0, PASSWORD_0))
                .content(content)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    private String generateAuthHeader(String username, String password) {
        String plainCredentials = username + ":" + password;
        String base64Credentials = Base64Utils.encodeToString(plainCredentials.getBytes());
        return "Basic " + base64Credentials;
    }

}