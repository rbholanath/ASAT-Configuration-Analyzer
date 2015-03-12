package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.JSLParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class JSLParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/jsltest.conf");

        parser = new JSLParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("leading_decimal_point"));
        Assert.assertEquals(1, result.getSingleOccurrence("trailing_decimal_point"));
        Assert.assertEquals(1, result.getSingleOccurrence("partial_option_explicit"));
        Assert.assertEquals(1, result.getSingleOccurrence("context"));
        Assert.assertEquals(1, result.getSingleOccurrence("lambda_assign_requires_semicolon"));
        Assert.assertEquals(1, result.getSingleOccurrence("legacy_control_comments"));

        Assert.assertEquals(1, result.getSingleExclusion("block_without_braces"));
        Assert.assertEquals(1, result.getSingleExclusion("missing_option_explicit"));
        Assert.assertEquals(1, result.getSingleExclusion("jscript_function_extensions"));
        Assert.assertEquals(1, result.getSingleExclusion("always_use_option_explicit"));

        Assert.assertEquals(0, result.getSingleOccurrence("process"));
        Assert.assertEquals(0, result.getSingleOccurrence("define"));
        Assert.assertEquals(0, result.getSingleOccurrence("output-format"));
        Assert.assertEquals(0, result.getSingleOccurrence("pauseatend"));
    }
}