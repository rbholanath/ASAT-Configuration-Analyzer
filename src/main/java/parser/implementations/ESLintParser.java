package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ESLintParser implements Parser
{
    private final String toolName = "eslint";

    public ConfigAnalysis parse(final InputStream stream, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                if (line.trim().startsWith("\"rules\":"))
                {
                    configAnalysis = parseRules(bufferedReader, configAnalysis);
                }
            }
        }
        catch (IOException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + e.getMessage());
        }

        return configAnalysis;
    }

    private ConfigAnalysis parseRules(BufferedReader bufferedReader, ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        try
        {
            String line;
            while ((line = bufferedReader.readLine()) != null && !(line.equals("}")))
            {
                line = line.trim();
                // Line length > 4 (two ", one colon, and at least one character for the name and value.
                if (line.length() > 4 && line.substring(0, 1).equals("\""))
                {
                    String[] parts = line.split(":");

                    if (parts.length > 1)
                    {
                        if (!((parts[1].split(",")[0]).contains("0")))
                        {
                            configAnalysis.addOccurrence(parts[0].split("\"")[1]);
                        }
                        else
                        {
                            configAnalysis.addExclusion(parts[0].split("\"")[1]);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + e.getMessage());
        }

        return configAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
