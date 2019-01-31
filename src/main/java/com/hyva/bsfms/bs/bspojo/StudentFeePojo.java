package com.hyva.bsfms.bs.bspojo;

import com.hyva.bsfms.bs.bsentities.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
@Data
public class StudentFeePojo {
    private Long studentId;
    private String studentName;
    private GradeMaster gradeMaster;
    private String admissionFormNo;
    private AcademicYearMaster academicYearMaster;
    private Date dateOfAdmission;
    private Date dateOfJoining;
    private String studentProfileId;
    private String fatherName;
    private String fatherContactNo;
    private String fatherEmailId;
    private String fatherOccupation;
    private String motherName;
    private String motherContactNo;
    private String motherEmailId;
    private String motherOccupation;
    private String bloodGroup;
    private String primaryContactNo;
    private String admissionStatus;
    private Long gradeId;
    private Long acdYearId;
    private String gradeName;
    private String discountRemarks;

    private Long feeTypeId;
    private String feeTypeName;
    private Double feeAmount;
    private String status;
    private Long studentFeeId;
    private FeeTypeMaster feeTypeMaster;
    private Student student;
    private Boolean installment;
    private Double installmentsAmount;
    private int noOfInstallments;
    private Double totalFeeAmount;
    private List<Student> studentList;
    private List<FeeTypeMasterPojo> feetypeList;
    private StudentFee studentFee;
    private Long studentFeeDetailsId;
    private String paymentType;
    private Double paidAmount;
    private String chequeNo;
    private Date paymentDate;
    private String bankName;
    private String ddNo;
    private boolean showDetails;
    private Double feeTypeAmount;
    private Double totalPayable;
    private Double dueAmount;
    private Double payingAmount;
    private Double payable;
    private Double discount;
    private String gradeStatus;
    private List<StudentFeeDetails> studentFeeDetailsList;

    }
