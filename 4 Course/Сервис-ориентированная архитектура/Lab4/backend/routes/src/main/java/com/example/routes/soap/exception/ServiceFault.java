package com.example.routes.soap.exception;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceFault", propOrder = {
        "code",
        "message",
        "time"
})
public class ServiceFault {

    private int code;
    private String message;
    private String time;

    public ServiceFault(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public ServiceFault() {
    }

    public ServiceFault(int code, String message, String time) {
        this.code = code;
        this.message = message;
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
