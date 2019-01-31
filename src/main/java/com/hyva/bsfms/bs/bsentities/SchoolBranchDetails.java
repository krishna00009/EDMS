package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "schoolbranchdetails", uniqueConstraints = @UniqueConstraint(columnNames = {"schoolBranchId"}))
public class SchoolBranchDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long schoolBranchId;
    private String branchCode;
    private String branchName;
    private String address;
    private String city;
    private String pinCode;
    private String state;
    private String phoneNumber;
    private String emailId;
    private Long receiptNo;
    @Column(columnDefinition = "LONGTEXT")
    private String termsAndConditions;
    @Column(columnDefinition = "LONGTEXT")
    private String receiptFooter;

}
