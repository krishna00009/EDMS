package com.hyva.bsfms.pusher.pusherMapper;
import com.google.gson.Gson;
import com.hyva.bsfms.pusher.pusherPojo.MasterPojo;
import org.json.JSONException;
import org.json.JSONObject;

public class MasterMapper {
    public static MasterPojo convetToMasterPojo1(Object o) throws JSONException {
        MasterPojo masterPojo=new MasterPojo();

        Gson gson=new Gson();
        String jsonInString=gson.toJson(o);
        JSONObject jsonObj = null;
        try {
             jsonObj = new JSONObject(jsonInString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        masterPojo.setMasterFlag("TEST");
//project
if(o.getClass().getSimpleName().equals("AcademicYearMaster")) {
    masterPojo.setProjectId(jsonObj.getString("acdyrId"));
    masterPojo.setProjectName(jsonObj.getString("acdyrName"));
    if(jsonObj.optBoolean("acdyrDescription"))
    masterPojo.setProjectDesc(jsonObj.getString("acdyrDescription"));
    masterPojo.setStartDate(jsonObj.getString("fromDate"));
    masterPojo.setOpeningBalance(jsonObj.getString("toDate"));
    masterPojo.setStatus(jsonObj.getString("status"));
//    masterPojo.setLocationId();
//    masterPojo.setUseraccount_id();
}
// else if(o.getClass().getSimpleName().equals("FeeReceipt")){
//masterPojo.setFeeReceiptID(jsonObj.getString("feeReceiptID"));
//masterPojo.setFeeReceiptReceiptNo(jsonObj.getString("receiptNo"));
//masterPojo.setFeeReceiptPaymentMode(jsonObj.getString("paymentMode"));
//masterPojo.setFeeReceiptCashAmt(jsonObj.getString("cashAmt"));
//masterPojo.setFeeReceiptCardAmt(jsonObj.getString("cardAmt"));
//masterPojo.setFeeReceiptBankAmt(jsonObj.getString("bankAmt"));
//masterPojo.setFeeReceiptChequeDate(jsonObj.getString("chequeDate"));
//masterPojo.setFeeReceiptReceiptDate(jsonObj.getString("receiptDate"));
//masterPojo.setFeeReceiptChequeNo(jsonObj.getString("chequeNo"));
//masterPojo.setFeeReceiptTotalReceived(jsonObj.getString("totalReceived"));
//masterPojo.setFeeReceiptTotalPayable(jsonObj.getString("totalPayable"));
//masterPojo.setFeeReceiptApprovalCode(jsonObj.getString("approvalCode"));
//masterPojo.setFeeReceiptstudentFee(jsonObj.getString("studentFee"));
//masterPojo.setFeeReceiptcardNo(jsonObj.getString("cardNo"));
//masterPojo.setFeeReceiptbankName(jsonObj.getString("bankName"));
//}
//else if(o.getClass().getSimpleName().equals("FeeReceiptDetails")){
//masterPojo.setDetailsId(jsonObj.getString("detailsId"));
//masterPojo.setDetailsFeeReceipt(jsonObj.getString("feeReceipt"));
//masterPojo.setDetailsTotalReceived(jsonObj.getString("totalReceived"));
//masterPojo.setDetailsFeeType(jsonObj.getString("feeType"));
//masterPojo.setDetailsReceiptNo(jsonObj.getString("receiptNo"));
//}
//else if(o.getClass().getSimpleName().equals("Installments")){
//    masterPojo.setInstallmentsId(jsonObj.getString("installmentsId"));
//    masterPojo.setInstallmentsAmount(jsonObj.getString("installmentsAmount"));
//    masterPojo.setInstallmentsdueDate(jsonObj.getString("dueDate"));
//    masterPojo.setInstallmentsstatus(jsonObj.getString("status"));
//    masterPojo.setInstallmentsfeeTypeName(jsonObj.getString("feeTypeName"));
//    masterPojo.setInstallmentspaidAmt(jsonObj.getString("paidAmt"));
//    String feeTypeMasterFK=jsonObj.getString("feeTypeMaster");
//    JSONObject jsonObject=new JSONObject(feeTypeMasterFK);
//    masterPojo.setInstallmentsfeeTypeMasterFK(jsonObject);
//    String studentFeeFK=jsonObj.getString("studentFee");
//    JSONObject jsonObject1=new JSONObject(studentFeeFK);
//    masterPojo.setInstallmentsstudentFeeFK(jsonObject1);
//}

//Fee to Item
else if(o.getClass().getSimpleName().equals("FeeTypeMaster")){
   // masterPojo.setItemId(jsonObj.getString("feeTypeId"));
    //masterPojo.setItemCode(jsonObj.getString("value"));
    masterPojo.setItemName(jsonObj.getString("feeTypeName"));
//    masterPojo.setItemDesc(jsonObj.getString("status"));
    masterPojo.setSalesPrice(jsonObj.getString("feeAmount"));
   // masterPojo.setTaxId(jsonObj.getString("payingFee"));
   // masterPojo.setOutputTaxId(jsonObj.getString("value"));
     masterPojo.setItemStatus("Active");
//    masterPojo.setDiscountAmountRpercent(jsonObj.getString("value"));
//    masterPojo.setStock(jsonObj.getString("value"));
//    masterPojo.setIsDiscountInPercent(jsonObj.getString("value"));
//    masterPojo.setSalesPrice(jsonObj.getString("value"));
//    masterPojo.setPurchasePrice(jsonObj.getString("value"));
//    masterPojo.setItemTypeName(jsonObj.getString("value"));
//    masterPojo.setItemCategoryName(jsonObj.getString("value"));
//    masterPojo.setBrandName(jsonObj.getString("value"));
//    masterPojo.setStatus(jsonObj.getString("value"));
//    masterPojo.setUnitPrice(jsonObj.getString("value"));
//    masterPojo.setSerializableItemId(jsonObj.getString("value"));
//    masterPojo.setSerializableStatus(jsonObj.getString("value"));
//    masterPojo.setCertificateno(jsonObj.getString("value"));
//    masterPojo.setCountType(jsonObj.getString("value"));
//    masterPojo.setUnitOfMeasurementId(jsonObj.getString("value"));
//    masterPojo.setMscid(jsonObj.getString("value"));
//    masterPojo.setInputTaxId(jsonObj.getString("value"));
//    masterPojo.setQty(jsonObj.getString("value"));
//    masterPojo.setBrandId(jsonObj.getString("value"));
//    masterPojo.setInventoryMovementId(jsonObj.getString("value"));
//    masterPojo.setSerializeNo(jsonObj.getString("value"));
//    masterPojo.setCess(jsonObj.getString("value"));
//    masterPojo.setHsnCode(jsonObj.getString("value"));
//    masterPojo.setUomName(jsonObj.getString("value"));
//    masterPojo.setInclusiveJSON(jsonObj.getString("value"));
//    masterPojo.setFileName(jsonObj.getString("value"));
//    String academicYearMasterFK = jsonObj.getString("acdyrmaster");
//    JSONObject jsonObject = new JSONObject(academicYearMasterFK);
//    masterPojo.setFeeTypeAcademicYearMasterFK(jsonObject);
//    String gradeMasterFK=jsonObj.getString("gradeMaster");
//    JSONObject jsonObjectGr=new JSONObject(gradeMasterFK);
//    masterPojo.setFeeTypeGradeMasterFK(jsonObjectGr);
}
else if(o.getClass().getSimpleName().equals("GradeMaster")){
            masterPojo.setItemId(jsonObj.getString("gradeId"));
            masterPojo.setItemName(jsonObj.getString("gradeName"));
            masterPojo.setItemDesc(jsonObj.getString("gradeDescription"));
            masterPojo.setStatus(jsonObj.getString("gradeStatus"));
}
else if(o.getClass().getSimpleName().equals("SchoolBranch")) {
            masterPojo.setInventoryLocationId(jsonObj.getString("schoolBranchId"));
            masterPojo.setInventoryLocationName(jsonObj.getString("branchName"));
            masterPojo.setInventoryLocationFaxNo(jsonObj.getString("branchCode"));
            masterPojo.setInventoryLocationAddress(jsonObj.getString("address"));
            masterPojo.setStateName(jsonObj.getString("state"));
            masterPojo.setGstIn(jsonObj.getString("pinCode"));
            masterPojo.setInventoryLocationContact(jsonObj.getString("phoneNumber"));
            masterPojo.setInventoryLocationEmail(jsonObj.getString("emailId"));

}
else if(o.getClass().getSimpleName().equals("User")) {
            masterPojo.setEmployeeId(jsonObj.getString("useraccount_id"));
            masterPojo.setEmployeeName(jsonObj.getString("userName"));
            masterPojo.setEmployeePhone(jsonObj.getString("phone"));
            masterPojo.setEmployeeAddr(jsonObj.getString("address"));
            masterPojo.setStatus(jsonObj.getString("status"));
            masterPojo.setAccountCode(jsonObj.getString("branchCode"));
            masterPojo.setLocationId(jsonObj.getString("branchId"));
}
else if(o.getClass().getSimpleName().equals("Parents")){
            masterPojo.setHospitalName(jsonObj.getString("fatherName"));
            masterPojo.setDoctorName(jsonObj.getString("motherName"));
            masterPojo.setPatientName(jsonObj.getString("gaurdianName"));
            masterPojo.setEmail(jsonObj.getString("fatherEmailId"));
            masterPojo.setMobile(jsonObj.getString("primaryContactNo"));
            masterPojo.setAccountNo(jsonObj.getString("fatherContactNo"));
            masterPojo.setPatientAccountCode(jsonObj.getString("annualIncome"));
            masterPojo.setUseraccount_id(jsonObj.getString("motherEmailId"));
            masterPojo.setIcNo(jsonObj.getString("gaurdianNumber"));
            masterPojo.setStatus("Active");
}
else if(o.getClass().getSimpleName().equals("Student")){
//    masterPojo.setStudentId(jsonObj.getString("studentId"));
//    masterPojo.setStudentName(jsonObj.getString("studentName"));
//    String gradeMasterFK = jsonObj.getString("gradeMaster");
//    JSONObject jsonObject = new JSONObject(gradeMasterFK);
//    masterPojo.setStudentGradeMasterFK(jsonObject);
//    masterPojo.setStudentAdmissionFormNo(jsonObj.getString("admissionFormNo"));
//    String academicYearMasterFK = jsonObj.getString("academicYearMaster");
//    JSONObject jsonObjectAYM = new JSONObject(academicYearMasterFK);
//    masterPojo.setStudentAcademicYearMasterFK(jsonObjectAYM);
//    masterPojo.setStudentDateofbirth(jsonObj.getString("dateofbirth"));
//    masterPojo.setStudentGender(jsonObj.getString("gender"));
//    masterPojo.setStudentDateOfAdmission(jsonObj.getString("dateOfAdmission"));
//    masterPojo.setStudentDateOfJoining(jsonObj.getString("dateOfJoining"));
//    masterPojo.setStudentProfileId(jsonObj.getString("studentProfileId"));
//    masterPojo.setStudentFatherName(jsonObj.getString("fatherName"));
//    masterPojo.setStudentFatherOccupation(jsonObj.getString("fatherContactNo"));
//    masterPojo.setStudentFatherEmailId(jsonObj.getString("fatherEmailId"));
//    masterPojo.setStudentFatherOccupation(jsonObj.getString("fatherOccupation"));
//    masterPojo.setStudentMotherName(jsonObj.getString("motherName"));
//    masterPojo.setStudentMotherContactNo(jsonObj.getString("motherContactNo"));
//    masterPojo.setStudentMotherEmailId(jsonObj.getString("motherEmailId"));
//    masterPojo.setStudentMotherOccupation(jsonObj.getString("motherOccupation"));
//    masterPojo.setStudentBloodGroup(jsonObj.getString("bloodGroup"));
//    masterPojo.setStudentPrimaryContactNo(jsonObj.getString("primaryContactNo"));
////    masterPojo.setStudentAdmissionStatus(jsonObj.getString("admissionStatus"));
//    masterPojo.setStudentGaurdianName(jsonObj.getString("gaurdianName"));
////    masterPojo.setStudentAnnualIncome(jsonObj.getString("annualIncome"));
////    masterPojo.setStudentPresentAddress(jsonObj.getString("presentAddress"));
//    masterPojo.setStudentPermanentAddress(jsonObj.getString("permanentAddress"));
//    masterPojo.setStudentReligion(jsonObj.getString("religion"));
//    masterPojo.setStudentPhysicalCondition(jsonObj.getString("physicalCondition"));
////   masterPojo.setStudentDocumentUpload(jsonObj.getString("documentUpload"));
//

        masterPojo.setCustomerId(jsonObj.getString("studentId"));
        masterPojo.setCustomerName(jsonObj.getString("studentName"));
        masterPojo.setCustomerNumber(jsonObj.getString("studentProfileId"));
      //  masterPojo.setCustomerCode(jsonObj.getString("azgar"));
        masterPojo.setCustomerEmail(jsonObj.getString("fatherEmailId"));
   //     masterPojo.setCustomerContact(jsonObj.getString("fatherContactNo"));
     //   masterPojo.setGstIn(jsonObj.getString("azgar"));

      //  masterPojo.setBankName(jsonObj.getString("azgar"));
     //   masterPojo.setAccountNo(jsonObj.getString("azgar"));;
     //   masterPojo.setiFSCCode(jsonObj.getString("azgar"));
     //   masterPojo.setBranchName(jsonObj.getString("azgar"));
     //   masterPojo.setPersonIncharge(jsonObj.getString("azgar"));
        masterPojo.setCurrencyId("India Rupee");
        masterPojo.setCountryId("India");
        masterPojo.setState("Karnataka");
      //  masterPojo.setPanNO(jsonObj.getString("azgar"));
     //   masterPojo.setBillingAddress(jsonObj.getString("presentAddress"));
     //   masterPojo.setWebsite(jsonObj.getString("azgar"));
      //  masterPojo.setLoyaltyPoints(jsonObj.getString("azgar"));
     //   masterPojo.setTotalLoyaltyPoints(jsonObj.getString("azgar"));
   //     masterPojo.setPhoneNumber(jsonObj.getString("primaryContactNo"));
   //     masterPojo.setAddress(jsonObj.getString("presentAddress"));
   //     masterPojo.setContactPerson(jsonObj.getString("fatherName"));
      //  masterPojo.setCompanyNumber(jsonObj.getString("azgar"));
     //   masterPojo.setFaxTelex(jsonObj.getString("azgar"));
     //   masterPojo.setCreditedLimit((jsonObj.getString("azgar"));
    //    masterPojo.setTerms(jsonObj.getString("azgar"));
     //   masterPojo.setStatus(jsonObj.getString("admissionStatus"));
    //    masterPojo.setPincode(jsonObj.getString("azgar"));
    //    masterPojo.setVehicleSeries(jsonObj.getString("azgar"));
}
//else if(o.getClass().getSimpleName().equals("StudentFee")){
//    masterPojo.setStudentFeeId(jsonObj.getString("studentFeeId"));
//    masterPojo.setStudentFeetotalFeeAmount(jsonObj.getString("totalFeeAmount"));
//    masterPojo.setStudentFeeStudentName(jsonObj.getString("StudentName"));
//    String academicYearMasterFK=jsonObj.getString("academicYearMaster");
//    JSONObject jsonObject=new JSONObject(academicYearMasterFK);
//    masterPojo.setStudentFeeacademicYearMasterFK(jsonObject);
//    String gradeMasterFK=jsonObj.getString("gradeMaster");
//    JSONObject jsonObject1=new JSONObject(gradeMasterFK);
//    masterPojo.setStudentFeegradeMasterFK(jsonObject1);
//    masterPojo.setStudentFeestudentFK(jsonObj.getString("student"));
//    masterPojo.setStudentFeenoOfInstallments(jsonObj.getString("noOfInstallments"));
//    masterPojo.setStudentFeepaymentType(jsonObj.getString("paymentType"));
//    masterPojo.setStudentFeepaidAmount(jsonObj.getString("paidAmount"));
//    masterPojo.setStudentFeechequeNo(jsonObj.getString("chequeNo"));
//    masterPojo.setStudentFeepaymentDate(jsonObj.getString("paymentDate"));
//    masterPojo.setStudentFeebankName(jsonObj.getString("bankName"));
//    masterPojo.setStudentFeeddNo(jsonObj.getString("ddNo"));
//    masterPojo.setStudentFeefeeTypeAmount(jsonObj.getString("feeTypeAmount"));
//    masterPojo.setStudentFeetotalPayable(jsonObj.getString("totalPayable"));
//    masterPojo.setStudentFeedueAmount(jsonObj.getString("dueAmount"));
//    masterPojo.setStudentFeepayingFee(jsonObj.getString("payingFee"));
//    masterPojo.setStudentFeebankDetails(jsonObj.getString("bankDetails"));
//    masterPojo.setStudentFeecardDetails(jsonObj.getString("cardDetails"));
//    masterPojo.setStudentFeebankAmt(jsonObj.getString("bankAmt"));
//    masterPojo.setStudentFeecardAmt(jsonObj.getString("cardAmt"));
//    masterPojo.setStudentFeecashAmt(jsonObj.getString("cashAmt"));
//
//}
//else if(o.getClass().getSimpleName().equals("StudentFeeDetails")) {
////    List<StudentFeeDetails> list = new ArrayList<StudentFeeDetails>(Arrays.asList(array));
////    masterPojo.setStudentFeeDetailsId(jsonObj.getString("studentFeeDetailsId"));
//    masterPojo .setStudentFeeDetailsfeeTypeName(jsonObj.getString("feeTypeName"));
//    masterPojo.setStudentFeeDetailsfeeTypeAmount(jsonObj.getString("feeTypeAmount"));
//    //  masterPojo.setStudentFeeDetailscheckboxstatus(jsonObj.getString("ttttttt"));
//    masterPojo.setStudentFeeDetailsnoOfInstallments(jsonObj.getString("noOfInstallments"));
//    masterPojo.setStudentFeeDetailsinstallmentsAmount(jsonObj.getString("installmentsAmount"));
//    masterPojo.setStudentFeeDetailspendingFee(jsonObj.getString("pendingFee"));
//    masterPojo.setStudentFeeDetailsstatus(jsonObj.getString("status"));
//    // masterPojo.setStudentFeeDetailsdueDate(jsonObj.getString("dueDate"));
//    masterPojo.setStudentFeeDetailspayable(jsonObj.getString("payable"));
//    masterPojo.setStudentFeeDetailsdiscount(jsonObj.getString("discount"));
//    masterPojo.setStudentFeeDetailspaidAmt(jsonObj.getString("paidAmt"));
//    //  masterPojo.setStudentFeeDetailspaidDate(jsonObj.getString("paidDate"));
//    String feetypemasterFK = jsonObj.getString("feetypemaster");
//    JSONObject jsonObject = new JSONObject(feetypemasterFK);
//    masterPojo.setStudentFeeDetailsfeetypemasterFK(jsonObject);
//    String studentfeeFK = jsonObj.getString("studentfee");
//    JSONObject jsonObject1 = new JSONObject(studentfeeFK);
//    masterPojo.setStudentFeeDetailsstudentfeeFK(jsonObject1);
//
//
//
//}

           return masterPojo;
    }


//    public <T> MasterPojo convetToMasterPojo(T t) {
//
//        MasterPojo masterPojo=new MasterPojo();
//
//
//
//
//        return masterPojo;
//    }
//
//

}
