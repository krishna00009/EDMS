package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.SchedulerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BsSchedulerRepository extends JpaRepository<SchedulerData, Long> {
    List<SchedulerData> findAllByStudent(String student);
    List<SchedulerData> findAllByInstallmentsId(String installmentId);

}
