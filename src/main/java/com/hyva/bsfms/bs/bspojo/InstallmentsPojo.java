package com.hyva.bsfms.bs.bspojo;

import com.hyva.bsfms.bs.bsentities.FeeTypeMaster;
import com.hyva.bsfms.bs.bsentities.StudentFee;
import lombok.Data;

import java.sql.Date;

@Data
public class InstallmentsPojo {
    private Date dueDate;
    private Double installmentsAmount;
    private Long feeTypeId;
    private Long installmentsId;
    private String status;
    private String feeTypeName;
    private Boolean checkBox;
    private Double payingAmt;
    private Double paidAmt;
    private Double dueAmt;
    private FeeTypeMaster feeTypeMaster;
    private StudentFee studentFee;
    }
