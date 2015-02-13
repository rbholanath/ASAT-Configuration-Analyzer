package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.FindBugsParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class FindBugsParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/findbugs_xml_parser_test.xml");

        parser = new FindBugsParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

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