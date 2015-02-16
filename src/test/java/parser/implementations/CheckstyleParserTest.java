package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.CheckstyleParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class CheckstyleParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/checkstyle_parser_test.xml");

        parser = new CheckstyleParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("PackageHtml"));
        Assert.assertEquals(1, result.getSingleOccurrence("Translation"));
        Assert.assertEquals(1, result.getSingleOccurrence("TreeWalker"));
        Assert.assertEquals(1, result.getSingleOccurrence("AvoidStarImport"));
        Assert.assertEquals(1, result.getSingleOccurrence("ConstantName"));
        Assert.assertEquals(1, result.getSingleOccurrence("EmptyBlock"));
        Assert.assertEquals(1, result.getSingleOccurrence("MethodLengthCheck"));
        Assert.assertEquals(2, result.getSingleOccurrence("LeftCurly"));
        Assert.assertEquals(2, result.getSingleOccurrence("OperatorWrap"));
    }
}