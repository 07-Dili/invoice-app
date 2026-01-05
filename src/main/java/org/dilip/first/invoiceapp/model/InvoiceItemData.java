package org.dilip.first.invoiceapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceItemData {

    private String name;
    private String barcode;
    private Long quantity;
    private Double sellingPrice;
}
