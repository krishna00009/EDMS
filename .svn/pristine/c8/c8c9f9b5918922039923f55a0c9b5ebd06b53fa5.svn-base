package com.hyva.bsfms.bs.bsrespositories;
import com.hyva.bsfms.bs.bsentities.FeeReceipt;
import com.hyva.bsfms.bs.bsentities.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BsFeeReceiptRepository extends JpaRepository<FeeReceipt,Long>,JpaSpecificationExecutor {
    List<FeeReceipt> findByStudentFee(StudentFee studentFee);
}
