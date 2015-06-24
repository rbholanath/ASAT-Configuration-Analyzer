package main.java.configreader;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.ConfigParser;
import main.java.parser.RuleSetParser;
import main.java.util.AnalyzerLogger;

import java.io.*;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConfigReader
{
    private final static RuleSetParser ruleSetParser = new RuleSetParser();

    public static List<ConfigAnalysis> read(final List<ConfigParser> parsers, final List<File> directories, final List<File> defaults,
                                            final List<File> possibleRules, final List<File> redefines)
    {
        List<ConfigAnalysis> configAnalyses = new ArrayList<>();

        if (parsers.size() == directories.size())
        {
            for (int i = 0; i < parsers.size(); i++)
            {
                configAnalyses.add(readForTool(parsers.get(i), directories.get(i), defaults.get(i),
                        possibleRules.get(i), redefines.get(i)));
            }
        }

        return configAnalyses;
    }

    private static ConfigAnalysis readForTool(final ConfigParser parser, final File directory, final File defaultConfig,
                                              final File possibleRuleSet, final File redefine)
    {
        try
        {
            AnalyzerLogger.getLogger().log(Level.INFO, "Reading tool: " + parser.getToolName());

            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory.getAbsolutePath()), "*.{txt}");

            // Before reading any files, set up the possible rules, redefines, and the default configuration.
            ConfigAnalysis configAnalysis = new MapConfigAnalysis(parser.getToolName());

            configAnalysis.addPossibleRuleSet(ruleSetParser.parse(new FileInputStream(possibleRuleSet)));

            configAnalysis.addRedefineRuleSet(ruleSetParser.parse(new FileInputStream(redefine)));

            configAnalysis.addDefaultConfig(parser.parse(new FileInputStream(defaultConfig)));

            for (Path entry: stream)
            {
                AnalyzerLogger.getLogger().log(Level.FINE, "Reading file: " + entry.toString());

                configAnalysis = readURLList(entry.toString(), parser, configAnalysis);
            }

            return configAnalysis;
        }
        catch (IOException e)
        {
            AnalyzerLogger.getLogger().log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    private static ConfigAnalysis readURLList(final String filename, final ConfigParser parser, final ConfigAnalysis oldConfigAnalysis)
    {
        int filesRead = 0;

        try
        {
            ConfigAnalysis configAnalysis = oldConfigAnalysis;

            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                try
                {
                    URL url = new URL(line);
                    InputStream stream = url.openStream();

                    AnalyzerLogger.getLogger().log(Level.FINER, "Reading file: " + line);

                    configAnalysis.addSingleConfigAnalysis(parser.parse(stream));

                    filesRead++;
                }
                catch (IOException e)
                {
                    AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + e.getMessage());
                }
            }

            bufferedReader.close();
            fileReader.close();

            AnalyzerLogger.getLogger().log(Level.FINE, "Files read: " + filesRead);

            return configAnalysis;
        }
        catch (IOException e)
        {
            AnalyzerLogger.getLogger().log(Level.SEVERE, e.getMessage());
        }

        return null;
    }
}
