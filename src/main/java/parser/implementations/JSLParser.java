package main.java.parser.implementations;

import main.java.configanalysis.SingleConfigAnalysis;
import main.java.configanalysis.implementations.MapSingleConfigAnalysis;
import main.java.parser.ConfigParser;
import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class JSLParser implements ConfigParser
{
    private final String toolName = "jsl";

    private final List<String> filterRules = new LinkedList<>(Arrays.asList("process", "define", "output-format"));

    public SingleConfigAnalysis parse(final InputStream stream)
    {
        SingleConfigAnalysis configAnalysis = new MapSingleConfigAnalysis();

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                line = line.trim();
                // Line length > 2 (a + or a -, and a character).
                if (line.length() > 2 && (line.substring(0, 1).equals("+") || line.substring(0, 1).equals("-")))
                {
                    String theRest = line.substring(1).trim();
                    String configName = theRest.split("\\s+")[0];

                    if (!filterRules.contains(configName))
                    {
                        if (line.substring(0, 1).equals("+"))
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
