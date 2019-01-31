package com.hyva.bsfms.bs.bspojo;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by azgar on 1/15/18.
 */
@Data
public class UserPojo {

    @NotNull
    private String userName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String companyName;
}
