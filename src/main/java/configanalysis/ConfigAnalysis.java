package main.java.configanalysis;

import java.util.Map;

public interface ConfigAnalysis
{
    void addOccurrence(final String name);

    int getSingleOccurrence(final String name);

    void addExclusion(final String name);

    int getSingleExclusion(final String name);

    Map<String, Integer> getOccurrences();

    Map<String, Integer> getExclusions();

    String getToolName();
}
