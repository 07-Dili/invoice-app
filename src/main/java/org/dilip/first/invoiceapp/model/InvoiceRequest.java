package org.dilip.first.invoiceapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceRequest {

    private Long orderId;

    private String orderDate;

    private Double totalAmount;
    private List<InvoiceItemData> items;
}
