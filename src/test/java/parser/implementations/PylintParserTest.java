package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.ConfigParser;
import main.java.parser.implementations.PylintParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class PylintParserTest
{
    private InputStream stream;
    private ConfigParser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/.pylinttestrc");

        parser = new PylintParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = new MapConfigAnalysis("pylint");

        result.addSingleConfigAnalysis(parser.parse(stream));

        Assert.assertEquals(1, result.getSingleOccurrence("persistent"));
        Assert.assertEquals(1, result.getSingleOccurrence("min-similarity-lines"));
        Assert.assertEquals(1, result.getSingleOccurrence("ignore-comments"));
        Assert.assertEquals(1, result.getSingleOccurrence("ignore-docstrings"));
        Assert.assertEquals(1, result.getSingleOccurrence("max-line-length"));
        Assert.assertEquals(1, result.getSingleOccurrence("variable-rgx"));
        Assert.assertEquals(1, result.getSingleOccurrence("max-locals"));
        Assert.assertEquals(0, result.getSingleOccurrence("max-module-lines"));
        Assert.assertEquals(0, result.getSingleOccurrence("rcfile"));

        Assert.assertEquals(1, result.getSingleOccurrence("E1606"));
        Assert.assertEquals(1, result.getSingleOccurrence("W1621"));
        Assert.assertEquals(1, result.getSingleOccurrence("W1620"));
        Assert.assertEquals(1, result.getSingleExclusion("E1608"));
        Assert.assertEquals(1, result.getSingleExclusion("W1627"));
        Assert.assertEquals(1, result.getSingleExclusion("E1601"));
    }
}