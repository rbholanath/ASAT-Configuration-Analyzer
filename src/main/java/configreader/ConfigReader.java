package main.java.configreader;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader
{
    public static List<ConfigAnalysis> read(final List<Parser> parsers, final List<String> directories)
    {
        if (parsers.size() != directories.size())
        {
            return new ArrayList<>();
        }

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            List<ConfigAnalysis> configAnalyses = new ArrayList<>();

            for (int i = 0; i < parsers.size(); i++)
            {
                System.out.println("-- Reading tool: " + parsers.get(i).getToolName());

                ConfigAnalysis toolConfigAnalysis = new MapConfigAnalysis(parsers.get(i).getToolName());

                DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directories.get(i)), "*.{xml}");

                for (Path entry: stream)
                {
                    System.out.println("- Reading file: " + entry.toString());

                    configAnalyses.add(parsers.get(i).parse(builder.parse(entry.toString()), toolConfigAnalysis));
                }
            }

            return configAnalyses;
        }
        catch (ParserConfigurationException | SAXException | IOException | DirectoryIteratorException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
