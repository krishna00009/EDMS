package com.hyva.bsfms.bs.bsrespositories;
import com.hyva.bsfms.bs.bsentities.GradeMaster;
import com.hyva.bsfms.bs.bsentities.Student;
import com.hyva.bsfms.bs.bsentities.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
public interface BsStudentFeeRepository  extends JpaRepository<StudentFee, Long> ,JpaSpecificationExecutor {
    StudentFee findByStudentFeeId(Long studentFeeId);
    StudentFee findByStudent(Student student);
    List<StudentFee> findByGradeMaster(GradeMaster gradeMaster);
    StudentFee findByStudentAndGradeMaster(Student student,GradeMaster gradeMaster);
    List<StudentFee> findAllByStudentFeeId(Long id);
    List<StudentFee> findStudentFeeByStudentNameIsLike(String searchText);

}
