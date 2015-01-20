package main.java.configanalysis;

import java.util.Map;

public interface ConfigAnalysis
{
    void addOccurrence(String name);

    int getSingleOccurrence(String name);

    void addExclusion(String name);

    int getSingleExclusion(String name);

    Map<String, Integer> getOccurrences();

    Map<String, Integer> getExclusions();

    String getToolName();
}
