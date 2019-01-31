package com.hyva.bsfms.bs.bspojo;

import lombok.Data;

import java.sql.Date;
@Data
public class StudentDatapojo {
    private Long studentFeeDetailsId;
    private String feeTypeName;
    private Double feeTypeAmount;
    private Double totalPayable;
    private Boolean checkboxstatus;
    private int noOfInstallments;
    private Double installmentsAmount;
    private String status;
    private Double dueAmount;
    private Double totalConfiguredFee;
    private Date dueDate;
    private String paymenttype;
    private String paidAmount;
    private String chequeNo;
    private Date paymentDate;
    private String bankName;
    private String ddNo;
    private Double payingAmount;

   }
