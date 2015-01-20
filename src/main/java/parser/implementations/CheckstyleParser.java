package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

public class CheckstyleParser implements Parser
{
    private final String toolName = "checkstyle";

    public ConfigAnalysis parse(final Document document, ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

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

        for (Node childModule : childrenModules)
        {
            List<Node> grandChildrenModules = DOMUtil.childrenByTagName(element, "module");

            if (grandChildrenModules.size() > 0)
            {
                newConfigAnalysis = parseModule((Element) childModule, newConfigAnalysis);
            }

            newConfigAnalysis.addOccurrence(((Element) childModule).getAttribute("name"));
        }

        return newConfigAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
