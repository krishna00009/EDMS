package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.FeeReceipt;
import com.hyva.bsfms.bs.bsentities.FeeReceiptDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BsFeeReceiptDetailsRepository extends JpaRepository<FeeReceiptDetails,Long> {
    List<FeeReceiptDetails> findByFeeReceipt(FeeReceipt feeReceipt);
}
