package com.zuora.exercise.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class TopNPopularPathServiceImpl implements TopNPopularPathService {

    private Map<String, Long> pathsWithCount = new LinkedHashMap<>();

    @Override
    public void setup(String[][] data) {

        Map<String, List<String>> pagesVisitedByUser = getPagesVisitedByUser(data);
        Map<String, Long> threePagePathsWithCount = getThreePagePathsWithCount(pagesVisitedByUser);

        pathsWithCount = getReverseSortedThreePagePathsWithCount(threePagePathsWithCount);
    }

    @Override
    public String[] getTopNPopularPaths(int n) {
         return pathsWithCount.entrySet()
                 .stream()
                 .limit(n)
                 .map(entry -> entry.getKey() + " : " + entry.getValue())
                 .toArray(String[]::new);
    }

    LinkedHashMap<String, Long> getReverseSortedThreePagePathsWithCount(Map<String, Long> threePagePathsWithCount) {
        return threePagePathsWithCount
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    Map<String, Long> getThreePagePathsWithCount(Map<String, List<String>> pagesVisitedByUser) {
        return getAllThreePagePaths(pagesVisitedByUser)
                .stream()
                .collect(groupingBy(identity(), counting()));
    }

    Map<String, List<String>> getPagesVisitedByUser(String[][] data) {
        return stream(data).
                collect(groupingBy(userPage -> userPage[0]
                        , mapping(userPage -> userPage[1], toList())));
    }

    List<String> getAllThreePagePaths(Map<String, List<String>> pagesVisitedByUser) {
        List<String> threePagePathList = new ArrayList<>();

        pagesVisitedByUser.forEach((key, value) -> {
            for(int i=0; i< value.size() - 2; i++)
            {
                threePagePathList.add(value.get(i) + " -> " + value.get(i+1) + " -> " + value.get(i+2));
            }
        });
        return threePagePathList;
    }
}
