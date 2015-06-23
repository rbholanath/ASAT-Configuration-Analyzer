package main.java.configanalysis;

import java.util.List;
import java.util.Map;

public interface ConfigAnalysis
{
    void addOccurrence(final String name);

    int getSingleOccurrence(final String name);

    void addExclusion(final String name);

    int getSingleExclusion(final String name);

    Map<String, Integer> getOccurrences();

    Map<String, Integer> getExclusions();

    void addDefaultOccurrence(final String name);

    boolean isDefaultOccurrence(final String name);

    void addDefaultExclusion(final String name);

    boolean isDefaultExclusion(final String name);

    List<String> getDefaultOccurrences();

    List<String> getDefaultExclusions();

    String getToolName();
}
