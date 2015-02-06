package main.java.parser;

import main.java.configanalysis.ConfigAnalysis;

import java.io.InputStream;

public interface Parser
{
    ConfigAnalysis parse(final InputStream stream, final ConfigAnalysis oldConfigAnalysis);

    String getToolName();
}
