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
                        String configName = parts[0].split("\"")[1];

                        // Skip the globals section
                        if (configName.equals("globals") && line.indexOf('}') == -1)
                        {
                            bufferedReader = skipToClosingBrace(bufferedReader);
                        }
                        else
                        {
                            configAnalysis.addOccurrence(parts[0].split("\"")[1]);
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


    public BufferedReader skipToClosingBrace(final BufferedReader oldBufferedReader)
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
