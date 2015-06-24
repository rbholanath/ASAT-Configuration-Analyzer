package main.java.configanalysis.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.SingleConfigAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapConfigAnalysis implements ConfigAnalysis
{
    private final Map<String, Integer> occurrences = new HashMap<>();

    private final Map<String, Integer> exclusions = new HashMap<>();

    private final List<String> defaultOccurrences = new ArrayList<>();

    private final List<String> defaultExclusions = new ArrayList<>();

    private final List<String> possibleRules = new ArrayList<>();

    private int deviations = 0;

    private int total = 0;

    private int onlyRedefineDeviations = 0;

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

    public void addDefaultOccurrence(String name)
    {
        defaultOccurrences.add(name);
    }

    public boolean isDefaultOccurrence(String name)
    {
        return defaultOccurrences.contains(name);
    }

    public void addDefaultExclusion(String name)
    {
        defaultExclusions.add(name);
    }

    public boolean isDefaultExclusion(String name)
    {
        return defaultExclusions.contains(name);
    }

    public void addPossibleRule(String name)
    {
        possibleRules.add(name);
    }

    public boolean isPossibleRule(String name)
    {
        return possibleRules.contains(name);
    }

    public Map<String, Integer> getOccurrences()
    {
        return occurrences;
    }

    public Map<String, Integer> getExclusions()
    {
        return exclusions;
    }

    public List<String> getDefaultOccurrences()
    {
        return defaultOccurrences;
    }

    public List<String> getDefaultExclusions()
    {
        return defaultExclusions;
    }

    public List<String> getPossibleRules()
    {
        return possibleRules;
    }

    public String getToolName()
    {
        return toolName;
    }

    public void addSingleConfigAnalysis(final SingleConfigAnalysis singleConfigAnalysis)
    {

    }
}
