package org.dilip.first.invoiceapp.service;

import org.dilip.first.invoiceapp.model.InvoiceRequest;
import org.dilip.first.invoiceapp.model.InvoiceResponse;
import org.dilip.first.invoiceapp.util.InvoiceFoBuilder;
import org.dilip.first.invoiceapp.util.InvoicePdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    @Autowired
    private InvoicePdfUtil invoicePdfUtil;

    public InvoiceResponse generateInvoice(InvoiceRequest request) {
        String fo = InvoiceFoBuilder.buildFo(request);
        String base64Pdf = invoicePdfUtil.generateBase64Pdf(fo);
        return new InvoiceResponse(base64Pdf);
    }
}
