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

    public ConfigAnalysis parse(final Document document, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        Element ruleSet = document.getDocumentElement();

        if (ruleSet.getNodeType() == Node.ELEMENT_NODE && ruleSet.getNodeName().equals("ruleset"))
        {
            configAnalysis = parseRuleset(ruleSet, configAnalysis);
        }

        return configAnalysis;
    }

    private ConfigAnalysis parseRuleset(final Element element, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        List<Node> rules = DOMUtil.childrenByTagName(element, "rule");

        for (Node rule : rules)
        {
            if (((Element) rule).hasAttribute("ref"))
            {
                String ruleName =((Element) rule).getAttribute("ref");

                List<Node> exclusions = DOMUtil.childrenByTagName((Element) rule, "exclude");

                for (Node exclusion : exclusions)
                {
                    configAnalysis.addExclusion(ruleName + "/" + ((Element) exclusion).getAttribute("name"));
                }

                configAnalysis.addOccurrence(ruleName);
            }
        }

        return configAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
