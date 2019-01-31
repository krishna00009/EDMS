package com.hyva.bsfms.bs.bspojo;

import lombok.Data;

/**
 * Created by azgar on 1/15/18.
 */
@Data
public class EntityResponse {

    int status;
    String message;
    Object object;

    public EntityResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public EntityResponse(int status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object=object;
    }

    public EntityResponse(int status) {
        this.status = status;
    }

    /**
     * Sets new status.
     *
     * @param status New value of status.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Sets new message.
     *
     * @param message New value of message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets status.
     *
     * @return Value of status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets message.
     *
     * @return Value of message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets object.
     *
     * @return Value of object.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets new object.
     *
     * @param object New value of object.
     */
    public void setObject(Object object) {
        this.object = object;
    }
}
