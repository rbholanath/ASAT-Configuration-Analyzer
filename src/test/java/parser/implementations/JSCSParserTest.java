package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.JSCSParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class JSCSParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/.jscstestrc");

        parser = new JSCSParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("disallowEmptyBlocks"));
        Assert.assertEquals(1, result.getSingleOccurrence("disallowKeywords"));
        Assert.assertEquals(1, result.getSingleOccurrence("disallowLeftStickedOperators"));
        Assert.assertEquals(1, result.getSingleOccurrence("requireSpaceAfterBinaryOperators"));
        Assert.assertEquals(1, result.getSingleOccurrence("requireSpaceAfterKeywords"));
        Assert.assertEquals(1, result.getSingleOccurrence("validateIndentation"));
        Assert.assertEquals(1, result.getSingleOccurrence("validateQuoteMarks"));
        Assert.assertEquals(0, result.getSingleOccurrence("validateLineBreaks"));
    }
}