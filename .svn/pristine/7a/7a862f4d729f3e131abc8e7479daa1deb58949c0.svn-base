package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "grademaster", uniqueConstraints = @UniqueConstraint(columnNames = {"gradeId"}))
public class GradeMaster implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long gradeId;
    private String gradeName;
    private String gradeDescription;
    private String gradeStatus;
    @OneToOne
    private User userId;
}
