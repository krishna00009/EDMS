package com.hyva.bsfms.bs.bspojo;

import com.hyva.bsfms.bs.bsentities.AcademicYearMaster;
import com.hyva.bsfms.bs.bsentities.GradeMaster;
import com.hyva.bsfms.bs.bsentities.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Data
public class FeeTypeMasterPojo {
    private Long feeTypeId;
    private Long studentFeeDetailsId;
    private String feeTypeName;
    private Double feeAmount;
    private String status;
    private AcademicYearMaster acdyrmaster;
    private GradeMaster gradeMaster;
    private Boolean installment;
    private Double installmentsAmount;
    private Double dueAmt;
    private int installments;
    private String dueDate;
    private Student student;
    private Double payingFee;
   // private String checkbox;
    private int acdId;
    private int gradeId;
    private Double payable;
    private Double discount;
    private double paidAmt;
    private List<InstallmentsPojo> installmentsPojosList = new ArrayList<>();
    private String acdyrName;
    private String gradeName;
    private String value;
    private String discountRemarks;
    private boolean checkBox;
    private String userId;
}
