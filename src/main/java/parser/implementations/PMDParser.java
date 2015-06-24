package main.java.parser.implementations;

import main.java.configanalysis.SingleConfigAnalysis;
import main.java.configanalysis.implementations.MapSingleConfigAnalysis;
import main.java.parser.ConfigParser;
import main.java.util.AnalyzerLogger;
import main.java.util.DOMBuilder;
import main.java.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

public class PMDParser implements ConfigParser
{
    private final String toolName = "pmd";

    public SingleConfigAnalysis parse(final InputStream stream)
    {
        SingleConfigAnalysis configAnalysis = new MapSingleConfigAnalysis();

        try
        {
            Document document = DOMBuilder.getBuilder().parse(stream);

            Element ruleSet = document.getDocumentElement();

            if (ruleSet.getNodeType() == Node.ELEMENT_NODE && ruleSet.getNodeName().equals("ruleset"))
            {
                configAnalysis = parseRuleset(ruleSet, configAnalysis);
            }
        }
        catch (IOException | SAXException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + e.getMessage());
        }

        return configAnalysis;
    }

    private SingleConfigAnalysis parseRuleset(final Element element, final SingleConfigAnalysis oldConfigAnalysis)
    {
        SingleConfigAnalysis configAnalysis = oldConfigAnalysis;

        List<Node> rules = DOMUtil.childrenByTagName(element, "rule");

        for (Node rule : rules)
        {
            if (((Element) rule).hasAttribute("ref"))
            {
                String ruleName = cleanUpRuleName(((Element) rule).getAttribute("ref"));

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

    private String cleanUpRuleName(final String oldRuleName)
    {
        String ruleName = oldRuleName;

        int slash = ruleName.indexOf('/');

        if (slash != -1)
        {
            String beginning = ruleName.substring(0, slash);
            String extension = beginning.substring(beginning.lastIndexOf(".") + 1, beginning.length());

            if (!extension.equals("xml"))
            {
                ruleName = cleanUpRuleName(ruleName.substring(slash + 1));
            }
        }

        return ruleName;
    }

    public String getToolName()
    {
        return toolName;
    }
}
