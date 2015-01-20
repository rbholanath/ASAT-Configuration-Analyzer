package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.CheckstyleConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

public class CheckstyleParser implements Parser
{
    private final String toolName = "checkstyle";

    public ConfigAnalysis parse(final Document document)
    {
        ConfigAnalysis configAnalysis = new CheckstyleConfigAnalysis();

        Element parentModule = document.getDocumentElement();

        if (parentModule.getNodeType() == Node.ELEMENT_NODE && parentModule.getNodeName().equals("module")
                && parentModule.getAttribute("name").equals("Checker"))
        {
            configAnalysis = parseModule(parentModule, configAnalysis);
        }

        return configAnalysis;
    }

    private ConfigAnalysis parseModule(final Element element, final ConfigAnalysis configAnalysis)
    {
        ConfigAnalysis newConfigAnalysis = configAnalysis;

        List<Node> childrenModules = DOMUtil.childrenByTagName(element, "module");

        for (Node childrenModule : childrenModules)
        {
            List<Node> grandChildrenModules = DOMUtil.childrenByTagName(element, "module");

            if (grandChildrenModules.size() > 0)
            {
                newConfigAnalysis = parseModule((Element) childrenModule, newConfigAnalysis);
            }

            newConfigAnalysis.addOccurrence(((Element) childrenModule).getAttribute("name"));
        }

        return newConfigAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
