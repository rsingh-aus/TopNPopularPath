package com.zuora.exercise.service;

import com.zuora.exercise.input.InputProcessor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Test
public class TopNPopularPathServiceIntegrationTest {

    private final String LOG_FILE = "logs.txt";

    private TopNPopularPathService service;

    private String[] expectedTop2Paths;

    private String[] expectedTop1Paths;

    private String[] expectedTop10Paths;

    @BeforeMethod
    public void setUp() {
        service = new TopNPopularPathServiceImpl();
        service.setup(new InputProcessor().getData(LOG_FILE));

        expectedTop10Paths = new String[]{"/ -> login -> subscriber : 3",
                "/ -> login -> product : 2", "login -> subscriber -> / : 1"};

        expectedTop2Paths = new String[]{"/ -> login -> subscriber : 3", "/ -> login -> product : 2"};

        expectedTop1Paths = new String[]{"/ -> login -> subscriber : 3"};
    }

    public void shouldGetCorrectTop10PopularPathFromLogFile() {
        assertThat(service.getTopNPopularPaths(10), is(expectedTop10Paths));
    }

    public void shouldGetCorrectTop2PopularPathFromLogFile() {
        assertThat(service.getTopNPopularPaths(2), is(expectedTop2Paths));
    }

    public void shouldGetCorrectTop1PopularPathFromLogFile() {
        assertThat(service.getTopNPopularPaths(1), is(expectedTop1Paths));
    }

}