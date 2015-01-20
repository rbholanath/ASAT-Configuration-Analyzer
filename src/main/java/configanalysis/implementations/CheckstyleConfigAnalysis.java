package main.java.configanalysis.implementations;

import main.java.configanalysis.ConfigAnalysis;

import java.util.HashMap;
import java.util.Map;

public class CheckstyleConfigAnalysis implements ConfigAnalysis
{
    private final Map<String, Integer> occurrences = new HashMap<>();

    public void addOccurrence(final String moduleName)
    {
        if (occurrences.containsKey(moduleName))
        {
            occurrences.put(moduleName, occurrences.get(moduleName) + 1);
        }
        else
        {
            occurrences.put(moduleName, 1);
        }
    }

    public int getModuleOccurrences(final String moduleName)
    {
        if (occurrences.containsKey(moduleName))
        {
            return occurrences.get(moduleName);
        }
        else
        {
            return 0;
        }
    }

    public Map<String, Integer> getOccurrences()
    {
        return occurrences;
    }
}
