package main.java.parser;

import main.java.configanalysis.SingleConfigAnalysis;

import java.io.InputStream;

public interface ConfigParser
{
    SingleConfigAnalysis parse(final InputStream stream);

    String getToolName();
}
