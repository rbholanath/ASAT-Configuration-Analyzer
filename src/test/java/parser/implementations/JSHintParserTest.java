package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.JSHintParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class JSHintParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/.jshinttestrc");

        parser = new JSHintParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("evil"));
        Assert.assertEquals(1, result.getSingleOccurrence("regexdash"));
        Assert.assertEquals(1, result.getSingleOccurrence("smarttabs"));
        Assert.assertEquals(1, result.getSingleOccurrence("supernew"));
        Assert.assertEquals(1, result.getSingleOccurrence("laxcomma"));
        Assert.assertEquals(1, result.getSingleOccurrence("bitwise"));
        Assert.assertEquals(1, result.getSingleOccurrence("camelcase"));

        Assert.assertEquals(1, result.getSingleExclusion("latedef"));
        Assert.assertEquals(1, result.getSingleExclusion("newcap"));

        Assert.assertEquals(0, result.getSingleOccurrence("indent"));
        Assert.assertEquals(0, result.getSingleOccurrence("globals"));
        Assert.assertEquals(0, result.getSingleOccurrence("require"));
        Assert.assertEquals(0, result.getSingleOccurrence("module"));
    }
}