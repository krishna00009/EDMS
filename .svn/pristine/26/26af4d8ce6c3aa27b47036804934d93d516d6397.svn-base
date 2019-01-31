package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.AcademicYearMaster;
import com.hyva.bsfms.bs.bsentities.FeeTypeMaster;
import com.hyva.bsfms.bs.bsentities.GradeMaster;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BsFeeTypeMasterRepository extends CrudRepository<FeeTypeMaster, Long> {

    FeeTypeMaster findByFeeTypeId(Long FeeTypeId);
    FeeTypeMaster findByFeeTypeNameAndGradeMasterAndAcdyrmasterAndFeeAmount(String name,GradeMaster gradeMaster,AcademicYearMaster academicYearMaster,double fee);
    List<FeeTypeMaster> findByFeeTypeNameIsStartingWith(String name);
    List<FeeTypeMaster>findByStatus(String Active);
    List<FeeTypeMaster>findByGradeMaster(GradeMaster gradeMaster);
    List<FeeTypeMaster> findByFeeTypeNameIsStartingWithAndStatus(String name ,String status);
    List<FeeTypeMaster>findByAcdyrmaster(AcademicYearMaster academicYearMaster);
    List<FeeTypeMaster>findByAcdyrmasterAndGradeMasterAndStatus(AcademicYearMaster academicYearMaster, GradeMaster gradeMaster,String status);
    List<FeeTypeMaster>findByGradeMasterAndAcdyrmasterAndFeeTypeIdNotInAndStatus(GradeMaster gradeMaster,AcademicYearMaster academicYearMaster,List<Long> ids,String status);


}
