package com.hyva.bsfms.bs.bsmapper;
import com.hyva.bsfms.bs.bspojo.StudentFeePojo;
import com.hyva.bsfms.bs.bsentities.StudentFee;

public class BsStudentFeeMapper{
    public static StudentFee saveStudentFee(StudentFeePojo saveStudentDetails) {
            StudentFee student = new StudentFee();
            student.setStudent(saveStudentDetails.getStudent());
            student.setGradeMaster(saveStudentDetails.getGradeMaster());
            student.setAcademicYearMaster(saveStudentDetails.getAcademicYearMaster());
            student.setStudentName(saveStudentDetails.getStudentName());
            student.setTotalFeeAmount(saveStudentDetails.getTotalFeeAmount());
            student.setDueAmount(saveStudentDetails.getTotalPayable());
            student.setPaidAmount(0.00);
            student.setCashAmt(0.00);
            student.setCardAmt(0.00);
            student.setBankAmt(0.00);
            student.setTotalPayable(saveStudentDetails.getTotalPayable());
        return student;
    }

}
