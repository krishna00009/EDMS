package com.hyva.bsfms.bs.bspojo;



public class FormsetupDTO {

    private Long formsetupId;
    private String typename;
    private String typeprefix;
    private boolean include_date;
    private String nextref;
    private String transactionType;
    private String termsDesc;
    private String formNo;
    private Long smsId;
    private String message;

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
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

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
