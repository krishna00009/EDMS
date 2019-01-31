package com.hyva.bsfms.bs.bsmapper;

import com.hyva.bsfms.bs.bspojo.StudentPojo;
import com.hyva.bsfms.bs.bsentities.Student;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;

public class BsStudentMapper {
    public static Student saveStudent(StudentPojo saveStudentDetails) {
        Student student = new Student();
        student.setStudentId(saveStudentDetails.getStudentId());
        student.setStudentName(saveStudentDetails.getStudentName());
        student.setStudentProfileId(saveStudentDetails.getStudentProfileId());
        student.setAcademicYearMaster(saveStudentDetails.getAcademicYearMaster());
        student.setAdmissionFormNo(saveStudentDetails.getAdmissionFormNo());
        student.setAdmissionStatus(saveStudentDetails.getAdmissionStatus());
        student.setBloodGroup(saveStudentDetails.getBloodGroup());
        student.setDateOfAdmission(saveStudentDetails.getDateOfAdmission());
        student.setDateOfJoining(saveStudentDetails.getDateOfJoining());
        student.setFatherContactNo(saveStudentDetails.getFatherContactNo());
        student.setFatherEmailId(saveStudentDetails.getFatherEmailId());
        student.setFatherName(saveStudentDetails.getFatherName());
        student.setFatherOccupation(saveStudentDetails.getFatherOccupation());
        student.setGradeMaster(saveStudentDetails.getGradeMaster());
        student.setMotherContactNo(saveStudentDetails.getMotherContactNo());
        student.setMotherEmailId(saveStudentDetails.getMotherEmailId());
        student.setMotherName(saveStudentDetails.getMotherName());
        student.setMotherOccupation(saveStudentDetails.getMotherOccupation());
        student.setPrimaryContactNo(saveStudentDetails.getPrimaryContactNo());
        student.setAdmissionStatus(saveStudentDetails.getAdmissionStatus());
        student.setDateofbirth(saveStudentDetails.getDateofbirth());
        student.setGender(saveStudentDetails.getGender());
        student.setGaurdianName(saveStudentDetails.getGaurdianName());
        student.setAnnualIncome(saveStudentDetails.getAnnualIncome());
        student.setPresentAddress(saveStudentDetails.getPresentAddress());
        student.setPermanentAddress(saveStudentDetails.getPermanentAddress());
        student.setReligion(saveStudentDetails.getReligion());
        student.setPhysicalCondition(saveStudentDetails.getPhysicalCondition());
        if(saveStudentDetails.getDocumentUpload()!=null){
            student.setDocumentUpload(saveStudentDetails.getDocumentUpload());
        }
        student.setAadhaarNo(saveStudentDetails.getAadhaarNo());
        if(StringUtils.equalsIgnoreCase(saveStudentDetails.getStudentStatus(),"true")){
            student.setStudentStatus("Active");
        }
        else {
            student.setStudentStatus("InActive");
        }
        student.setGaurdianNumber(saveStudentDetails.getGaurdianNumber());

        return student;
    }

    public static String generateStudentProfileNo(String grade,Date fromYear,int size) {

    String academicyear = String.valueOf(fromYear);
    String[] year = academicyear.split("-");
    String grd;
    String admissionNo="0";
    grade = grade.trim();
      try {
        if (Integer.parseInt(grade) <= 9) {
            grd = "0" + Integer.parseInt(grade);
        } else {
            grd = "" + Integer.parseInt(grade);
        }
    } catch (NumberFormatException nuex) {
        grd = "0" + grade.toUpperCase().charAt(0);
    }
    int no = size;
    no++;
      if (no < 10) {
        admissionNo = admissionNo + grd +  year[0].substring(2)  + "0000" + no;
    } else if (no >= 10 && no < 100) {
        admissionNo = admissionNo + grd +  year[0].substring(2) + "000" + no;
    } else if (no >= 100 && no < 1000) {
        admissionNo = admissionNo + grd +  year[0].substring(2) + "00" + no;
    } else if (no >= 1000 && no < 10000) {
        admissionNo = admissionNo + grd +  year[0].substring(2) +"0" + no;
    } else {
        admissionNo = admissionNo + grd +  year[0].substring(2) + no;
    }
//        System.out.println("admissionNoadmissionNoadmissionNo" + admissionNo);
      return admissionNo;
}


}
