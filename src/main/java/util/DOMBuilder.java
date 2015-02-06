package main.java.util;

import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.util.logging.Level;

public class DOMBuilder
{
    private static DocumentBuilder builder;

    private static void createBuilder()
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

            // Don't output error messages to System.err when a XML file is erroneous.
            builder.setErrorHandler(null);

            // Don't download external referenced entities.
            builder.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));
        }
        catch (ParserConfigurationException e)
        {
            AnalyzerLogger.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    public static DocumentBuilder getBuilder()
    {
        if (builder == null)
        {
            createBuilder();
        }

        return builder;
    }
}
