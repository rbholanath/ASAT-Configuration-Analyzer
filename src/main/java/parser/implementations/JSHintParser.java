package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class JSHintParser implements Parser
{
    private final String toolName = "jshint";

    public ConfigAnalysis parse(final InputStream stream, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                line = line.trim();
                // Line length > 4 (two ", one colon, and at least one character for the name and value.
                if (line.length() > 4 && line.substring(0, 1).equals("\""))
                {
                    String[] parts = line.split(":");

                    if (parts.length > 1)
                    {
                        String configName = (parts[0].split("\"")[1]).trim();

                        // Skip the globals/predef section
                        if (configName.equals("globals") || configName.equals("predef"))
                        {
                            bufferedReader = skipToClosingBrace(bufferedReader);
                        }
                        else
                        {
                            if (!((parts[1].split(",")[0]).contains("false")))
                            {
                                configAnalysis.addOccurrence(configName);
                            }
                            else
                            {
                                configAnalysis.addExclusion(configName);
                            }
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


    private BufferedReader skipToClosingBrace(final BufferedReader oldBufferedReader)
    {
        BufferedReader bufferedReader = oldBufferedReader;

        try
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                if (line.indexOf('}') != -1)
                {
                    break;
                }
            }
        }
        catch (IOException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + e.getMessage());
        }

        return bufferedReader;
    }

    public String getToolName()
    {
        return toolName;
    }
}
