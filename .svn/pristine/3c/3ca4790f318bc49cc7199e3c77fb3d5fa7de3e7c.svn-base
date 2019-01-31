package com.hyva.bsfms.bs.bsentities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table(name = "installments", uniqueConstraints = @UniqueConstraint(columnNames = {"installmentsId"}))
public class Installments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long installmentsId;
    private Double installmentsAmount;
    private Date dueDate;
    private String status;
    private String feeTypeName;
    private double paidAmt;
    @OneToOne
    private FeeTypeMaster feeTypeMaster;
    @OneToOne
    private StudentFee studentFee;
}
