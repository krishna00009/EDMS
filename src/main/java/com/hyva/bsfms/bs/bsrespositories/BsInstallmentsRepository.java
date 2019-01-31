package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.FeeTypeMaster;
import com.hyva.bsfms.bs.bsentities.Installments;
import com.hyva.bsfms.bs.bsentities.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Date;
import java.util.List;

public interface BsInstallmentsRepository extends JpaRepository<Installments, Long>,JpaSpecificationExecutor {
    List<Installments> findByStudentFeeAndFeeTypeMaster(StudentFee studentFee,FeeTypeMaster feeTypeMaster);
    List<Installments> findByStudentFee(StudentFee studentFee);
    List<Installments> findByStudentFeeAndDueDate(StudentFee studentFee,Date dueDate);
    List<Installments> findByStudentFeeAndFeeTypeMasterAndPaidAmtEquals(StudentFee studentFee, FeeTypeMaster feeTypeMaster, double val);
}
