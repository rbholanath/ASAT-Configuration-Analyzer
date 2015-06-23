package main.java.configanalysis;

import java.util.List;
import java.util.Map;

public interface SingleConfigAnalysis
{
    void addOccurrence(final String name);

    boolean isOccurrence(final String name);

    void addExclusion(final String name);

    boolean isExclusion(final String name);

    void addConfig(String name, String config);

    String getConfig(final String name);

    List<String> getOccurrences();

    List<String> getExclusions();

    Map<String, String> getConfigs();
}
