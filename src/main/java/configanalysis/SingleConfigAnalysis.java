package main.java.configanalysis;

import java.util.List;

public interface SingleConfigAnalysis
{
    void addOccurrence(final String name);

    boolean isOccurrence(final String name);

    void addExclusion(final String name);

    boolean isExclusion(final String name);

    List<String> getOccurrences();

    List<String> getExclusions();
}
