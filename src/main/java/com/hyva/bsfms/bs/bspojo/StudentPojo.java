package com.hyva.bsfms.bs.bspojo;

import com.hyva.bsfms.bs.bsentities.AcademicYearMaster;
import com.hyva.bsfms.bs.bsentities.GradeMaster;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Data
public class StudentPojo {
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
    private String receiptNo;
    private Date dateofbirth;
    private String gender;
    private String studentFeeID;
    private String gaurdianName;
    private Double annualIncome;
    private String presentAddress;
    private String permanentAddress;
    private String religion;
    private String physicalCondition;
    private String documentUpload;
    private String aadhaarNo;
    private String studentStatus;
    private String gaurdianNumber;
    private String academicYear;
    private List<FeeTypeMasterPojo> feeTypeMasterPojoList = new ArrayList<>();

}
