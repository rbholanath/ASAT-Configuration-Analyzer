package test.java.parser.implementations;

import junit.framework.Assert;
import main.java.configanalysis.ConfigAnalysis;
import main.java.configanalysis.implementations.MapConfigAnalysis;
import main.java.parser.Parser;
import main.java.parser.implementations.FindBugsPrefsParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class FindBugsPrefsParserTest
{
    private InputStream stream;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        stream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/findbugs_prefs_parser_test.xml");

        parser = new FindBugsPrefsParser();
    }

    @Test
    public void testParse() throws Exception
    {
        ConfigAnalysis result = parser.parse(stream, new MapConfigAnalysis(parser.getToolName()));

        Assert.assertEquals(1, result.getSingleOccurrence("AppendingToAnObjectOutputStream"));
        Assert.assertEquals(1, result.getSingleOccurrence("BadAppletConstructor"));
        Assert.assertEquals(1, result.getSingleOccurrence("BCPMethodReturnCheck"));
        Assert.assertEquals(1, result.getSingleOccurrence("RepeatedConditionals"));
        Assert.assertEquals(1, result.getSingleOccurrence("ResolveAllReferences"));
        Assert.assertEquals(1, result.getSingleOccurrence("UnreadFields"));
        Assert.assertEquals(1, result.getSingleOccurrence("UseObjectEquals"));
        Assert.assertEquals(1, result.getSingleExclusion("ConfusedInheritance"));
        Assert.assertEquals(1, result.getSingleExclusion("VolatileUsage"));
    }
}