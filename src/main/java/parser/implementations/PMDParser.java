package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

public class PMDParser implements Parser
{
    private final String toolName = "pmd";

    public ConfigAnalysis parse(final Document document, ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        Element ruleSet = document.getDocumentElement();

        if (ruleSet.getNodeType() == Node.ELEMENT_NODE && ruleSet.getNodeName().equals("ruleset"))
        {
            configAnalysis = parseRuleset(ruleSet, configAnalysis);
        }

        return configAnalysis;
    }

    private ConfigAnalysis parseRuleset(final Element element, final ConfigAnalysis configAnalysis)
    {
        ConfigAnalysis newConfigAnalysis = configAnalysis;

        List<Node> rules = DOMUtil.childrenByTagName(element, "rule");

        for (Node rule : rules)
        {
            String ruleName = ((Element) rule).getAttribute("ref");

            List<Node> exclusions = DOMUtil.childrenByTagName((Element) rule, "exclude");

            for (Node exclusion : exclusions)
            {
                newConfigAnalysis.addExclusion(ruleName + "/" + ((Element) exclusion).getAttribute("name"));
            }

            newConfigAnalysis.addOccurrence(ruleName);
        }

        return newConfigAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
