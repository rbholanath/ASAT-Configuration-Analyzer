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

public class CheckstyleParser implements ConfigParser
{
    private final String toolName = "checkstyle";

    public SingleConfigAnalysis parse(final InputStream stream)
    {
        SingleConfigAnalysis configAnalysis = new MapSingleConfigAnalysis();

        try
        {
            Document document = DOMBuilder.getBuilder().parse(stream);

            Element parentModule = document.getDocumentElement();

            if (parentModule.getNodeType() == Node.ELEMENT_NODE && parentModule.getNodeName().equals("module")
                    && parentModule.getAttribute("name").equals("Checker"))
            {
                configAnalysis = parseModule(parentModule, configAnalysis);
            }
        }
        catch (IOException | SAXException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + e.getMessage());
        }

        return configAnalysis;
    }

    private SingleConfigAnalysis parseModule(final Element element, final SingleConfigAnalysis oldConfigAnalysis)
    {
        SingleConfigAnalysis configAnalysis = oldConfigAnalysis;

        List<Node> childrenModules = DOMUtil.childrenByTagName(element, "module");

        for (Node childModule : childrenModules)
        {
            List<Node> grandChildrenModules = DOMUtil.childrenByTagName(element, "module");

            if (grandChildrenModules.size() > 0)
            {
                configAnalysis = parseModule((Element) childModule, configAnalysis);
            }

            configAnalysis.addOccurrence(cleanUpRuleName(((Element) childModule).getAttribute("name")));
        }

        return configAnalysis;
    }

    private String cleanUpRuleName(final String oldRuleName)
    {
        String ruleName = oldRuleName;

        int period = ruleName.lastIndexOf('.');

        if (period != -1)
        {
            ruleName = ruleName.substring(period + 1);
        }

        return ruleName;
    }

    public String getToolName()
    {
        return toolName;
    }
}
