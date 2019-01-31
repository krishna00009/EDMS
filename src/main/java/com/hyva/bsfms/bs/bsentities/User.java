package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long useraccount_id;
    private String userName;
    private String phone;
    private String passwordUser;
    private String full_name;
    private String email;
    @Column(length = 2000)
    private String securityQuestion;
    private String securityAnswer;
    private String status;
    private String branchCode;
    private Long branchId;
    private Long organizationId;
    private String userType;
    @Column(unique = true, nullable = false)
    private String userToken = UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(0,16);
}
