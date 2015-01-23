package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

public class FindBugsParser implements Parser
{
    private final String toolName = "findbugs";

    public ConfigAnalysis parse(final Document document, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        Element filter = document.getDocumentElement();

        if (filter.getNodeType() == Node.ELEMENT_NODE && filter.getNodeName().equals("FindBugsFilter"))
        {
            List<Node> matches = DOMUtil.childrenByTagName(filter, "Match");

            for (Node match : matches)
            {
                configAnalysis = parseMatch((Element) match, configAnalysis);
            }
        }

        return configAnalysis;
    }

    private ConfigAnalysis parseMatch(final Element element, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        List<Node> bugs = DOMUtil.childrenByTagName(element, "Bug");
        List<Node> excludes = DOMUtil.childrenByTagName(element, "Not");
        List<Node> conjunctions = DOMUtil.childrenByTagName(element, "And");
        List<Node> disjunctions = DOMUtil.childrenByTagName(element, "Or");

        for (Node exclude : excludes)
        {
            configAnalysis = parseNot((Element) exclude, configAnalysis);
        }

        for (Node bug : bugs)
        {
            String[] bugCodes = ((Element) bug).hasAttribute("code") ? ((Element) bug).getAttribute("code").split(",") :
                    new String[] {};

            String[] patternCodes = ((Element) bug).hasAttribute("pattern") ? ((Element) bug).getAttribute("pattern").split(",") :
                    new String[] {};

            for (String bugCode : bugCodes)
            {
                configAnalysis.addOccurrence(bugCode);
            }

            for (String patternCode : patternCodes)
            {
                configAnalysis.addOccurrence(patternCode);
            }
        }

        for (Node conjunction: conjunctions)
        {
            configAnalysis = parseMatch((Element) conjunction, configAnalysis);
        }

        for (Node disjunction: disjunctions)
        {
            configAnalysis = parseMatch((Element) disjunction, configAnalysis);
        }

        return configAnalysis;
    }

    private ConfigAnalysis parseNot(final Element element, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        List<Node> bugs = DOMUtil.childrenByTagName(element, "Bug");

        for (Node bug : bugs)
        {
            String[] bugCodes = ((Element) bug).hasAttribute("code") ? ((Element) bug).getAttribute("code").split(",") :
                    new String[] {};

            String[] patternCodes = ((Element) bug).hasAttribute("pattern") ? ((Element) bug).getAttribute("pattern").split(",") :
                    new String[] {};

            for (String bugCode : bugCodes)
            {
                configAnalysis.addExclusion(bugCode);
            }

            for (String patternCode : patternCodes)
            {
                configAnalysis.addExclusion(patternCode);
            }
        }

        return configAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
