package com.hyva.bsfms.bs.bsentities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity
@Data

public class FormSetUp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long formsetupId;
    private String typename;
    private String typeprefix;
    private boolean include_date;
    private String nextref;
    private String transactionType;
    @Column(columnDefinition="text")
    private String termsDesc;
    private Long smsId;
    private Long emailId;
    private String locPrefix;

    public String getLocPrefix() {
        return locPrefix;
    }

    public void setLocPrefix(String locPrefix) {
        this.locPrefix = locPrefix;
    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getTermsDesc() {
        return termsDesc;
    }

    public void setTermsDesc(String termsDesc) {
        this.termsDesc = termsDesc;
    }

    public Long getFormsetupId() {
        return formsetupId;
    }

    public void setFormsetupId(Long formsetupId) {
        this.formsetupId = formsetupId;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypeprefix() {
        return typeprefix;
    }

    public void setTypeprefix(String typeprefix) {
        this.typeprefix = typeprefix;
    }

    public boolean isInclude_date() {
        return include_date;
    }

    public void setInclude_date(boolean include_date) {
        this.include_date = include_date;
    }

    public String getNextref() {
        return nextref;
    }

    public void setNextref(String nextref) {
        this.nextref = nextref;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
