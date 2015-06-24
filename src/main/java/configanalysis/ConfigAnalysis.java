package main.java.configanalysis;

import java.util.List;
import java.util.Map;

public interface ConfigAnalysis
{
    void addOccurrence(final String name);

    int getSingleOccurrence(final String name);

    void addExclusion(final String name);

    int getSingleExclusion(final String name);

    void addDefaultOccurrence(final String name);

    boolean isDefaultOccurrence(final String name);

    void addDefaultExclusion(final String name);

    boolean isDefaultExclusion(final String name);

    void addPossibleRule(String name);

    boolean isPossibleRule(String name);

    void addRedefineRule(String name);

    boolean isRedefineRule(String name);

    Map<String, Integer> getOccurrences();

    Map<String, Integer> getExclusions();

    void addPossibleRuleSet(final List<String> possibleRuleSet);

    void addRedefineRuleSet(final List<String> redefineRuleSet);

    void addDefaultConfig(final SingleConfigAnalysis singleConfigAnalysis);

     void addSingleConfigAnalysis(final SingleConfigAnalysis singleConfigAnalysis);

    String getToolName();

    int getDeviations();

    int getTotal();

    int getOnlyRedefines();

    int getAddsRules();

    int getUsesCustomRules();
}
