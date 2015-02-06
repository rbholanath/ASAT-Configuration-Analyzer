package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.ESLintParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class ESLintParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/.eslinttestrc");

        parser = new ESLintParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("valid-jsdoc"));
        Assert.assertEquals(1, result.getSingleOccurrence("curly"));
        Assert.assertEquals(1, result.getSingleOccurrence("brace-style"));
        Assert.assertEquals(1, result.getSingleOccurrence("no-constant-condition"));
        Assert.assertEquals(1, result.getSingleOccurrence("func-style"));
        Assert.assertEquals(1, result.getSingleOccurrence("no-unused-vars"));
        Assert.assertEquals(1, result.getSingleOccurrence("one-var"));
        Assert.assertEquals(0, result.getSingleOccurrence("browser"));
        Assert.assertEquals(0, result.getSingleOccurrence("amd"));
        Assert.assertEquals(0, result.getSingleOccurrence("DocumentFragment"));
        Assert.assertEquals(0, result.getSingleOccurrence("quotes"));
    }
}