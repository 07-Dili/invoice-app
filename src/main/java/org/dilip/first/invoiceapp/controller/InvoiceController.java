package org.dilip.first.invoiceapp.controller;

import org.dilip.first.invoiceapp.model.InvoiceRequest;
import org.dilip.first.invoiceapp.model.InvoiceResponse;
import org.dilip.first.invoiceapp.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private  InvoiceService invoiceService;

    @PostMapping("/generate")
    public InvoiceResponse generate(@RequestBody InvoiceRequest request) {
        return invoiceService.generateInvoice(request);
    }
}
