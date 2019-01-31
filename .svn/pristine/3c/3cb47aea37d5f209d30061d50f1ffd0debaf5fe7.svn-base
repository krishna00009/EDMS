package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "feetypemaster", uniqueConstraints = @UniqueConstraint(columnNames = {"feeTypeId"}))
public class FeeTypeMaster implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long feeTypeId;
    private String feeTypeName;
    private Double feeAmount;
    private String status;
    private Double payingFee;
    private String value;
    @OneToOne
    private AcademicYearMaster acdyrmaster;
    @OneToOne
    private GradeMaster gradeMaster;
    @OneToOne
    private User userId;


}
