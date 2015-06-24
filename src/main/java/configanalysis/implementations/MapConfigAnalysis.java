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

    private final List<String> redefineRules = new ArrayList<>();

    private int deviations = 0;

    private int total = 0;

    private int onlyRedefines = 0;

    private int addsRules = 0;

    private int usesCustomRules = 0;

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

    public void addRedefineRule(String name)
    {
        redefineRules.add(name);
    }

    public boolean isRedefineRule(String name)
    {
        return redefineRules.contains(name);
    }

    public Map<String, Integer> getOccurrences()
    {
        return occurrences;
    }

    public Map<String, Integer> getExclusions()
    {
        return exclusions;
    }

    public void addPossibleRuleSet(final List<String> possibleRuleSet)
    {
        possibleRuleSet.forEach(this::addPossibleRule);
    }

    public void addRedefineRuleSet(final List<String> redefineRuleSet)
    {
        redefineRuleSet.forEach(this::addRedefineRule);
    }

    public void addDefaultConfig(final SingleConfigAnalysis singleConfigAnalysis)
    {
        List<String> defaultOccurrences = singleConfigAnalysis.getOccurrences();

        for (String occurrence : defaultOccurrences)
        {
            addDefaultOccurrence(occurrence.trim());
        }

        List<String> defaultExclusions = singleConfigAnalysis.getExclusions();

        for (String exclusion : defaultExclusions)
        {
            addDefaultExclusion(exclusion.trim());
        }
    }

    public void addSingleConfigAnalysis(final SingleConfigAnalysis singleConfigAnalysis)
    {
        total++;

        boolean deviation = false;
        boolean redefine = false;
        boolean addingTo = false;
        boolean useOfCustomRules = false;

        List<String> singleOccurrences = singleConfigAnalysis.getOccurrences();

        for (String occurrence : singleOccurrences)
        {
            occurrence = occurrence.trim();

            addOccurrence(occurrence);

            if (isPossibleRule(occurrence))
            {
                if (isDefaultOccurrence(occurrence) && isRedefineRule(occurrence))
                {
                    redefine = true;
                }
                else if (isDefaultExclusion(occurrence))
                {
                    deviation = true;
                }
                else if (!isDefaultOccurrence(occurrence) && !isDefaultExclusion(occurrence))
                {
                    addingTo = true;
                }
            }
            else
            {
                useOfCustomRules = true;
            }
        }

        List<String> singleExclusions = singleConfigAnalysis.getExclusions();

        for (String exclusion : singleExclusions)
        {
            exclusion = exclusion.trim();

            addExclusion(exclusion);

            if (isPossibleRule(exclusion))
            {
                // Redefining an exclusion makes no sense and it therefore of no importance to us.
                if (isDefaultOccurrence(exclusion))
                {
                    deviation = true;
                }
                else if (!isDefaultExclusion(exclusion))
                {
                    addingTo = true;
                }
            }
            else
            {
                useOfCustomRules = true;
            }
        }

        if (deviation)
        {
            deviations++;
        }

        if (redefine && !deviation)
        {
            onlyRedefines++;
        }

        if (addingTo)
        {
            addsRules++;
        }

        if (useOfCustomRules)
        {
            usesCustomRules++;
        }
    }

    public String getToolName()
    {
        return toolName;
    }

    public int getDeviations()
    {
        return deviations;
    }

    public int getTotal()
    {
        return total;
    }

    public int getOnlyRedefines()
    {
        return onlyRedefines;
    }

    public int getAddsRules()
    {
        return addsRules;
    }

    public int getUsesCustomRules()
    {
        return usesCustomRules;
    }
}
