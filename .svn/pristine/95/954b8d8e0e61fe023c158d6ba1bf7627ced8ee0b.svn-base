package com.hyva.bsfms.exceptions;

/**
 * Created by tnataraj on 1/15/18.
 */
public class BadRequestException extends RuntimeException {

    private int errorCode;
    public BadRequestException( String message) {
        super(message);
    }
    public BadRequestException(String message, int code) {
        super(message);
        this.errorCode = code;
    }

    /**
     * Sets new errorCode.
     *
     * @param errorCode New value of errorCode.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets errorCode.
     *
     * @return Value of errorCode.
     */
    public int getErrorCode() {
        return errorCode;
    }
}