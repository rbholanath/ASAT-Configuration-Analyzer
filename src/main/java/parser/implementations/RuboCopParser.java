package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class RuboCopParser implements Parser
{
    private final String toolName = "rubocop";

    private final List<String> filterIdentifiers = new LinkedList<>(Arrays.asList("inherit_from", "AllCops"));

    public ConfigAnalysis parse(final InputStream stream, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        try
        {
            Yaml yaml = new Yaml();
            Map<String, Object> data = (Map<String, Object>) yaml.load(stream);

            for (String key : data.keySet())
            {
                if (!filterIdentifiers.contains(key))
                {
                    Map<String, Object> childMap = (Map<String, Object>) data.get(key);

                    if (childMap.containsKey("Enabled") && !((Boolean) childMap.get("Enabled")))
                    {
                        configAnalysis.addExclusion(key);
                    }
                    else
                    {
                        configAnalysis.addOccurrence(key);
                    }
                }
            }
        }
        catch (Exception e)
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
