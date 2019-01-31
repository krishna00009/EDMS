package com.hyva.bsfms.bs.bsmapper;

import com.hyva.bsfms.bs.bsentities.SchoolBranchDetails;
import com.hyva.bsfms.bs.bspojo.SchoolBranchDetailsPojo;

public class BsSchoolBranchDetailsMapper {
    public static SchoolBranchDetails mapPojoToEntity(SchoolBranchDetailsPojo pojo){
        SchoolBranchDetails master=new SchoolBranchDetails();
        master.setSchoolBranchId(pojo.getSchoolBranchId());
        master.setAddress(pojo.getAddress());
        master.setBranchCode(pojo.getBranchCode());
        master.setBranchName(pojo.getBranchName());
        master.setCity(pojo.getCity());
        master.setEmailId(pojo.getEmailId());
        master.setPhoneNumber(pojo.getPhoneNumber());
        master.setPinCode(pojo.getPinCode());
        master.setState(pojo.getState());
        master.setReceiptNo(pojo.getReceiptNo());
        master.setTermsAndConditions(pojo.getTermsAndConditions());
        master.setReceiptFooter(pojo.getReceiptFooter());
        return master;
    }
}
