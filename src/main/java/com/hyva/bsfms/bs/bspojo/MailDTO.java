package com.hyva.bsfms.bs.bspojo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by azgar.h on 7/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MailDTO {
    private long id;
    private String userName;
    private String password;
    private String smtpHost;
    private String port;
    private String forMail;
    private String logoUrl;
    private String status;

    }
