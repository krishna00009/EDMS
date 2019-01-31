package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "feeReceiptDetails", uniqueConstraints = @UniqueConstraint(columnNames = {"detailsId"}))
public class FeeReceiptDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long detailsId;
    @OneToOne
    private FeeReceipt feeReceipt;
    private double totalReceived;
    private String feeType;
    private String receiptNo;


}
