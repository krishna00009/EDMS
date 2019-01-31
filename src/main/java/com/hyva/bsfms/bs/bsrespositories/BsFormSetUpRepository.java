package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.FormSetUp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BsFormSetUpRepository extends JpaRepository<FormSetUp,Long> {

    FormSetUp findAllByTypename(String type);
    FormSetUp findAllByTypenameAndFormsetupIdNotIn(String type, Long id);
    List<FormSetUp> findAllByTypenameContaining(String typeName, Pageable pageable);
    FormSetUp findFirstByTypenameContaining(String typeName, Sort sort);
    FormSetUp findFirstBy(Sort sort);
    List<FormSetUp> findAllBy(Pageable pageable);
}
