package main.java.util;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DOMUtil
{
    public static List<Node> childrenByTagName(final Element element, final String tagName)
    {
        NodeList unFiltered = element.getElementsByTagName(tagName);
        List<Node> filtered = new ArrayList<>();

        for (int i = 0; i < unFiltered.getLength(); i++)
        {
            Node itemNode = unFiltered.item(i);

            if (itemNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element itemElement = (Element) itemNode;

                if (itemElement.getParentNode().getNodeName().equals(element.getTagName())
                        && checkAttributes(itemElement.getParentNode().getAttributes(), element.getAttributes()))
                {
                    filtered.add(itemNode);
                }
            }
        }

        return filtered;
    }

    private static boolean checkAttributes(final NamedNodeMap list1, final NamedNodeMap list2)
    {
        if (list1.getLength() != list2.getLength())
        {
            return false;
        }

        for (int i = 0; i < list1.getLength(); i++)
        {
            if (!(list1.item(i).getNodeName().equals(list2.item(i).getNodeName())) ||
                    !(list1.item(i).getNodeValue().equals(list2.item(i).getNodeValue())))
            {
                return false;
            }
        }

        return true;
    }
}
