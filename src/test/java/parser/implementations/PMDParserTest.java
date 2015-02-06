package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.PMDParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class PMDParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/pmd_parser_test.xml");

        parser = new PMDParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("rulesets/java/strings.xml"));
        Assert.assertEquals(1, result.getSingleOccurrence("rulesets/java/imports.xml/DuplicateImports"));
        Assert.assertEquals(1, result.getSingleOccurrence("rulesets/java/braces.xml"));
        Assert.assertEquals(1, result.getSingleOccurrence("rulesets/java/optimizations.xml/AvoidInstantiatingObjectsInLoops"));
        Assert.assertEquals(1, result.getSingleOccurrence("rulesets/java/optimizations.xml/UseArrayListInsteadOfVector"));
        Assert.assertEquals(1, result.getSingleOccurrence("rulesets/java/basic.xml/EmptyCatchBlock"));
        Assert.assertEquals(1, result.getSingleOccurrence("rulesets/java/codesize.xml/CyclomaticComplexity"));
        Assert.assertEquals(1, result.getSingleExclusion("rulesets/java/braces.xml/WhileLoopsMustUseBraces"));
        Assert.assertEquals(1, result.getSingleExclusion("rulesets/java/braces.xml/IfElseStmtsMustUseBraces"));
    }
}