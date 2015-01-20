package test.java.util;

import main.java.util.DOMUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

public class DOMUtilTest
{
    private Document document;

    private Element parent;

    @Before
    public void setUp() throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(System.getProperty("user.dir") + "/src/test/resources/domutil_test.xml");

        parent = document.getDocumentElement();
    }

    @Test
    public void testChildrenByTagName() throws Exception
    {
        List<Node> results = DOMUtil.childrenByTagName(parent, "Target");
        List<Node> results2 = DOMUtil.childrenByTagName(parent, "GrandChild");

        Assert.assertEquals(results.size(), 3);
        Assert.assertEquals(results2.size(), 0);
    }
}