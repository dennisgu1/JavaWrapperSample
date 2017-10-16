import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helpers to work with XML
 *
 */
public class XmlUtil {

    /**
	 * Find the Application ID by its name within an XML document
	 *
	 * @param appName
	 *            - Name of an application
	 * @param xmlAppListResult
	 *            - XML result from a GetAppList call
	 * @return The ID of the application or null if the ID is not found
	 * @throws Exception
	 *             when given invalid parameter(s) or an error occurred when
	 *             parsing the given XML.
	 */
	public static final String parseAppId(String appName, String xmlAppListResult) throws Exception {
        Document xml = getXmlDocument(xmlAppListResult);
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPathObj = xpf.newXPath();
        NodeList nodeList = (NodeList)xPathObj.evaluate("/*/*[local-name()='app'][@app_id][@app_name]",
														xml.getDocumentElement(), XPathConstants.NODESET);
		String app_id = null;

		for (int x = 0; x < nodeList.getLength(); x++) {
			Node node = nodeList.item(x);

			if (StringUtil.compare(node.getAttributes().getNamedItem("app_name").getNodeValue(), appName, true) == 0) {
				app_id = node.getAttributes().getNamedItem("app_id").getNodeValue();
				break;
			}
		}
		return app_id;

	}

	/**
	 * Find the build ID within a XML document.
	 *
	 * @param xmlBuildInfoResult
	 *            A String that represents the buildinfo XML document, which
	 *            references the buildinfo.xsd schema.
	 * @return A build ID
	 * @throws Exception
	 *             when given invalid XML document or an error occurred when
	 *             parsing the given XML.
	 */
	public static final String parseBuildId(String xmlBuildInfoResult) throws Exception {
		if (StringUtil.isNullOrEmpty(xmlBuildInfoResult)) {
			throw new IllegalArgumentException("Empty XML document.");
		}

        Document xml = getXmlDocument(xmlBuildInfoResult);
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPathObj = xpf.newXPath();
        Node node = (Node)xPathObj.evaluate("/*/*[local-name()='build'][@build_id]", xml.getDocumentElement(), XPathConstants.NODE);
		String buildId = "";
		if (null != node) {
			buildId = node.getAttributes().getNamedItem("build_id").getNodeValue();
		}
		return (!StringUtil.isNullOrEmpty(buildId)) ? buildId : "";
	}

	/**
	 * Takes an XML return an creates a DOM tree.
	 *
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
	public static final Document getXmlDocument(String xmlString) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); /* Solves security vulnerability */
		dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
		dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		dbf.setXIncludeAware(false);
		dbf.setExpandEntityReferences(false);

		InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes());
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document xml = db.parse(new InputSource(new InputStreamReader(inputStream, "UTF-8")));
		return xml;
	}

	/**
	 * Get the error string, if any, from a XML document.
	 *
	 * @param xmlString
	 *            A XML document
	 * @return The error message in the XML document if found, empty string
	 *         otherwise. The return value will never be null.
	 */
	public static final String getErrorString(String xmlString) {
		if (StringUtil.isNullOrEmpty(xmlString)) {
			return "";
		}

		String errorString = StringUtil.EMPTY;
		StringBuilder builder = new StringBuilder();
		// named groups (ie: "<error>(?<text>.*?)</error>") not compatible with
		// all versions of java
		Pattern pattern = Pattern.compile("<error>(.*?)</error>");
		Matcher matcher = pattern.matcher(xmlString);
		while (matcher.find()) {
			builder.append(matcher.group(1) + StringUtil.NEWLINE);
		}
		errorString = builder.toString();
		if (errorString.contains(StringUtil.NEWLINE)) {
			errorString = errorString.substring(0, builder.lastIndexOf(StringUtil.NEWLINE));
		}
		return errorString;
	}
}