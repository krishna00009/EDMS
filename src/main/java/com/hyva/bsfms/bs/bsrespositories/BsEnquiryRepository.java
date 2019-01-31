package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.EnquiryForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BsEnquiryRepository extends JpaRepository<EnquiryForm,Long> {
//    EnquiryForm findByStudentFullName(String studentfullname,Long id);
//    EnquiryForm findByStudentFullName(String studentfullname);
    List<EnquiryForm> findByDate(Date date);
}
