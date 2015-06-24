package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.ConfigParser;
import main.java.parser.implementations.ESLintParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class ESLintParserTest
{
    private InputStream stream;
    private ConfigParser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/.eslinttestrc");

        parser = new ESLintParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = new MapConfigAnalysis("eslint");

        result.addSingleConfigAnalysis(parser.parse(stream));

        Assert.assertEquals(1, result.getSingleOccurrence("curly"));
        Assert.assertEquals(1, result.getSingleOccurrence("brace-style"));
        Assert.assertEquals(1, result.getSingleOccurrence("no-constant-condition"));
        Assert.assertEquals(1, result.getSingleOccurrence("one-var"));
        Assert.assertEquals(1, result.getSingleOccurrence("no-unused-vars"));

        Assert.assertEquals(1, result.getSingleExclusion("valid-jsdoc"));
        Assert.assertEquals(1, result.getSingleExclusion("no-multiple-empty-lines"));

        Assert.assertEquals(0, result.getSingleOccurrence("browser"));
        Assert.assertEquals(0, result.getSingleOccurrence("amd"));
        Assert.assertEquals(0, result.getSingleOccurrence("DocumentFragment"));
        Assert.assertEquals(0, result.getSingleOccurrence("quotes"));
    }
}