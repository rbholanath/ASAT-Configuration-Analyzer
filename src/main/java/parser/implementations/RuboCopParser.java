package main.java.parser.implementations;

import main.java.configanalysis.ConfigAnalysis;
import main.java.parser.Parser;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RuboCopParser implements Parser
{
    private final String toolName = "rubocop";

    private final List<String> filterIdentifiers = new LinkedList<>(Arrays.asList("inherit_from", "AllCops"));

    public ConfigAnalysis parse(final InputStream stream, final ConfigAnalysis oldConfigAnalysis)
    {
        ConfigAnalysis configAnalysis = oldConfigAnalysis;

        Yaml yaml = new Yaml();
        Map<String, Object> data = (Map<String, Object>) yaml.load(stream);

        data.keySet().stream().filter(key -> !filterIdentifiers.contains(key)).forEach(configAnalysis::addOccurrence);

        return configAnalysis;
    }

    public String getToolName()
    {
        return toolName;
    }
}
