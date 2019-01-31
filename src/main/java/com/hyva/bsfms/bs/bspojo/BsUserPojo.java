package com.hyva.bsfms.bs.bspojo;

import lombok.Data;

@Data
public class BsUserPojo {
    private int useraccount_id;
    private String userName;
    private String phone;
    private String passwordUser;
    private String full_name;
    private String email;
    private String securityQuestion;
    private String securityAnswer;
    private String status;
    private String branchCode;
    private String userType;
    private Long branchId;
    private Long organizationId;

   }
