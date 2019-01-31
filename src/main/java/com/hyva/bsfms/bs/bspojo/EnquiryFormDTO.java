package com.hyva.bsfms.bs.bspojo;

import lombok.Data;

import java.util.Date;

@Data
public class EnquiryFormDTO {

    private Long id;
    private String studentFullName;
    private String fatherFullName;
    private String fatherOccupation;
    private String fatherIncome;
    private String motherIncome;
    private String motherFullName;
    private String motherOccupation;
    private String gender;
    private Date dateOfBirth;
    private String fatherMobile;
    private String motherMobile;
    private String motherEmailId;
    private String fatherEmailId;
    //    private String Email;
    private String grade;
    private String nonBsimGrade;
    private String division;
    private String country;
    private String city;
    private String state;
    private String pincode;
    private String schoolName;
    private String residentialAddress;
    private String area;

    private String bsis;
    private String board;
    private String extParentRef;
    private String socialMedia;
    private String facebook;
    private String google;
    //    private String email;
    private String watsapp;
    private String sms;
    private String website;
    private String newspaper;
    private String media;
    private String nonBsimboard;
    private String banner;
    private String siblings;
    private String siblingsFullName;
    private String siblingsGender;
    private String siblingsBoard;
    private String siblingsGrade;
    private Date siblingsdate;
    private String currentSchool;
    private String attended;
    private String proRemarks;
    private String managementRemarks;
    private Date date;
    private Double age;
    private String enquiryNo;
    private String oldEnquiryNo;

}