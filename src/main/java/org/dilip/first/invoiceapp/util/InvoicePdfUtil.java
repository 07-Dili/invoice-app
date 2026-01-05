package org.dilip.first.invoiceapp.util;

import org.apache.fop.apps.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class InvoicePdfUtil {

    public String generateBase64Pdf(String foXml) {
        ByteArrayOutputStream pdfOutputStream = null;
        try {
            pdfOutputStream = new ByteArrayOutputStream();

            FopFactory fopFactory = FopFactory.newInstance(new java.io.File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, pdfOutputStream);

            javax.xml.parsers.SAXParserFactory saxParserFactory = javax.xml.parsers.SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            saxParserFactory.setValidating(false);

            javax.xml.parsers.SAXParser saxParser = saxParserFactory.newSAXParser();
            org.xml.sax.XMLReader xmlReader = saxParser.getXMLReader();

            xmlReader.setContentHandler(fop.getDefaultHandler());

            xmlReader.parse(new org.xml.sax.InputSource(
                    new ByteArrayInputStream(foXml.getBytes(StandardCharsets.UTF_8))));

            pdfOutputStream.close();

            byte[] pdfBytes = pdfOutputStream.toByteArray();

            if (pdfBytes == null || pdfBytes.length == 0) {
                throw new RuntimeException("Generated PDF is empty");
            }

            return Base64.getEncoder().encodeToString(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Invoice PDF generation failed: " + e.getMessage(), e);
        } finally {
            if (pdfOutputStream != null) {
                try {
                    pdfOutputStream.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
