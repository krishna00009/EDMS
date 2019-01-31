package com.hyva.bsfms.bs.bsentities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class EnquiryForm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String StudentFullName;
    private String FatherFullName;
    private String FatherOccupation;
    private String FatherIncome;
    private String MotherIncome;
    private String MotherFullName;
    private String MotherOccupation;
    private String Gender;
    private Date DateOfBirth;
    private String FatherMobile;
    private String MotherMobile;
    private String MotherEmailId;
    private String FatherEmailId;
//    private String Email;
    private String Grade;
    private String NonBsimGrade;
    private String Division;
    private String Country;
    private String City;
    private String State;
    private String Pincode;
    private String SchoolName;
    private String ResidentialAddress;
    private String Area;

    private String bsis;
    private String Board;
    private String ExtParentRef;
    private String SocialMedia;
    private String facebook;
    private String google;
    //    private String email;
    private String watsapp;
    private String sms;
    private String website;
    private String Newspaper;
    private String Media;
    private String nonBsimboard;
    private String Banner;
    private String Siblings;
    private String SiblingsFullName;
    private String SiblingsGender;
    private String SiblingsBoard;
    private String SiblingsGrade;
    private Date Siblingsdate;
    private String CurrentSchool;
    private String Attended;
    private String ProRemarks;
    private String ManagementRemarks;
    private Date date;
    private Double age;
    private String enquiryNo;
    private String oldEnquiryNo;


    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public Date getSiblingsdate() {
        return Siblingsdate;
    }

    public void setSiblingsdate(Date siblingsdate) {
        Siblingsdate = siblingsdate;
    }

    public String getOldEnquiryNo() {
        return oldEnquiryNo;
    }

    public void setOldEnquiryNo(String oldEnquiryNo) {
        this.oldEnquiryNo = oldEnquiryNo;
    }

    public String getEnquiryNo() {
        return enquiryNo;
    }

    public void setEnquiryNo(String enquiryNo) {
        this.enquiryNo = enquiryNo;
    }

    //    private Long userId;
//    private Long relationId;
//    private Long tokenNo;



    public String getFatherIncome() {
        return FatherIncome;
    }

    public String getNonBsimboard() {
        return nonBsimboard;
    }

    public void setNonBsimboard(String nonBsimboard) {
        this.nonBsimboard = nonBsimboard;
    }

    public void setFatherIncome(String fatherIncome) {
        FatherIncome = fatherIncome;
    }

    public String getMotherIncome() {
        return MotherIncome;
    }

    public void setMotherIncome(String motherIncome) {
        MotherIncome = motherIncome;
    }

    public String getFatherMobile() {
        return FatherMobile;
    }

    public void setFatherMobile(String fatherMobile) {
        FatherMobile = fatherMobile;
    }

    public String getMotherMobile() {
        return MotherMobile;
    }

    public void setMotherMobile(String motherMobile) {
        MotherMobile = motherMobile;
    }

    public String getMotherEmailId() {
        return MotherEmailId;
    }

    public void setMotherEmailId(String motherEmailId) {
        MotherEmailId = motherEmailId;
    }

    public String getFatherEmailId() {
        return FatherEmailId;
    }

    public void setFatherEmailId(String fatherEmailId) {
        FatherEmailId = fatherEmailId;
    }

    public String getNonBsimGrade() {
        return NonBsimGrade;
    }

    public void setNonBsimGrade(String nonBsimGrade) {
        NonBsimGrade = nonBsimGrade;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getResidentialAddress() {
        return ResidentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        ResidentialAddress = residentialAddress;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getWatsapp() {
        return watsapp;
    }

    public void setWatsapp(String watsapp) {
        this.watsapp = watsapp;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSiblingsFullName() {
        return SiblingsFullName;
    }

    public void setSiblingsFullName(String siblingsFullName) {
        SiblingsFullName = siblingsFullName;
    }

    public String getSiblingsGender() {
        return SiblingsGender;
    }

    public void setSiblingsGender(String siblingsGender) {
        SiblingsGender = siblingsGender;
    }

    public String getSiblingsBoard() {
        return SiblingsBoard;
    }

    public void setSiblingsBoard(String siblingsBoard) {
        SiblingsBoard = siblingsBoard;
    }

    public String getSiblingsGrade() {
        return SiblingsGrade;
    }

    public void setSiblingsGrade(String siblingsGrade) {
        SiblingsGrade = siblingsGrade;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentFullName() {
        return StudentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
    }

    public String getFatherFullName() {
        return FatherFullName;
    }

    public void setFatherFullName(String fatherFullName) {
        FatherFullName = fatherFullName;
    }

    public String getFatherOccupation() {
        return FatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        FatherOccupation = fatherOccupation;
    }



    public String getMotherFullName() {
        return MotherFullName;
    }

    public void setMotherFullName(String motherFullName) {
        MotherFullName = motherFullName;
    }

    public String getMotherOccupation() {
        return MotherOccupation;
    }

    public void setMotherOccupation(String motherOccupation) {
        MotherOccupation = motherOccupation;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getBsis() {
        return bsis;
    }

    public void setBsis(String bsis) {
        this.bsis = bsis;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getExtParentRef() {
        return ExtParentRef;
    }

    public void setExtParentRef(String extParentRef) {
        ExtParentRef = extParentRef;
    }

    public String getSocialMedia() {
        return SocialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        SocialMedia = socialMedia;
    }

    public String getNewspaper() {
        return Newspaper;
    }

    public void setNewspaper(String newspaper) {
        Newspaper = newspaper;
    }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
    }

    public String getSiblings() {
        return Siblings;
    }

    public void setSiblings(String siblings) {
        Siblings = siblings;
    }

    public String getCurrentSchool() {
        return CurrentSchool;
    }

    public void setCurrentSchool(String currentSchool) {
        CurrentSchool = currentSchool;
    }

    public String getAttended() {
        return Attended;
    }

    public void setAttended(String attended) {
        Attended = attended;
    }

    public String getProRemarks() {
        return ProRemarks;
    }

    public void setProRemarks(String proRemarks) {
        ProRemarks = proRemarks;
    }

    public String getManagementRemarks() {
        return ManagementRemarks;
    }

    public void setManagementRemarks(String managementRemarks) {
        ManagementRemarks = managementRemarks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}