package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.GradeMaster;
import com.hyva.bsfms.bs.bsentities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BsStudentRepository extends CrudRepository<Student, Long> {
    Student findByStudentName(String studentName);
    Student findByStudentNameAndStudentProfileId(String studentName,String studentProfileId);
    Student findByStudentNameAndAdmissionFormNo(String studentName,String formNo);
    Student findByStudentNameAndAdmissionFormNoAndStudentIdNot(String studentName,String formNo,Long id);

    Student save(String Email);

    List<Student> findByStudentId(Long studentId);


    List<Student> findByGradeMaster(GradeMaster grademaster);


    List<Student> findStudentByStudentNameIsLike(String searchText);

    List<Student> findByStudentStatus(String status);



}