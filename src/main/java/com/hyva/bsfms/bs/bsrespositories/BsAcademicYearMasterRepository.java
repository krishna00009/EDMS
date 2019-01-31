package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.AcademicYearMaster;
import com.hyva.bsfms.bs.bspojo.AcademicYearMasterPojo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BsAcademicYearMasterRepository extends CrudRepository<AcademicYearMaster, Long> {
//    GradeMaster findByEmailAndAndUserNameAndAndPasswordUser(String email, String  userName, String Password);

    AcademicYearMaster findByAcdyrId(Long acdYearId);
    AcademicYearMaster findByAcdyrName(String s);
    AcademicYearMaster findByAcdyrNameAndAcdyrIdIsNotIn(String s,Long acdYearId);
    List<AcademicYearMaster> findByAcdyrNameIsStartingWith(String name);
    List<AcademicYearMaster> findByStatus(String name);

}
