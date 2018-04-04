package com.zuora.exercise.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static co.unruly.matchers.StreamMatchers.equalTo;
import static org.hamcrest.core.Is.is;

@Test
public class TopNPopularPathServiceImplTest {

    private TopNPopularPathServiceImpl service;
    private String[][] data;
    private Map<String, List<String>> expectedMapOfPagesGroupedByUser;
    private List<String> expectedAllThreePagePathsList;
    private Map<String, Long> expectedMapOfThreePagePathsWithCount;
    private String[] expectedTop3Paths;
    private String[] expectedTop1Paths;
    private String[] expectedTop10Paths;

    @BeforeMethod
    public void setUp() {
        service = new TopNPopularPathServiceImpl();
        data = new String[][]{{"User1", "/"}, {"User1", "login"}, {"User2", "/"},
                {"User1", "checkout"}, {"User2", "login"}, {"User1", "login"},
                {"User1", "subscriber"}, {"User2", "login"}, {"User2", "subscriber"}, {"User1", "/"},
                {"User3", "/"}, {"User3","login"}, {"User3","checkout"},
                {"User4", "/"}, {"User4","login"}, {"User4","checkout"},
                {"User5", "/"}, {"User5","login"}, {"User5","login"}};

        expectedMapOfPagesGroupedByUser = new HashMap<>();
        expectedMapOfPagesGroupedByUser.put("User1", asList("/", "login", "checkout", "login", "subscriber", "/"));
        expectedMapOfPagesGroupedByUser.put("User2", asList("/", "login", "login", "subscriber"));
        expectedMapOfPagesGroupedByUser.put("User3", asList("/", "login", "checkout"));
        expectedMapOfPagesGroupedByUser.put("User4", asList("/", "login", "checkout"));
        expectedMapOfPagesGroupedByUser.put("User5", asList("/", "login", "login"));

        expectedAllThreePagePathsList = asList("/ -> login -> login", "login -> login -> subscriber",
                "/ -> login -> checkout", "login -> checkout -> login",
                "checkout -> login -> subscriber", "login -> subscriber -> /",
                "/ -> login -> login", "/ -> login -> checkout",
                "/ -> login -> checkout");

        expectedMapOfThreePagePathsWithCount = new HashMap<>();
        expectedMapOfThreePagePathsWithCount.put("/ -> login -> login", 2L);
        expectedMapOfThreePagePathsWithCount.put("login -> subscriber -> /", 1L);
        expectedMapOfThreePagePathsWithCount.put("login -> login -> subscriber", 1L);
        expectedMapOfThreePagePathsWithCount.put("login -> checkout -> login", 1L);
        expectedMapOfThreePagePathsWithCount.put("checkout -> login -> subscriber", 1L);
        expectedMapOfThreePagePathsWithCount.put("/ -> login -> checkout", 3L);

        expectedTop3Paths = new String[]{"/ -> login -> checkout : 3", "/ -> login -> login : 2", "login -> subscriber -> / : 1"};
        expectedTop1Paths = new String[]{"/ -> login -> checkout : 3"};
        expectedTop10Paths = new String[]{"/ -> login -> checkout : 3", "/ -> login -> login : 2",
                "login -> subscriber -> / : 1", "login -> login -> subscriber : 1",
                "login -> checkout -> login : 1", "checkout -> login -> subscriber : 1"};
    }

    public void shouldCreateMapOfPagesGroupedByUser() {
        Map<String, List<String>> threePagePathsWithCount = service.getPagesVisitedByUser(data);
        assertThat(threePagePathsWithCount.entrySet().stream(), equalTo(expectedMapOfPagesGroupedByUser.entrySet().stream()));
    }

    public void shouldCreateListOfAllThreePagePathsWhenProvidedMapOfPagesGroupedByUser() {
        List<String> allThreePagePathsList = service.getAllThreePagePaths(expectedMapOfPagesGroupedByUser);
        assertThat(allThreePagePathsList.stream(), equalTo(expectedAllThreePagePathsList.stream()));
    }

    public void shouldCreateMapOfThreePagePathWithCount() {
        Map<String, Long> threePagePathsWithCount = service.getThreePagePathsWithCount(expectedMapOfPagesGroupedByUser);
        assertThat(threePagePathsWithCount.entrySet().stream(), equalTo(expectedMapOfThreePagePathsWithCount.entrySet().stream()));
    }

    public void shouldReturnTopNPopularPaths() {
        service.setup(data);
        String[] top3Paths = service.getTopNPopularPaths(3);
        String[] top1Paths = service.getTopNPopularPaths(1);
        String[] top10Paths = service.getTopNPopularPaths(10);

        assertThat(top1Paths, is(expectedTop1Paths));
        assertThat(top3Paths, is(expectedTop3Paths));
        assertThat(top10Paths, is(expectedTop10Paths));
    }

}