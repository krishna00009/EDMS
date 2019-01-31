package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table(name = "student", uniqueConstraints = @UniqueConstraint(columnNames = {"studentId"}))
public class Student implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long studentId;
    private String studentName;
    @OneToOne
    private GradeMaster gradeMaster;
    private String admissionFormNo;
    @OneToOne
    private AcademicYearMaster academicYearMaster;
    private Date dateofbirth;
    private String gender;
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


}
