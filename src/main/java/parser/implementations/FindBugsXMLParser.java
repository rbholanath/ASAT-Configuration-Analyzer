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

public class FindBugsXMLParser implements ConfigParser
{
    private final String toolName = "findbugsfilter";

    public SingleConfigAnalysis parse(final InputStream stream)
    {
        SingleConfigAnalysis configAnalysis = new MapSingleConfigAnalysis();

        try
        {
            Document document = DOMBuilder.getBuilder().parse(stream);

            Element filter = document.getDocumentElement();

            if (filter.getNodeType() == Node.ELEMENT_NODE && filter.getNodeName().equals("FindBugsFilter"))
            {
                List<Node> matches = DOMUtil.childrenByTagName(filter, "Match");

                for (Node match : matches)
                {
                    configAnalysis = parseMatch((Element) match, configAnalysis);
                }
            }
        }
        catch (IOException | SAXException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + e.getMessage());
        }

        return configAnalysis;
    }

    private SingleConfigAnalysis parseMatch(final Element element, final SingleConfigAnalysis oldConfigAnalysis)
    {
        SingleConfigAnalysis configAnalysis = oldConfigAnalysis;

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
                configAnalysis.addOccurrence(bugCode.trim());
            }

            for (String patternCode : patternCodes)
            {
                configAnalysis.addOccurrence(patternCode.trim());
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

    private SingleConfigAnalysis parseNot(final Element element, final SingleConfigAnalysis oldConfigAnalysis)
    {
        SingleConfigAnalysis configAnalysis = oldConfigAnalysis;

        List<Node> bugs = DOMUtil.childrenByTagName(element, "Bug");

        for (Node bug : bugs)
        {
            String[] bugCodes = ((Element) bug).hasAttribute("code") ? ((Element) bug).getAttribute("code").split(",") :
                    new String[] {};

            String[] patternCodes = ((Element) bug).hasAttribute("pattern") ? ((Element) bug).getAttribute("pattern").split(",") :
                    new String[] {};

            for (String bugCode : bugCodes)
            {
                configAnalysis.addExclusion(bugCode.trim());
            }

            for (String patternCode : patternCodes)
            {
                configAnalysis.addExclusion(patternCode.trim());
            }
        }

        return configAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
