package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class FindBugsPrefsParser implements Parser
{
    private final String toolName = "findbugseclipseprefs";

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
                // Line length > 17 ("detector", one equals sign, at least one character for the name and value, and minimally "true".
                if (line.length() > 17 && line.substring(0, 8).equals("detector"))
                {
                    String[] equalStrings = line.split("=");

                    if (equalStrings.length > 1)
                    {
                        String[] pipeStrings = equalStrings[1].split("\\|");

                        if (pipeStrings.length > 1)
                        {
                            if (pipeStrings[1].equals("true"))
                            {
                                configAnalysis.addOccurrence(pipeStrings[0].trim());
                            }
                            else
                            {
                                configAnalysis.addExclusion(pipeStrings[0].trim());
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

    public String getToolName()
    {
        return toolName;
    }
}
