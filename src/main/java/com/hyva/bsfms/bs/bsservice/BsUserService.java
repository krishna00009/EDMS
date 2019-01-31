package com.hyva.bsfms.bs.bsservice;

import com.google.gson.Gson;
import com.hyva.bsfms.bs.Quartz.TenantContext;
import com.hyva.bsfms.bs.bsentities.*;
import com.hyva.bsfms.bs.bsmapper.*;
import com.hyva.bsfms.bs.bspojo.*;
import com.hyva.bsfms.pusher.PusherService;

import com.hyva.bsfms.bs.bsrespositories.*;
import com.hyva.bsfms.bs.bsutil.ObjectMapperUtils;
//import com.hyva.bsfms.pusher.PusherService;
import com.hyva.bsfms.pusher.pusherMapper.MasterMapper;
import com.hyva.bsfms.pusher.pusherMapper.TransactionMapper;
import com.hyva.bsfms.pusher.pusherPojo.MasterPojo;
import com.hyva.bsfms.pusher.pusherPojo.TransactionPojo;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.lowagie.text.Font;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class BsUserService {

    @Autowired
    BsUserRepository bsUserRepository;
    @Autowired
    BsStudentRepository bsStudentRepository;
    @Autowired
    BsGrademasterRepository bsGrademasterRepository;
    @Autowired
    BsSchoolBranchDetailsRepository bsSchoolBranchDetailsRepository;
    @Autowired
    BsAcademicYearMasterRepository bsAcademicYearMasterRepository;
    @Autowired
    BsFeeTypeMasterRepository bsFeeTypeMasterRepository;
    @Autowired
    BsMailRepository bsMailRepository;
    @Autowired
    BsSchedulerRepository bsSchedulerRepository;
    @Autowired
    BsStudentFeeRepository bsStudentFeeRepository;
    @Autowired
    BsFeeReceiptRepository bsFeeReceiptRepository;
    @Autowired
    BsStudentFeeDetailsRepository bsStudentFeeDetailsRepository;
    @Autowired
    BsFeeReceiptDetailsRepository bsFeeReceiptDetailsRepository;
    @Autowired
    PusherService pusherService;
    @Autowired
    BsInstallmentsRepository bsInstallmentsRepository;
    @Autowired
    SchedulerService schedulerService;
    @Autowired
    CartMasterRepository CartMasterRepository;
    @Autowired
    BsFormSetUpRepository bsFormSetUpRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    BsEnquiryRepository bsEnquiryRepository;
    @Autowired
    PosStateRepository posStateRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    PosFormSetupRepository posFormSetupRepository;


    @Autowired
    PusherService pusherService1;
    int paginatedConstants = 5;

    public void saveMailSchedule(MailSchedulerData mailSchedulerData) throws Exception {
        Mail mailServerPops = bsMailRepository.findOne(1L);
        mailSchedulerData.setFromMail(mailServerPops);
        String filename = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ReportPojo reportPojo = new ReportPojo();
        reportPojo.setFromDate(mailSchedulerData.getFromDate());
        reportPojo.setToDate(mailSchedulerData.getToDate());
        if (StringUtils.isNotEmpty(mailSchedulerData.getReportName()))
            switch (mailSchedulerData.getReportName()) {
                case "feeDue":
                    if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(mailSchedulerData.getReportType(), "application/pdf")) {
                        downloadFeeDueReportPdf(outputStream, reportPojo);
                        filename = "FeeDueReport.pdf";
                    } else {
                        downloadFeeDueReportExcel(outputStream, reportPojo);
                        filename = "FeeDueReport.xls";
                    }
                    break;
                case "feeCollect":
                    if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(mailSchedulerData.getReportType(), "application/pdf")) {
                        downloadFeeCollectedReportPdf(outputStream, reportPojo);
                        filename = "FeeCollectedReport.pdf";
                    } else {
                        downloadFeeCollectedReportExcel(outputStream, reportPojo);
                        filename = "FeeCollectedReport.xls";
                    }
                    break;
            }
        if (StringUtils.isEmpty(mailSchedulerData.getBody())) {
            mailSchedulerData.setBody("");
        }
        mailSchedulerData.setDbKeyword(TenantContext.getCurrentTenant());
        MailService.sendMailWithAttachment(mailSchedulerData.getFromMail(),
                mailSchedulerData.getToEmail(), "", mailSchedulerData.getSubject(),
                mailSchedulerData.getBody(), mailSchedulerData.getReportType(),
                outputStream.toByteArray(), filename);
    }

    public List<SchedulerData> getSchedulerList() {
        return bsSchedulerRepository.findAll();
    }

    public void deleteMailSchedulerDetails(String schedulerid) {
        bsSchedulerRepository.delete(Long.parseLong(schedulerid));
    }

    public User userValidate(BsUserPojo bsUserPojo) {
        User user = bsUserRepository.findByUserNameAndPasswordUser(
                bsUserPojo.getUserName(), bsUserPojo.getPasswordUser());
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public User saveUserDetails(BsUserPojo bsUserPojo) {
        User user = null;
        user = bsUserRepository.findByEmail(bsUserPojo.getEmail());
        if (user != null) {
            user = null;
        } else {
            user = BsUserMapper.mapPojoToEntity(bsUserPojo);
            bsUserRepository.save(user);
        }
        return user;
    }
    public FormsetupDTO getFormSetup(String type) {
        FormSetUp formSetUp = posFormSetupRepository.findAllByTypename(type);
        if (formSetUp != null) {
            List<FormSetUp> formSetUpList = new ArrayList<>();
            formSetUpList.add(formSetUp);
            FormsetupDTO formsetupDTO = BsUserMapper.mapFormSetupEntityToPojo(formSetUpList).get(0);
            int incValue = Integer.parseInt(formSetUp.getNextref());
            formsetupDTO.setFormNo(getNextRefInvoice(formSetUp.getTypeprefix(), String.format("%05d", incValue)));
            return formsetupDTO;
        }
        return null;
    }

    public static String getNextRefInvoice(String prefix, String nextRef) {
        StringBuilder sb = new StringBuilder();
        return sb.append(prefix).append(nextRef).toString();
    }

    public Student getStudentByName(String name) {
        Student student = new Student();
//        if (userid != 1) {
//            customers = customerRepository.findByFirstNameOrPhoneNumberOrUhidAndUserId(name, name, name, userid).get(0);
//        } else {
//        student = bsStudentRepository.findByFirstNameOrPhoneNumberOrUhid(name, name, name).get(0);
//        }
        return student;
    }


    //call to save StudentTable
    public Student SaveStudentDetails(StudentPojo saveStudentDetails) throws JSONException, ParseException, IOException {
        Student student = null;
        List<SchoolBranchDetails> schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll();
        if (schoolBranchDetails.size() == 0) {
            SchoolBranchDetails branchDetails = new SchoolBranchDetails();
            branchDetails.setReceiptNo(0L);
            bsSchoolBranchDetailsRepository.save(branchDetails);
        }
        Mail mail = bsMailRepository.findOne(1L);
        GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(saveStudentDetails.getGradeId());
        AcademicYearMaster acdobj = bsAcademicYearMasterRepository.findByAcdyrId(saveStudentDetails.getAcdYearId());
        saveStudentDetails.setAcademicYearMaster(acdobj);
        saveStudentDetails.setGradeMaster(grdmstrobj);
        List<Student> studList = (List<Student>) bsStudentRepository.findAll();
        int studId = 0;
        for (Student stud : studList) {
            studId = Math.toIntExact(stud.getStudentId());
        }
//        if (saveStudentDetails.getStudentId() != null) {
//            student1 = bsStudentRepository.findByStudentNameAndAdmissionFormNoAndStudentIdNot(saveStudentDetails.getStudentName(), saveStudentDetails.getAdmissionFormNo(), saveStudentDetails.getStudentId());
//        } else {
//            student1 = bsStudentRepository.findByStudentNameAndAdmissionFormNo(saveStudentDetails.getStudentName(), saveStudentDetails.getAdmissionFormNo());
//        }
//        if (student1 != null) {
//            return null;
//        }
        if (saveStudentDetails.getStudentId() != null) {
            Student student2 = bsStudentRepository.findByStudentId(saveStudentDetails.getStudentId()).get(0);
            saveStudentDetails.setStudentProfileId(student2.getStudentProfileId());
        } else {
            String admissionNo = BsStudentMapper.generateStudentProfileNo(grdmstrobj.getGradeName(), acdobj.getFromDate(), studId);
            saveStudentDetails.setStudentProfileId(admissionNo);
        }
        student = BsStudentMapper.saveStudent(saveStudentDetails);
        bsStudentRepository.save(student);
        //          commented for time being
        Gson gson = new Gson();
        MasterPojo masterPojoStudent = MasterMapper.convetToMasterPojo1(student);
        masterPojoStudent.setMasterFlag("Student");
        masterPojoStudent.setSubscriptionType("RTR");
        masterPojoStudent.setStatus("Active");
        String JsonStudentString = gson.toJson(masterPojoStudent);
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if (cartMaster != null) {
            cartID = cartMaster.getHiConnectCompanyRegNo();
        }
        String customerCode = pusherService.BroadCastMasterData(JsonStudentString, cartID, cartID, "AddCustomer", "AddMaster");

        if(saveStudentDetails.getFeeTypeMasterPojoList()!=null){
            StudentPojo saveStudentDetails1=new StudentPojo() ;
            saveStudentDetails1.setStudentProfileId(saveStudentDetails.getStudentProfileId());
            saveStudentDetails1.setStudentName(saveStudentDetails.getStudentName());
            saveStudentDetails1.setFeeTypeMasterPojoList(saveStudentDetails.getFeeTypeMasterPojoList());
            Student student1=SaveStudentfeee(saveStudentDetails1);}

        String JsonBrainyStudentString = gson.toJson(saveStudentDetails);
        User user = bsUserRepository.findOne(1l);
        String branchCode = "";
        if(!user.equals(null)) branchCode = user.getBranchCode();
        String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString,branchCode,branchCode,"AddStudent","AddMaster");
        return student;


    }



//    public List<EnquiryFormDTO> studentList() {
//        List<EnquiryForm> enquiryForms = new ArrayList<>();
//        enquiryForms =bsEnquiryRepository.findAll();
//        List<EnquiryFormDTO> enquiryFormDTOS = BsEnquiryMapper.mapEntiyToPojo(enquiryFormDTOS);
//        return enquiryFormDTOS;
//    }


    public EnquiryForm saveNewEnquiry(EnquiryFormDTO enquiryFormDTO) {
        EnquiryForm enquiryForm = null;
        if (enquiryForm == null) {
            enquiryForm = BsEnquiryMapper.mapEnquiryPojoToEntity( enquiryFormDTO );
            FormSetUp tokennumber = bsFormSetUpRepository.findAllByTypename( "EnquiryNumber" );
            List<EnquiryForm> list = bsEnquiryRepository.findByDate( enquiryFormDTO.getDate() );
            if (tokennumber != null) {
                tokennumber.setNextref( "0000" + list.size() );
                int incValue2 = Integer.parseInt( tokennumber.getNextref() );
                enquiryFormDTO.setEnquiryNo( getNextRefInvoice( tokennumber.getTypeprefix(), String.format( "%05d", ++incValue2 ) ) );
                tokennumber.setNextref( String.format( "%05d", incValue2 ) );
                bsFormSetUpRepository.save( tokennumber );
            }
            bsEnquiryRepository.save( enquiryForm );
            return enquiryForm;
        } else {
            return null;
        }
    }
    public EnquiryForm saveoldEnquiry(EnquiryFormDTO enquiryFormDTO) {
        EnquiryForm enquiryForm = null;
        if (enquiryForm == null) {
            enquiryForm = BsEnquiryMapper.mapEnquiryPojoToEntity( enquiryFormDTO );
            List<EnquiryForm> list = bsEnquiryRepository.findByDate( enquiryFormDTO.getDate() );
        }
            bsEnquiryRepository.save( enquiryForm );
            return enquiryForm;

        }

    //call to save StudentFeeTable
    public Student SaveStudentfeee(StudentPojo saveStudentDetails1) throws JSONException, ParseException, IOException {
        Student student=bsStudentRepository.findByStudentNameAndStudentProfileId(saveStudentDetails1.getStudentName(),saveStudentDetails1.getStudentProfileId());
        Mail mail = bsMailRepository.findOne(1L);
        Gson gson = new Gson();
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if (cartMaster != null) {
            cartID = cartMaster.getHiConnectCompanyRegNo();
        }
        StudentFeePojo studentFeeTypeMasterPojo = new StudentFeePojo();
        studentFeeTypeMasterPojo.setStudent(student);
        studentFeeTypeMasterPojo.setGradeMaster(student.getGradeMaster());
        studentFeeTypeMasterPojo.setAcademicYearMaster(student.getAcademicYearMaster());
        studentFeeTypeMasterPojo.setStudentName(student.getStudentName());
        studentFeeTypeMasterPojo.setFeetypeList(saveStudentDetails1.getFeeTypeMasterPojoList());
        Double totalFee = 0.00, totalDiscount = 0.00;
        for (FeeTypeMasterPojo feelist : studentFeeTypeMasterPojo.getFeetypeList()) {
            if (feelist.isCheckBox()== true) {
                totalFee = totalFee + feelist.getFeeAmount();
                if (feelist.getDiscount() == null) {
                    feelist.setDiscount(0.00);
                }
                totalDiscount = totalDiscount + feelist.getDiscount();
            }
        }
        studentFeeTypeMasterPojo.setTotalFeeAmount(totalFee);
        studentFeeTypeMasterPojo.setTotalPayable(totalFee - totalDiscount);
        studentFeeTypeMasterPojo.setDiscount(totalDiscount);
        StudentFee studentFee = null;
        studentFee = bsStudentFeeRepository.findByStudent(student);
        if (studentFee != null) {
            Double remainingAmt = studentFeeTypeMasterPojo.getTotalPayable() - studentFee.getTotalPayable();
            if (student.getGradeMaster() == studentFee.getGradeMaster()) {
                studentFeeTypeMasterPojo.setGradeStatus("true");
            } else {
                studentFeeTypeMasterPojo.setGradeStatus("false");
            }
            studentFee.setGradeMaster(student.getGradeMaster());
            studentFee.setAcademicYearMaster(student.getAcademicYearMaster());
            studentFee.setTotalFeeAmount(studentFeeTypeMasterPojo.getTotalFeeAmount());
            studentFee.setTotalPayable(studentFeeTypeMasterPojo.getTotalPayable());
            studentFee.setDueAmount(studentFee.getDueAmount() + remainingAmt);
            studentFee.setStudentName(student.getStudentName());
            studentFee.setStudent(student);
        } else {
            studentFee = BsStudentFeeMapper.saveStudentFee(studentFeeTypeMasterPojo);
        }
        bsStudentFeeRepository.save(studentFee);
        studentFeeTypeMasterPojo.setStudentFee(studentFee);
        studentFeeTypeMasterPojo.setStudentId(student.getStudentId());
        List<StudentFeeDetails> studentFeedetails = null;
        studentFeedetails = saveStudentFeeDetails(studentFeeTypeMasterPojo);
        for (int i = 0; i < studentFeedetails.size(); i++) {
            studentFeeTypeMasterPojo.getFeetypeList().get(i).setPaidAmt(studentFeedetails.get(i).getPaidAmt());
            studentFeedetails.get(i).setFeetypemaster(bsFeeTypeMasterRepository.findByFeeTypeId(saveStudentDetails1.getFeeTypeMasterPojoList().get(i).getFeeTypeId()));
        }
        bsStudentFeeDetailsRepository.save(studentFeedetails);
        if (StringUtils.equalsIgnoreCase(studentFeeTypeMasterPojo.getGradeStatus(), "false")) {
            if (studentFee != null) {
                List<Installments> installments = bsInstallmentsRepository.findByStudentFee(studentFee);
                bsInstallmentsRepository.delete(installments);
            }
        }
        for (FeeTypeMasterPojo feeTypeMasterPojo : studentFeeTypeMasterPojo.getFeetypeList()) {
            if (feeTypeMasterPojo.getPaidAmt() == 0) {
                FeeTypeMaster feeTypeMaster = bsFeeTypeMasterRepository.findByFeeTypeId(feeTypeMasterPojo.getFeeTypeId());
                List<Installments> installment = bsInstallmentsRepository.findByStudentFeeAndFeeTypeMasterAndPaidAmtEquals(studentFee, feeTypeMaster, 0.00);
                bsInstallmentsRepository.delete(installment);
                for (InstallmentsPojo installmentsPojo : feeTypeMasterPojo.getInstallmentsPojosList()) {
                    Installments installments = new Installments();
                    installments.setFeeTypeName(feeTypeMasterPojo.getFeeTypeName());
                    installments.setInstallmentsAmount(installmentsPojo.getInstallmentsAmount());
                    installments.setDueDate(installmentsPojo.getDueDate());
                    installments.setStudentFee(studentFeeTypeMasterPojo.getStudentFee());
                    installments.setStatus("pending");
                    installments.setFeeTypeMaster(feeTypeMaster);
                    bsInstallmentsRepository.save(installments);
                }
            }
        }
        List<StudentFeeDetails> list = bsStudentFeeDetailsRepository.findByStudentfee(studentFee);
//        List<MasterPojo> pojoList = new ArrayList<>();
//        for (int count = 0; count < list.size(); count++) {
//            MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(list.get(count));
//            masterPojo.setMasterFlag("Fee Configuration");
//            pojoList.add(masterPojo);
//        }
        if (StringUtils.equalsIgnoreCase(student.getStudentStatus(), "Active")) {
            for (StudentFeeDetails studentFeeDetail : studentFeedetails) {
                if (studentFeeDetail.getCheckboxstatus() == true) {
                    List<Installments> installments = bsInstallmentsRepository.findByStudentFeeAndFeeTypeMaster(studentFee, studentFeeDetail.getFeetypemaster());
                    List<SchedulerData> schedulerDataList = bsSchedulerRepository.findAllByStudent(studentFee.getStudent().getStudentId().toString());
                    bsSchedulerRepository.delete(schedulerDataList);
                    for (Installments installments1 : installments) {
                        if (StringUtils.isNotEmpty(student.getFatherEmailId())) {
                            if (installments1.getDueDate().after(new Date())) {
                                if (installments1.getPaidAmt() < installments1.getInstallmentsAmount()) {
                                    SchedulerData schedulerData = new SchedulerData();
                                    schedulerData.setDatabaseKeyWord(TenantContext.getCurrentTenant());
                                    schedulerData.setReportName("DueRemainder" + bsSchedulerRepository.findAll().size());
                                    schedulerData.setScheduleType("Yearly");
                                    schedulerData.setStudent(student.getStudentId().toString());
                                    schedulerData.setInstallmentsId(installments1.getInstallmentsId().toString());
                                    schedulerData.setTime("10:00:00");
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                                    c.setTime(sdf.parse(installments1.getDueDate().toString()));
                                    c.add(Calendar.DAY_OF_MONTH, -1);
                                    String newDate = sdf1.format(c.getTime());
                                    schedulerData.setDate(newDate);
                                    if (StringUtils.isNotEmpty(student.getMotherEmailId())) {
                                        schedulerData.setToEmailId(student.getFatherEmailId() + "," + student.getMotherEmailId());
                                    } else {
                                        schedulerData.setToEmailId(student.getFatherEmailId());
                                    }
                                    bsSchedulerRepository.save(schedulerData);
                                    MailSchedulerData mailSchedulerData = new MailSchedulerData();
                                    mailSchedulerData.setDbKeyword(schedulerData.getDatabaseKeyWord());
                                    mailSchedulerData.setToEmail(schedulerData.getToEmailId());
                                    mailSchedulerData.setScheduleTime(schedulerData.getTime());
                                    mailSchedulerData.setInstallmentsId(schedulerData.getInstallmentsId());
                                    mailSchedulerData.setScheduleType(schedulerData.getScheduleType());
                                    mailSchedulerData.setReportName(schedulerData.getReportName());
                                    mailSchedulerData.setScheduleDate(schedulerData.getDate());
                                    mailSchedulerData.setStudent(schedulerData.getStudent());
                                    mailSchedulerData.setFromMail(mail);
                                    mailSchedulerData.setReportType("Pdf");
                                    schedulerService.schedule(mailSchedulerData);
                                }
                            }
                        }
                    }
                }
            }
        }

        StudentPojo saveStudentDetails=new StudentPojo();
        saveStudentDetails.setStudentProfileId(student.getStudentProfileId());
        saveStudentDetails.setStudentName(student.getStudentName());
        saveStudentDetails.setFeeTypeMasterPojoList(saveStudentDetails.getFeeTypeMasterPojoList());

        TransactionPojo transactionPojo = TransactionMapper.studentToTransaction(saveStudentDetails);
        transactionPojo.setAmtToBePaid(totalFee);
        transactionPojo.setDiscountAmount(totalDiscount);
        transactionPojo.setTotalCheckOutamt(totalFee);
        String JsonInString = gson.toJson(transactionPojo);
        String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "SIN", "Sales");
        String JsonBrainyStudentString = gson.toJson(saveStudentDetails);
        User user = bsUserRepository.findOne(1l);
        String branchCode = "";
        if(!user.equals(null)) branchCode = user.getBranchCode();
        String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString,branchCode,branchCode,"AddStudent","AddMaster");
        return student;
    }


    public List<StudentFeeDetails> saveStudentFeeDetails(StudentFeePojo saveStudentDetails) {
        List<StudentFeeDetails> studentFeeDetails = new ArrayList<>();
        Student student = null;
        StudentFee studentfee = null;
        for (FeeTypeMasterPojo feeTypeMasterPojo : saveStudentDetails.getFeetypeList()) {
            StudentFeeDetails details = new StudentFeeDetails();
            if (saveStudentDetails.getStudentId() != null) {
                student = bsStudentRepository.findByStudentId(saveStudentDetails.getStudentId()).get(0);
                studentfee = bsStudentFeeRepository.findByStudent(student);
                saveStudentDetails.setStudentFee(studentfee);
                if (feeTypeMasterPojo.getStudentFeeDetailsId() != null) {
                    details = bsStudentFeeDetailsRepository.findOne(feeTypeMasterPojo.getStudentFeeDetailsId());
                }
                if (StringUtils.equalsIgnoreCase(saveStudentDetails.getGradeStatus(), "false")) {
                    if (studentfee != null) {
                        List<StudentFeeDetails> studentFeeDetails1 = bsStudentFeeDetailsRepository.findByStudentfee(studentfee);
                        bsStudentFeeDetailsRepository.delete(studentFeeDetails1);
                    }
                }
            }
            if (details == null) {
                details = new StudentFeeDetails();
            }
            if (details.getPaidAmt() == null) {
                details.setPaidAmt(0.00);
            }
            if (feeTypeMasterPojo.getInstallments() >= 1 && details.getPaidAmt() == 0) {
                details.setNoOfInstallments(feeTypeMasterPojo.getInstallments());
                details.setFeeTypeName(feeTypeMasterPojo.getFeeTypeName());
                details.setDiscountRemarks(feeTypeMasterPojo.getDiscountRemarks());
                details.setFeeTypeAmount(feeTypeMasterPojo.getFeeAmount());
                details.setCheckboxstatus(Boolean.valueOf(feeTypeMasterPojo.isCheckBox()));
                if (feeTypeMasterPojo.getDiscount() == null) {
                    Double discount = 0.0;
                    details.setInstallmentsAmount(feeTypeMasterPojo.getFeeAmount() - discount);
                    details.setPendingFee(feeTypeMasterPojo.getFeeAmount() - discount);
                    details.setDiscount(discount);
                    details.setPayable(feeTypeMasterPojo.getFeeAmount() - discount);
                } else {
                    details.setInstallmentsAmount(feeTypeMasterPojo.getFeeAmount() - feeTypeMasterPojo.getDiscount());
                    details.setPendingFee(feeTypeMasterPojo.getFeeAmount() - feeTypeMasterPojo.getDiscount());
                    details.setDiscount(feeTypeMasterPojo.getDiscount());
                    details.setPayable(feeTypeMasterPojo.getFeeAmount() - feeTypeMasterPojo.getDiscount());
                }
                details.setStatus("Pending");
            }
            details.setStudentfee(saveStudentDetails.getStudentFee());
            studentFeeDetails.add(details);
        }
        return studentFeeDetails;
    }

    public Boolean deleteStudentDetails(StudentPojo StudentDetails) {
        Student student = bsStudentRepository.findByStudentId(StudentDetails.getStudentId()).get(0);
        StudentFee studentFee = bsStudentFeeRepository.findByStudent(student);
        List<FeeReceipt> feeReceipt = bsFeeReceiptRepository.findByStudentFee(studentFee);
        if (feeReceipt.size() == 0) {
            bsStudentFeeDetailsRepository.delete(bsStudentFeeDetailsRepository.findByStudentfee(studentFee));
            bsInstallmentsRepository.delete(bsInstallmentsRepository.findByStudentFee(studentFee));
            bsStudentFeeRepository.delete(bsStudentFeeRepository.findByStudent(student));
            bsStudentRepository.delete(student);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteGradeDetails(GradeMasterPojo details) {
        GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(details.getGradeId());
        List<FeeTypeMaster> list = bsFeeTypeMasterRepository.findByGradeMaster(gradeMaster);
        if (list.size() == 0) {
            bsGrademasterRepository.delete(details.getGradeId());
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteAcademicDetails(AcademicYearMasterPojo details) {
        AcademicYearMaster academicYearMaster = bsAcademicYearMasterRepository.findByAcdyrId(details.getAcdyrId());
        List<FeeTypeMaster> list = bsFeeTypeMasterRepository.findByAcdyrmaster(academicYearMaster);
        if (list.size() == 0) {
            bsAcademicYearMasterRepository.delete(details.getAcdyrId());
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteFeeDetails(FeeTypeMasterPojo Details) {
        FeeTypeMaster feeTypeMaster = bsFeeTypeMasterRepository.findOne(Details.getFeeTypeId());
        List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemaster(feeTypeMaster);
        if (studentFeeDetails.size() == 0) {
            bsFeeTypeMasterRepository.delete(feeTypeMaster);
            return true;
        } else {
            return false;
        }
    }

    public List<GradeMasterPojo> gradeMasterList() {
        List<GradeMaster> grade = new ArrayList<>();
//       grade = (List<GradeMaster>) bsGrademasterRepository.findAll();
        grade = bsGrademasterRepository.findByGradeStatus("Active");
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade, GradeMasterPojo.class);
        return gdPojoList;
    }


    public List<SchoolBranchDetailsPojo> schoolBranchDetailsList() {
        List<SchoolBranchDetails> branchDetailsList = new ArrayList<>();
        branchDetailsList = (List<SchoolBranchDetails>) bsSchoolBranchDetailsRepository.findAll();
        List<SchoolBranchDetailsPojo> gdPojoList = ObjectMapperUtils.mapAll(branchDetailsList, SchoolBranchDetailsPojo.class);
        return gdPojoList;
    }

    public List<GradeMasterPojo> gradeList(Long academicID) {
        AcademicYearMaster academicYearMaster = bsAcademicYearMasterRepository.findByAcdyrId(academicID);
        List<FeeTypeMaster> feeTypeMasters = new ArrayList<>();
        feeTypeMasters = bsFeeTypeMasterRepository.findByAcdyrmaster(academicYearMaster);
        List<GradeMaster> grade1 = new ArrayList<>();
        Map<GradeMaster, List<FeeTypeMaster>> feeList =
                feeTypeMasters.parallelStream().collect(Collectors.groupingBy(w -> w.getGradeMaster()));
        for (Map.Entry m : feeList.entrySet()) {
            GradeMaster grade = (GradeMaster) m.getKey();
            if (StringUtils.equalsIgnoreCase(grade.getGradeStatus(), "Active")) {
                grade1.add(grade);
            }
        }
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade1, GradeMasterPojo.class);
        return gdPojoList;
    }

    public List<GradeMasterPojo> gradeMasterList2(String searchText, String checkboxForInActive, String userId) {
        List<GradeMaster> grade = new ArrayList<>();
        User user = bsUserRepository.findOne(Long.parseLong(userId));
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("true")) {
            grade = (List<GradeMaster>) bsGrademasterRepository.findAll();
        }
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("false")) {
//            grade = (List<GradeMaster>) bsGrademasterRepository.findAll();

            grade = bsGrademasterRepository.findByGradeStatus("Active");
            if (user.getUseraccount_id() == 1) {
                grade = bsGrademasterRepository.findByGradeStatus("Active");
            } else {
                grade = bsGrademasterRepository.findByGradeStatusAndUserId("Active", user);
            }
        }
        if (searchText != "") {
            String status = "Active";
            grade = bsGrademasterRepository.findByGradeNameIsStartingWithAndGradeStatusAndUserId(searchText, status, user);
        }
        if (searchText != "" && checkboxForInActive.equals("true")) {
            grade = bsGrademasterRepository.findByGradeNameIsStartingWith(searchText);
        }
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade, GradeMasterPojo.class);
        return gdPojoList;
    }

    public List<GradeMasterPojo> gradeMasterListBasedOnInactive() {
        List<GradeMaster> grade = new ArrayList<>();
        grade = bsGrademasterRepository.findByGradeStatus("InActive");
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade, GradeMasterPojo.class);
        return gdPojoList;
    }

    public List<FeeTypeMasterPojo> feeTypeMasterList() {
        List<FeeTypeMaster> feemaster = new ArrayList<>();
//        feemaster = (List<FeeTypeMaster>) bsFeeTypeMasterRepository.findAll();
        feemaster = bsFeeTypeMasterRepository.findByStatus("Active");

        List<FeeTypeMasterPojo> ftPojoList = ObjectMapperUtils.mapAll(feemaster, FeeTypeMasterPojo.class);
        return ftPojoList;
    }

    public List<FeeTypeMasterPojo> feeTypeMasterList2(String searchText, String checkboxForInActive) {
        List<FeeTypeMaster> feemaster = new ArrayList<>();
        GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeName(searchText);
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("true")) {
            feemaster = (List<FeeTypeMaster>) bsFeeTypeMasterRepository.findAll();
        }
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("false")) {
//            grade = (List<GradeMaster>) bsGrademasterRepository.findAll();

            feemaster = bsFeeTypeMasterRepository.findByStatus("Active");
        }
        if (searchText != "") {
            String status = "Active";
            if(grdmstrobj!=null){
                feemaster = bsFeeTypeMasterRepository.findByGradeMaster(grdmstrobj);
            }else {
                feemaster = bsFeeTypeMasterRepository.findByFeeTypeNameIsStartingWithAndStatus(searchText, status);
            }
        }
        if (searchText != "" && checkboxForInActive.equals("true")) {
            if(grdmstrobj!=null){
                feemaster = bsFeeTypeMasterRepository.findByGradeMaster(grdmstrobj);
            }else {
                feemaster = bsFeeTypeMasterRepository.findByFeeTypeNameIsStartingWith(searchText);
            }
        }

        List<FeeTypeMasterPojo> ftPojoList = ObjectMapperUtils.mapAll(feemaster, FeeTypeMasterPojo.class);
        for (FeeTypeMasterPojo feeTypeMasterPojo : ftPojoList) {
            feeTypeMasterPojo.setAcdId(feeTypeMasterPojo.getAcdyrmaster().getAcdyrId().intValue());
            feeTypeMasterPojo.setGradeId(feeTypeMasterPojo.getGradeMaster().getGradeId().intValue());
        }
        return ftPojoList;
    }

    public List<FeeTypeMasterPojo> feeListOfAcademicAndGrade(Long academicId, Long gradeId) {
        List<FeeTypeMaster> feemaster = new ArrayList<>();
        AcademicYearMaster academicYearMaster = bsAcademicYearMasterRepository.findByAcdyrId(academicId);
        GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(gradeId);
        feemaster = bsFeeTypeMasterRepository.findByAcdyrmasterAndGradeMasterAndStatus(academicYearMaster, gradeMaster, "Active");
        List<FeeTypeMasterPojo> ftPojoList = ObjectMapperUtils.mapAll(feemaster, FeeTypeMasterPojo.class);
        return ftPojoList;
    }

    //studentFeeList
    public List<StudentFeePojo> studentFeeList(String searchText, String grade, String student) {
        List<StudentFeePojo> studentPojoList = null;
        if (grade != "" && student == "") {
            GradeMaster gdobj = bsGrademasterRepository.findByGradeId(Long.parseLong(grade));
            List<StudentFee> studentfeeTypelist = (List<StudentFee>) bsStudentFeeRepository.findByGradeMaster(gdobj);
            if (studentfeeTypelist != null) {
                studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
            } else {
                studentPojoList = null;
            }
        } else if (student != "" && grade == "") {
            List<Student> stuobj = bsStudentRepository.findByStudentId(Long.parseLong(student));
            List<StudentFee> studentfeeTypelist = Collections.singletonList(bsStudentFeeRepository.findByStudent(stuobj.get(0)));
            studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
        } else if (student != "" && grade != "") {
            GradeMaster gdobj = bsGrademasterRepository.findByGradeId(Long.parseLong(grade));
            List<Student> stuobj = bsStudentRepository.findByStudentId(Long.parseLong(student));
            List<StudentFee> studentfeeTypelist = Collections.singletonList(bsStudentFeeRepository.findByStudentAndGradeMaster(stuobj.get(0), gdobj));
            studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
        } else {
            List<StudentFee> studentfeeTypelist = (List<StudentFee>) bsStudentFeeRepository.findAll();
            studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
        }
        for (StudentFeePojo pojolist : studentPojoList) {
            pojolist.setShowDetails(false);
            StudentFee studentobj = bsStudentFeeRepository.findByStudentFeeId(pojolist.getStudentFeeId());
            pojolist.setDueAmount(studentobj.getDueAmount());
            pojolist.setStatus(studentobj.getStudent().getStudentStatus());
            List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(studentobj);
            pojolist.setStudentFeeDetailsList(studentFeeDetails);

        }
        return studentPojoList;
    }

    public List<AcademicYearMasterPojo> getAcademicYearList() {
        List<AcademicYearMaster> academics = new ArrayList<>();
        String Status = "Active";
        academics = (List<AcademicYearMaster>) bsAcademicYearMasterRepository.findByStatus(Status);
        List<AcademicYearMasterPojo> acdPojoList = ObjectMapperUtils.mapAll(academics, AcademicYearMasterPojo.class);
        return acdPojoList;
    }


    public List<StudentFeeDetails> getStudentFeeDetails(String studentId) {
        List<StudentFeeDetails> studentFeeDetailslist = new ArrayList<>();
        List<Student> student = bsStudentRepository.findByStudentId(Long.parseLong(studentId));
        if (student.size() > 0) {
            Student studentobj = bsStudentRepository.findByStudentNameAndAdmissionFormNo(student.get(0).getStudentName(), student.get(0).getAdmissionFormNo());
            StudentFee studentFeeobj = bsStudentFeeRepository.findByStudent(studentobj);

            studentFeeDetailslist = (List<StudentFeeDetails>) bsStudentFeeDetailsRepository.findByStudentfee(studentFeeobj);

            List<StudentFeeDetails> acdPojoList = ObjectMapperUtils.mapAll(studentFeeDetailslist, StudentFeeDetails.class);
            return acdPojoList;
        } else {
            return null;
        }

    }

    public List<AcademicYearMasterPojo> getAcademicYear2List(String searchText, String checkboxStatus) {
        List<AcademicYearMaster> academics = new ArrayList<>();
        if (StringUtils.isBlank(searchText) && checkboxStatus.equalsIgnoreCase("true")) {
            academics = (List<AcademicYearMaster>) bsAcademicYearMasterRepository.findAll();
        }
        if (StringUtils.isBlank(searchText) && checkboxStatus.equalsIgnoreCase("false")) {
            String status = "Active";
            academics = (List<AcademicYearMaster>) bsAcademicYearMasterRepository.findByStatus(status);
        }

        else if(!StringUtils.isEmpty(searchText)) {
            academics = (List<AcademicYearMaster>) bsAcademicYearMasterRepository.findByAcdyrNameIsStartingWith(searchText);
        }
        List<AcademicYearMasterPojo> acdPojoList = ObjectMapperUtils.mapAll(academics, AcademicYearMasterPojo.class);
        return acdPojoList;

    }

    public List<StudentPojo> getStudentList(String searchText, String grade, String student, String checkboxStatusForStudent) {
        List<StudentPojo> studPojoList = null;
        List<Student> studList = new ArrayList<>();
        if (searchText != "") {
            studList = bsStudentRepository.findStudentByStudentNameIsLike("%" + searchText + "%");
        }
        if (grade != "" && student == "") {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(grade));
            studList = bsStudentRepository.findByGradeMaster(grdmstrobj);
        } else if (student != "" && grade == "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student != "" && grade != "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("false")) {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("true")) {
            String status = "InActive";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        }
        studPojoList = ObjectMapperUtils.mapAll(studList, StudentPojo.class);
        for (StudentPojo studentPojo : studPojoList) {
            studentPojo.setAcdYearId(studentPojo.getAcademicYearMaster().getAcdyrId());
            studentPojo.setGradeId(studentPojo.getGradeMaster().getGradeId());
        }
        return studPojoList;
    }

    //    getStudentBasedOnGradeList
    public List<StudentPojo> getStudentBasedOnGradeList(String searchText) {
        List<StudentPojo> studPojoList = null;
        if (searchText == "") {
            List<Student> studList = (List<Student>) bsStudentRepository.findAll();
            studPojoList = ObjectMapperUtils.mapAll(studList, StudentPojo.class);
        } else {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(searchText));
            List<Student> studList = bsStudentRepository.findByGradeMaster(grdmstrobj);
            studPojoList = ObjectMapperUtils.mapAll(studList, StudentPojo.class);
        }
        return studPojoList;
    }

    public List<BsUserPojo> sassUserList() {
        List<User> users = (List<User>) bsUserRepository.findAll();
        List<BsUserPojo> bsUserPojoList = ObjectMapperUtils.mapAll(users, BsUserPojo.class);
        return bsUserPojoList;
    }

    public GradeMaster SaveGradeMaster(GradeMasterPojo details) throws JSONException, IOException {
        GradeMaster gradeMaster = null;
        //findByGradeNameAndGradeIdNotIn
        User user = bsUserRepository.findOne(Long.parseLong(details.getUserId()));//getting userObj
        if (details.getGradeId() != null) {
            gradeMaster = bsGrademasterRepository.findByGradeNameAndGradeIdNotIn(details.getGradeName(), details.getGradeId());
            if (gradeMaster == null) {
                gradeMaster = BsGradeMapper.mapPojoToEntity(details);
                gradeMaster.setUserId(user);//userId saveing in gradeMaster Table
                bsGrademasterRepository.save(gradeMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(gradeMaster);
                masterPojo.setMasterFlag("GradeMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                //  String statusCode = pusherService.SavePusher(JsonInString, "", "GradeMaster");
                return gradeMaster;
            } else {
                return null;
            }
        } else {
            gradeMaster = bsGrademasterRepository.findByGradeName(details.getGradeName());
            if (gradeMaster == null) {
                gradeMaster = BsGradeMapper.mapPojoToEntity(details);
                gradeMaster.setUserId(user);//userId saveing in gradeMaster Table
                bsGrademasterRepository.save(gradeMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(gradeMaster);
                masterPojo.setMasterFlag("GradeMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                CartMaster cartMaster = CartMasterRepository.findOne(1l);
                String cartID = "";
                if (cartMaster!=null){ cartID = cartMaster.getHiConnectCompanyRegNo();}
                String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "AddGrade", "AddMaster");
                String JsonBrainyStudentString = gson.toJson(details);
                String branchCode = "";
                if(!user.equals(null)) branchCode = user.getBranchCode();
                String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString,branchCode,branchCode,"AddGrade", "AddMaster");
                return gradeMaster;
            } else {
                return null;
            }
        }
    }

    public Country saveCountry(CountryDTO countryDTO) {
        Country countries = new Country();
        if (countryDTO.getCountryId() != null) {
            countries = countryRepository.findByCountryNameAndCountryIdNotIn(countryDTO.getCountryName(), countryDTO.getCountryId());
        } else {
            countries = countryRepository.findByCountryName(countryDTO.getCountryName());
        }
        if (countries == null) {
            Country country = BsUserMapper.mapCountryPojoToEntity(countryDTO);
            countryRepository.save(country);
            return country;
        } else {
            return null;
        }

    }

    public List<StateDTO> getStateListBasedOnCountry(String country) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "stateName"));
        List<State> designations = posStateRepository.findAllByCountryNameAndStatus(country, "Active", sort);
        List<StateDTO> stateDTOS = BsUserMapper.mapStateEntityToPojo(designations);
        return stateDTOS;
    }

    public List<StateDTO> stateList() {
        List<State> state = (List<State>) posStateRepository.findAll();
        List<StateDTO> statePojos=new ArrayList<>();
        for(State state1:state){
            StateDTO stateDTO=new StateDTO();
            stateDTO.setId(state1.getId());
            stateDTO.setStateName(state1.getStateName());
            stateDTO.setStatus(state1.getStatus());
            stateDTO.setStateCode(state1.getStateCode());
            stateDTO.setVehicleSeries(state1.getVehicleSeries());
            if(state1.getCountryName()!=null)
                stateDTO.setCountry(state1.getCountryName());
            statePojos.add(stateDTO);
        }
        return statePojos;
    }

    public List<CityDTO> cityList() {
        List<City> city = (List<City>) cityRepository.findAll();
        List<CityDTO> cityDTOS=new ArrayList<>();
        for(City city1:city){
            CityDTO cityDTO=new CityDTO();
            cityDTO.setId(city1.getId());
            cityDTO.setCityName(city1.getCityName());
            cityDTO.setStatus(city1.getStatus());
            cityDTO.setCityCode(city1.getCityCode());
//            cityDTO.setC(state1.getVehicleSeries());
            if(city1.getCountryName()!=null)
                cityDTO.setCountryName(city1.getCountryName());
            cityDTOS.add(cityDTO);
        }
        return cityDTOS;
    }
    public BasePojo getStateList(String status, BasePojo basePojo, String searchText) {
        List<State> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<State> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posStateRepository.findAllByStateCodeContainingOrStateNameContainingAndStatus(searchText, searchText, status);
            } else {
                list1 = posStateRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        State state = new State();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            state = posStateRepository.findFirstByStateCodeContainingOrStateNameContainingAndStatus(searchText, searchText, status, sort);
            list = posStateRepository.findAllByStateCodeContainingOrStateNameContainingAndStatus(searchText, searchText, status, pageable);
        } else {
            state = posStateRepository.findFirstByStatus(status, sort);
            list = posStateRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(state)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<StateDTO> stateList = BsUserMapper.mapStateEntityToPojo(list);
        basePojo = calculatePagination(basePojo, stateList.size());
        basePojo.setList(stateList);
        return basePojo;
    }

    public State saveState(StateDTO stateDTO) {
        State state1 = new State();
        if (stateDTO.getId() != null) {
            state1 = posStateRepository.findByStateNameAndIdNotIn(stateDTO.getStateName(), stateDTO.getId());
        } else {
            state1 = posStateRepository.findByStateName(stateDTO.getStateName());
        }
        if (state1 == null) {
            State state = BsUserMapper.mapStatePojoToEntity(stateDTO);
            Country country = countryRepository.findByCountryName(stateDTO.getCountry());
            if (country != null)
                state.setCountryName(country.getCountryName());
            posStateRepository.save(state);
            return state;
        } else {
            return null;
        }
    }

    public City saveCity(CityDTO cityDTO) {
        City city1 = new City();
        if (cityDTO.getId() != null) {
            city1 = cityRepository.findByCityNameAndCountryNameAndStateNameAndIdNotIn(cityDTO.getCityName(), cityDTO.getCountryName(), cityDTO.getState(), cityDTO.getId());
        } else {
            city1 = cityRepository.findByCityNameAndCountryNameAndStateName(cityDTO.getCityName(), cityDTO.getCountryName(), cityDTO.getState());
        }
        if (city1 == null) {
            City city = BsUserMapper.mapCityPojoToEntity(cityDTO);
            State state = posStateRepository.findByStateName(cityDTO.getState());
//            city.setStateName(state.getStateName());
            cityRepository.save(city);
            return city;
        } else {
            return null;
        }
    }


    public CityDTO editCity(String cityName) {
        City city = cityRepository.findOne(Long.valueOf(cityName));
        List<City> cityList = new ArrayList<>();
        cityList.add(city);
        CityDTO cityDTO = BsUserMapper.mapCityEntityToPojo(cityList).get(0);
        return cityDTO;
    }

    public void deleteState(String stateName) {
        posStateRepository.delete(posStateRepository.findById(Long.valueOf(stateName)));
    }


    public List<CityDTO> getStateCity(String stateName) {
        List<City> cities = cityRepository.findAllByStateName(stateName);
        List<CityDTO> cityList = BsUserMapper.mapCityEntityToPojo(cities);
        return cityList;
    }

    public List<CityDTO> getCityListBasedOnState(String state) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "cityName"));
        List<City> designations = cityRepository.findAllByStateName(state, sort);
        List<CityDTO> cityDTOS = BsUserMapper.mapCityEntityToPojo(designations);
        return cityDTOS;
    }

    public BasePojo getCityList(String status, BasePojo basePojo, String searchText) {
        List<City> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<City> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = cityRepository.findAllByCityCodeContainingOrCityNameContainingAndStatus(searchText, searchText, status);
            } else {
                list1 = cityRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        City city = new City();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            city = cityRepository.findFirstByCityCodeContainingOrStateNameContainingAndStatus(searchText, searchText, status, sort);
            list = cityRepository.findAllByCityCodeContainingOrCityNameContainingAndStatus(searchText, searchText, status, pageable);
        } else {
            city = cityRepository.findFirstByStatus(status, sort);
            list = cityRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(city)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<CityDTO> cityList = BsUserMapper.mapCityEntityToPojo(list);
        basePojo = calculatePagination(basePojo, cityList.size());
        basePojo.setList(cityList);
        return basePojo;
    }

    public List<StateDTO> getCountryState(String countryName) {
        Sort sort = new Sort( new Sort.Order( Sort.Direction.ASC,"stateName" ) );
        List<State> states = posStateRepository.findAllByCountryNameAndStatus(countryName,"Active",sort);
        List<StateDTO> stateList = BsUserMapper.mapStateEntityToPojo(states);
        return stateList;
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

    public StateDTO editState(String stateName) {
        State state = posStateRepository.findByStateName(stateName);
        List<State> states = new ArrayList<>();
        states.add(state);
        StateDTO stateDTO = BsUserMapper.mapStateEntityToPojo(states).get(0);
        return stateDTO;
    }


    public List<CountryDTO> getCountryList(String status) {
        List<Country> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            list = countryRepository.findAll();
        } else {
            list = countryRepository.findAllByStatus(status);
        }
        List<CountryDTO> country = BsUserMapper.mapCountryEntityToPojo(list);
        return country;
    }

    public void deleteCountry(String countryName) {
        countryRepository.delete(countryRepository.findByCountryName(countryName));
    }

    public CountryDTO editCountry(String countryName) {
        Country country = countryRepository.findByCountryName(countryName);
        List<Country> countries = new ArrayList<>();
        countries.add(country);
        CountryDTO countryDTO = BsUserMapper.mapCountryEntityToPojo(countries).get(0);
        return countryDTO;
    }


    public BasePojo calculatePagination(BasePojo basePojo, int size) {
        if (basePojo.isLastPage() == true) {
            basePojo.setFirstPage(false);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(false);
        } else if (basePojo.isFirstPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(true);
            if (basePojo.isStatus() == true) {
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        } else if (basePojo.isNextPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setPrevPage(false);
            basePojo.setNextPage(false);
            if (basePojo.isStatus() == true) {
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        } else if (basePojo.isPrevPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(false);
            if (basePojo.isStatus() == true) {
                basePojo.setPrevPage(true);
                basePojo.setFirstPage(true);
            }
        }
        if (size == 0) {
            basePojo.setLastPage(true);
            basePojo.setFirstPage(true);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(true);
        }
        return basePojo;
    }

    public BasePojo getPaginatedCountryList(String status, BasePojo basePojo, String searchText) {
        List<Country> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "countryId"));
        if (basePojo.isLastPage() == true) {
            List<Country> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = countryRepository.findAllByCountryNameContaining(searchText);
            } else {
                list1 = countryRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        Country country = new Country();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "countryId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            country = countryRepository.findFirstByCountryNameContainingAndStatus(searchText, sort, status);
            list = countryRepository.findAllByCountryNameContainingAndStatus(searchText, pageable, status);
        } else {
            country = countryRepository.findFirstByStatus(status, sort);
            list = countryRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(country)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<CountryDTO> countryDtoList = BsUserMapper.mapCountryEntityToPojo(list);
        basePojo = calculatePagination(basePojo, countryDtoList.size());
        basePojo.setList(countryDtoList);
        return basePojo;
    }

    public BasePojo getPaginatedFormSetUpList(BasePojo basePojo, String searchText) {
        List<FormSetUp> list = new ArrayList<>();
        basePojo.setPageSize(5);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"typename"));
        if(basePojo.isLastPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"typename"));
        }
        FormSetUp formSetUp=new FormSetUp();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),5,sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"typename"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            formSetUp=posFormSetupRepository.findFirstByTypenameContaining(searchText,sort);
            list = posFormSetupRepository.findAllByTypenameContaining(searchText,pageable);
        } else {
            formSetUp=posFormSetupRepository.findFirstBy(sort);
            list = posFormSetupRepository.findAllBy(pageable);
        }
        if(list.contains(formSetUp)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<FormsetupDTO> formsetupDTOList = BsUserMapper.mapFormSetupEntityToPojo(list);
        basePojo=calculatePagination(basePojo,formsetupDTOList.size());
        basePojo.setList(formsetupDTOList);
        return basePojo;
    }

    public SchoolBranchDetails SaveSchoolBranchDetails(SchoolBranchDetailsPojo details) throws IOException, JSONException {
        SchoolBranchDetails schoolBranchDetails = null;
        List<SchoolBranchDetails> list = bsSchoolBranchDetailsRepository.findAll();
        if (list.size() > 0) {
            details.setSchoolBranchId(list.get(0).getSchoolBranchId());
        }
        schoolBranchDetails = BsSchoolBranchDetailsMapper.mapPojoToEntity(details);
        bsSchoolBranchDetailsRepository.save(schoolBranchDetails);
        MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(details);
        masterPojo.setMasterFlag("FeeTypeMaster");
        Gson gson = new Gson();
        String JsonInString = gson.toJson(masterPojo);
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if (cartMaster!=null){ cartID = cartMaster.getHiConnectCompanyRegNo();}
        String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "AddLocation", "AddMaster");
        String JsonBrainyStudentString = gson.toJson(details);
        User user = bsUserRepository.findOne(1l);
        String branchCode = "";
        if(!user.equals(null)) branchCode = user.getBranchCode();
        String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString,branchCode,branchCode,"AddBranch","AddMaster");
        return schoolBranchDetails;
    }

    public FeeTypeMaster SaveFeeTypeMaster(FeeTypeMasterPojo details) throws JSONException, IOException {
        GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeName((details.getGradeName()));
        AcademicYearMaster acdobj = bsAcademicYearMasterRepository.findByAcdyrName(details.getAcdyrName());
        FeeTypeMaster feeTypeMaster = null;
        if (details.getFeeTypeId() != null) {
//            feeTypeMaster = bsFeeTypeMasterRepository.findByFeeTypeNameAndFeeTypeIdIsNotInAndGradeMasterContaining(details.getFeeTypeName(),details.getFeeTypeId(),grdmstrobj);
            if (feeTypeMaster == null) {
                feeTypeMaster = BsFeeTypeMasterMapper.mapPojoToEntity(details);
                feeTypeMaster.setGradeMaster(grdmstrobj);
                feeTypeMaster.setAcdyrmaster(acdobj);
                bsFeeTypeMasterRepository.save(feeTypeMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(feeTypeMaster);
                masterPojo.setMasterFlag("FeeTypeMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                //   String statusCode = pusherService.SavePusher(JsonInString, "", "FeeTypeMaster");
                return feeTypeMaster;
            } else {
                return null;
            }

        } else {
//            feeTypeMaster = bsFeeTypeMasterRepository.findByFeeTypeNameAndGradeMasterContaining(details.getFeeTypeName(),grdmstrobj);
            if (feeTypeMaster == null) {
                feeTypeMaster = BsFeeTypeMasterMapper.mapPojoToEntity(details);
                feeTypeMaster.setGradeMaster(grdmstrobj);
                feeTypeMaster.setAcdyrmaster(acdobj);

                bsFeeTypeMasterRepository.save(feeTypeMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(feeTypeMaster);
                masterPojo.setMasterFlag("FeeTypeMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                CartMaster cartMaster = CartMasterRepository.findOne(1l);
                String cartID = "";
                if (cartMaster!=null){
                    cartID = cartMaster.getHiConnectCompanyRegNo();
                }
                String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "AddItem", "AddMaster");
                String JsonBrainyStudentString = gson.toJson(details);
                User user = bsUserRepository.findOne(1l);
                String branchCode = "";
                if(!user.equals(null)) branchCode = user.getBranchCode();
                String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString,branchCode,branchCode,"AddFeeType","AddMaster");
                //   String statusCode = pusherService.SavePusher(JsonInString, "", "AddItem","FeeTypeMaster");

                return feeTypeMaster;
            } else {
                return null;
            }
        }

//
//            FeeTypeMaster feeTypeMasterforgettingobj = bsFeeTypeMasterRepository.findByFeeTypeName(details.getFeeTypeName());
//            if (feeTypeMasterforgettingobj == null && details.getFeeTypeId()==null) {
//                FeeTypeMaster feeTypeMaster = BsFeeTypeMasterMapper.mapPojoToEntity(details);
//                feeTypeMaster.setGradeMaster(grdmstrobj);
//                feeTypeMaster.setAcdyrmaster(acdobj);
//                if (details.getFeeTypeId() != null) {
//                    details.setFeeTypeId(details.getFeeTypeId());
//                }
//                bsFeeTypeMasterRepository.save(feeTypeMaster);
//                return feeTypeMaster;
//
//        } else if (feeTypeMasterforgettingobj != null && details.getFeeTypeId() != null) {
//            FeeTypeMaster feeTypeMaster = BsFeeTypeMasterMapper.mapPojoToEntity(details);
//            feeTypeMaster.setGradeMaster(grdmstrobj);
//            feeTypeMaster.setAcdyrmaster(acdobj);
//            if (details.getFeeTypeId() != null) {
//                details.setFeeTypeId(details.getFeeTypeId());
//            }
//            bsFeeTypeMasterRepository.save(feeTypeMaster);
//            return feeTypeMaster;
//        } else {
//            return null;
//        }

    }


    public FormSetUp saveFormSetup(FormsetupDTO formsetupDTO) {
        FormSetUp formSetUps =new FormSetUp();
        if(formsetupDTO.getFormsetupId()!=null){
            formSetUps=bsFormSetUpRepository.findAllByTypenameAndFormsetupIdNotIn(formsetupDTO.getTypename(),formsetupDTO.getFormsetupId());

        }else{
            formSetUps=bsFormSetUpRepository.findAllByTypename(formsetupDTO.getTypename());
        }
        if(formSetUps==null){
            FormSetUp formSetUp = BsUserMapper.mapFormSetupPojoToEntity(formsetupDTO);
            bsFormSetUpRepository.save(formSetUp);
            return formSetUp;
        }else{
            return null;
        }
    }

    public List<EnquiryFormDTO> getEnquiry() {
        List<EnquiryForm> enquiryForms = new ArrayList<>();
        enquiryForms =bsEnquiryRepository.findAll();
        List<EnquiryFormDTO> enquiryFormDTOList = BsEnquiryMapper.mapEntityToPojo(enquiryForms);
        return enquiryFormDTOList;
    }
    public String getFormSetUpNo() {
        FormSetUp formSetUp = posFormSetupRepository.findOne(1L);
        int incValue = Integer.parseInt(formSetUp.getNextref());
        String formsetupNo =getNextRefInvoice(formSetUp.getTypeprefix(),String.format("%05d", ++incValue));
        return formsetupNo;
    }

    public FormsetupDTO editFormsetupMethod(String formsetupName) {
        FormSetUp formSetUp = bsFormSetUpRepository.findAllByTypename(formsetupName);
        List<FormSetUp> formSetUpList = new ArrayList<>();
        formSetUpList.add(formSetUp);
        FormsetupDTO formsetupDTO = BsUserMapper.mapFormSetupEntityToPojo(formSetUpList).get(0);
        return formsetupDTO;
    }

    public AcademicYearMaster SaveAcademicYearMaster(AcademicYearMasterPojo details) throws JSONException, IOException {
        AcademicYearMaster yearMaster = null;
        if (details.getAcdyrId() != null) {
            yearMaster = bsAcademicYearMasterRepository.findByAcdyrNameAndAcdyrIdIsNotIn(details.getAcdyrName(), details.getAcdyrId());
            if (yearMaster == null) {
                yearMaster = BsAcademicMapper.mapPojoToEntity(details);
                bsAcademicYearMasterRepository.save(yearMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(yearMaster);
                masterPojo.setMasterFlag("AcademicYearMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                //  String statusCode = pusherService.SavePusher(JsonInString, "", "AcademicYearMaster");
                return yearMaster;
            } else {
                return null;
            }

        } else {
            yearMaster = bsAcademicYearMasterRepository.findByAcdyrName(details.getAcdyrName());
            if (yearMaster == null) {
                yearMaster = BsAcademicMapper.mapPojoToEntity(details);
                bsAcademicYearMasterRepository.save(yearMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(yearMaster);
                masterPojo.setMasterFlag("AcademicYearMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                //        String statusCode = pusherService.SavePusher(JsonInString, "", "AcademicYearMaster");
                CartMaster cartMaster = CartMasterRepository.findOne(1l);
                String cartID = "";
                if (cartMaster!=null){ cartID = cartMaster.getHiConnectCompanyRegNo();}
                String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "AddProject", "AddMaster");
                String JsonBrainyStudentString = gson.toJson(details);
                User user = bsUserRepository.findOne(1l);
                String branchCode = "";
                if(!user.equals(null)) branchCode = user.getBranchCode();
                String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString,branchCode,branchCode,"AddAcademicYear","AddMaster");
                return yearMaster;
            } else {
                return null;
            }
        }
    }

    public StudentFeeDto getStudentFeeDetailsList(Long studentId, String type) {
        StudentFee studentFee = bsStudentFeeRepository.findByStudent(bsStudentRepository.findByStudentId(studentId).get(0));
        StudentFeeDto studentFeeDto = new StudentFeeDto();
        studentFeeDto.setStudentId(studentFee.getStudent().getStudentId());
        studentFeeDto.setStudentFeeId(studentFee.getStudentFeeId());
        studentFeeDto.setStudentName(studentFee.getStudent().getStudentName());
        studentFeeDto.setFatherName(studentFee.getStudent().getFatherName());
        studentFeeDto.setMotherName(studentFee.getStudent().getMotherName());
        studentFeeDto.setAcademicYear(studentFee.getAcademicYearMaster().getAcdyrName());
        studentFeeDto.setAcademicYearId(studentFee.getAcademicYearMaster().getAcdyrId());
        studentFeeDto.setStudentProfileId(studentFee.getStudent().getStudentProfileId());
        studentFeeDto.setGradeId(studentFee.getGradeMaster().getGradeId());
        studentFeeDto.setGradeName(studentFee.getGradeMaster().getGradeName());
        studentFeeDto.setTotalPaid(studentFee.getPaidAmount());
        List<StudentFeeDetailsPojo> studentFeeDetailsPojos = new ArrayList<>();
        List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(studentFee);
        List<Long> ids = new ArrayList<>();
        for (StudentFeeDetails studentFeeDetails1 : studentFeeDetails) {
            ids.add(studentFeeDetails1.getFeetypemaster().getFeeTypeId());
            if (!StringUtils.equalsIgnoreCase(type, "Fee") || (StringUtils.equalsIgnoreCase(type, "Fee") && studentFeeDetails1.getCheckboxstatus() == true)) {
                List<Installments> installments = bsInstallmentsRepository.findByStudentFeeAndFeeTypeMaster(studentFee, studentFeeDetails1.getFeetypemaster());
                List<InstallmentsPojo> list = new ArrayList<>();
                for (Installments installments1 : installments) {
                    InstallmentsPojo installmentsPojo = new InstallmentsPojo();
                    installmentsPojo.setInstallmentsId(installments1.getInstallmentsId());
                    if (installments1.getInstallmentsAmount() != null) {
                        installmentsPojo.setInstallmentsAmount(installments1.getInstallmentsAmount() - installments1.getPaidAmt());
                    } else {
                        installmentsPojo.setInstallmentsAmount(studentFeeDetails1.getPendingFee());
                    }
                    installmentsPojo.setPayingAmt(installmentsPojo.getInstallmentsAmount());
                    installmentsPojo.setPaidAmt(installments1.getPaidAmt());
                    installmentsPojo.setDueAmt(installments1.getInstallmentsAmount());
                    if (installmentsPojo.getInstallmentsAmount() != 0)
                        installmentsPojo.setCheckBox(true);
                    else
                        installmentsPojo.setCheckBox(false);
                    if (installments1.getDueDate() != null)
                        installmentsPojo.setDueDate(installments1.getDueDate());
                    installmentsPojo.setStatus(installments1.getStatus());
                    list.add(installmentsPojo);
                }
                StudentFeeDetailsPojo studentFeeDetailsPojo = new StudentFeeDetailsPojo();
                studentFeeDetailsPojo.setInstallmentsPojos(list);
                FeeTypeMaster feeTypeMaster = studentFeeDetails1.getFeetypemaster();
                if (StringUtils.equalsIgnoreCase(feeTypeMaster.getStatus(), "InActive")) {
                    if (studentFeeDetails1.getCheckboxstatus() == false) {
                        studentFeeDetailsPojo.setFeeTypeStatus(false);
                    } else {
                        studentFeeDetailsPojo.setFeeTypeStatus(true);
                    }
                } else {
                    studentFeeDetailsPojo.setFeeTypeStatus(true);
                }
                studentFeeDetailsPojo.setFeeTypeAmount(studentFeeDetails1.getFeeTypeAmount());
                studentFeeDetailsPojo.setStudentFeeDetailsId(studentFeeDetails1.getStudentFeeDetailsId());
                studentFeeDetailsPojo.setNoOfInstallments(studentFeeDetails1.getNoOfInstallments());
                studentFeeDetailsPojo.setValue(studentFeeDetails1.getFeetypemaster().getValue());
                studentFeeDetailsPojo.setFeeTypeName(studentFeeDetails1.getFeeTypeName());
                studentFeeDetailsPojo.setFeeTypeId(studentFeeDetails1.getFeetypemaster().getFeeTypeId());
                studentFeeDetailsPojo.setDiscountRemarks(studentFeeDetails1.getDiscountRemarks());
                studentFeeDetailsPojo.setDiscount(studentFeeDetails1.getDiscount());
                if (studentFeeDetails1.getPaidAmt() != null)
                    studentFeeDetailsPojo.setDueAmount(studentFeeDetails1.getPayable() - studentFeeDetails1.getPaidAmt());
                else
                    studentFeeDetailsPojo.setDueAmount(studentFeeDetails1.getPayable());
                studentFeeDetailsPojo.setPayable(studentFeeDetails1.getPayable());
                studentFeeDetailsPojo.setPaidAmount(studentFeeDetails1.getPaidAmt());
                studentFeeDetailsPojo.setDueDate(studentFeeDetails1.getDueDate());
                studentFeeDetailsPojo.setCheckBox(studentFeeDetails1.getCheckboxstatus());
                studentFeeDetailsPojos.add(studentFeeDetailsPojo);
            }
        }
        if (StringUtils.equalsIgnoreCase(type, "Student")) {
            List<FeeTypeMaster> feeTypeMasters = bsFeeTypeMasterRepository.findByGradeMasterAndAcdyrmasterAndFeeTypeIdNotInAndStatus(studentFee.getGradeMaster(), studentFee.getAcademicYearMaster(), ids, "Active");
            for (FeeTypeMaster feeTypeMaster : feeTypeMasters) {
                StudentFeeDetailsPojo studentFeeDetailsPojo = new StudentFeeDetailsPojo();
                InstallmentsPojo installmentsPojo = new InstallmentsPojo();
                installmentsPojo.setPaidAmt(0.00);
                installmentsPojo.setDueAmt(feeTypeMaster.getFeeAmount());
                installmentsPojo.setInstallmentsAmount(feeTypeMaster.getFeeAmount());
                installmentsPojo.setPayingAmt(feeTypeMaster.getFeeAmount());
                installmentsPojo.setFeeTypeMaster(feeTypeMaster);
                List<InstallmentsPojo> list = new ArrayList<>();
                list.add(installmentsPojo);
                studentFeeDetailsPojo.setFeeTypeStatus(true);
                studentFeeDetailsPojo.setInstallmentsPojos(list);
                studentFeeDetailsPojo.setFeeTypeAmount(feeTypeMaster.getFeeAmount());
                studentFeeDetailsPojo.setNoOfInstallments(1);
                studentFeeDetailsPojo.setValue(feeTypeMaster.getValue());
                studentFeeDetailsPojo.setFeeTypeName(feeTypeMaster.getFeeTypeName());
                studentFeeDetailsPojo.setFeeTypeId(feeTypeMaster.getFeeTypeId());
                studentFeeDetailsPojo.setDiscount(0.00);
                studentFeeDetailsPojo.setDueAmount(feeTypeMaster.getFeeAmount());
                studentFeeDetailsPojo.setPayable(feeTypeMaster.getFeeAmount());
                studentFeeDetailsPojo.setPaidAmount(0.00);
                studentFeeDetailsPojo.setCheckBox(false);
                studentFeeDetailsPojos.add(studentFeeDetailsPojo);
            }
        }
        SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
        if (schoolBranchDetails != null) {
            studentFeeDto.setReceiptNo(String.valueOf(schoolBranchDetails.getReceiptNo() + 1));
        }
        studentFeeDto.setStudentFeeDetailsPojoList(studentFeeDetailsPojos);
        return studentFeeDto;
    }

    public List<StudentFee> retrieveStudentFee(ReportPojo filter, String type) {
        List<StudentFee> employees = bsStudentFeeRepository.findAll(new Specification<StudentFee>() {
            @Override
            public Predicate toPredicate(Root<StudentFee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (filter.getAcademicYearMaster() != null) {
                    Predicate predicates = cb.equal(root.get("academicYearMaster"), filter.getAcademicYearMaster());
                    predicatesList.add(predicates);
                }
                if (filter.getGradeMasters() != null)
                    if (filter.getGradeMasters().size() > 0) {
                        Predicate predicates = cb.in(root.get("gradeMaster")).value(filter.getGradeMasters());
                        predicatesList.add(predicates);
                    }
                if (filter.getStudentId() != null) {
                    Predicate predicates = cb.equal(root.get("student").get("studentId"), filter.getStudentId());
                    predicatesList.add(predicates);
                }
                if (StringUtils.equalsIgnoreCase(type, "Due")) {
                    Predicate predicates = cb.equal(root.get("student").get("studentStatus"), "Active");
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        return employees;
    }

    public List<Installments> retrieveInstalments(ReportPojo filter) {
        List<StudentFee> list = retrieveStudentFee(filter, "Due");
        if (list.size() > 0) {
            List<Installments> employee = bsInstallmentsRepository.findAll(new Specification<Installments>() {
                @Override
                public Predicate toPredicate(Root<Installments> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicatesList = new ArrayList<>();
                    if (list.size() > 0) {
                        Predicate predicates = cb.in(root.get("studentFee")).value(list);
                        predicatesList.add(predicates);
                    }
                    if (filter.getFromDate() != null && filter.getToDate() != null) {
                        Predicate predicates = cb.between(root.get("dueDate"), filter.getFromDate(), filter.getToDate());
                        predicatesList.add(predicates);
                    }
                    return cb.and(predicatesList.toArray(new Predicate[0]));
                }
            });
            return employee;
        }
        return null;
    }

    public List<StudentFeePojo> getStudentDueList(ReportPojo reportPojo) {
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        List<StudentFeePojo> studentPojoList = new ArrayList<>();
        List<StudentFee> list = retrieveStudentFee(reportPojo, "Due");
        studentPojoList = ObjectMapperUtils.mapAll(list, StudentFeePojo.class);
        for (StudentFeePojo pojolist : studentPojoList) {
            pojolist.setShowDetails(false);
            StudentFee studentobj = bsStudentFeeRepository.findByStudentFeeId(pojolist.getStudentFeeId());
            reportPojo.setStudentId(studentobj.getStudent().getStudentId());
            List<Installments> installmentsList = retrieveInstalments(reportPojo);
            pojolist.setDueAmount(0.00);
            for (Installments installments : installmentsList) {
                StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemasterAndStudentfee(installments.getFeeTypeMaster(), installments.getStudentFee());
                if (studentFeeDetails.getCheckboxstatus() == true) {
                    pojolist.setDueAmount(pojolist.getDueAmount() + (installments.getInstallmentsAmount() - installments.getPaidAmt()));
                }
            }
            List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(studentobj);
            pojolist.setStudentFeeDetailsList(studentFeeDetails);

        }
        return studentPojoList;
    }

    public List<FeeTypeMasterPojo> getReportDetails(ReportPojo reportPojo) {
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        List<Installments> list = retrieveInstalments(reportPojo);
        List<FeeTypeMasterPojo> installmentsPojoList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int i = 1;
        for (Installments installments : list) {
            StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemasterAndStudentfee(installments.getFeeTypeMaster(), installments.getStudentFee());
            if (studentFeeDetails.getCheckboxstatus() == true) {
                FeeTypeMasterPojo feeTypeMasterPojo = new FeeTypeMasterPojo();
                if (list.size() > 0) {
                    feeTypeMasterPojo.setFeeTypeName(installments.getFeeTypeName() + "\t" + "Inst " + i++);
                } else {
                    feeTypeMasterPojo.setFeeTypeName(installments.getFeeTypeName());
                }
                feeTypeMasterPojo.setPaidAmt(installments.getPaidAmt());
                feeTypeMasterPojo.setDueAmt(installments.getInstallmentsAmount() - installments.getPaidAmt());
                feeTypeMasterPojo.setDueDate(dateFormat.format(installments.getDueDate()).toString());
//                if(feeTypeMasterPojo.getDueAmt()>0){
                installmentsPojoList.add(feeTypeMasterPojo);
//                }
            }
        }
        return installmentsPojoList;
    }

    public List<FeeReceipt> retrieveFeeCollected(ReportPojo filter) {
        List<StudentFee> list = retrieveStudentFee(filter, "Collect");
        if (list.size() > 0) {
            List<FeeReceipt> feeReceipts = bsFeeReceiptRepository.findAll(new Specification<FeeReceipt>() {
                @Override
                public Predicate toPredicate(Root<FeeReceipt> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicatesList = new ArrayList<>();
                    if (list.size() > 0) {
                        Predicate predicates = cb.in(root.get("studentFee")).value(list);
                        predicatesList.add(predicates);
                    }
                    if (filter.getFromDate() != null && filter.getToDate() != null) {
                        Predicate predicates = cb.between(root.get("receiptDate"), filter.getFromDate(), filter.getToDate());
                        predicatesList.add(predicates);
                    }
                    return cb.and(predicatesList.toArray(new Predicate[0]));
                }
            });
            return feeReceipts;
        }
        return null;
    }

    public List<FeeReceiptPojo> getReportCollected(ReportPojo reportPojo) {
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        List<FeeReceiptPojo> feeReceiptPojoArrayList = new ArrayList<>();
        List<FeeReceipt> list = retrieveFeeCollected(reportPojo);
        if (list != null) {
            Map<Long, List<FeeReceipt>> outputlist =
                    list.parallelStream().collect(Collectors.groupingBy(w -> w.getStudentFee().getStudentFeeId()));
            for (Map.Entry receipt : outputlist.entrySet()) {
                StudentFee fee = bsStudentFeeRepository.findOne(Long.parseLong(receipt.getKey().toString()));
                FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
                for (FeeReceipt feeReceipt : (List<FeeReceipt>) receipt.getValue()) {
                    feeReceiptPojo.setStudentName(fee.getStudentName());
                    feeReceiptPojo.setStudentProfileId(fee.getStudent().getStudentProfileId());
                    feeReceiptPojo.setCashAmt(feeReceipt.getCashAmt() + feeReceiptPojo.getCashAmt());
                    feeReceiptPojo.setCardAmt(feeReceipt.getCardAmt() + feeReceiptPojo.getCardAmt());
                    if (StringUtils.equalsIgnoreCase(feeReceipt.getPaymentMode(), "Online")) {
                        feeReceiptPojo.setOnlineAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getOnlineAmt());
                    } else {
                        feeReceiptPojo.setBankAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getBankAmt());
                    }
                    feeReceiptPojo.setTotalAmt(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalAmt());
                }
                feeReceiptPojoArrayList.add(feeReceiptPojo);
            }
        }
        return feeReceiptPojoArrayList;
    }

    @Transactional
    public void downloadFeeDueReportExcel(OutputStream out, ReportPojo reportPojo) {
        try {
            List<StudentFeePojo> studentDueList = getStudentDueList(reportPojo);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
            String address = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getAddress())) {
                    address = schoolBranchDetails.getAddress();
                }
            }
            String branchName = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getBranchName())) {
                    branchName = schoolBranchDetails.getBranchName();
                }
            }
            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(2).setCellValue("INTERNATIONAL HOLISTIC MONTESSORI & SCHOOL" + "\n" + branchName + "\n" + address + "\n" + "Fee Due Report");
            CellRangeAddress cell = new CellRangeAddress(0, 2, 2, 4);
            sheet.addMergedRegion(cell);
            RegionUtil.setBorderTop(2, cell, sheet, hwb);
            RegionUtil.setBorderLeft(2, cell, sheet, hwb);
            RegionUtil.setBorderRight(2, cell, sheet, hwb);
            RegionUtil.setBorderBottom(2, cell, sheet, hwb);
            HSSFRow headerRow5 = sheet.createRow(3);
            headerRow5.createCell(0).setCellValue("From Date");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            headerRow5.createCell(1).setCellValue(dateFormat.format(reportPojo.getFromDate()));
            headerRow5.createCell(2).setCellValue("To Date");
            headerRow5.createCell(3).setCellValue(dateFormat.format(reportPojo.getToDate()));
            if (reportPojo.getAcademicYearMaster() != null) {
                HSSFRow headerRow6 = sheet.createRow(4);
                headerRow6.createCell(0).setCellValue("Academic Year");
                headerRow6.createCell(1).setCellValue(reportPojo.getAcademicYearMaster().getAcdyrName());
            }
            if (reportPojo.getGradeMasters() != null) {
                HSSFRow headerRow7 = sheet.createRow(5);
                headerRow7.createCell(0).setCellValue("Grades");
                int col = 1;
                for (GradeMaster gradeMaster : reportPojo.getGradeMasters())
                    headerRow7.createCell(col++).setCellValue(gradeMaster.getGradeName());
            }
            HSSFRow headerRow1 = sheet.createRow(7);
            headerRow1.createCell(0).setCellValue("Student Profile ID");
            headerRow1.createCell(1).setCellValue("Name");
            headerRow1.createCell(2).setCellValue("Total Fee");
            headerRow1.createCell(3).setCellValue("Total Discount");
            headerRow1.createCell(4).setCellValue("Total Paid");
            headerRow1.createCell(5).setCellValue("Total Due");
            int i = 7;
            double totalAmountPaid = 0, totalBalance = 0, totalFeeAmt = 0, totalDiscount = 0;
            for (StudentFeePojo list : studentDueList) {
                if (list.getDueAmount() > 0) {
                    HSSFRow row = sheet.createRow(++i);
                    totalFeeAmt = totalFeeAmt + list.getTotalFeeAmount();
                    totalDiscount = totalDiscount + (list.getTotalFeeAmount() - list.getTotalPayable());
                    totalAmountPaid = totalAmountPaid + list.getPaidAmount();
                    totalBalance = totalBalance + list.getDueAmount();
                    row.createCell(0).setCellValue(list.getStudent().getStudentProfileId());
                    row.createCell(1).setCellValue(list.getStudent().getStudentName());
                    row.createCell(2).setCellValue(list.getTotalFeeAmount());
                    row.createCell(3).setCellValue(list.getTotalFeeAmount() - list.getTotalPayable());
                    row.createCell(4).setCellValue(list.getPaidAmount());
                    row.createCell(5).setCellValue(list.getDueAmount());
                    reportPojo.setStudentId(list.getStudent().getStudentId());
                    int col = 5, row1 = 5;
                    List<FeeTypeMasterPojo> feeTypeMasterPojoList = getReportDetails(reportPojo);
                    if (feeTypeMasterPojoList.size() > 0) {
                        for (FeeTypeMasterPojo feeTypeMasterPojo : feeTypeMasterPojoList) {
                            headerRow1.createCell(++row1).setCellValue("Fee Type");
                            headerRow1.createCell(++row1).setCellValue("Due Amount");
                            headerRow1.createCell(++row1).setCellValue("Due Date");
                            CellStyle style = hwb.createCellStyle();
                            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                            if (feeTypeMasterPojo.getDueAmt() == 0) {
                                HSSFCell cell1 = row.createCell(++col);
                                cell1.setCellValue(feeTypeMasterPojo.getFeeTypeName());
                                cell1.setCellStyle(style);
                                cell1 = row.createCell(++col);
                                cell1.setCellValue(feeTypeMasterPojo.getDueAmt());
                                cell1.setCellStyle(style);
                                cell1 = row.createCell(++col);
                                cell1.setCellValue(feeTypeMasterPojo.getDueDate());
                                cell1.setCellStyle(style);
                            } else {
                                row.createCell(++col).setCellValue(feeTypeMasterPojo.getFeeTypeName());
                                row.createCell(++col).setCellValue(feeTypeMasterPojo.getDueAmt());
                                row.createCell(++col).setCellValue(feeTypeMasterPojo.getDueDate());
                            }
                        }
                    }
                }
            }
            HSSFRow headerRow3 = sheet.createRow(++i);
            headerRow3.createCell(0).setCellValue("Total");
            headerRow3.createCell(1).setCellValue("");
            headerRow3.createCell(2).setCellValue(totalFeeAmt);
            headerRow3.createCell(3).setCellValue(totalDiscount);
            headerRow3.createCell(4).setCellValue(totalAmountPaid);
            headerRow3.createCell(5).setCellValue(totalBalance);
            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    public MailDTO createSaveMailDetails(MailDTO saveMailDetails) {
        Mail mail = new Mail();
        mail.setUserName(saveMailDetails.getUserName());
        mail.setPassword(saveMailDetails.getPassword());
        mail.setPort(saveMailDetails.getPort());
        mail.setSmtpHost(saveMailDetails.getSmtpHost());
        mail.setForMail(saveMailDetails.getForMail());
        mail.setStatus("Active");
        bsMailRepository.save(mail);
        return saveMailDetails;
    }

    //downloadStudentDetailsExcel
    @Transactional
    public void downloadStudentDetailsExcel(OutputStream out, StudentFee studentDetails, List<StudentFeeDetails> studentFeeDetails) {
        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(2).setCellValue("Student Details");
            HSSFRow headerRow1 = sheet.createRow(5);
            headerRow1.createCell(0).setCellValue("Academic Year");
            headerRow1.createCell(1).setCellValue("Date Of Admission");
            headerRow1.createCell(2).setCellValue("Admission Form No");
            headerRow1.createCell(3).setCellValue("Grade");
            headerRow1.createCell(4).setCellValue("Joining Date ");
            headerRow1.createCell(5).setCellValue("Student Name ");

            headerRow1.createCell(6).setCellValue("Permanent Address");
            headerRow1.createCell(7).setCellValue("Date Of Birth ");
            headerRow1.createCell(8).setCellValue("Local/Present Address");
            headerRow1.createCell(9).setCellValue("Gender ");
            headerRow1.createCell(10).setCellValue("Physical Condition ");

            headerRow1.createCell(11).setCellValue("Aadhaar No");
            headerRow1.createCell(12).setCellValue("Religion");
            headerRow1.createCell(13).setCellValue("Father Name");
            headerRow1.createCell(14).setCellValue("Father Email Id ");
            headerRow1.createCell(15).setCellValue("Father Mobile");

            headerRow1.createCell(16).setCellValue("Father Occupation");
            headerRow1.createCell(17).setCellValue("Mother Name");
            headerRow1.createCell(18).setCellValue("Mother Email Id");
            headerRow1.createCell(19).setCellValue("Mother Mobile ");
            headerRow1.createCell(20).setCellValue("Mother Occupation ");

            headerRow1.createCell(21).setCellValue("Primary Contact No");
            headerRow1.createCell(22).setCellValue("Local Guardian Name");
            headerRow1.createCell(23).setCellValue("Parents Annual Income");
            headerRow1.createCell(24).setCellValue("Guardian Number ");
            headerRow1.createCell(25).setCellValue("Status");
            int i = 5;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            HSSFRow row = sheet.createRow(++i);
            row.createCell(0).setCellValue(studentDetails.getStudent().getAcademicYearMaster().getAcdyrName());
            row.createCell(1).setCellValue(dateFormat.format(studentDetails.getStudent().getDateOfAdmission()));
            row.createCell(2).setCellValue(studentDetails.getStudent().getAdmissionFormNo());
            row.createCell(3).setCellValue(studentDetails.getStudent().getGradeMaster().getGradeName());
            row.createCell(4).setCellValue(dateFormat.format(studentDetails.getStudent().getDateOfJoining()));
            row.createCell(5).setCellValue(studentDetails.getStudent().getStudentName());
            row.createCell(6).setCellValue(studentDetails.getStudent().getPermanentAddress());
            row.createCell(7).setCellValue(dateFormat.format(studentDetails.getStudent().getDateofbirth()));
            row.createCell(8).setCellValue(studentDetails.getStudent().getPresentAddress());
            row.createCell(9).setCellValue(studentDetails.getStudent().getGender());
            row.createCell(10).setCellValue(studentDetails.getStudent().getPhysicalCondition());

            row.createCell(11).setCellValue(studentDetails.getStudent().getAadhaarNo());
            row.createCell(12).setCellValue(studentDetails.getStudent().getReligion());
            row.createCell(13).setCellValue(studentDetails.getStudent().getFatherName());
            row.createCell(14).setCellValue(studentDetails.getStudent().getFatherEmailId());
            row.createCell(15).setCellValue(studentDetails.getStudent().getFatherContactNo());

            row.createCell(16).setCellValue(studentDetails.getStudent().getFatherOccupation());
            row.createCell(17).setCellValue(studentDetails.getStudent().getMotherName());
            row.createCell(18).setCellValue(studentDetails.getStudent().getMotherEmailId());
            row.createCell(19).setCellValue(studentDetails.getStudent().getMotherContactNo());
            row.createCell(20).setCellValue(studentDetails.getStudent().getMotherOccupation());

            row.createCell(21).setCellValue(studentDetails.getStudent().getPrimaryContactNo());
            row.createCell(22).setCellValue(studentDetails.getStudent().getGaurdianName());
            if (studentDetails.getStudent().getAnnualIncome() != null)
                row.createCell(23).setCellValue(studentDetails.getStudent().getAnnualIncome());
            else
                row.createCell(23).setCellValue("");
            row.createCell(24).setCellValue(studentDetails.getStudent().getGaurdianNumber());
            row.createCell(25).setCellValue(studentDetails.getStudent().getStudentStatus());
            ++i;
            HSSFRow headerRow6 = sheet.createRow(++i);
            headerRow6.createCell(4).setCellValue("Fee Configuration");
            CellRangeAddress cell6 = new CellRangeAddress(0, 2, 2, 4);
            sheet.addMergedRegion(cell6);

            HSSFRow headerRow7 = sheet.createRow(++i);
            headerRow7.createCell(1).setCellValue("Fee Name");
            headerRow7.createCell(2).setCellValue("Fee Amount");
            headerRow7.createCell(3).setCellValue("Discount");
            headerRow7.createCell(4).setCellValue("Payable");
            headerRow7.createCell(5).setCellValue("Installments ");
            headerRow7.createCell(6).setCellValue("Instalments Amount");
            headerRow7.createCell(7).setCellValue("Remarks");
            for (StudentFeeDetails list : studentFeeDetails) {
                if (list.getCheckboxstatus() == true) {
                    HSSFRow row2 = sheet.createRow(++i);
                    row2.createCell(1).setCellValue(list.getFeeTypeName());
                    row2.createCell(2).setCellValue(list.getFeeTypeAmount());
                    row2.createCell(3).setCellValue(list.getDiscount());
                    row2.createCell(4).setCellValue(list.getPayable());
                    row2.createCell(5).setCellValue(list.getNoOfInstallments());
                    row2.createCell(6).setCellValue(list.getInstallmentsAmount());
                    row2.createCell(7).setCellValue(list.getDiscountRemarks());
                }
            }
            HSSFRow headerRow8 = sheet.createRow(++i);
            headerRow8.createCell(0).setCellValue("");
            headerRow8.createCell(1).setCellValue("Total");
            headerRow8.createCell(2).setCellValue(studentDetails.getTotalFeeAmount());
            headerRow8.createCell(3).setCellValue(studentDetails.getTotalFeeAmount() - studentDetails.getTotalPayable());
            headerRow8.createCell(4).setCellValue(studentDetails.getTotalPayable());
            headerRow8.createCell(6).setCellValue(studentDetails.getTotalPayable());
            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }


    //downloadStudentExportToExcel

    @Transactional
    public void downloadStudentListExportToExcel(OutputStream out, List<Student> studentFeeAllDetails) {
        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
//            HSSFRow headerRow = sheet.createRow(0);
//            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
//            headerRow.createCell(2).setCellValue("Student Details");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Academic Year");
            headerRow1.createCell(1).setCellValue("Date Of Admission");
            headerRow1.createCell(2).setCellValue("Admission Form No");
            headerRow1.createCell(3).setCellValue("Grade");
            headerRow1.createCell(4).setCellValue("Joining Date ");
            headerRow1.createCell(5).setCellValue("Student Name ");

            headerRow1.createCell(6).setCellValue("Permanent Address");
            headerRow1.createCell(7).setCellValue("Date Of Birth ");
            headerRow1.createCell(8).setCellValue("Local/Present Address");
            headerRow1.createCell(9).setCellValue("Gender ");
            headerRow1.createCell(10).setCellValue("Physical Condition ");

            headerRow1.createCell(11).setCellValue("Aadhaar No");
            headerRow1.createCell(12).setCellValue("Religion");
            headerRow1.createCell(13).setCellValue("Father Name");
            headerRow1.createCell(14).setCellValue("Father Email Id ");
            headerRow1.createCell(15).setCellValue("Father Mobile");

            headerRow1.createCell(16).setCellValue("Father Occupation");
            headerRow1.createCell(17).setCellValue("Mother Name");
            headerRow1.createCell(18).setCellValue("Mother Email Id");
            headerRow1.createCell(19).setCellValue("Mother Mobile ");
            headerRow1.createCell(20).setCellValue("Mother Occupation ");

            headerRow1.createCell(21).setCellValue("Primary Contact No");
            headerRow1.createCell(22).setCellValue("Local Guardian Name");
            headerRow1.createCell(23).setCellValue("Parents Annual Income");
            headerRow1.createCell(24).setCellValue("Guardian Number ");
            headerRow1.createCell(25).setCellValue("Status");

//            headerRow1.createCell(26).setCellValue("Fee Type 1");
//            headerRow1.createCell(27).setCellValue("Amount");
//            headerRow1.createCell(28).setCellValue("Fee Type 2");
//            headerRow1.createCell(29).setCellValue("Amount ");
//            headerRow1.createCell(30).setCellValue("Fee Type 3");
//
//            headerRow1.createCell(31).setCellValue("Amount");
//            headerRow1.createCell(32).setCellValue("Fee Type 4");
//            headerRow1.createCell(33).setCellValue("Amount");
//            headerRow1.createCell(34).setCellValue("Fee Type 5 ");
//            headerRow1.createCell(35).setCellValue("Amount");
//
//
//            headerRow1.createCell(36).setCellValue("Fee Type 6");
//            headerRow1.createCell(37).setCellValue("Amount");
//            headerRow1.createCell(38).setCellValue("Total Discount ");
//            headerRow1.createCell(39).setCellValue("Total Paid");
            int i = 0;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            int j = 0;
            for (Student studentDetails : studentFeeAllDetails) {

                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(studentDetails.getAcademicYearMaster().getAcdyrName());
                row.createCell(1).setCellValue(dateFormat.format(studentDetails.getDateOfAdmission()));
                row.createCell(2).setCellValue(studentDetails.getAdmissionFormNo());
                row.createCell(3).setCellValue(studentDetails.getGradeMaster().getGradeName());
                row.createCell(4).setCellValue(dateFormat.format(studentDetails.getDateOfJoining()));
                row.createCell(5).setCellValue(studentDetails.getStudentName());
                row.createCell(6).setCellValue(studentDetails.getPermanentAddress());
                row.createCell(7).setCellValue(dateFormat.format(studentDetails.getDateofbirth()));
                row.createCell(8).setCellValue(studentDetails.getPresentAddress());
                row.createCell(9).setCellValue(studentDetails.getGender());
                row.createCell(10).setCellValue(studentDetails.getPhysicalCondition());

                row.createCell(11).setCellValue(studentDetails.getAadhaarNo());
                row.createCell(12).setCellValue(studentDetails.getReligion());
                row.createCell(13).setCellValue(studentDetails.getFatherName());
                row.createCell(14).setCellValue(studentDetails.getFatherEmailId());
                row.createCell(15).setCellValue(studentDetails.getFatherContactNo());

                row.createCell(16).setCellValue(studentDetails.getFatherOccupation());
                row.createCell(17).setCellValue(studentDetails.getMotherName());
                row.createCell(18).setCellValue(studentDetails.getMotherEmailId());
                row.createCell(19).setCellValue(studentDetails.getMotherContactNo());
                row.createCell(20).setCellValue(studentDetails.getMotherOccupation());

                row.createCell(21).setCellValue(studentDetails.getPrimaryContactNo());
                row.createCell(22).setCellValue(studentDetails.getGaurdianName());
                if (studentDetails.getAnnualIncome() != null) {
                    row.createCell(23).setCellValue(studentDetails.getAnnualIncome());
                }
                row.createCell(24).setCellValue(studentDetails.getGaurdianNumber());
                row.createCell(25).setCellValue(studentDetails.getStudentStatus());

            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    @Transactional
    public void downloadFeeListExcel(OutputStream out, List<StudentFee> studentFeeAllDetails) {
        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");

            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Student Name");
            headerRow1.createCell(1).setCellValue("Student ProfileId");
            headerRow1.createCell(2).setCellValue("FeeType Name");
            headerRow1.createCell(3).setCellValue("Status");
            headerRow1.createCell(4).setCellValue("Fee Amount");
            headerRow1.createCell(5).setCellValue("Paying Fee");
            headerRow1.createCell(6).setCellValue("Payable");
            headerRow1.createCell(7).setCellValue("Discount");
            headerRow1.createCell(8).setCellValue("Value");
            headerRow1.createCell(9).setCellValue("Remark ");
            headerRow1.createCell(10).setCellValue("Installments");
            headerRow1.createCell(11).setCellValue("Installment1 Amount");
            headerRow1.createCell(12).setCellValue("Installment1 Date");
            headerRow1.createCell(13).setCellValue("Installment2 Amount");
            headerRow1.createCell(14).setCellValue("Installment2 Date");
            headerRow1.createCell(15).setCellValue("Installment3 Amount");
            headerRow1.createCell(16).setCellValue("Installment3 Date");
            headerRow1.createCell(17).setCellValue("Installment4 Amount");
            headerRow1.createCell(18).setCellValue("Installment4 Date");
            headerRow1.createCell(19).setCellValue("Installment5 Amount");
            headerRow1.createCell(20).setCellValue("Installment5 Date");
            headerRow1.createCell(21).setCellValue("Installment6 Amount");
            headerRow1.createCell(22).setCellValue("Installment6 Date");
            headerRow1.createCell(23).setCellValue("Installment7 Amount");
            headerRow1.createCell(24).setCellValue("Installment7 Date");
            headerRow1.createCell(25).setCellValue("Installment8 Amount");
            headerRow1.createCell(26).setCellValue("Installment8 Date");
            headerRow1.createCell(27).setCellValue("Installment9 Amount");
            headerRow1.createCell(28).setCellValue("Installment9 Date");
            headerRow1.createCell(29).setCellValue("Installment10 Amount");
            headerRow1.createCell(30).setCellValue("Installment10 Date");
            headerRow1.createCell(31).setCellValue("Installment11 Amount");
            headerRow1.createCell(32).setCellValue("Installment11 Date");
            headerRow1.createCell(33).setCellValue("Installment12 Amount");
            headerRow1.createCell(34).setCellValue("Installment12 Date");
            int i = 0;
            for (StudentFee studentDetails : studentFeeAllDetails) {
                List<StudentFeeDetails> feeDetailsList = bsStudentFeeDetailsRepository.findByStudentfee(studentDetails);
                for(StudentFeeDetails studentFeeDetails:feeDetailsList){
                    HSSFRow row = sheet.createRow(++i);
                    row.createCell(0).setCellValue(studentDetails.getStudent().getStudentName());
                    row.createCell(1).setCellValue(studentDetails.getStudent().getStudentProfileId());
                    row.createCell(2).setCellValue(studentFeeDetails.getFeetypemaster().getFeeTypeName());
                    row.createCell(3).setCellValue(studentFeeDetails.getFeetypemaster().getStatus());
                    row.createCell(4).setCellValue(studentFeeDetails.getFeetypemaster().getFeeAmount());
                    row.createCell(5).setCellValue(studentFeeDetails.getFeetypemaster().getPayingFee());
                    row.createCell(6).setCellValue(studentFeeDetails.getPayable());
                    row.createCell(7).setCellValue(studentFeeDetails.getDiscount());
                    row.createCell(8).setCellValue(studentFeeDetails.getCheckboxstatus()==true?"true":"false");
                    row.createCell(9).setCellValue(studentFeeDetails.getDiscountRemarks());
                    row.createCell(10).setCellValue(studentFeeDetails.getNoOfInstallments());
                    List<Installments> installments=bsInstallmentsRepository.findByStudentFeeAndFeeTypeMaster(studentDetails,studentFeeDetails.getFeetypemaster());
                    int j=11;
                    for(Installments installments1:installments){
                        Date date = installments1.getDueDate();
                        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        String dt = df.format(date);
                        row.createCell(j++).setCellValue(installments1.getInstallmentsAmount());
                        row.createCell(j++).setCellValue(dt);
                    }
                }
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }


    public Paragraph printSchoolDetails() {
        SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
        Paragraph preface2 = new Paragraph(schoolBranchDetails.getAddress());
        Paragraph preface1 = new Paragraph(schoolBranchDetails.getBranchName());
        Paragraph preface = new Paragraph("INTERNATIONAL HOLISTIC MONTESSORI & SCHOOL");
        preface.setAlignment(Element.ALIGN_CENTER);
        preface1.setAlignment(Element.ALIGN_CENTER);
        preface2.setAlignment(Element.ALIGN_CENTER);
        preface.add("\n");
        preface.add(preface1);
        preface.add(preface2);
        preface.add("\n");
        return preface;
    }

    @Transactional
    public void downloadFeeCollectedReportExcel(OutputStream out, ReportPojo reportPojo) {
        try {
            List<FeeReceiptPojo> feeReceiptList = getReportCollected(reportPojo);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            String address = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getAddress())) {
                    address = schoolBranchDetails.getAddress();
                }
            }
            String branchName = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getBranchName())) {
                    branchName = schoolBranchDetails.getBranchName();
                }
            }
            headerRow.createCell(2).setCellValue("INTERNATIONAL HOLISTIC MONTESSORI & SCHOOL" + "\n" + branchName + "\n" + address + "\n" + "Fee Collected Report");
            CellRangeAddress cell = new CellRangeAddress(0, 2, 2, 4);
            sheet.addMergedRegion(cell);
            RegionUtil.setBorderTop(2, cell, sheet, hwb);
            RegionUtil.setBorderLeft(2, cell, sheet, hwb);
            RegionUtil.setBorderRight(2, cell, sheet, hwb);
            RegionUtil.setBorderBottom(2, cell, sheet, hwb);
            HSSFRow headerRow5 = sheet.createRow(3);
            headerRow5.createCell(0).setCellValue("From Date");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            headerRow5.createCell(1).setCellValue(dateFormat.format(reportPojo.getFromDate()));
            headerRow5.createCell(2).setCellValue("To Date");
            headerRow5.createCell(3).setCellValue(dateFormat.format(reportPojo.getToDate()));
            HSSFRow headerRow6 = sheet.createRow(4);
            if (reportPojo.getAcademicYearMaster() != null) {
                headerRow6.createCell(0).setCellValue("Academic Year");
                headerRow6.createCell(1).setCellValue(reportPojo.getAcademicYearMaster().getAcdyrName());
            }
            if (reportPojo.getGradeMasters() != null) {
                HSSFRow headerRow7 = sheet.createRow(5);
                headerRow7.createCell(0).setCellValue("Grades");
                int col = 1;
                for (GradeMaster gradeMaster : reportPojo.getGradeMasters())
                    headerRow7.createCell(col++).setCellValue(gradeMaster.getGradeName());
            }
            HSSFRow headerRow1 = sheet.createRow(7);
            headerRow1.createCell(0).setCellValue("Name");
            headerRow1.createCell(1).setCellValue("Student Profile ID");
            headerRow1.createCell(2).setCellValue("Cash");
            headerRow1.createCell(3).setCellValue("Bank");
            headerRow1.createCell(4).setCellValue("Card");
            headerRow1.createCell(5).setCellValue("Online");
            headerRow1.createCell(6).setCellValue("Total");
            int i = 7;
            double totalCashAmt = 0, totalBankAmt = 0, totalCardAmt = 0, totalOnlineAmt = 0, totalAmt = 0;
            for (FeeReceiptPojo list : feeReceiptList) {
                HSSFRow row = sheet.createRow(++i);
                totalCardAmt = totalCardAmt + list.getCardAmt();
                totalOnlineAmt = totalOnlineAmt + list.getOnlineAmt();
                totalCashAmt = totalCashAmt + list.getCashAmt();
                totalBankAmt = totalBankAmt + list.getBankAmt();
                totalAmt = totalAmt + list.getTotalAmt();
                row.createCell(0).setCellValue(list.getStudentName());
                row.createCell(1).setCellValue(list.getStudentProfileId());
                row.createCell(2).setCellValue(list.getCashAmt());
                row.createCell(3).setCellValue(list.getBankAmt());
                row.createCell(4).setCellValue(list.getCardAmt());
                row.createCell(5).setCellValue(list.getOnlineAmt());
                row.createCell(6).setCellValue(list.getTotalAmt());
            }
            HSSFRow headerRow3 = sheet.createRow(++i);
            headerRow3.createCell(0).setCellValue("Total");
            headerRow3.createCell(1).setCellValue("");
            headerRow3.createCell(2).setCellValue(totalCashAmt);
            headerRow3.createCell(3).setCellValue(totalBankAmt);
            headerRow3.createCell(4).setCellValue(totalCardAmt);
            headerRow3.createCell(5).setCellValue(totalOnlineAmt);
            headerRow3.createCell(6).setCellValue(totalAmt);
            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    public void downloadFeeDueReportPdf(OutputStream outputStream, ReportPojo reportPojo) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFilterTable(reportPojo, "Fee Due Report");
            PdfPTable table1 = createFirstTableFeeDueReport(reportPojo);
            table.setHeaderRows(1);
            document.add(printSchoolDetails());
            document.add(table);
            document.add(table1);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable createFilterTable(ReportPojo reportPojo, String type) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable tbl = new PdfPTable(a + 2);
        PdfPTable tbl2 = new PdfPTable(1);
        PdfPTable tbl3 = new PdfPTable(1);
        PdfPTable tab = new PdfPTable(1);
        Font f = new Font(getcustomfont(), 15, Font.BOLD, Color.WHITE);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase(type, f));
        p.setBackgroundColor(myColor);
        tab.addCell(p);
        PdfPCell p1 = new PdfPCell(new Phrase("From Date", font));
        p1.setBackgroundColor(myColor);
        PdfPCell p2 = new PdfPCell(new Phrase("To Date", font));
        p2.setBackgroundColor(myColor);
        tbl.addCell(p1);
        tbl.addCell(p2);
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tbl.addCell(new Phrase(dateFormat.format(reportPojo.getFromDate()) + "", font1));
        tbl.addCell(new Phrase(dateFormat.format(reportPojo.getToDate()) + "", font1));
        if (reportPojo.getAcademicYearMaster() != null) {
            PdfPCell p5 = new PdfPCell(new Phrase("Academic Year", font));
            p5.setBackgroundColor(myColor);
            tbl2.addCell(p5);
            tbl2.addCell(new Phrase(reportPojo.getAcademicYearMaster().getAcdyrName() + "", font1));
        }
        if (reportPojo.getGradeMasters() != null) {
            PdfPCell p6 = new PdfPCell(new Phrase("Grades", font));
            p6.setBackgroundColor(myColor);
            tbl3.addCell(p6);
            for (GradeMaster gradeMaster : reportPojo.getGradeMasters())
                tbl3.addCell(new Phrase(gradeMaster.getGradeName() + "", font1));
        }
        tab.addCell(tbl);
        tab.addCell(tbl2);
        tab.addCell(tbl3);
        return tab;
    }

    public PdfPTable createFirstTableFeeDueReport(ReportPojo reportPojo) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        Font f1 = new Font(getcustomfont(), 8, Font.BOLD, Color.BLACK);
        PdfPTable table = new PdfPTable(a + 6);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell pc2 = new PdfPCell(new Phrase("Student Profile ID", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Name", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Fee Amount", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Discount", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Paid", font));
        pc6.setBackgroundColor(myColor);
        PdfPCell pc7 = new PdfPCell(new Phrase("Due", font));
        pc7.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        table.addCell(pc7);
        List<StudentFeePojo> studentDueList = getStudentDueList(reportPojo);
        double totalAmountPaid = 0, totalBalance = 0, totalFeeAmt = 0, totalDiscount = 0;
        for (StudentFeePojo list : studentDueList) {
            if (list.getDueAmount() > 0) {
                totalFeeAmt = totalFeeAmt + list.getTotalFeeAmount();
                totalDiscount = totalDiscount + (list.getTotalFeeAmount() - list.getTotalPayable());
                totalAmountPaid = totalAmountPaid + list.getPaidAmount();
                totalBalance = totalBalance + list.getDueAmount();
                table.addCell(new Phrase(list.getStudent().getStudentProfileId() + "", font1));
                table.addCell(new Phrase(list.getStudent().getStudentName() + "", font1));
                table.addCell(new Phrase(list.getTotalFeeAmount() + "", font1));
                table.addCell(new Phrase(list.getTotalFeeAmount() - list.getTotalPayable() + "", font1));
                table.addCell(new Phrase(list.getPaidAmount() + "", font1));
                table.addCell(new Phrase(list.getDueAmount() + "", font1));
                reportPojo.setStudentId(list.getStudent().getStudentId());
                List<FeeTypeMasterPojo> feeTypeMasterPojoList = getReportDetails(reportPojo);
                if (feeTypeMasterPojoList.size() > 0) {
                    table.addCell(new Phrase("", font1));
                    table.addCell(new Phrase("", font1));
                    table.addCell(new Phrase("Fee Type", f1));
                    table.addCell(new Phrase("Due Amount", f1));
                    table.addCell(new Phrase("Due Date", f1));
                    table.addCell(new Phrase("", font1));
                    for (FeeTypeMasterPojo feeTypeMasterPojo : feeTypeMasterPojoList) {
                        table.addCell(new Phrase("", font1));
                        table.addCell(new Phrase("", font1));
                        table.addCell(new Phrase(feeTypeMasterPojo.getFeeTypeName() + "", font1));
                        table.addCell(new Phrase(feeTypeMasterPojo.getDueAmt() + "", font1));
                        table.addCell(new Phrase(feeTypeMasterPojo.getDueDate() + "", font1));
                        table.addCell(new Phrase("", font1));
                    }
                }
            }
        }
        table.addCell(new Phrase("Total" + "", font1));
        table.addCell(new Phrase("", font1));
        table.addCell(new Phrase(totalFeeAmt + "", font1));
        table.addCell(new Phrase(totalDiscount + "", font1));
        table.addCell(new Phrase(totalAmountPaid + "", font1));
        table.addCell(new Phrase(totalBalance + "", font1));
        return table;
    }

    //downloadStudentDetailsPdf
    public void downloadStudentDetailsPdf(OutputStream outputStream, StudentFee studentDetails, List<StudentFeeDetails> studentFeeDetails) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableStudentDetails(studentDetails, studentFeeDetails);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable createFirstTableStudentDetails(StudentFee studentDetails, List<StudentFeeDetails> studentFeeDetails) throws ParseException {
        int a = 0;
        PdfPTable tab = new PdfPTable(1);
        PdfPTable tab1 = new PdfPTable(1);
        PdfPTable tab2 = new PdfPTable(1);
        PdfPTable tab3 = new PdfPTable(1);
        PdfPTable tab4 = new PdfPTable(1);
        Font f = new Font(getcustomfont(), 15, Font.BOLD, Color.BLACK);
        PdfPTable table1 = new PdfPTable(a + 2);
        PdfPTable table2 = new PdfPTable(a + 2);
        PdfPTable table3 = new PdfPTable(a + 2);
        PdfPTable table4 = new PdfPTable(a + 7);
        PdfPTable table5 = new PdfPTable(a + 2);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.BLACK);
        Font font2 = new Font(getcustomfont(), 12, Font.NORMAL, Color.BLACK);
        Color myColor = WebColors.getRGBColor("#FFFFFF");
        PdfPCell p = new PdfPCell(new Phrase("Student Details", f));
        p.setBackgroundColor(myColor);
        p.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tab.addCell(p);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        PdfPCell p1 = new PdfPCell(new Phrase("Office Use", font2));
        p1.setBackgroundColor(myColor);
        p1.setBorder(PdfPCell.NO_BORDER);
        p1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc2 = new PdfPCell(new Phrase("Academic Year :" + studentDetails.getStudent().getAcademicYearMaster().getAcdyrName(), font));
        pc2.setBackgroundColor(myColor);
        pc2.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc3 = new PdfPCell(new Phrase("Grade :" + studentDetails.getStudent().getGradeMaster().getGradeName(), font));
        pc3.setBackgroundColor(myColor);
        pc3.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc4 = new PdfPCell(new Phrase("Date Of Admission :" + dateFormat.format(studentDetails.getStudent().getDateOfAdmission()), font));
        pc4.setBackgroundColor(myColor);
        pc4.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc5 = new PdfPCell(new Phrase("Joining Date :" + dateFormat.format(studentDetails.getStudent().getDateOfJoining()), font));
        pc5.setBackgroundColor(myColor);
        pc5.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc6 = new PdfPCell(new Phrase("Admission Form No :" + studentDetails.getStudent().getAdmissionFormNo(), font));
        pc6.setBackgroundColor(myColor);
        pc6.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc7 = new PdfPCell(new Phrase("", font));
        pc7.setBackgroundColor(myColor);
        pc7.setBorder(PdfPCell.NO_BORDER);
        PdfPCell p2 = new PdfPCell(new Phrase("Student", font2));
        p2.setBackgroundColor(myColor);
        p2.setBorder(PdfPCell.NO_BORDER);
        p2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc8 = new PdfPCell(new Phrase("Student Name :" + studentDetails.getStudent().getStudentName(), font));
        pc8.setBackgroundColor(myColor);
        pc8.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc9 = new PdfPCell(new Phrase("Permanent Address :" + studentDetails.getStudent().getPermanentAddress(), font));
        pc9.setBackgroundColor(myColor);
        pc9.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc10 = new PdfPCell(new Phrase("Date Of Birth :" + dateFormat.format(studentDetails.getStudent().getDateofbirth()), font));
        pc10.setBackgroundColor(myColor);
        pc10.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc11 = new PdfPCell(new Phrase("Local/Present Address :" + studentDetails.getStudent().getPresentAddress(), font));
        pc11.setBackgroundColor(myColor);
        pc11.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc12 = new PdfPCell(new Phrase("Gender :" + studentDetails.getStudent().getGender(), font));
        pc12.setBackgroundColor(myColor);
        pc12.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc13 = new PdfPCell(new Phrase("Physical Condition :" + studentDetails.getStudent().getPhysicalCondition(), font));
        pc13.setBackgroundColor(myColor);
        pc13.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc14 = new PdfPCell(new Phrase("Aadhaar No :" + studentDetails.getStudent().getAadhaarNo(), font));
        pc14.setBackgroundColor(myColor);
        pc14.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc15 = new PdfPCell(new Phrase("Religion :" + studentDetails.getStudent().getReligion(), font));
        pc15.setBackgroundColor(myColor);
        pc15.setBorder(PdfPCell.NO_BORDER);

        PdfPCell p3 = new PdfPCell(new Phrase("Parents", font2));
        p3.setBackgroundColor(myColor);
        p3.setBorder(PdfPCell.NO_BORDER);
        p3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc16 = new PdfPCell(new Phrase("Father Name :" + studentDetails.getStudent().getFatherName(), font));
        pc16.setBackgroundColor(myColor);
        pc16.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc17 = new PdfPCell(new Phrase("Father Email Id :" + studentDetails.getStudent().getFatherEmailId(), font));
        pc17.setBackgroundColor(myColor);
        pc17.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc18 = new PdfPCell(new Phrase("Father Mobile :" + studentDetails.getStudent().getFatherContactNo(), font));
        pc18.setBackgroundColor(myColor);
        pc18.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc19 = new PdfPCell(new Phrase("Father Occupation :" + studentDetails.getStudent().getFatherOccupation(), font));
        pc19.setBackgroundColor(myColor);
        pc19.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc20 = new PdfPCell(new Phrase("Mother Name :" + studentDetails.getStudent().getMotherName(), font));
        pc20.setBackgroundColor(myColor);
        pc20.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc21 = new PdfPCell(new Phrase("Mother Email Id :" + studentDetails.getStudent().getMotherEmailId(), font));
        pc21.setBackgroundColor(myColor);
        pc21.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc22 = new PdfPCell(new Phrase("Mother Mobile :" + studentDetails.getStudent().getMotherContactNo(), font));
        pc22.setBackgroundColor(myColor);
        pc22.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc23 = new PdfPCell(new Phrase("Mother Occupation :" + studentDetails.getStudent().getMotherOccupation(), font));
        pc23.setBackgroundColor(myColor);
        pc23.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc24 = new PdfPCell(new Phrase("Primary Contact No :" + studentDetails.getStudent().getPrimaryContactNo(), font));
        pc24.setBackgroundColor(myColor);
        pc24.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc25 = new PdfPCell(new Phrase("Local Guardian Name :" + studentDetails.getStudent().getGaurdianName(), font));
        pc25.setBackgroundColor(myColor);
        pc25.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc26 = null;
        if (studentDetails.getStudent().getAnnualIncome() != null) {
            pc26 = new PdfPCell(new Phrase("Parents Annual Income :" + studentDetails.getStudent().getAnnualIncome(), font));
        } else {
            pc26 = new PdfPCell(new Phrase("Parents Annual Income :" + "", font));
        }
        pc26.setBackgroundColor(myColor);
        pc26.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc27 = new PdfPCell(new Phrase("Guardian Number :" + studentDetails.getStudent().getGaurdianNumber(), font));
        pc27.setBackgroundColor(myColor);
        pc27.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc28 = new PdfPCell(new Phrase("Status :" + studentDetails.getStudent().getStudentStatus(), font));
        pc28.setBackgroundColor(myColor);
        pc28.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc29 = new PdfPCell(new Phrase("", font));
        pc29.setBackgroundColor(myColor);
        pc29.setBorder(PdfPCell.NO_BORDER);

        PdfPCell p4 = new PdfPCell(new Phrase("Fee Configuration", font2));
        p4.setBackgroundColor(myColor);
        p4.setBorder(PdfPCell.NO_BORDER);
        p4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc30 = new PdfPCell(new Phrase("Fee Name ", font2));
        pc30.setBackgroundColor(myColor);

        PdfPCell pc31 = new PdfPCell(new Phrase("Fee Amount", font2));
        pc31.setBackgroundColor(myColor);
        PdfPCell pc32 = new PdfPCell(new Phrase("Discount", font2));
        pc32.setBackgroundColor(myColor);
        PdfPCell pc33 = new PdfPCell(new Phrase("Payable", font2));
        pc33.setBackgroundColor(myColor);
        PdfPCell pc34 = new PdfPCell(new Phrase("Installments", font2));
        pc34.setBackgroundColor(myColor);
        PdfPCell pc35 = new PdfPCell(new Phrase("Instalments Amount ", font2));
        pc35.setBackgroundColor(myColor);
        PdfPCell pc36 = new PdfPCell(new Phrase("Remarks ", font2));
        pc36.setBackgroundColor(myColor);
        table1.addCell(pc2);
        table1.addCell(pc3);
        table1.addCell(pc4);
        table1.addCell(pc5);
        table1.addCell(pc6);
        table1.addCell(pc7);

        table2.addCell(pc8);
        table2.addCell(pc9);
        table2.addCell(pc10);
        table2.addCell(pc11);
        table2.addCell(pc12);
        table2.addCell(pc13);
        table2.addCell(pc14);
        table2.addCell(pc15);

        table3.addCell(pc16);
        table3.addCell(pc17);
        table3.addCell(pc18);
        table3.addCell(pc19);
        table3.addCell(pc20);
        table3.addCell(pc21);
        table3.addCell(pc22);
        table3.addCell(pc23);
        table3.addCell(pc24);
        table3.addCell(pc25);
        table3.addCell(pc26);
        table3.addCell(pc27);
        table3.addCell(pc28);
        table3.addCell(pc29);

        table4.addCell(pc30);
        table4.addCell(pc31);
        table4.addCell(pc32);
        table4.addCell(pc33);
        table4.addCell(pc34);
        table4.addCell(pc35);
        table4.addCell(pc36);
        tab1.addCell(p1);
        tab2.addCell(p2);
        tab3.addCell(p3);
        tab4.addCell(p4);
        for (StudentFeeDetails list : studentFeeDetails) {
            if (list.getCheckboxstatus() == true) {
                table4.addCell(new Phrase(list.getFeeTypeName() + "", font2));
                table4.addCell(new Phrase(list.getFeeTypeAmount() + "", font2));
                table4.addCell(new Phrase(list.getDiscount() + "", font2));
                table4.addCell(new Phrase(list.getPayable() + "", font2));
                table4.addCell(new Phrase(list.getNoOfInstallments() + "", font2));
                table4.addCell(new Phrase(list.getInstallmentsAmount() + "", font2));
                if (list.getDiscountRemarks() != null) {
                    table4.addCell(new Phrase(list.getDiscountRemarks() + "", font2));
                } else {
                    table4.addCell(new Phrase("", font2));
                }
            }
        }
        PdfPCell p5 = new PdfPCell(new Phrase("Fee Configuration", font2));
        p5.setBackgroundColor(myColor);
        p5.setBorder(PdfPCell.NO_BORDER);
        p5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//        table5.addCell(new Phrase("Total", font2));
//        table5.addCell(new Phrase("", ""));
        PdfPCell pc37 = new PdfPCell(new Phrase("Total Amount :" + studentDetails.getTotalFeeAmount(), font));
        pc37.setBackgroundColor(myColor);
        pc37.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc38 = new PdfPCell(new Phrase("Total Discount :" + (studentDetails.getTotalFeeAmount() - studentDetails.getTotalPayable()), font));
        pc38.setBackgroundColor(myColor);
        pc38.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc39 = new PdfPCell(new Phrase("Total Payable :" + studentDetails.getTotalPayable(), font));
        pc39.setBackgroundColor(myColor);
        pc39.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc40 = new PdfPCell(new Phrase("Total Instalments Amount :" + studentDetails.getTotalPayable(), font));
        pc40.setBackgroundColor(myColor);
        pc40.setBorder(PdfPCell.NO_BORDER);
        table5.addCell(pc37);
        table5.addCell(pc38);
        table5.addCell(pc39);
        table5.addCell(pc40);
        tab.addCell(tab1);
        tab.addCell(table1);
        tab.addCell(tab2);
        tab.addCell(table2);
        tab.addCell(tab3);
        tab.addCell(table3);
        tab.addCell(tab4);
        tab.addCell(table4);
        tab.addCell(table5);
        return tab;
    }

    public void downloadFeeCollectedReportPdf(OutputStream outputStream, ReportPojo reportPojo) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFilterTable(reportPojo, "Fee Collected Report");
            PdfPTable table1 = createFirstTableFeeCollectedReport(reportPojo);
            table.setHeaderRows(1);
            document.add(printSchoolDetails());
            document.add(table);
            document.add(table1);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable createFirstTableFeeCollectedReport(ReportPojo reportPojo) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 7);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell pc2 = new PdfPCell(new Phrase("Student Profile ID", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Name", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Cash ", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Bank", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Card", font));
        pc6.setBackgroundColor(myColor);
        PdfPCell pc7 = new PdfPCell(new Phrase("Online", font));
        pc7.setBackgroundColor(myColor);
        PdfPCell pc8 = new PdfPCell(new Phrase("Total", font));
        pc8.setBackgroundColor(myColor);
        table.addCell(pc3);
        table.addCell(pc2);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        table.addCell(pc7);
        table.addCell(pc8);
        List<FeeReceiptPojo> feePojoList = getReportCollected(reportPojo);
        double totalCardAmt = 0, totalOnlineAmt = 0, totalCashAmt = 0, totalBankAmt = 0, totalAmt = 0;
        for (FeeReceiptPojo list : feePojoList) {
            totalCardAmt = totalCardAmt + list.getCardAmt();
            totalOnlineAmt = totalOnlineAmt + list.getOnlineAmt();
            totalCashAmt = totalCashAmt + list.getCashAmt();
            totalBankAmt = totalBankAmt + list.getBankAmt();
            totalAmt = totalAmt + list.getTotalAmt();
            table.addCell(new Phrase(list.getStudentName() + "", font1));
            table.addCell(new Phrase(list.getStudentProfileId() + "", font1));
            table.addCell(new Phrase(list.getCashAmt() + "", font1));
            table.addCell(new Phrase(list.getBankAmt() + "", font1));
            table.addCell(new Phrase(list.getCardAmt() + "", font1));
            table.addCell(new Phrase(list.getOnlineAmt() + "", font1));
            table.addCell(new Phrase(list.getTotalAmt() + "", font1));
        }
        table.addCell(new Phrase("Total" + "", font1));
        table.addCell(new Phrase("", font1));
        table.addCell(new Phrase(totalCashAmt + "", font1));
        table.addCell(new Phrase(totalBankAmt + "", font1));
        table.addCell(new Phrase(totalCardAmt + "", font1));
        table.addCell(new Phrase(totalOnlineAmt + "", font1));
        table.addCell(new Phrase(totalAmt + "", font1));
        return table;
    }

    public static BaseFont getcustomfont() {
        return FontFactory.getFont("arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, Color.BLACK).getBaseFont();
    }

    public List<FeeReceiptPojo> getReceiptDetails(Long studentFeeId) {
        List<FeeReceipt> list = bsFeeReceiptRepository.findByStudentFee(bsStudentFeeRepository.findOne(studentFeeId));
        List<FeeReceiptPojo> feeReceiptPojos = new ArrayList<>();
        for (FeeReceipt feeReceipt : list) {
            FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
            feeReceiptPojo.setReceiptDate(feeReceipt.getReceiptDate());
            feeReceiptPojo.setReceiptId(feeReceipt.getFeeReceiptID());
            feeReceiptPojo.setPaidAmt(feeReceipt.getTotalReceived());
            feeReceiptPojo.setReceiptNo(feeReceipt.getReceiptNo());
            feeReceiptPojos.add(feeReceiptPojo);
        }
        return feeReceiptPojos;
    }

    public StudentFee getStudentDetails(Long id) {
        List<Student> student = bsStudentRepository.findByStudentId(id);
        StudentFee feeReceipt = bsStudentFeeRepository.findByStudent(student.get(0));
        return feeReceipt;
    }

    public List<StudentFeeDetails> getStudentFeeDetails(StudentFee studentDetails) {
        StudentFee feeReceipt = bsStudentFeeRepository.findByStudentFeeId(studentDetails.getStudentFeeId());
        List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(feeReceipt);

        return studentFeeDetails;
    }

    public List<StudentFee> getStudentFeeExportToExcel(String searchText, String grade, String student) {
        List<StudentFee> studList = new ArrayList<>();
        if (grade != "" && student == "") {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(grade));
            studList = bsStudentFeeRepository.findByGradeMaster(grdmstrobj);
        } else if (student != "" && grade == "") {
            studList = bsStudentFeeRepository.findAllByStudentFeeId(Long.valueOf(student));
        } else if (student != "" && grade != "") {
            studList = bsStudentFeeRepository.findAllByStudentFeeId(Long.valueOf(student));
        } else if (!StringUtils.isEmpty(searchText)) {
            studList = bsStudentFeeRepository.findStudentFeeByStudentNameIsLike("%" + searchText + "%");
        }
        else {
            studList = (List<StudentFee>) bsStudentFeeRepository.findAll();
        }
        return studList;
    }

    public List<Student> getStudentExportToExcelList(String searchText, String grade, String student, String checkboxStatusForStudent) {
        List<Student> studList = new ArrayList<>();
        if (grade != "" && student == "") {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(grade));
            studList = bsStudentRepository.findByGradeMaster(grdmstrobj);
        } else if (student != "" && grade == "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student != "" && grade != "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("false")) {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("true")) {
            String status = "InActive";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else if (!StringUtils.isEmpty(searchText)) {
            studList = bsStudentRepository.findStudentByStudentNameIsLike("%" + searchText + "%");
        }
        else {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        }
        return studList;
    }


    public FeeReceiptPojo getDuplicateReceipt(Long id) {
        FeeReceipt feeReceipt = bsFeeReceiptRepository.findOne(id);
        List<FeeReceiptDetails> feeReceiptDetails = bsFeeReceiptDetailsRepository.findByFeeReceipt(feeReceipt);
        StudentPojo studentPojo = new StudentPojo();
        Student student = feeReceipt.getStudentFee().getStudent();
        studentPojo.setStudentName(student.getStudentName());
        studentPojo.setFatherName(student.getFatherName());
        studentPojo.setAdmissionFormNo(student.getAdmissionFormNo());
        studentPojo.setAcademicYear(student.getAcademicYearMaster().getAcdyrName());
        studentPojo.setGradeName(student.getGradeMaster().getGradeName());
        studentPojo.setReceiptNo(feeReceipt.getReceiptNo());
        studentPojo.setStudentProfileId(student.getStudentProfileId());
        studentPojo.setMotherName(student.getMotherName());
        FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
        feeReceiptPojo.setStudentPojo(studentPojo);
        feeReceiptPojo.setPaymentType(feeReceipt.getPaymentMode());
        feeReceiptPojo.setTotalPaid(feeReceipt.getTotalReceived());
        feeReceiptPojo.setReceiptDate(feeReceipt.getReceiptDate());
        feeReceiptPojo.setCardNo(feeReceipt.getCardNo());
        feeReceiptPojo.setApprovalCode(feeReceipt.getApprovalCode());
        feeReceiptPojo.setTransactionNo(feeReceipt.getChequeNo());
        feeReceiptPojo.setApprovalCode(feeReceipt.getApprovalCode());
        feeReceiptPojo.setChequeDate(feeReceipt.getChequeDate());
        feeReceiptPojo.setBankName(feeReceipt.getBankName());
        feeReceiptPojo.setStudentFee(feeReceipt.getStudentFee());
        List<FeeTypeMasterPojo> feeTypeMasterPojoList = new ArrayList<>();
        for (FeeReceiptDetails feeReceiptDetails1 : feeReceiptDetails) {
            FeeTypeMasterPojo feeTypeMasterPojo = new FeeTypeMasterPojo();
            feeTypeMasterPojo.setFeeTypeName(feeReceiptDetails1.getFeeType());
            feeTypeMasterPojo.setPayable(feeReceiptDetails1.getTotalReceived());
            feeTypeMasterPojo.setCheckBox(true);
            feeTypeMasterPojoList.add(feeTypeMasterPojo);
        }
        feeReceiptPojo.setFeeTypeMasterPojoList(feeTypeMasterPojoList);
        return feeReceiptPojo;
    }
    public List<FeeReceiptPojo> receiptNoReport(ReportPojo reportPojo) {
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        List<FeeReceiptPojo> feeReceiptPojoArrayList = new ArrayList<>();
        List<FeeReceipt> list = retrieveFeeCollected(reportPojo);
        if (list != null) {
            for (FeeReceipt receipt : list) {
                FeeReceiptPojo feeReceiptPojo=new FeeReceiptPojo();
                feeReceiptPojo.setStudentName(receipt.getStudentFee().getStudentName());
                feeReceiptPojo.setStudentProfileId(receipt.getStudentFee().getStudent().getStudentProfileId());
                feeReceiptPojo.setReceiptId(receipt.getFeeReceiptID());
                feeReceiptPojo.setReceiptNo(receipt.getReceiptNo());
                feeReceiptPojo.setReceiptDate(receipt.getReceiptDate());
                feeReceiptPojo.setPaidAmt(receipt.getTotalReceived());
                feeReceiptPojoArrayList.add(feeReceiptPojo);
            }
        }
        return feeReceiptPojoArrayList;
    }

    public StudentFeeDto saveStudentFeeDetails(StudentFeeDto studentFeeDto) throws JSONException, IOException {
        StudentFee studentFee = bsStudentFeeRepository.findByStudentFeeId(studentFeeDto.getStudentFeeId());
        if (studentFee != null) {
            FeeReceipt feeReceipt = new FeeReceipt();
            studentFee.setPaymentDate(studentFeeDto.getPaymentDate());
            studentFee.setPaymentType(studentFeeDto.getPaymentType());
            if (studentFee.getPaidAmount() < studentFee.getTotalPayable()) {
                studentFee.setPaidAmount(studentFeeDto.getPayingFee() + studentFee.getPaidAmount());
                studentFee.setDueAmount(studentFee.getTotalPayable() - studentFee.getPaidAmount());
                if (StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Cash")) {
                    if (studentFee.getCashAmt() == null) {
                        studentFee.setCashAmt(0.0);
                    }
                    studentFee.setCashAmt(studentFeeDto.getCashAmt() + studentFee.getCashAmt());
                    feeReceipt.setCashAmt(studentFeeDto.getCashAmt());
                } else if (StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Card")) {
                    if (studentFee.getCardAmt() == null) {
                        studentFee.setCardAmt(0.0);
                    }
                    studentFee.setCardDetails(studentFeeDto.getCardDetails());
                    studentFee.setCardAmt(studentFeeDto.getCardAmt() + studentFee.getCardAmt());
                    feeReceipt.setCardAmt(studentFeeDto.getCardAmt());
                    if (StringUtils.isNotEmpty(studentFeeDto.getCardNo())) {
                        String cardNo = StringUtils.overlay(studentFeeDto.getCardNo(), StringUtils.repeat("X", studentFeeDto.getCardNo().length() - 4), 0, studentFeeDto.getCardNo().length() - 4);
                        feeReceipt.setCardNo(cardNo);
                        studentFeeDto.setCardNo(cardNo);
                    }
                    feeReceipt.setApprovalCode(studentFeeDto.getApprovalCode());
                } else if (StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Bank") || StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Online")) {
                    studentFee.setBankDetails(studentFeeDto.getBankDetails());
                    if (studentFee.getBankAmt() == null) {
                        studentFee.setBankAmt(0.0);
                    }
                    studentFee.setBankAmt(studentFeeDto.getBankAmt() + studentFee.getBankAmt());
                    feeReceipt.setBankAmt(studentFeeDto.getBankAmt());
                    feeReceipt.setBankName(studentFeeDto.getBankName());
                    feeReceipt.setChequeNo(studentFeeDto.getChequeNo());
                    feeReceipt.setChequeDate(studentFeeDto.getChequeDate());
                }
                bsStudentFeeRepository.save(studentFee);
                if (studentFee.getDueAmount() == 0) {
                    List<SchedulerData> schedulerDataList = bsSchedulerRepository.findAllByStudent(studentFee.getStudent().getStudentId().toString());
                    bsSchedulerRepository.delete(schedulerDataList);
                }
                feeReceipt.setTotalPayable(studentFee.getTotalPayable());
                feeReceipt.setStudentFee(studentFee);
                feeReceipt.setReceiptDate(studentFee.getPaymentDate());
                feeReceipt.setTotalReceived(studentFeeDto.getPayingFee());
                feeReceipt.setPaymentMode(studentFee.getPaymentType());
                SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
                schoolBranchDetails.setReceiptNo(schoolBranchDetails.getReceiptNo() + 1);
                bsSchoolBranchDetailsRepository.save(schoolBranchDetails);
                feeReceipt.setReceiptNo(String.valueOf(schoolBranchDetails.getReceiptNo()));
                bsFeeReceiptRepository.save(feeReceipt);
                for (StudentFeeDetailsPojo studentFeeDetailsPojo : studentFeeDto.getStudentFeeDetailsPojoList()) {
                    double amt = 0.00;
                    StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findOne(studentFeeDetailsPojo.getStudentFeeDetailsId());
                    for (InstallmentsPojo installmentsPojo : studentFeeDetailsPojo.getInstallmentsPojos()) {
                        Installments installments = bsInstallmentsRepository.findOne(installmentsPojo.getInstallmentsId());
                        installments.setPaidAmt(installments.getPaidAmt() + installmentsPojo.getPayingAmt());
                        if (installments.getInstallmentsAmount() != null)
                            if (installments.getInstallmentsAmount() == installments.getPaidAmt()) {
                                installments.setStatus("paid");
                                bsSchedulerRepository.delete(bsSchedulerRepository.findAllByInstallmentsId(installments.getInstallmentsId().toString()));
                            }
                        studentFeeDetails.setPendingFee(studentFeeDetails.getPendingFee() - installmentsPojo.getPayingAmt());
                        if (studentFeeDetails.getPaidAmt() != null)
                            studentFeeDetails.setPaidAmt(installmentsPojo.getPayingAmt() + studentFeeDetails.getPaidAmt());
                        else
                            studentFeeDetails.setPaidAmt(installmentsPojo.getPayingAmt());
                        amt += installmentsPojo.getPayingAmt();
                        bsInstallmentsRepository.save(installments);
                    }
                    studentFeeDetailsPojo.setPayingAmount(amt);
                    if (studentFeeDetails.getPaidAmt() == studentFeeDetails.getFeeTypeAmount())
                        studentFeeDetails.setStatus("paid");
                    bsStudentFeeDetailsRepository.save(studentFeeDetails);
                    if (studentFeeDetailsPojo.isCheckBox() == true) {
                        FeeReceiptDetails feeReceiptDetails = new FeeReceiptDetails();
                        feeReceiptDetails.setFeeReceipt(feeReceipt);
                        feeReceiptDetails.setReceiptNo(feeReceipt.getReceiptNo());
                        feeReceiptDetails.setFeeType(studentFeeDetailsPojo.getFeeTypeName());
                        feeReceiptDetails.setTotalReceived(amt);
                        bsFeeReceiptDetailsRepository.save(feeReceiptDetails);
                    }
                }
            }
        }
        Gson gson = new Gson();
        TransactionPojo transactionPojo = TransactionMapper.feePaymentToTransaction(studentFeeDto);
        String JsonInString = gson.toJson(transactionPojo);
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if (cartMaster!=null){ cartID = cartMaster.getHiConnectCompanyRegNo();}
        String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "CP", "sales");
        String JsonBrainyStudentString = gson.toJson(studentFeeDto);
        User user = bsUserRepository.findOne(1l);
        String branchCode = "";
        if(!user.equals(null)) branchCode = user.getBranchCode();
        String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString,branchCode,branchCode,"FeePayment","AddMaster");
        //String statusCode = pusherService.SavePusher(JsonInString, "", "SIN","Purchase");
        return studentFeeDto;
    }

    public static String readDomainName() throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = BsUserService.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(in);
            in.close();
        } catch (Exception e) {
        } finally {
            in.close();
        }
        return prop.getProperty("hisaas_domainname");
    }

    public String getHiConnectId(CartMasterPojo cartMasterPojo) throws IOException, JSONException {
        String url = readDomainName() + "/hiConnectService/getHiconnectNumber";//HiConnect Server URL
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("email", cartMasterPojo.getEmail());
        objectNode.put("username", cartMasterPojo.getUserName());
        objectNode.put("password", cartMasterPojo.getPassword());
        //  objectNode.put("type_flag", notificationType);
        //Spring Rest Client Call
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(objectNode.toString(), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        // LOGGER.info("Get All NotificationsList---->" + responseEntity);
        String uu = null;
        if(responseEntity.getStatusCode().is2xxSuccessful()){

            String jsonString = responseEntity.getBody();
            JSONObject jsonObj = new JSONObject(jsonString);
            String detailObj = jsonObj.get("object").toString();
            JSONObject jsonObj1 = new JSONObject(detailObj);
            String detailObj1 = jsonObj1.get("hiConnectCompnyRegNo").toString();
            uu=detailObj1;

        }

        String jsonString = responseEntity.getBody();
        // JSONObject jsonObj = new JSONObject(jsonString);
        //  String detailObj = jsonObj.get("object").toString();



        return uu;

    }

    public CartMaster Cartmastersave(CartMasterPojo cartMasterPojo) throws IOException, JSONException {
        CartMaster master=CartMasterRepository.findOne(1l);
        String uu=getHiConnectId(cartMasterPojo);
        if(!uu.equals("null")){
            master.setPassword(cartMasterPojo.getPassword());
            master.setEmail(cartMasterPojo.getEmail());
            master.setUserName(cartMasterPojo.getUserName());
            master.setHiConnectCompanyRegNo(uu);
            CartMasterRepository.save(master);}
        return master;
    }
    public List<CartMasterPojo>cartMasterList(){
        List<CartMaster> cartMasters = new ArrayList<>();
        cartMasters = (List<CartMaster>) CartMasterRepository.findAll();
        List<CartMasterPojo> PojoList = ObjectMapperUtils.mapAll(cartMasters, CartMasterPojo.class);
        return PojoList;
    }

    public void downloadFeeTypeExcel(OutputStream out,String searchText,String type) {
        try {
            List<FeeTypeMasterPojo> feeTypeMasterPojoList = feeTypeMasterList2(searchText,"true");
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("FeeType Name");
            headerRow1.createCell(1).setCellValue("Amount");
            headerRow1.createCell(2).setCellValue("Academic Year");
            headerRow1.createCell(3).setCellValue("Grade Master");
            headerRow1.createCell(4).setCellValue("Status");
            int i = 0;
            for (FeeTypeMasterPojo pojo : feeTypeMasterPojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pojo.getFeeTypeName());
                row.createCell(1).setCellValue(pojo.getFeeAmount());
                row.createCell(2).setCellValue(pojo.getAcdyrmaster().getAcdyrName());
                row.createCell(3).setCellValue(pojo.getGradeMaster().getGradeName());
                row.createCell(4).setCellValue(pojo.getStatus());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    public void downloadGradeMasterExcel(OutputStream out,String searchText,String type,String userId) {
        try {
            List<GradeMasterPojo> gradeMasterPojoList = gradeMasterList2(searchText,type,userId);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Grade Master Name");
            headerRow1.createCell(1).setCellValue("Description");
            int i = 0;
            for (GradeMasterPojo pojo : gradeMasterPojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pojo.getGradeName());
                row.createCell(1).setCellValue(pojo.getGradeDescription());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    public void downloadAcademicYearExcel(OutputStream out,String searchText,String type) {
        try {
            List<AcademicYearMasterPojo> academicYearMasterPojoList = getAcademicYear2List(searchText,type);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("AcademicYear Name");
            headerRow1.createCell(1).setCellValue("Description");
            headerRow1.createCell(2).setCellValue("From Date(yyyy/MM/dd)");
            headerRow1.createCell(3).setCellValue("To Date(yyyy/MM/dd)");
            int i = 0;
            for (AcademicYearMasterPojo pojo : academicYearMasterPojoList) {
                HSSFRow row = sheet.createRow(++i);
                Date startDate = pojo.getFromDate();
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String recDate = df.format(startDate);
                Date endDate = pojo.getToDate();
                DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                String date = df1.format(endDate);
                row.createCell(0).setCellValue(pojo.getAcdyrName());
                row.createCell(1).setCellValue(pojo.getAcdyrDescription());
                row.createCell(2).setCellValue(recDate);
                row.createCell(3).setCellValue(date);
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
}