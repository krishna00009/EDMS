package com.hyva.bsfms.bs.bspojo;


import com.hyva.bsfms.bs.bsentities.Mail;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
@Data
public class MailSchedulerData implements Serializable {

    private String toEmail;
    private Mail fromMail;
    private String senderName;
    private String subject;
    private String reportType;
    private String fileName;
    private String dbKeyword;
    private String message;
    private String scheduleType;
    private String reportName;
    private String scheduleTime;
    private String scheduleDate;
    private String student;
    private Date fromDate;
    private Date toDate;
    private String body;
    private String installmentsId;
    }
