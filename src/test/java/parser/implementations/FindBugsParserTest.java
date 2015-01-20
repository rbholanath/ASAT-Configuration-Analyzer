package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.FindBugsParser;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FindBugsParserTest
{
    private Document document;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(System.getProperty("user.dir") + "/src/test/resources/findbugs_parser_test.xml");

        parser = new FindBugsParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(document, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("DE"));
        Assert.assertEquals(1, result.getSingleOccurrence("UrF"));
        Assert.assertEquals(1, result.getSingleOccurrence("SIC"));
        Assert.assertEquals(1, result.getSingleOccurrence("XYZ"));
        Assert.assertEquals(1, result.getSingleOccurrence("DC"));
        Assert.assertEquals(1, result.getSingleExclusion("IJU"));
        Assert.assertEquals(1, result.getSingleExclusion("IC"));
        Assert.assertEquals(1, result.getSingleExclusion("BAC"));
        Assert.assertEquals(1, result.getSingleExclusion("PS"));
    }
}