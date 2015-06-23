package main.java.configreader;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
    public static List<ConfigAnalysis> read(final List<Parser> parsers, final List<File> directories, final List<File> defaults,
                                            final List<File> classifications)
    {
        List<ConfigAnalysis> configAnalyses = new ArrayList<>();

        if (parsers.size() == directories.size())
        {
            for (int i = 0; i < parsers.size(); i++)
            {
                configAnalyses.add(readForTool(parsers.get(i), directories.get(i), defaults.get(i), classifications.get(i)));
            }
        }

        return configAnalyses;
    }

    private static ConfigAnalysis readForTool(final Parser parser, final File directory, final File defaultConfig,
                                              final File classification)
    {
        try
        {
            AnalyzerLogger.getLogger().log(Level.INFO, "Reading tool: " + parser.getToolName());

            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory.getAbsolutePath()), "*.{txt}");

            ConfigAnalysis configAnalysis = new MapConfigAnalysis(parser.getToolName());

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

    private static ConfigAnalysis readURLList(final String filename, final Parser parser, final ConfigAnalysis oldConfigAnalysis)
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

                    configAnalysis = parser.parse(stream, configAnalysis);

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
