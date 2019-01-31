package com.hyva.bsfms.bs.bspojo;

import com.hyva.bsfms.bs.bsentities.Student;
import com.hyva.bsfms.bs.bsentities.StudentFee;
import lombok.Data;

import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;
@Data
public class FeeReceiptPojo {
    private String receiptNo;
    private double paidAmt;
    private Long receiptId;
    private Date receiptDate;
    private double totalPaid;
    private String paymentType;
    private StudentPojo studentPojo;
    private List<FeeTypeMasterPojo> feeTypeMasterPojoList;
    private String bankName;
    private String cardNo;
    private String transactionNo;
    private String studentName;
    private String studentProfileId;
    private String approvalCode;
    private Date chequeDate;
    private double cardAmt;
    private double cashAmt;
    private double bankAmt;
    private double onlineAmt;
    private double totalAmt;
 @OneToOne
    private StudentFee studentFee;
}
