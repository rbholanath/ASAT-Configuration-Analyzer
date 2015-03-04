package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.RuboCopParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class RuboCopParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/rubocop_parser_test.yml");

        parser = new RuboCopParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("ParameterLists"));
        Assert.assertEquals(1, result.getSingleOccurrence("BlockNesting"));
        Assert.assertEquals(1, result.getSingleOccurrence("CollectionMethods"));
        Assert.assertEquals(1, result.getSingleOccurrence("AccessModifierIndentation"));
        Assert.assertEquals(1, result.getSingleOccurrence("AndOr"));
        Assert.assertEquals(1, result.getSingleOccurrence("PercentLiteralDelimiters"));
        Assert.assertEquals(1, result.getSingleOccurrence("AlignParameters"));
        Assert.assertEquals(0, result.getSingleOccurrence("EndAlignment"));
        Assert.assertEquals(0, result.getSingleOccurrence("inherit_from"));
        Assert.assertEquals(0, result.getSingleOccurrence("AllCops"));
    }
}