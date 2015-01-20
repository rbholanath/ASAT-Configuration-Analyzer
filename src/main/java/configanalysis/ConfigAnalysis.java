package main.java.configanalysis;

import java.util.Map;

public interface ConfigAnalysis
{
    void addOccurrence(String moduleName);

    int getModuleOccurrences(String moduleName);

    Map<String, Integer> getOccurrences();
}
