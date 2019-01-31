package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table(name = "studentfee", uniqueConstraints = @UniqueConstraint(columnNames = {"studentFeeId"}))
public class StudentFee implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long studentFeeId;
    private Double totalFeeAmount;
    private String studentName;
    @OneToOne
    private AcademicYearMaster academicYearMaster;
    @OneToOne
    private GradeMaster gradeMaster;
    @OneToOne
    private Student student;
    private int noOfInstallments;
    private String paymentType;
    private Double paidAmount;
    private String chequeNo;
    private Date paymentDate;
    private String bankName;
    private String ddNo;
    private Double feeTypeAmount;
    private Double totalPayable;
    private Double dueAmount;
    private Double payingFee;
    private String bankDetails;
    private String cardDetails;
    private Double bankAmt;
    private Double cardAmt;
    private Double cashAmt;


}
