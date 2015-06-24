package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class PossibleRulesParser implements Parser
{
    public ConfigAnalysis parse(InputStream stream, ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                configAnalysis.addPossibleRule(line.trim());
            }
        }
        catch (IOException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading possible rules file: " + e.getMessage());
        }

        return configAnalysis;
    }

    public String getToolName()
    {
        return null;
    }
}
