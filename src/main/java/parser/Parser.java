package main.java.parser;

import main.java.configanalysis.ConfigAnalysis;
import org.w3c.dom.Document;

public interface Parser
{
    ConfigAnalysis parse(final Document document, final ConfigAnalysis oldConfigAnalysis);

    String getToolName();
}
