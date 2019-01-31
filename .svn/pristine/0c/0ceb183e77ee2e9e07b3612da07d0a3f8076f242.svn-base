package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table(name = "studentfeedetails", uniqueConstraints = @UniqueConstraint(columnNames = {"studentFeeDetailsId"}))
public class StudentFeeDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long studentFeeDetailsId;
    private String feeTypeName;
    private Double feeTypeAmount;
    private Boolean checkboxstatus;
    private int noOfInstallments;
    private Double installmentsAmount;
    private Double pendingFee;
    private String status;
    private Date dueDate;
    private Double payable;
    private Double discount;
    private Double paidAmt;
    private Date paidDate;
    @Column(length = 500,columnDefinition = "LONGTEXT")
    private String discountRemarks;
    @OneToOne
    private FeeTypeMaster feetypemaster;
    @OneToOne
    private StudentFee studentfee;

}
