package main.java.configreader;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.util.AnalyzerLogger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConfigReader
{
    public static List<ConfigAnalysis> read(final List<Parser> parsers, final List<File> directories)
    {
        List<ConfigAnalysis> configAnalyses = new ArrayList<>();

        if (parsers.size() == directories.size())
        {
            for (int i = 0; i < parsers.size(); i++)
            {
                configAnalyses.add(readForTool(parsers.get(i), directories.get(i)));
            }
        }

        return configAnalyses;
    }

    private static ConfigAnalysis readForTool(final Parser parser, final File directory)
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
        int errors = 0;

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Don't output error messages to System.err when a XML file is erroneous.
            builder.setErrorHandler(null);

            // Don't download external referenced entities.
            builder.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));

            ConfigAnalysis configAnalysis = oldConfigAnalysis;

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

                    AnalyzerLogger.getLogger().log(Level.FINER, "Successfully read file: " + line);

                    filesRead++;
                }
                catch (SAXException | IOException e)
                {
                    AnalyzerLogger.getLogger().log(Level.FINER, "Error reading file: " + line + ", error: " + e.getMessage());
                    errors++;
                }
            }

            bufferedReader.close();
            fileReader.close();

            AnalyzerLogger.getLogger().log(Level.FINE, "Files read: " + filesRead + ", URL failures: " + errors);

            return configAnalysis;
        }
        catch (IOException | ParserConfigurationException e)
        {
            AnalyzerLogger.getLogger().log(Level.SEVERE, e.getMessage());
        }

        return null;
    }
}
