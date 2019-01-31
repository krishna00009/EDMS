package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.State;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosStateRepository extends JpaRepository<State,Long> {
    List<State> findAllByCountryNameAndStatus(String countryName, String status, Sort sort);
    State findById (Long id);
    State findByStateName(String stateName);
    State findByStateNameAndIdNotIn(String stateName, Long id);
    List<State> findAllByStateCodeContainingOrStateNameContainingAndStatus(String code, String name, String status, Pageable pageable);
    List<State> findAllByStateCodeContainingOrStateNameContainingAndStatus(String code, String name, String status);
    State findFirstByStateCodeContainingOrStateNameContainingAndStatus(String code, String name, String status, Sort sort);
    State findFirstByStatus(String status, Sort sort);
    List<State> findAllByStatus(String status, org.springframework.data.domain.Pageable pageable);
}