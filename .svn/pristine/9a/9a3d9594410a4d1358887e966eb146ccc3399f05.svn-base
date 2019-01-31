package com.hyva.bsfms.bs.bsrespositories;
import com.hyva.bsfms.bs.bsentities.FeeTypeMaster;
import com.hyva.bsfms.bs.bsentities.StudentFee;
import com.hyva.bsfms.bs.bsentities.StudentFeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BsStudentFeeDetailsRepository  extends JpaRepository<StudentFeeDetails, Long> {

    List<StudentFeeDetails> findByStudentfee(StudentFee studentFee);
    List<StudentFeeDetails> findByFeetypemaster(FeeTypeMaster feeTypeMaster);
    StudentFeeDetails findByFeetypemasterAndStudentfee(FeeTypeMaster feeTypeMaster,StudentFee studentFee);
}
