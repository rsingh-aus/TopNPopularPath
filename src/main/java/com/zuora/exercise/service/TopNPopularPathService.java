package com.zuora.exercise.service;

public interface TopNPopularPathService {

    void setup(String[][] data);

    String[] getTopNPopularPaths(int n);
}
