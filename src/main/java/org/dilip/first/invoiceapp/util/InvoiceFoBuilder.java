package org.dilip.first.invoiceapp.util;

import org.dilip.first.invoiceapp.model.InvoiceItemData;
import org.dilip.first.invoiceapp.model.InvoiceRequest;

public class InvoiceFoBuilder {

  public static String buildFo(InvoiceRequest request) {
    StringBuilder fo = new StringBuilder();

    fo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    fo.append("<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">\n");
    fo.append("  <fo:layout-master-set>\n");
    fo.append("    <fo:simple-page-master master-name=\"A4\" ");
    fo.append("page-height=\"29.7cm\" page-width=\"21cm\" margin=\"2cm\">\n");
    fo.append("      <fo:region-body/>\n");
    fo.append("    </fo:simple-page-master>\n");
    fo.append("  </fo:layout-master-set>\n");
    fo.append("  <fo:page-sequence master-reference=\"A4\">\n");
    fo.append("    <fo:flow flow-name=\"xsl-region-body\">\n");

    fo.append("      <fo:block font-size=\"18pt\" font-weight=\"bold\" text-align=\"center\">\n");
    fo.append("        INVOICE\n");
    fo.append("      </fo:block>\n");
    fo.append("      <fo:block space-after=\"10pt\"/>\n");

    fo.append("      <fo:block>Order ID: ").append(request.getOrderId()).append("</fo:block>\n");
    fo.append("      <fo:block>Order Date: ").append(request.getOrderDate()).append("</fo:block>\n");
    fo.append("      <fo:block space-after=\"10pt\"/>\n");

    for (InvoiceItemData item : request.getItems()) {
      fo.append("      <fo:block>\n");
      fo.append("        ").append(escapeXml(item.getName()));
      fo.append(" | Qty: ").append(item.getQuantity());
      fo.append(" | Price: ").append(String.format("%.2f", item.getSellingPrice()));
      fo.append("\n      </fo:block>\n");
    }

    fo.append("      <fo:block space-before=\"10pt\" font-weight=\"bold\">\n");
    fo.append("        Total Amount: ").append(String.format("%.2f", request.getTotalAmount()));
    fo.append("\n      </fo:block>\n");

    fo.append("    </fo:flow>\n");
    fo.append("  </fo:page-sequence>\n");
    fo.append("</fo:root>\n");

    return fo.toString();
  }

  private static String escapeXml(String text) {
    if (text == null)
      return "";
    return text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;");
  }
}
