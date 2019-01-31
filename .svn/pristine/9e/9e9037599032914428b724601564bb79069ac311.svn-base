package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "feeReceipt", uniqueConstraints = @UniqueConstraint(columnNames = {"feeReceiptID"}))
public class FeeReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long feeReceiptID;
    private String receiptNo;
    private String paymentMode;
    private double cashAmt;
    private double cardAmt;
    private double bankAmt;
    private Date chequeDate;
    private Date receiptDate;
    private String chequeNo;
    private double totalReceived;
    private double totalPayable;
    private String approvalCode;
    @OneToOne
    private StudentFee studentFee;
    private String cardNo;
    private String bankName;


}
