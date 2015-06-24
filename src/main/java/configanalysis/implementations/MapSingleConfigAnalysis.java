package main.java.configanalysis.implementations;

import main.java.configanalysis.SingleConfigAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSingleConfigAnalysis implements SingleConfigAnalysis
{
    private final List<String> occurrences = new ArrayList<>();

    private final List<String> exclusions = new ArrayList<>();

    private final Map<String, String> configs = new HashMap<>();

    public MapSingleConfigAnalysis() { }

    public void addOccurrence(final String name)
    {
        if (!occurrences.contains(name))
        {
            occurrences.add(name);
        }
    }

    public boolean isOccurrence(final String name)
    {
        return occurrences.contains(name);
    }

    public void addExclusion(final String name)
    {
        if (!exclusions.contains(name))
        {
            exclusions.add(name);
        }
    }

    public boolean isExclusion(final String name)
    {
        return exclusions.contains(name);
    }

    public List<String> getOccurrences()
    {
        return occurrences;
    }

    public List<String> getExclusions()
    {
        return exclusions;
    }
}
