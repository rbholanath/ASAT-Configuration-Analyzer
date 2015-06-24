package main.java.parser;

import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class RuleSetParser
{
    public List<String> parse(InputStream stream)
    {
        List<String> rules = new ArrayList<>();

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                rules.add(line.trim());
            }
        }
        catch (IOException e)
        {
            AnalyzerLogger.getLogger().log(Level.FINER, "Error reading possible rules file: " + e.getMessage());
        }

        return rules;
    }
}
