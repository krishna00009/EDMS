package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "usertype", uniqueConstraints = @UniqueConstraint(columnNames = {"userTypeId"}))
public class UserType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long userTypeId;
    private String userRole;
    private String RoleDescription;
    private String status;

}
