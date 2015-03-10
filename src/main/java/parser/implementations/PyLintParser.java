package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class PylintParser implements Parser
{
    private final String toolName = "pylint";

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
                // Line length > 2 (one equals sign and at least one character for the name and value.
                if (line.length() > 2 && line.matches("^[a-z].*$"))
                {
                    String[] parts = line.split("=");

                    String id = parts[0].trim();

                    if (parts.length > 1)
                    {
                        if ("enable".equals(id) || "enable-checker".equals(id))
                        {
                            configAnalysis = processEnableDisable(true, parts[1], configAnalysis);
                        }
                        else if ("disable".equals(id) || "disable-checker".equals(id))
                        {
                            configAnalysis = processEnableDisable(false, parts[1], configAnalysis);
                        }
                        else
                        {
                            configAnalysis.addOccurrence(id);
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

    private ConfigAnalysis processEnableDisable(boolean enable, String line, ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        List<String> codes = Arrays.asList(line.split(","));

        if (enable)
        {
            codes.forEach(configAnalysis::addOccurrence);
        }
        else
        {
            codes.forEach(configAnalysis::addExclusion);
        }

        return configAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
