package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CartMaster", uniqueConstraints = @UniqueConstraint(columnNames = {"cartmasterId"}))
public class CartMaster  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long cartmasterId;
    private String hiConnectCompanyRegNo;
    private String email;
    private String userName;
    private String password;

}
