/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://jaxp.dev.java.net/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * https://jaxp.dev.java.net/CDDLv1.0.html
 * If applicable add the following below this CDDL HEADER
 * with the fields enclosed by brackets "[]" replaced with
 * your own identifying information: Portions Copyright
 * [year] [name of copyright owner]
 */

/*
 * $Id: SAX2StAXStreamWriter.java,v 1.7 2006/04/12 08:52:20 sunithareddy Exp $
 * @(#)SAX2StAXStreamWriter.java	1.10 06/06/21
 *
 * Copyright 2005 Sun Microsystems, Inc. All Rights Reserved.
 */

package com.sun.org.apache.xalan.internal.xsltc.trax;

import java.util.Iterator;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLEventWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Locator2;

/**
 * @author Sunitha Reddy
 */

public class SAX2StAXStreamWriter extends SAX2StAXBaseWriter {


	private XMLStreamWriter writer;
        
        private boolean needToCallStartDocument = false;
        
	public SAX2StAXStreamWriter() {

	}

	public SAX2StAXStreamWriter(XMLStreamWriter writer) {

		this.writer = writer;

	}


	public XMLStreamWriter getStreamWriter() {

		return writer;

	}


	public void setStreamWriter(XMLStreamWriter writer) {

		this.writer = writer;

	}

	public void startDocument() throws SAXException {

		super.startDocument();
                // Encoding and version info will be available only after startElement 
                // is called for first time. So, defer START_DOCUMENT event of StAX till
                // that point of time.
                needToCallStartDocument = true;
        }

	public void endDocument() throws SAXException {

		try {

			writer.writeEndDocument();

		} catch (XMLStreamException e) {

			throw new SAXException(e);

		}

		super.endDocument();

	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
                
                if (needToCallStartDocument) {
                    try {
                        if (docLocator == null)
                            writer.writeStartDocument();
                        else {
                            try{
                                writer.writeStartDocument(((Locator2)docLocator).getXMLVersion());
                            }catch(ClassCastException e){
                                writer.writeStartDocument();
                            }
                        }

                    } catch (XMLStreamException e) {

                            throw new SAXException(e);

                    }
                    needToCallStartDocument = false;
                }
                
		try {

			String[] qname = {null, null};
			parseQName(qName, qname);
                        //Do not call writeStartElement with prefix and namespaceURI, as it writes out
                        //namespace declaration.
                        //writer.writeStartElement(qname[0], qname[1], uri);
                        writer.writeStartElement(qName);
                       

			// No need to write namespaces, as they are written as part of attributes.
			/*if (namespaces != null) {

                            final int nDecls = namespaces.size();
                            for (int i = 0; i < nDecls; i++) {
                                final String prefix = (String) namespaces.elementAt(i);
                                if (prefix.length() == 0) {
                                    writer.setDefaultNamespace((String)namespaces.elementAt(++i)); 
                                } else {
                                    writer.setPrefix(prefix, (String) namespaces.elementAt(++i)); 
                                }
                                
                                writer.writeNamespace(prefix, (String)namespaces.elementAt(i)); 
                            }

                                                 
			}*/

			// write attributes
			for (int i = 0, s = attributes.getLength(); i < s; i++) {

				parseQName(attributes.getQName(i), qname);

				String attrPrefix = qname[0];
				String attrLocal = qname[1];

				String attrQName = attributes.getQName(i);
				String attrValue = attributes.getValue(i);
				String attrURI = attributes.getURI(i);

				if ("xmlns".equals(attrPrefix) || "xmlns".equals(attrQName)) {

					// namespace declaration disguised as an attribute. 
					// write it as an namespace
                                        
                                        if (attrLocal.length() == 0) {

                                            writer.setDefaultNamespace(attrValue);

                                        } else {

                                            writer.setPrefix(attrLocal, attrValue);

                                        }

                                        writer.writeNamespace(attrLocal, attrValue);

				} else if (attrPrefix.length() > 0) {

					writer.writeAttribute(attrPrefix, attrURI, attrLocal,
							attrValue);

				} else {
                                        writer.writeAttribute(attrQName, attrValue);
                                }

			}

		} catch (XMLStreamException e) {
                        throw new SAXException(e);

		} finally {

			super.startElement(uri, localName, qName, attributes);

		}

	}
        
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		try {

			writer.writeEndElement();

		} catch (XMLStreamException e) {

			throw new SAXException(e);

		} finally {

			super.endElement(uri, localName, qName);

		}

	}

	public void comment(char[] ch, int start, int length) throws SAXException {

		super.comment(ch, start, length);
		try {

			writer.writeComment(new String(ch, start, length));

		} catch (XMLStreamException e) {

			throw new SAXException(e);

		}

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		super.characters(ch, start, length);
		try {

			if (!isCDATA) {

				writer.writeCharacters(ch, start, length);

			}

		} catch (XMLStreamException e) {

			throw new SAXException(e);

		}

	}

	public void endCDATA() throws SAXException {

		try {

			writer.writeCData(CDATABuffer.toString());

		} catch (XMLStreamException e) {

			throw new SAXException(e);

		}

		super.endCDATA();

	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {

		super.ignorableWhitespace(ch, start, length);
		try {

			writer.writeCharacters(ch, start, length);

		} catch (XMLStreamException e) {

			throw new SAXException(e);

		}

	}

	public void processingInstruction(String target, String data)
			throws SAXException {

		super.processingInstruction(target, data);
		try {

			writer.writeProcessingInstruction(target, data);

		} catch (XMLStreamException e) {

			throw new SAXException(e);

		}

	}

}
