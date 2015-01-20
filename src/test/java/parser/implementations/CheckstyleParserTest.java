package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.CheckstyleParser;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CheckstyleParserTest
{
    Document document;
    Parser parser;

    @Before
    public void setUp() throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(System.getProperty("user.dir") + "/src/test/resources/checkstyle_parser_test.xml");

        parser = new CheckstyleParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(document, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("PackageHtml"));
        Assert.assertEquals(1, result.getSingleOccurrence("Translation"));
        Assert.assertEquals(1, result.getSingleOccurrence("TreeWalker"));
        Assert.assertEquals(1, result.getSingleOccurrence("AvoidStarImport"));
        Assert.assertEquals(1, result.getSingleOccurrence("ConstantName"));
        Assert.assertEquals(1, result.getSingleOccurrence("EmptyBlock"));
        Assert.assertEquals(2, result.getSingleOccurrence("LeftCurly"));
        Assert.assertEquals(2, result.getSingleOccurrence("OperatorWrap"));
    }
}