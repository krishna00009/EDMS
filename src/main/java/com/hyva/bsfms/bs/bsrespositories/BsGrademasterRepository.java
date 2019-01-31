package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.GradeMaster;
import com.hyva.bsfms.bs.bsentities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BsGrademasterRepository extends CrudRepository<GradeMaster, Long> {
//    GradeMaster findByEmailAndAndUserNameAndAndPasswordUser(String email, String  userName, String Password);

    GradeMaster findByGradeId(Long gradeId);
    GradeMaster findByGradeName(String GradeName);
    GradeMaster findByGradeNameAndGradeIdNotIn(String GradeName,Long gradeId);
    List<GradeMaster> findByGradeNameIsStartingWith(String name);
    List<GradeMaster> findByGradeNameIsStartingWithAndGradeStatusAndUserId(String name ,String Active,User userId);
    List<GradeMaster>findByGradeStatus(String Active);
    List<GradeMaster> findByGradeStatusAndUserId(String Active,User userId);
    List<GradeMaster> findAllByGradeIdIn(List<Long> ids);
}
