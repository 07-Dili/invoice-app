package org.dilip.first.invoiceapp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.dilip.first.invoiceapp.model.InvoiceItemData;
import org.dilip.first.invoiceapp.model.InvoiceRequest;

public class InvoiceFoBuilder {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
          DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  private static final DateTimeFormatter INPUT_DATE_FORMATTER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static String buildFo(InvoiceRequest request) {
    StringBuilder fo = new StringBuilder();

    fo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    fo.append("<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">\n");

    fo.append("  <fo:layout-master-set>\n");
    fo.append("    <fo:simple-page-master master-name=\"A4\" ")
            .append("page-height=\"29.7cm\" page-width=\"21cm\" margin=\"2cm\">\n");
    fo.append("      <fo:region-body/>\n");
    fo.append("    </fo:simple-page-master>\n");
    fo.append("  </fo:layout-master-set>\n");

    fo.append("  <fo:page-sequence master-reference=\"A4\">\n");
    fo.append("    <fo:flow flow-name=\"xsl-region-body\">\n");


    fo.append("      <fo:block font-size=\"18pt\" font-weight=\"bold\" text-align=\"center\">INVOICE</fo:block>\n");
    fo.append("      <fo:block space-after=\"10pt\"/>\n");


    fo.append("      <fo:block>Order ID: ")
            .append(escapeXml(String.valueOf(request.getOrderId())))
            .append("</fo:block>\n");


    LocalDate orderDate = LocalDateTime
            .parse(request.getOrderDate())
            .toLocalDate();


    LocalDateTime invoiceDateTime =
            orderDate.atTime(LocalTime.now());

    fo.append("      <fo:block>Order Date &amp; Time: ")
            .append(escapeXml(invoiceDateTime.format(DATE_TIME_FORMATTER)))
            .append("</fo:block>\n");

    fo.append("      <fo:block space-after=\"12pt\"/>\n");

    fo.append("      <fo:table table-layout=\"fixed\" width=\"100%\" ")
            .append("border=\"0.5pt solid black\">\n");

    fo.append("        <fo:table-column column-width=\"45%\"/>\n");
    fo.append("        <fo:table-column column-width=\"15%\"/>\n");
    fo.append("        <fo:table-column column-width=\"20%\"/>\n");
    fo.append("        <fo:table-column column-width=\"20%\"/>\n");


    fo.append("        <fo:table-header>\n");
    fo.append("          <fo:table-row font-weight=\"bold\" background-color=\"#f0f0f0\">\n");
    addHeaderCell(fo, "Item");
    addHeaderCell(fo, "Qty");
    addHeaderCell(fo, "Price");
    addHeaderCell(fo, "Total");
    fo.append("          </fo:table-row>\n");
    fo.append("        </fo:table-header>\n");


    fo.append("        <fo:table-body>\n");

    for (InvoiceItemData item : request.getItems()) {
      double lineTotal = item.getQuantity() * item.getSellingPrice();

      fo.append("          <fo:table-row>\n");
      addBodyCell(fo, escapeXml(item.getName()), "left");
      addBodyCell(fo, String.valueOf(item.getQuantity()), "right");
      addBodyCell(fo, String.format("%.2f", item.getSellingPrice()), "right");
      addBodyCell(fo, String.format("%.2f", lineTotal), "right");
      fo.append("          </fo:table-row>\n");
    }

    fo.append("        </fo:table-body>\n");
    fo.append("      </fo:table>\n");


    fo.append("      <fo:block text-align=\"right\" font-weight=\"bold\" margin-top=\"10pt\">\n");
    fo.append("        Grand Total: ")
            .append(String.format("%.2f", request.getTotalAmount()));
    fo.append("\n      </fo:block>\n");

    fo.append("    </fo:flow>\n");
    fo.append("  </fo:page-sequence>\n");
    fo.append("</fo:root>\n");

    return fo.toString();
  }


  private static void addHeaderCell(StringBuilder fo, String text) {
    fo.append("            <fo:table-cell border=\"0.5pt solid black\" padding=\"5pt\">\n");
    fo.append("              <fo:block>")
            .append(text)
            .append("</fo:block>\n");
    fo.append("            </fo:table-cell>\n");
  }

  private static void addBodyCell(StringBuilder fo, String text, String align) {
    fo.append("            <fo:table-cell border=\"0.5pt solid black\" padding=\"5pt\" ")
            .append("text-align=\"").append(align).append("\">\n");
    fo.append("              <fo:block>")
            .append(text)
            .append("</fo:block>\n");
    fo.append("            </fo:table-cell>\n");
  }

  private static String escapeXml(String text) {
    if (text == null) return "";
    return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;");
  }
}
