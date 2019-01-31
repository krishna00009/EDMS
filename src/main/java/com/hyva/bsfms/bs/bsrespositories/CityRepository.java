package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {
    List<City> findAllByStateName(String stateName);
    City findByCityName(String cityName);
    City findByCountryName(String CountryName);
    City findByCityNameAndIdNotIn(String cityName, Long id);

    City  findByCityNameAndCountryNameAndStateName(String cityName,String countryname,String statename);
    City findByCityNameAndCountryNameAndStateNameAndIdNotIn(String cityName,String countryname,String statename, Long id);
    List<City> findAllByCityCodeContainingOrCityNameContainingAndStatus(String code, String name, String status, Pageable pageable);
    List<City> findAllByCityCodeContainingOrCityNameContainingAndStatus(String code, String name, String status);
    City findFirstByCityCodeContainingOrStateNameContainingAndStatus(String code, String name, String status, Sort sort);
    City findFirstByStatus(String status, Sort sort);
    List<City> findAllByStatus(String status, org.springframework.data.domain.Pageable pageable);
    List<City> findAllByStateName(String stateName,Sort sort);

}