package com.hyva.bsfms.bs.bsmapper;

import com.hyva.bsfms.bs.bsentities.EnquiryForm;
import com.hyva.bsfms.bs.bsentities.GradeMaster;
import com.hyva.bsfms.bs.bspojo.EnquiryFormDTO;
import com.hyva.bsfms.bs.bspojo.GradeMasterPojo;

import java.util.ArrayList;
import java.util.List;

public class BsEnquiryMapper {
    public static EnquiryForm mapEnquiryPojoToEntity(EnquiryFormDTO bspojo) {
        EnquiryForm enquiryForm = new EnquiryForm();
        enquiryForm.setId(bspojo.getId());
        enquiryForm.setStudentFullName(bspojo.getStudentFullName());
        enquiryForm.setFatherFullName(bspojo.getFatherFullName());
//        enquiryForm.setEmail(bspojo.getEmail());
        enquiryForm.setFatherOccupation(bspojo.getFatherOccupation());
        enquiryForm.setFatherIncome(bspojo.getFatherIncome());
        enquiryForm.setMotherIncome(bspojo.getMotherIncome());
        enquiryForm.setMotherMobile(bspojo.getMotherMobile());
        enquiryForm.setFatherMobile(bspojo.getFatherMobile());
        enquiryForm.setFatherEmailId(bspojo.getFatherEmailId());
        enquiryForm.setMotherEmailId(bspojo.getMotherEmailId());
        enquiryForm.setMotherFullName( bspojo.getMotherFullName() );
        enquiryForm.setCity(bspojo.getCity());
        enquiryForm.setAge(bspojo.getAge());
        enquiryForm.setMotherOccupation( bspojo.getMotherOccupation() );
        enquiryForm.setGender( bspojo.getGender() );
        enquiryForm.setResidentialAddress( bspojo.getResidentialAddress() );
        enquiryForm.setArea( bspojo.getArea() );
        enquiryForm.setDateOfBirth(bspojo.getDateOfBirth());
//        enquiryForm.setMobile(bspojo.getMobile());
        enquiryForm.setGrade(bspojo.getGrade());
        enquiryForm.setDivision(bspojo.getDivision());
        enquiryForm.setCountry(bspojo.getCountry());
        enquiryForm.setCity(bspojo.getCity());
        enquiryForm.setState(bspojo.getState());
        enquiryForm.setPincode(bspojo.getPincode());
        enquiryForm.setDate( bspojo.getDate() );
        enquiryForm.setBsis( bspojo.getBsis() );
        enquiryForm.setSchoolName( bspojo.getSchoolName() );
        enquiryForm.setNonBsimboard( bspojo.getNonBsimboard() );
        enquiryForm.setCurrentSchool( bspojo.getCurrentSchool() );
        enquiryForm.setBoard( bspojo.getBoard());
        enquiryForm.setExtParentRef( bspojo.getExtParentRef() );
        enquiryForm.setSocialMedia( bspojo.getSocialMedia() );
        enquiryForm.setFacebook( bspojo.getFacebook() );
        enquiryForm.setGoogle( bspojo.getGoogle() );
        enquiryForm.setWatsapp( bspojo.getWatsapp() );
        enquiryForm.setSms( bspojo.getSms() );
        enquiryForm.setWebsite( bspojo.getWebsite() );
        enquiryForm.setNewspaper( bspojo.getNewspaper() );
        enquiryForm.setMedia( bspojo.getMedia() );
        enquiryForm.setBanner( bspojo.getBanner() );
        enquiryForm.setSiblings( bspojo.getSiblings() );
        enquiryForm.setSiblingsFullName( bspojo.getSiblingsFullName() );
        enquiryForm.setSiblingsGender( bspojo.getSiblingsGender() );
        enquiryForm.setSiblingsBoard( bspojo.getSiblingsBoard() );
        enquiryForm.setSiblingsGrade( bspojo.getSiblingsGrade() );
        enquiryForm.setSiblingsdate( bspojo.getSiblingsdate() );
        enquiryForm.setNonBsimGrade( bspojo.getNonBsimGrade() );
        enquiryForm.setCurrentSchool( bspojo.getCurrentSchool() );
        enquiryForm.setAttended( bspojo.getAttended() );
        enquiryForm.setProRemarks( bspojo.getProRemarks() );
        enquiryForm.setEnquiryNo( bspojo.getEnquiryNo() );
        enquiryForm.setOldEnquiryNo( bspojo.getOldEnquiryNo() );
        enquiryForm.setManagementRemarks( bspojo.getManagementRemarks() );
        return enquiryForm;
    }


    public static List<EnquiryFormDTO> mapEntityToPojo(List<EnquiryForm> enquiryFormList){
        List<EnquiryFormDTO> list = new ArrayList<>( );
        for(EnquiryForm enquiryForm:enquiryFormList){
            EnquiryFormDTO enquiryFormDTO = new EnquiryFormDTO();
            enquiryFormDTO.setId(enquiryForm.getId());
            enquiryFormDTO.setStudentFullName(enquiryForm.getStudentFullName());
            enquiryFormDTO.setFatherFullName(enquiryForm.getFatherFullName());
//            enquiryFormDTO.setEmail(enquiryForm.getEmail());
            enquiryFormDTO.setFatherOccupation(enquiryForm.getFatherOccupation());
            enquiryFormDTO.setFatherIncome(enquiryForm.getFatherIncome());
            enquiryFormDTO.setMotherIncome(enquiryForm.getMotherIncome());
            enquiryFormDTO.setMotherMobile(enquiryForm.getMotherMobile());
            enquiryFormDTO.setFatherMobile(enquiryForm.getFatherMobile());
            enquiryFormDTO.setFatherEmailId(enquiryForm.getFatherEmailId());
            enquiryFormDTO.setMotherEmailId(enquiryForm.getMotherEmailId());
            enquiryFormDTO.setMotherFullName( enquiryForm.getMotherFullName() );
            enquiryFormDTO.setCity(enquiryForm.getCity());
            enquiryFormDTO.setCountry(enquiryForm.getCountry());
            enquiryFormDTO.setCurrentSchool(enquiryForm.getCurrentSchool());
            enquiryFormDTO.setMotherOccupation( enquiryForm.getMotherOccupation() );
            enquiryFormDTO.setGender( enquiryForm.getGender() );
            enquiryFormDTO.setResidentialAddress( enquiryForm.getResidentialAddress() );
            enquiryFormDTO.setArea( enquiryForm.getArea() );
            enquiryFormDTO.setDateOfBirth(enquiryForm.getDateOfBirth());
//        enquiryForm.setMobile(enquiryForm.getMobile());
            enquiryFormDTO.setGrade(enquiryForm.getGrade());
            enquiryFormDTO.setDivision(enquiryForm.getDivision());
            enquiryFormDTO.setCity(enquiryForm.getCity());
            enquiryFormDTO.setAge(enquiryForm.getAge());
            enquiryFormDTO.setState(enquiryForm.getState());
            enquiryFormDTO.setPincode(enquiryForm.getPincode());
            enquiryFormDTO.setDate( enquiryForm.getDate() );
            enquiryFormDTO.setBsis( enquiryForm.getBsis() );
        enquiryForm.setBoard( enquiryForm.getBoard());
        enquiryForm.setSchoolName( enquiryForm.getSchoolName());
            enquiryFormDTO.setExtParentRef( enquiryForm.getExtParentRef() );
            enquiryFormDTO.setSocialMedia( enquiryForm.getSocialMedia() );
            enquiryFormDTO.setFacebook( enquiryForm.getFacebook() );
            enquiryFormDTO.setGoogle( enquiryForm.getGoogle() );
            enquiryFormDTO.setWatsapp( enquiryForm.getWatsapp() );
            enquiryFormDTO.setSms( enquiryForm.getSms() );
            enquiryFormDTO.setWebsite( enquiryForm.getWebsite() );
            enquiryFormDTO.setNewspaper( enquiryForm.getNewspaper() );
            enquiryFormDTO.setMedia( enquiryForm.getMedia() );
            enquiryFormDTO.setBanner( enquiryForm.getBanner() );
            enquiryFormDTO.setSiblings( enquiryForm.getSiblings() );
            enquiryFormDTO.setSiblingsFullName( enquiryForm.getSiblingsFullName() );
            enquiryFormDTO.setSiblingsGender( enquiryForm.getSiblingsGender() );
            enquiryFormDTO.setSiblingsBoard( enquiryForm.getSiblingsBoard() );
            enquiryFormDTO.setSiblingsGrade( enquiryForm.getSiblingsGrade() );
            enquiryFormDTO.setSiblingsdate( enquiryForm.getSiblingsdate() );
            enquiryFormDTO.setNonBsimGrade( enquiryForm.getNonBsimGrade() );
            enquiryFormDTO.setNonBsimboard( enquiryForm.getNonBsimboard() );
            enquiryFormDTO.setCurrentSchool( enquiryForm.getCurrentSchool() );
            enquiryFormDTO.setAttended( enquiryForm.getAttended() );
            enquiryFormDTO.setProRemarks( enquiryForm.getProRemarks() );
            enquiryFormDTO.setEnquiryNo( enquiryForm.getEnquiryNo() );
            enquiryFormDTO.setOldEnquiryNo( enquiryForm.getOldEnquiryNo());
            enquiryFormDTO.setManagementRemarks( enquiryForm.getManagementRemarks() );
            list.add( enquiryFormDTO);
        }
        return list;
    }


}
