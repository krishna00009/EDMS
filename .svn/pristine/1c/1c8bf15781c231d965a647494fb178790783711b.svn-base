package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table(name = "academicyearmaster", uniqueConstraints = @UniqueConstraint(columnNames = {"acdyrId"}))
public class AcademicYearMaster implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long acdyrId;
    private String acdyrName;
    private String acdyrDescription;
    private Date fromDate;
    private Date toDate;
    private String status;

}
