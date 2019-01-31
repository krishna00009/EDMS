package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Uday_Bhaskar on 14/7/16.
 *
 * @Author Uday_Bhaskar
 */
@Entity
@Data
public class Mail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String password;
    private String smtpHost;
    private String port;
    private String forMail;
    private String logoUrl;
    private String status;
}
