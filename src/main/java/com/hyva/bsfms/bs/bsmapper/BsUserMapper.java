package com.hyva.bsfms.bs.bsmapper;

import com.hyva.bsfms.bs.bsentities.*;
import com.hyva.bsfms.bs.bspojo.*;

import java.util.ArrayList;
import java.util.List;

public class BsUserMapper {

    public static User mapPojoToEntity(BsUserPojo bsUserPojo) {
        User user = new User();
        user.setEmail(bsUserPojo.getEmail());
        user.setUserName(bsUserPojo.getUserName());
        user.setPasswordUser(bsUserPojo.getPasswordUser());
        user.setFull_name(bsUserPojo.getFull_name());
        user.setPhone(bsUserPojo.getPhone());
        user.setSecurityAnswer(bsUserPojo.getSecurityAnswer());
        user.setSecurityQuestion(bsUserPojo.getSecurityQuestion());
        user.setStatus(bsUserPojo.getStatus());
        user.setBranchCode(bsUserPojo.getBranchCode());
        user.setUserType(bsUserPojo.getUserType());
        user.setBranchId(bsUserPojo.getBranchId());
        user.setOrganizationId(bsUserPojo.getOrganizationId());
        return user;
    }


    public static List<FormsetupDTO> mapFormSetupEntityToPojo(List<FormSetUp> formSetUpList){
        List<FormsetupDTO> list=new ArrayList<>();
        for(FormSetUp formSetUp:formSetUpList) {
            FormsetupDTO formsetupDTO = new FormsetupDTO();
            formsetupDTO.setNextref(formSetUp.getNextref());
            formsetupDTO.setFormsetupId(formSetUp.getFormsetupId());
            formsetupDTO.setTypename(formSetUp.getTypename());
            formsetupDTO.setTransactionType(formSetUp.getTransactionType());
            formsetupDTO.setTypeprefix(formSetUp.getTypeprefix());
            list.add(formsetupDTO);
        }
        return list;
    }
    public  static FormSetUp mapFormSetupPojoToEntity(FormsetupDTO formsetupDTO){
        FormSetUp formSetUp = new FormSetUp();
        formSetUp.setFormsetupId(formsetupDTO.getFormsetupId());
        formSetUp.setTypeprefix(formsetupDTO.getTypeprefix());
        formSetUp.setNextref(formsetupDTO.getNextref());
        formSetUp.setTypename(formsetupDTO.getTypename());
        formSetUp.setTransactionType(formsetupDTO.getTransactionType());
        return formSetUp;
    }


    public static List<CountryDTO> mapCountryEntityToPojo(List<Country> countryList){
        List<CountryDTO> list=new ArrayList<>();
        for(Country country:countryList) {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setCountryId(country.getCountryId());
            countryDTO.setCountryName(country.getCountryName());
            countryDTO.setStatus(country.getStatus());
            list.add(countryDTO);
        }
        return list;
    }
    public static Country mapCountryPojoToEntity(CountryDTO countryDTO){
        Country country = new Country();
        country.setCountryId(countryDTO.getCountryId());
        country.setCountryName(countryDTO.getCountryName());
        country.setStatus(countryDTO.getStatus());
        return country;
    }

    public static List<StateDTO> mapStateEntityToPojo(List<State> stateList){
        List<StateDTO> list=new ArrayList<>();
        for(State state:stateList) {
            StateDTO stateDTO = new StateDTO();
            stateDTO.setId(state.getId());
            stateDTO.setStateCode(state.getStateCode());
            stateDTO.setStateName(state.getStateName());
            stateDTO.setVehicleSeries(state.getVehicleSeries());
            stateDTO.setStatus(state.getStatus());
            stateDTO.setCountry(state.getCountryName());
            list.add(stateDTO);
        }
        return list;
    }

    public static State mapStatePojoToEntity(StateDTO stateDTO){
        State state = new State();
        state.setStateCode(stateDTO.getStateCode());
        state.setId(stateDTO.getId());
        state.setStateName(stateDTO.getStateName());
        state.setVehicleSeries(stateDTO.getVehicleSeries());
        state.setStatus(stateDTO.getStatus());
        return state;
    }

    public static City mapCityPojoToEntity(CityDTO cityDTO){
        City city = new City();
        city.setCityCode(cityDTO.getCityCode());
        city.setId(cityDTO.getId());
        city.setCityName(cityDTO.getCityName());
        city.setStatus(cityDTO.getStatus());
        city.setCountryName(cityDTO.getCountryName());
        city.setStateName(cityDTO.getState());
        return city;
    }

    public static List<CityDTO> mapCityEntityToPojo(List<City> cityList){
        List<CityDTO> list=new ArrayList<>();
        for(City city:cityList) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setId(city.getId());
            cityDTO.setCityCode(city.getCityCode());
            cityDTO.setCityName(city.getCityName());
            cityDTO.setStatus(city.getStatus());
            cityDTO.setState(city.getStateName());
            cityDTO.setCountryName(city.getCountryName());
            list.add(cityDTO);
        }
        return list;
    }


}
