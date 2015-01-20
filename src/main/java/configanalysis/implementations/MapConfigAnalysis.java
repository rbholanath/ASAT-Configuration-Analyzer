package main.java.configanalysis.implementations;

import main.java.configanalysis.ConfigAnalysis;

import java.util.HashMap;
import java.util.Map;

public class MapConfigAnalysis implements ConfigAnalysis
{
    private final Map<String, Integer> occurrences = new HashMap<>();

    private final Map<String, Integer> exclusions = new HashMap<>();

    private final String toolName;

    public MapConfigAnalysis(final String toolName)
    {
        this.toolName = toolName;
    }

    public void addOccurrence(final String name)
    {
        if (occurrences.containsKey(name))
        {
            occurrences.put(name, occurrences.get(name) + 1);
        }
        else
        {
            occurrences.put(name, 1);
        }
    }

    public int getSingleOccurrence(final String name)
    {
        if (occurrences.containsKey(name))
        {
            return occurrences.get(name);
        }
        else
        {
            return 0;
        }
    }

    public void addExclusion(final String name)
    {
        if (exclusions.containsKey(name))
        {
            exclusions.put(name, exclusions.get(name) + 1);
        }
        else
        {
            exclusions.put(name, 1);
        }
    }

    public int getSingleExclusion(final String name)
    {
        if (exclusions.containsKey(name))
        {
            return exclusions.get(name);
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

    public Map<String, Integer> getExclusions()
    {
        return exclusions;
    }

    public String getToolName()
    {
        return toolName;
    }
}
