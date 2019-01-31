package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.SchoolBranchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BsSchoolBranchDetailsRepository extends JpaRepository<SchoolBranchDetails, Long> {
}
