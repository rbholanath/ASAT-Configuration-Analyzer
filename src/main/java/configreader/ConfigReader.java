package main.java.configreader;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
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
            List<ConfigAnalysis> configAnalyses = new ArrayList<>();

            for (int i = 0; i < parsers.size(); i++)
            {
                System.out.println("-- Reading tool: " + parsers.get(i).getToolName());

                DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directories.get(i)), "*.{txt}");

                for (Path entry: stream)
                {
                    System.out.println("- Reading file: " + entry.toString());

                    configAnalyses.add(readURLList(entry.toString(), parsers.get(i)));
                }
            }

            return configAnalyses;
        }
        catch (IOException | DirectoryIteratorException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static ConfigAnalysis readURLList(String filename, Parser parser)
    {
        int filesRead = 0;
        int errors = 0;

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            ConfigAnalysis configAnalysis = new MapConfigAnalysis(parser.getToolName());

            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                try
                {
                    URL url = new URL(line);
                    InputStream stream = url.openStream();

                    configAnalysis = parser.parse(builder.parse(stream), configAnalysis);

                    filesRead++;
                }
                catch (SAXException | IOException e)
                {
                    errors++;
                }
            }

            bufferedReader.close();
            fileReader.close();

            return configAnalysis;
        }
        catch (IOException | ParserConfigurationException e)
        {
            e.printStackTrace();
        }

        System.out.println("Files read: " + filesRead + ", URL failures: " + errors);

        return null;
    }
}
