package com.hyva.bsfms.bs.bsendpoints;
import com.google.gson.Gson;
import com.hyva.bsfms.Interceptor.UserInterceptor;
import com.hyva.bsfms.bs.bsentities.*;
import com.hyva.bsfms.bs.bspojo.*;
import com.hyva.bsfms.bs.bsrespositories.*;
import com.hyva.bsfms.bs.bsservice.BsUserService;
import com.hyva.bsfms.bs.bspojo.EntityResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.*;
import java.util.stream.Collectors;
/**
 * Created by azgar on 3/13/18.
 */
@RestController
@RequestMapping("/bs")
public class BsController extends HttpServlet{
    //        @Autowired
//        BshimOrdersService bshimOrdersService;
    @Autowired
    BsUserService bsUserService;
    @Autowired
    UserInterceptor userInterceptor;
    @Autowired
    CartMasterRepository cartMaster;
    @Autowired
    BsStudentRepository studentRepository;
    @Autowired
    BsAcademicYearMasterRepository bsAcademicYearMasterRepository;
    @Autowired
    BsGrademasterRepository bsGrademasterRepository;
    @Autowired
    BsFeeTypeMasterRepository bsFeeTypeMasterRepository;
    @Value("${php_domainame}")
    private String PROPERTY_SAAS_DOMAIN;
    @RequestMapping(value = "/login",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse login(@RequestBody User credentials) throws Exception {
//            User bshimData = bshimUserService.get(credentials) ;
        String accessToken = "12345";
        if (StringUtils.isBlank(credentials.getEmail()) || StringUtils.isBlank(credentials.getUserName()) || StringUtils.isBlank(credentials.getPasswordUser())) {
            return new EntityResponse(HttpStatus.OK.value(), "Invalid User");
        }
        return new EntityResponse(HttpStatus.OK.value(), "success", credentials);
    }

    @RequestMapping(value = "/saveLoginDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveLoginDetails(@RequestBody BsUserPojo bsUserPojo) {
        return bsUserService.saveUserDetails(bsUserPojo);
    }

    @RequestMapping(value = "/userValidate", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    public User userValidate(@RequestBody BsUserPojo bsUserPojo,HttpServletResponse response,HttpServletRequest request,Object handler) throws Exception {
        User user = bsUserService.userValidate(bsUserPojo);
        //If user Table is empty fetching Branchdata from server and saving in user Table
        if(user == null){
            RestTemplate restTemplate = new RestTemplate();
            String url = PROPERTY_SAAS_DOMAIN + "desktoplogin";
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("branchcode", bsUserPojo.getBranchCode());
            objectNode.put("password", bsUserPojo.getPasswordUser());
            objectNode.put("username", bsUserPojo.getUserName());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(objectNode.toString(), headers);
            ResponseEntity<String> responseEntity=restTemplate.exchange(url , HttpMethod.POST,entity,String.class);
            String jsonString = responseEntity.getBody();
            JSONObject jsonObject = new JSONObject(jsonString);
            if(!StringUtils.equalsIgnoreCase(jsonObject.getString("status"),"fail")) {
                String loginData = jsonObject.getString("data");
                JSONObject loginDataObj = new JSONObject(loginData);
                BsUserPojo userPojo = new BsUserPojo();
                userPojo.setBranchCode(loginDataObj.getString("branch_code"));
                if(!StringUtils.isNotEmpty(loginDataObj.getString("primaryemail"))) {
                    if (!StringUtils.equalsIgnoreCase(loginDataObj.getString("primaryemail"), "null")) {
                        userPojo.setEmail(loginDataObj.getString("primaryemail"));
                    }
                }
                if(!StringUtils.isNotEmpty(loginDataObj.getString("primarycontactnumber"))) {
                    if (!StringUtils.equalsIgnoreCase(loginDataObj.getString("primarycontactnumber"), "null")) {
                        userPojo.setPhone(loginDataObj.getString("primarycontactnumber"));
                    }
                }
                userPojo.setPasswordUser(loginDataObj.getString("plain_pass"));
                userPojo.setUserName(loginDataObj.getString("username"));
                userPojo.setBranchId(loginDataObj.getLong("fk_branchid"));
                userPojo.setOrganizationId(loginDataObj.getLong("fk_organisationid"));
                bsUserService.saveUserDetails(userPojo);
                CartMaster cartmaster=new CartMaster();
                cartmaster.setEmail("company Email");
                cartmaster.setUserName(" userName");
                cartmaster.setPassword(" Password");
                cartMaster.save(cartmaster);
                HttpSession session = request.getSession();
                session.setAttribute("email", userPojo.getUserName());
//              Cookie c = new Cookie("email",userPojo.getUserName());
//              response.addCookie(c);
                userInterceptor.preHandle(request, response, handler);
            }
        }
        return bsUserService.userValidate(bsUserPojo);
    }
//    @RequestMapping(value = "/userValidate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    public User userValidate(@RequestBody BsUserPojo bsUserPojo) {
//        return bsUserService.userValidate(bsUserPojo);
//    }

    @RequestMapping(value = "/getUserDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getUserDetailsList() {
        List<BsUserPojo> bsUserPojos = bsUserService.sassUserList();
        return new EntityResponse(HttpStatus.OK.value(), " success", bsUserPojos);
    }

    @RequestMapping(value = "/saveNewEnquiry",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity saveNewStudent(@RequestBody EnquiryFormDTO enquiryFormDTO){
        EnquiryForm enquiryForm = null;
        enquiryForm = bsUserService.saveNewEnquiry(enquiryFormDTO);
        return ResponseEntity.status(200).body(enquiryFormDTO);
    }
    @RequestMapping(value = "/saveoldEnquiry",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity saveoldEnquiry(@RequestBody EnquiryFormDTO enquiryFormDTO){
        EnquiryForm enquiryForm = null;
        enquiryForm = bsUserService.saveoldEnquiry(enquiryFormDTO);
        return ResponseEntity.status(200).body(enquiryFormDTO);
    }

//    @RequestMapping(value = "/getStudentList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public EntityResponse getStudentList() {
//        List<EnquiryFormDTO> studentList = bsUserService.studentList();
//        return new EntityResponse(HttpStatus.OK.value(), " success", studentList);
//    }


    @RequestMapping(value = "/saveNewStudent", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveNewStudent(@RequestBody StudentPojo saveStudentDetails) throws Exception {
        Student student = null;
        student = bsUserService.SaveStudentDetails(saveStudentDetails);
        return ResponseEntity.status(200).body(student);
    }

    @RequestMapping(value = "/getStudentByName", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getStudentByName(@RequestParam(value = "name", required = false) String name,
                                           @RequestAttribute String userName) {
//        EnquiryForm enquiryForm = userRepository.findByPhoneNumberOrUsername(userName,userName);
        return ResponseEntity.status(200).body(bsUserService.getStudentByName(name));
    }

    @RequestMapping(value = "/getFormSetupValue", method = RequestMethod.POST)
    public ResponseEntity getFormSetup() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("message", bsUserService.getFormSetUpNo());
        return ResponseEntity.status(200).body(objectNode.toString());
    }
    @RequestMapping(value = "/getEnquiry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getEnquiry() {
        List<EnquiryFormDTO> enquiryFormDTOList = bsUserService.getEnquiry();
        return new EntityResponse(HttpStatus.OK.value(), " success", enquiryFormDTOList);
    }
    @RequestMapping(value = "/saveGradeMasterImport" ,method = RequestMethod.POST)
    public ResponseEntity saveGradeMasterImport(@RequestAttribute String userId,@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null) {
                    GradeMasterPojo gradeMasterPojo = new GradeMasterPojo();
                    String name = row.getCell(0).toString();
                    Cell desc = row.getCell(1);
                    gradeMasterPojo.setGradeName(name);
                    gradeMasterPojo.setGradeDescription(desc==null?"":desc.toString());
                    gradeMasterPojo.setGradeStatus("Active");
                    saveNewGradeMaster(userId,gradeMasterPojo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/studentImportSave" ,method = RequestMethod.POST)
    public ResponseEntity studentImportSave(@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null) {
                    StudentPojo pojo = new StudentPojo();
                    Cell accyr = row.getCell(0);
                    AcademicYearMaster academicYearMaster=bsAcademicYearMasterRepository.findByAcdyrName(accyr.toString());
                    Cell date = row.getCell(1);
                    Cell formNo = row.getCell(2);
                    Cell grade = row.getCell(3);
                    GradeMaster gradeMaster=bsGrademasterRepository.findByGradeName(grade.toString());
                    Cell jngdate = row.getCell(4);
                    Cell name = row.getCell(5);
                    Cell perAddrs = row.getCell(6);
                    Cell dob = row.getCell(7);
                    Cell localAddrs = row.getCell(8);
                    Cell gender = row.getCell(9);
                    Cell phyCon = row.getCell(10);
                    Cell adharNo = row.getCell(11);
                    Cell religion = row.getCell(12);
                    Cell fatherName = row.getCell(13);
                    Cell fatherId = row.getCell(14);
                    Cell fatherMobile = row.getCell(15);
                    Cell fatherOcupt = row.getCell(16);
                    Cell motherName = row.getCell(17);
                    Cell motherId = row.getCell(18);
                    Cell motherMobile = row.getCell(19);
                    Cell motherOcupt = row.getCell(20);
                    Cell primaryContNo = row.getCell(21);
                    Cell guadianName = row.getCell(22);
                    Cell parentsAnulIncm = row.getCell(23);
                    Cell guadianNo = row.getCell(24);
                    Cell status=row.getCell(25);

                    //getting values
                    pojo.setAcdYearId(academicYearMaster.getAcdyrId());
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date date1 = sdf.parse(date.toString());
                    pojo.setDateOfAdmission(new java.sql.Date(date1==null?null:date1.getTime()));
                    pojo.setAdmissionFormNo(formNo==null?null:formNo.toString());
                    pojo.setStudentName(name==null?null:name.toString());
                    java.util.Date date2 = sdf.parse(dob.toString());
                    pojo.setDateofbirth(new java.sql.Date(date2==null?null:date2.getTime()));
                    pojo.setGender(gender==null?null:gender.toString());
                    pojo.setReligion(religion==null?null:religion.toString());
                    pojo.setAadhaarNo(adharNo==null?null:adharNo.toString());
                    pojo.setFatherName(fatherName==null?null:fatherName.toString());
                    pojo.setFatherContactNo(fatherMobile==null?null:fatherMobile.toString());
                    pojo.setMotherName(motherName==null?null:motherName.toString());
                    pojo.setMotherContactNo(motherMobile==null?null:motherMobile.toString());
                    pojo.setPrimaryContactNo(primaryContNo==null?null:primaryContNo.toString());
                    pojo.setAnnualIncome(parentsAnulIncm==null?null:Double.parseDouble(parentsAnulIncm.toString()));
                    pojo.setStudentStatus(status==null ? "Active" :status.toString());
                    if(StringUtils.equalsIgnoreCase(pojo.getStudentStatus(),"Active")){
                        pojo.setStudentStatus("true");
                    }else {
                        pojo.setStudentStatus("false");
                    }
                    pojo.setGradeId(gradeMaster.getGradeId());
                    java.util.Date date3 = sdf.parse(jngdate.toString());
                    pojo.setDateOfJoining(new java.sql.Date(date3==null?null:date3.getTime()));
                    pojo.setPermanentAddress(perAddrs==null?null:perAddrs.toString());
                    pojo.setPresentAddress(localAddrs==null?null:localAddrs.toString());
                    pojo.setPhysicalCondition(phyCon==null?null:phyCon.toString());
                    pojo.setFatherEmailId(fatherId==null?"":fatherId.toString());
                    pojo.setFatherOccupation(fatherOcupt==null?null:fatherOcupt.toString());
                    pojo.setMotherEmailId(motherId==null?"":motherId.toString());
                    pojo.setMotherOccupation(motherOcupt==null?null:motherOcupt.toString());
                    pojo.setGaurdianName(guadianName==null?null:guadianName.toString());
                    pojo.setGaurdianNumber(guadianNo==null?null:guadianNo.toString());
                    saveNewStudent(pojo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/feeImportSave" ,method = RequestMethod.POST)
    public ResponseEntity feeImportSave(@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null) {
                    StudentPojo pojo = new StudentPojo();
                    Cell name = row.getCell(0);
                    Cell profileId = row.getCell(1);
                    Cell feeTypeName = row.getCell(2);
                    Cell status = row.getCell(3);
                    Cell feeAmt = row.getCell(4);
                    Cell payFee = row.getCell(5);
                    Cell payable = row.getCell(6);
                    Cell discount = row.getCell(7);
                    Cell value = row.getCell(8);
                    Cell remark = row.getCell(9);
                    Cell installment = row.getCell(10);
                    Student student=studentRepository.findByStudentNameAndStudentProfileId(name.toString(),profileId.toString());
                    FeeTypeMaster feeTypeMaster=bsFeeTypeMasterRepository.findByFeeTypeNameAndGradeMasterAndAcdyrmasterAndFeeAmount(feeTypeName.toString(),student.getGradeMaster(),student.getAcademicYearMaster(),feeAmt.getNumericCellValue());
                    FeeTypeMasterPojo feeTypeMasterPojo=new FeeTypeMasterPojo();
                    feeTypeMasterPojo.setInstallments(new Double(installment.toString()).intValue());
                    feeTypeMasterPojo.setFeeTypeName(feeTypeMaster.getFeeTypeName());
                    feeTypeMasterPojo.setPayingFee(feeTypeMaster.getPayingFee());
                    feeTypeMasterPojo.setFeeAmount(feeTypeMaster.getFeeAmount());
                    feeTypeMasterPojo.setFeeTypeId(feeTypeMaster.getFeeTypeId());
                    feeTypeMasterPojo.setStatus(feeTypeMaster.getStatus());
                    feeTypeMasterPojo.setGradeMaster(student.getGradeMaster());
                    feeTypeMasterPojo.setAcdyrmaster(student.getAcademicYearMaster());
                    feeTypeMasterPojo.setPayable(payable.getNumericCellValue());
                    feeTypeMasterPojo.setDiscount(discount.getNumericCellValue());
                    feeTypeMasterPojo.setDiscountRemarks(remark==null?null:remark.toString());
                    feeTypeMasterPojo.setCheckBox(true);
                    feeTypeMasterPojo.setValue("true");
                    List<InstallmentsPojo> installmentsPojos=new ArrayList<>();
                    for(int j=11;j<34;j++){
                        InstallmentsPojo installmentsPojo=new InstallmentsPojo();
                        if(row.getCell(j)!=null&&row.getCell(j+1)!=null&&feeTypeMasterPojo.getInstallments()>installmentsPojos.size()){
                            installmentsPojo.setInstallmentsAmount(row.getCell(j).getNumericCellValue());
                            String date=row.getCell(++j).toString();
                            if(!StringUtils.isEmpty(date)) {
                                java.util.Date date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(date);
                                installmentsPojo.setDueDate(new java.sql.Date(date1.getTime()));
                            }
                            installmentsPojos.add(installmentsPojo);
                        }else {
                            break;
                        }
                    }
                    feeTypeMasterPojo.setInstallmentsPojosList(installmentsPojos);
                    List<FeeTypeMasterPojo> feeTypeMasterPojos=new ArrayList<>();
                    feeTypeMasterPojos.add(feeTypeMasterPojo);
                    pojo.setStudentName(name==null?null:name.toString());
                    pojo.setStudentProfileId(profileId==null?null:profileId.toString());
                    pojo.setFeeTypeMasterPojoList(feeTypeMasterPojos);
                    bsUserService.SaveStudentfeee(pojo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/saveFeeTypeImport" ,method = RequestMethod.POST)
    public ResponseEntity saveFeeTypeImport(@RequestAttribute String userId,@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null) {
                    FeeTypeMasterPojo feeTypeMasterPojo = new FeeTypeMasterPojo();
                    String name = row.getCell(0).toString();
                    String amount = row.getCell(1).toString();
                    String acedemicYear = row.getCell(2).toString();
                    String grade = row.getCell(3).toString();
                    feeTypeMasterPojo.setFeeTypeName(name);
                    feeTypeMasterPojo.setFeeAmount(Double.valueOf(amount));
                    feeTypeMasterPojo.setAcdyrName(acedemicYear);
                    feeTypeMasterPojo.setGradeName(grade);
                    feeTypeMasterPojo.setStatus("Active");
                    feeTypeMasterPojo.setValue("true");
                    saveNewFeeMaster(feeTypeMasterPojo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/saveAcademicYearImport" ,method = RequestMethod.POST)
    public ResponseEntity saveAcademicYearImport(@RequestAttribute String userId,@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null) {
                    AcademicYearMasterPojo academicYearMasterPojo = new AcademicYearMasterPojo();
                    String name = row.getCell(0).toString();
                    Cell desc = row.getCell(1);
                    String fromDate = row.getCell(2).toString();
                    String toDate = row.getCell(3).toString();
                    SimpleDateFormat parseFormat =
                            new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date startDate = parseFormat.parse(fromDate);
                    java.util.Date endDate = parseFormat.parse(toDate);
                    academicYearMasterPojo.setAcdyrName(name);
                    academicYearMasterPojo.setAcdyrDescription(desc==null?null:desc.toString());
                    academicYearMasterPojo.setFromDate(new java.sql.Date(startDate.getTime()));
                    academicYearMasterPojo.setToDate(new java.sql.Date(endDate.getTime()));
                    academicYearMasterPojo.setStatus("Active");
                    saveAcademicMaster(academicYearMasterPojo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/saveScheduler", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveScheduler(@RequestBody MailSchedulerData mailSchedulerData)throws Exception {
        bsUserService.saveMailSchedule(mailSchedulerData);
        return ResponseEntity.status(HttpStatus.OK).body(mailSchedulerData  );
    }

    @RequestMapping(value = "/getCountryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCountryList(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.status(200).body(bsUserService.getCountryList(type));
    }

    @RequestMapping(value = "/saveCountry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCountry(@RequestBody CountryDTO countryDTO) {
        return ResponseEntity.status(200).body(bsUserService.saveCountry(countryDTO));
    }

    @RequestMapping(value = "/getStateListBasedOnCountry", method = RequestMethod.POST)
    public ResponseEntity getStateListBasedOnCountry(@RequestParam(value = "countryName") String country) {
        return ResponseEntity.status(200).body(bsUserService.getStateListBasedOnCountry(country));
    }

    @RequestMapping(value = "/getStateList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getStateList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "searchText", required = false) String searchText,
                                       @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(bsUserService.getStateList(type, basePojo, searchText));
    }
    @RequestMapping(value = "/stateList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity stateList() {
        List<StateDTO> stateDTOS = bsUserService.stateList();
        return ResponseEntity.status(200).body(stateDTOS);
    }

    @RequestMapping(value = "/CityList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity CityList() {
        List<CityDTO> cityDTOS = bsUserService.cityList();
        return ResponseEntity.status(200).body(cityDTOS);
    }

    @RequestMapping(value = "/saveState", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveState(@RequestBody StateDTO state) {
        return ResponseEntity.status(200).body(bsUserService.saveState(state));
    }

    @RequestMapping(value = "/saveCity", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCity(@RequestBody CityDTO city) {
        return ResponseEntity.status(200).body(bsUserService.saveCity(city));
    }

    @RequestMapping(value = "/editCity", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCity(@RequestParam(value = "cityName", required = false) String cityName) {
        return ResponseEntity.status(200).body(bsUserService.editCity(cityName));
    }

    @RequestMapping(value = "/deleteState", method = RequestMethod.POST, produces = "application/json")
    public void deleteState(@RequestParam(value = "stateName", required = false) String stateName) {
        bsUserService.deleteState(stateName);
    }

    @RequestMapping(value = "/getStateCity", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getStateCity(@RequestParam(value = "stateName", required = false) String stateName) {
        return ResponseEntity.status(200).body(bsUserService.getStateCity(stateName));
    }

    @RequestMapping(value = "/getCityListBasedOnState", method = RequestMethod.POST)
    public ResponseEntity getCityListBasedOnState(@RequestParam(value = "stateName") String state) {
        return ResponseEntity.status(200).body(bsUserService.getCityListBasedOnState(state));
    }

    @RequestMapping(value = "/getCityList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCityList(@RequestParam(value = "type", required = false) String type,
                                      @RequestParam(value = "searchText", required = false) String searchText,
                                      @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(bsUserService.getCityList(type, basePojo, searchText));
    }

    @RequestMapping(value = "/getCountryState", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCountryState(@RequestParam(value = "countryName", required = false) String countryName) {
        return ResponseEntity.status(200).body(bsUserService.getCountryState(countryName));
    }

    @RequestMapping(value = "/deleteCountry", method = RequestMethod.POST, produces = "application/json")
    public void deleteCountry(@RequestParam(value = "countryName", required = false) String countryName) {
        bsUserService.deleteCountry(countryName);
    }

    @RequestMapping(value = "/editState", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editState(@RequestParam(value = "stateName", required = false) String stateName) {
        return ResponseEntity.status(200).body(bsUserService.editState(stateName));
    }

    @RequestMapping(value = "/editCountry", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCountry(@RequestParam(value = "countryName", required = false) String countryName) {
        return ResponseEntity.status(200).body(bsUserService.editCountry(countryName));
    }


    @RequestMapping(value = "/getPaginatedCountryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedCountryList(@RequestParam(value = "type", required = false) String type,
                                                  @RequestParam(value = "searchText", required = false) String searchText,
                                                  @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(bsUserService.getPaginatedCountryList(type, basePojo, searchText));
    }

    @RequestMapping(value = "/getPaginatedFormsetupList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedFormSetupList(@RequestParam(value = "searchText") String searchText,
                                                    @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(bsUserService.getPaginatedFormSetUpList(basePojo, searchText));
    }

    @RequestMapping(value = "/schedulerList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity schedulerList() {
        return ResponseEntity.status(HttpStatus.OK).body(bsUserService.getSchedulerList());
    }

    @RequestMapping(value = "/deleteMailScheduler", method = RequestMethod.POST)
    public ResponseEntity deleteMailScheduler(@RequestParam(value = "searchSchedulerText") String schedulerSearch) {
        bsUserService.deleteMailSchedulerDetails(schedulerSearch);
        return null;

    }

    @RequestMapping(value = "/saveNewFeeMaster", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveNewFeeMaster(@RequestBody FeeTypeMasterPojo saveFeeDetails) throws Exception {
        FeeTypeMaster master = null;
        master = bsUserService.SaveFeeTypeMaster(saveFeeDetails);
        return ResponseEntity.status(200).body(master);
    }

    @RequestMapping(value = "/saveNewGradeMaster", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveNewGradeMaster(@RequestAttribute String userId,@RequestBody GradeMasterPojo saveGradeDetails) throws Exception {
        GradeMaster master = null;
        saveGradeDetails.setUserId(userId);
        master = bsUserService.SaveGradeMaster(saveGradeDetails);
        return ResponseEntity.status(200).body(master);
    }

    @RequestMapping(value = "/saveAcademicMaster", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveAcademicMaster(@RequestBody AcademicYearMasterPojo saveAcademicMaster) throws Exception {
        AcademicYearMaster master = null;
        master = bsUserService.SaveAcademicYearMaster(saveAcademicMaster);
        return ResponseEntity.status(200).body(master);
    }
    @RequestMapping(value = "/savecartmasterdetails", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity savecartmasterdetails(@RequestBody CartMasterPojo cartMasterPojo) throws Exception {
        CartMaster master = null;
        master = bsUserService.Cartmastersave(cartMasterPojo);
        return ResponseEntity.status(200).body(master);
    }

    @RequestMapping(value = "/saveSchoolBranchDetails", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveSchoolBranchDetails(@RequestBody SchoolBranchDetailsPojo saveBranchDetails) throws Exception {
        SchoolBranchDetails master = null;
        master = bsUserService.SaveSchoolBranchDetails(saveBranchDetails);
        return ResponseEntity.status(200).body(master);
    }

    //getSchoolBranchDetailsList
    @RequestMapping(value = "/getSchoolBranchDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getSchoolBranchDetailsList() {
        List<SchoolBranchDetailsPojo> schoolBranchDetailsPojos = bsUserService.schoolBranchDetailsList();
        return new EntityResponse(HttpStatus.OK.value(), " success", schoolBranchDetailsPojos);
    }

    @RequestMapping(value = "/saveStudentFee", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveStudentFee(@RequestBody StudentFeeDto saveStudentFee) throws JSONException, IOException {
        return ResponseEntity.status(200).body(bsUserService.saveStudentFeeDetails(saveStudentFee));
    }
    @RequestMapping(value = "/receiptNoReport", method = RequestMethod.POST)
    public ResponseEntity receiptNoReport(@RequestBody ReportPojo reportPojo) {
        return ResponseEntity.status(200).body(bsUserService.receiptNoReport(reportPojo));
    }

    @RequestMapping(value = "/getReceiptDetails", method = RequestMethod.POST)
    public ResponseEntity getReceiptDetails(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(200).body(bsUserService.getReceiptDetails(id));
    }

    @RequestMapping(value = "/getReportReceiptDetails", method = RequestMethod.POST)
    public ResponseEntity getReportReceiptDetails(@RequestBody ReportPojo reportPojo) {
        return ResponseEntity.status(200).body(bsUserService.getReportDetails(reportPojo));
    }

    @RequestMapping(value = "/getStudentDueReportList", method = RequestMethod.POST)
    public ResponseEntity getStudentDueReportList(@RequestBody ReportPojo reportPojo) {
        return ResponseEntity.status(200).body(bsUserService.getStudentDueList(reportPojo));
    }


    @RequestMapping(value = "/saveformsetup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveformsetup(@RequestBody FormsetupDTO formsetupDTO) {
        return ResponseEntity.status(200).body(bsUserService.saveFormSetup(formsetupDTO));
    }

    @RequestMapping(value = "/editFormSetupMethod", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editFormSetupMethod(@RequestParam(value = "typeName", required = false) String typeName) {
        return ResponseEntity.status(200).body(bsUserService.editFormsetupMethod(typeName));
    }

    @RequestMapping(value = "/feeCollectedReportExcel", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity feeCollectedReportExcel(@RequestParam(value = "fromDate", required = false) String fromDate,
                                                  @RequestParam(value = "toDate", required = false) String toDate,
                                                  @RequestParam(value = "academicYear", required = false) String academicYear,
                                                  @RequestParam(value = "gradeIds", required = false) String gradeIds) throws ParseException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ReportPojo reportPojo = new ReportPojo();
        reportPojo.setFromDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).getTime()));
        reportPojo.setToDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(toDate).getTime()));
        reportPojo.setAcademicYearId(academicYear);
        if (gradeIds.length() > 0) {
            String g = gradeIds.substring(1, gradeIds.length() - 1);
            if (!StringUtils.isEmpty(g)) {
                List<Long> grades = Arrays.asList(g.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                reportPojo.setGradeIds(grades);
            }
        }
        bsUserService.downloadFeeCollectedReportExcel(outputStream, reportPojo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "FeeCollectedReport.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }



    @RequestMapping(value = "/feeCollectedReportPdf", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity feeCollectedReportPdf(@RequestParam(value = "fromDate", required = false) String fromDate,
                                                @RequestParam(value = "toDate", required = false) String toDate,
                                                @RequestParam(value = "academicYear", required = false) String academicYear,
                                                @RequestParam(value = "gradeIds", required = false) String gradeIds) throws ParseException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ReportPojo reportPojo = new ReportPojo();
        reportPojo.setFromDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).getTime()));
        reportPojo.setToDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(toDate).getTime()));
        reportPojo.setAcademicYearId(academicYear.toString());
        if (gradeIds.length() > 0) {
            String g = gradeIds.substring(1, gradeIds.length() - 1);
            if (!StringUtils.isEmpty(g)) {
                List<Long> grades = Arrays.asList(g.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                reportPojo.setGradeIds(grades);
            }
        }
        bsUserService.downloadFeeCollectedReportPdf(outputStream, reportPojo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "FeeCollectedReport.pdf" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(value = "/feeDueReportExcel", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity feeDueReportExcel(@RequestParam(value = "fromDate", required = false) String fromDate,
                                            @RequestParam(value = "toDate", required = false) String toDate,
                                            @RequestParam(value = "academicYear", required = false) String academicYear,
                                            @RequestParam(value = "gradeIds", required = false) String gradeIds) throws ParseException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ReportPojo reportPojo = new ReportPojo();
        reportPojo.setFromDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).getTime()));
        reportPojo.setToDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(toDate).getTime()));
        reportPojo.setAcademicYearId(academicYear);
        if (gradeIds.length() > 0) {
            String g = gradeIds.substring(1, gradeIds.length() - 1);
            if (!StringUtils.isEmpty(g)) {
                List<Long> grades = Arrays.asList(g.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                reportPojo.setGradeIds(grades);
            }
        }
        bsUserService.downloadFeeDueReportExcel(outputStream, reportPojo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "FeeDueReport.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(value = "/feeDueReportPdf", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity feeDueReportPdf(@RequestParam(value = "fromDate", required = false) String fromDate,
                                          @RequestParam(value = "toDate", required = false) String toDate,
                                          @RequestParam(value = "academicYear", required = false) String academicYear,
                                          @RequestParam(value = "gradeIds", required = false) String gradeIds) throws ParseException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ReportPojo reportPojo = new ReportPojo();
        reportPojo.setFromDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).getTime()));
        reportPojo.setToDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(toDate).getTime()));
        reportPojo.setAcademicYearId(academicYear);
        if (gradeIds.length() > 0) {
            String g = gradeIds.substring(1, gradeIds.length() - 1);
            if (!StringUtils.isEmpty(g)) {
                List<Long> grades = Arrays.asList(g.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                reportPojo.setGradeIds(grades);
            }
        }
        bsUserService.downloadFeeDueReportPdf(outputStream, reportPojo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "FeeDueReport.pdf" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(value = "/getReportCollected", method = RequestMethod.POST)
    public ResponseEntity getReportCollected(@RequestBody ReportPojo reportPojo) {
        return ResponseEntity.status(200).body(bsUserService.getReportCollected(reportPojo));
    }

    @RequestMapping(value = "/getDuplicateReceipt", method = RequestMethod.POST)
    public ResponseEntity getDuplicateReceipt(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(200).body(bsUserService.getDuplicateReceipt(id));
    }

    @RequestMapping(value = "/studentDetailsExcel", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity studentDetailsExcel(@RequestParam(value = "studentId") Long studentId) throws ParseException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StudentFee studentDetails = bsUserService.getStudentDetails(studentId);
        List<StudentFeeDetails> studentFeeDetails = bsUserService.getStudentFeeDetails(studentDetails);
//        StudentPojo reportPojo=new StudentPojo();
        bsUserService.downloadStudentDetailsExcel(outputStream, studentDetails, studentFeeDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "StudentDetails.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(value = "/saveMail", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveMail(@RequestBody MailDTO saveMailDetails) {
        MailDTO camDTO = null;
        camDTO = bsUserService.createSaveMailDetails(saveMailDetails);
        return ResponseEntity.status(200).body(camDTO);
    }

    @RequestMapping(value = "/studentExportToExcel", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity studentExportToExcel(@RequestParam(value = "searchText") String searchText,
                                               @RequestParam(value = "grade") String grade,
                                               @RequestParam(value = "student") String student,
                                               @RequestParam(value = "checkboxStatusForStudent") String checkboxStatusForStudent) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        List<Student> studentDetails = bsUserService.getStudentExportToExcelList(searchText, grade, student, checkboxStatusForStudent);
        bsUserService.downloadStudentListExportToExcel(outputStream, studentDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "StudentExportToExcel.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(value = "/feeExcel", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity feeExcel(@RequestParam(value = "searchText") String searchText,
                                   @RequestParam(value = "grade") String grade,
                                   @RequestParam(value = "student") String student) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        List<StudentFee> studentDetails = bsUserService.getStudentFeeExportToExcel(searchText, grade, student);
        bsUserService.downloadFeeListExcel(outputStream, studentDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "FeeExcel.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(value = "/studentDetailsPdf", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity studentDetailsPdf(@RequestParam(value = "studentId") Long studentId) throws ParseException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StudentFee studentDetails = bsUserService.getStudentDetails(studentId);
        List<StudentFeeDetails> studentFeeDetails = bsUserService.getStudentFeeDetails(studentDetails);
        bsUserService.downloadStudentDetailsPdf(outputStream, studentDetails, studentFeeDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "StudentDetails.pdf" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(value = "/getStudentDetails", method = RequestMethod.POST)
    public ResponseEntity getStudentDetails(@RequestParam(value = "studentId") Long studentId, @RequestParam(value = "type") String type) {
        StudentFeeDto student = null;
        student = bsUserService.getStudentFeeDetailsList(studentId, type);
        return ResponseEntity.status(200).body(student);
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity deleteStudent(@RequestBody StudentPojo studentDetails) {
        return ResponseEntity.status(200).body(bsUserService.deleteStudentDetails(studentDetails));
    }

    @RequestMapping(value = "/deleteGradeMaster", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity deleteGradeMaster(@RequestBody GradeMasterPojo details) {
        return ResponseEntity.status(200).body(bsUserService.deleteGradeDetails(details));
    }

    @RequestMapping(value = "/deleteAcademics", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity deleteAcademics(@RequestBody AcademicYearMasterPojo details) {
        return ResponseEntity.status(200).body(bsUserService.deleteAcademicDetails(details));
    }

    @RequestMapping(value = "/deleteFeeType", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity deleteFeeType(@RequestBody FeeTypeMasterPojo details) {
        return ResponseEntity.status(200).body(bsUserService.deleteFeeDetails(details));
    }

    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getGradeList() {
        List<GradeMasterPojo> gradeMasters = bsUserService.gradeMasterList();
        return new EntityResponse(HttpStatus.OK.value(), " success", gradeMasters);
    }
    @RequestMapping(value = "/getAcademicGradeList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getAcademicGradeList(@RequestParam(value = "academicId") Long academicID) {
        List<GradeMasterPojo> gradeMasterPojos = bsUserService.gradeList(academicID);
        return new EntityResponse(HttpStatus.OK.value(), " success", gradeMasterPojos);
    }
    @RequestMapping(value = "/getGradeList2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getGradeList2(@RequestAttribute String userId,@RequestParam(value = "searchText") String searchText, @RequestParam(value = "checkboxForInActive") String checkboxForInActive) {
        List<GradeMasterPojo> gradeMasters = bsUserService.gradeMasterList2(searchText, checkboxForInActive,userId);
        return new EntityResponse(HttpStatus.OK.value(), " success", gradeMasters);
    }
    @RequestMapping(value = "/getCartmasterList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getCartmasterList()  {
        List<CartMasterPojo> cartMasters = bsUserService.cartMasterList();
        return new EntityResponse(HttpStatus.OK.value(), " success", cartMasters);
    }

    @RequestMapping(value = "/getGradeListBasedOnInactive", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getGradeListBasedOnInactive() {
        List<GradeMasterPojo> gradeMasters = bsUserService.gradeMasterListBasedOnInactive();
        return new EntityResponse(HttpStatus.OK.value(), " success", gradeMasters);
    }

    @RequestMapping(value = "/studentFeeDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse studentFeeDetailsList(@RequestParam(value = "studentId") String studentId) {
        List<StudentFeeDetails> studentFeeDetails = bsUserService.getStudentFeeDetails(studentId);
        return new EntityResponse(HttpStatus.OK.value(), " success", studentFeeDetails);
    }

    @RequestMapping(value = "/getacdemicYearList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getacdemicYearList() {
        List<AcademicYearMasterPojo> academicList = bsUserService.getAcademicYearList();
        return new EntityResponse(HttpStatus.OK.value(), " success", academicList);
    }

    @RequestMapping(value = "/getAcdemicYearList2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getAcdemicYearList2(@RequestParam(value = "searchText") String searchText, @RequestParam(value = "checkboxStatus") String checkboxStatus) {
        List<AcademicYearMasterPojo> academicList = bsUserService.getAcademicYear2List(searchText, checkboxStatus);
        return new EntityResponse(HttpStatus.OK.value(), " success", academicList);
    }

    @RequestMapping(value = "/getStudentList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getStudentListBasedOnGrade(@RequestParam(value = "searchText") String searchText,
                                                     @RequestParam(value = "grade") String grade,
                                                     @RequestParam(value = "student") String student,
                                                     @RequestParam(value = "checkboxStatusForStudent") String checkboxStatusForStudent) {
        List<StudentPojo> studentlist = bsUserService.getStudentList(searchText, grade, student, checkboxStatusForStudent);
        return new EntityResponse(HttpStatus.OK.value(), " success", studentlist);
    }

    //getFeeTypeList
    @RequestMapping(value = "/getFeeTypeMasterList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getFeeTypeMasterList() {
        List<FeeTypeMasterPojo> feeTypeMasterPojos = bsUserService.feeTypeMasterList();
        return new EntityResponse(HttpStatus.OK.value(), " success", feeTypeMasterPojos);
    }

    @RequestMapping(value = "/getFeeList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getFeeList(@RequestParam(value = "academicId") Long academicId,
                                     @RequestParam(value = "gradeId") Long gradeId) {
        List<FeeTypeMasterPojo> feeTypeMasterPojos = bsUserService.feeListOfAcademicAndGrade(academicId, gradeId);
        return new EntityResponse(HttpStatus.OK.value(), " success", feeTypeMasterPojos);
    }

    @RequestMapping(value = "/getFeeTypeMasterList2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getFeeTypeMasterList2(@RequestParam(value = "searchText") String searchText, @RequestParam(value = "checkboxforInActive") String checkboxforInActive) {
        List<FeeTypeMasterPojo> feeTypeMasterPojos = bsUserService.feeTypeMasterList2(searchText, checkboxforInActive);
        return new EntityResponse(HttpStatus.OK.value(), " success", feeTypeMasterPojos);
    }

    @RequestMapping(value = "/getStudentFeeList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getStudentFeeList(@RequestParam(value = "searchText") String searchText,
                                            @RequestParam(value = "grade") String grade,
                                            @RequestParam(value = "student") String student) {
        List<StudentFeePojo> feeTypeMasterPojos = bsUserService.studentFeeList(searchText, grade, student);
        Gson json = new Gson();
        return new EntityResponse(HttpStatus.OK.value(), " success", json.toJson(feeTypeMasterPojos));
    }

    // getStudentListBasedOnGrade
    @RequestMapping(value = "/getStudentListBasedOnGrade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getStudentListBasedOnGrade(@RequestParam(value = "searchText") String searchText) {
        List<StudentPojo> student = bsUserService.getStudentBasedOnGradeList(searchText);
        return new EntityResponse(HttpStatus.OK.value(), " success", student);
    }

    @RequestMapping(path = "/feeTypeExcel", method = RequestMethod.GET)
    public ResponseEntity feeTypeExcel(@RequestParam(value = "type") String type,
                                       @RequestParam(value = "val") String searchText) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bsUserService.downloadFeeTypeExcel(outputStream,searchText,type);
        headers.add("Content-Disposition", "attachment; filename=\"" + "FeeType.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(path = "/gradeMasterExcel", method = RequestMethod.GET)
    public ResponseEntity gradeMasterExcel(@RequestParam(value = "type") String type,
                                           @RequestParam(value = "val") String searchText,
                                           @RequestAttribute String userId ) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bsUserService.downloadGradeMasterExcel(outputStream,searchText,type,userId);
        headers.add("Content-Disposition", "attachment; filename=\"" + "GradeMaster.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(path = "/acdYearExcel", method = RequestMethod.GET)
    public ResponseEntity acdYearExcel(@RequestParam(value = "type") String type,
                                       @RequestParam(value = "val") String searchText) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bsUserService.downloadAcademicYearExcel(outputStream,searchText,type);
        headers.add("Content-Disposition", "attachment; filename=\"" + "AcdYear.xls" + "\"");
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

}