package com.hyva.bsfms.pusher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.hyva.bsfms.bs.bspojo.AdmissionPojo;
import com.hyva.bsfms.bs.bspojo.StudentPojo;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class PusherService {
    @Autowired
    private KafkaPusherCreds kafkaPusherCreds;
    public String BroadCastMasterData(String jsonInString, String from_reg, String to_reg, String typeDoc, String typeFlag) throws IOException, JSONException {
        String url = "testurl";
        Gson gson = new Gson();
        //String jsonInString = gson.toJson(masterDTO);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("from_reg",from_reg);
        objectNode.put("to_reg", to_reg);
        objectNode.put("typeDoc",typeDoc);
        objectNode.put("typeFlag",typeFlag);
        objectNode.putPOJO("jsonData", jsonInString);
        objectNode.put("subscriptionType", "DesktopWithRTR");
        ObjectNode objectNode1 = mapper.createObjectNode();
        objectNode1.put("from_reg",from_reg);
        objectNode1.put("to_reg", to_reg);
        objectNode1.put("typeDoc",typeDoc);
        objectNode1.put("typeFlag",typeFlag);
        objectNode1.putPOJO("jsonData", objectNode.toString());
        String statusCode =SavePusher(objectNode1.toString(), url, typeDoc);
      //  LOGGER.info("Returned Value For Purchase Invoice Update Status-->" + statusCode);
        return url;
    }
    @Transactional
    public String SavePusher(String jsonInString, String Url, String typeDoc) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonInString);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
//        objectNode.put("kafkaServerAddress", kafkaPusherCreds.getKAFKA_SERVER_URL());
//        objectNode.put("kafkaConsumerGroupId", kafkaPusherCreds.getKAFKA_GROUPID());
//        objectNode.put("kafkaTopic", kafkaPusherCreds.getKAFKA_TOPIC());
        objectNode.put("kafkaType", typeDoc);
        objectNode.put("Url", Url);
        objectNode.putPOJO("jsonData", jsonObject);
        String jsonCompleteData = objectNode.toString();

        //Spring Rest Client Call
        String strUrl=readPusherDomainName();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonCompleteData, headers);
        ResponseEntity<String> responseEntity = null;
        try {


            responseEntity = restTemplate.exchange(strUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            return "Success";
        }
     //   LOG.info("response Status: " + responseEntity.getBody());
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
          //  pusher.setActive(false);
        }
     //   pusherDao.saveOrUpdate(pusher);
//        try
//        {
//            LOG.info("sending message='{}' to topic='{}'", jsonInString, kafkaTopic);
//            ListenableFutureReturnValueHandler aa= (ListenableFutureReturnValueHandler) kafkaTemplate.send(kafkaTopic, jsonInString);
//            LOG.info("--------------------->", aa);
//        }
//        catch (Exception ex)
//        {
//            pusher.setActive(false);
//           pusherDao.saveOrUpdate(pusher);
//            return null;
//        }
        return jsonInString;
    }
    public String BroadCastBrainyStarData(String jsonInString, String from_reg, String to_reg, String typeDoc, String typeFlag) throws IOException, JSONException {
        String url = "testurl";
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("from_reg",from_reg);
        objectNode.put("to_reg", to_reg);
        objectNode.put("typeDoc",typeDoc);
        objectNode.put("typeFlag",typeFlag);
        objectNode.putPOJO("jsonData", jsonInString);
        objectNode.put("subscriptionType", "DesktopWithRTR");
        String status =SaveBrainyStarPusher(objectNode.toString(), url, typeDoc);
        return url;
    }
    @Transactional
    public String SaveBrainyStarPusher(String jsonInString, String Url, String typeDoc) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonInString);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
       objectNode.put("kafkaServerAddress", kafkaPusherCreds.getKAFKA_SERVER_URL());
        objectNode.put("kafkaConsumerGroupId", kafkaPusherCreds.getKAFKA_GROUPID());
        objectNode.put("kafkaTopic", kafkaPusherCreds.getKAFKA_TOPIC());
        objectNode.put("kafkaType", typeDoc);
        objectNode.put("Url", Url);
        objectNode.putPOJO("jsonData", jsonObject);
        String jsonCompleteData = objectNode.toString();
        //Spring Rest Client Call
        String strUrl=readPusherDomainName()+"BrainyStars";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonCompleteData, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(strUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            return "Success";
        }
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            //  pusher.setActive(false);
        }
        return jsonInString;
    }
//    @Transactional
//    public String  SavePusher(String jsonInString,String Url,String typeDoc,String typeFlag) throws JSONException {
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode objectNode = mapper.createObjectNode();
//
//        objectNode.put("from_reg","2D75CD3BD510469A" );
//        objectNode.put("to_reg", "2D75CD3BD510469A");
//        objectNode.put("Url",Url);
//        objectNode.put("kafkaType",typeDoc);
//        objectNode.put("typeFlag",typeFlag);
//        objectNode.put("subscriptionType","DesktopWithRTR");
//        JSONObject jsonObject = new JSONObject(jsonInString);
//        objectNode.putPOJO("jsonData",jsonObject);
//
////        objectNode.put("typeDoc", "SIN");
//   //     objectNode.put("typeFlag","Purchase");
//        objectNode.put("status","Accepted");
////        objectNode.put("organizationId","21");
////        objectNode.put("branchId","3");
//        String jsonCompleteData = objectNode.toString();
//   //   Spring Rest Client Call
//        String strUrl="http://10.0.0.44:8090/pusher/savePusher";
//      //  String Url1="https://www.hiaccounts.in/karts/edms/api/Desktop/filter";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>(jsonCompleteData, headers);
//        ResponseEntity<String> responseEntity=null;
//        try {
//            responseEntity = restTemplate.exchange(strUrl, HttpMethod.POST, entity, String.class);
//            // responseEntity = restTemplate.exchange("https://www.hiaccounts.in/karts/edms/api/Desktop/filter", HttpMethod.POST, entity, String.class);
//        }catch(Exception e){
//            return "Success";
//        }
//        return jsonInString;
//    }
    public String  SavePusher(StudentPojo saveStudentDetails) {
        AdmissionPojo admissionPojo=new AdmissionPojo();
        admissionPojo.setDOB("-----");
        admissionPojo.setFk_levelid(saveStudentDetails.getGradeMaster().getGradeName());
//      admissionPojo.setFk_classid();
//      admissionPojo.setFk_organisationid();
//      admissionPojo.setUid();
        admissionPojo.setFk_branchid("MainBranch");
        admissionPojo.setAdmission_date(saveStudentDetails.getDateOfAdmission().toString());
        admissionPojo.setAdmissionno(saveStudentDetails.getAdmissionFormNo());
        admissionPojo.setStudentname(saveStudentDetails.getStudentName());
        admissionPojo.setBloodgroup(saveStudentDetails.getBloodGroup());
        admissionPojo.setAcademicyear(saveStudentDetails.getAcdYearId().toString());
        admissionPojo.setJoiningdate(saveStudentDetails.getDateOfJoining().toString());
        admissionPojo.setFathersname(saveStudentDetails.getFatherName());
        admissionPojo.setFathersemail(saveStudentDetails.getFatherEmailId());
        admissionPojo.setFathersmobile(saveStudentDetails.getFatherContactNo());
        admissionPojo.setFathersoccupation(saveStudentDetails.getFatherOccupation());
        admissionPojo.setMothername(saveStudentDetails.getMotherName());
        admissionPojo.setMotheremail(saveStudentDetails.getMotherEmailId());
        admissionPojo.setMothermobile(saveStudentDetails.getMotherContactNo());
        admissionPojo.setMotheroccupation(saveStudentDetails.getMotherOccupation());
        admissionPojo.setPrimarycontact(saveStudentDetails.getPrimaryContactNo());
        // admissionPojo.setPlace(saveStudentDetails.get);
//      admissionPojo.setGender();
//      admissionPojo.setStatus();
        String jsonInString = saveStudentDetails.toString();
        String Url = "https://www.hiaccounts.in/karts/edms/api/School/admission";
        String strUrl = "http://localhost:8090/pusher/savePusher";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(strUrl, HttpMethod.POST, entity, String.class);
        return  jsonInString;
    }
    public static String readPusherDomainName() throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = PusherService.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(in);
            in.close();
        } catch (Exception e) {
        } finally {
            in.close();
        }
        return prop.getProperty("pusher__domainame");
    }
}



//
//package com.hyva.bsfms.bs.bsservice;
//        import com.hyva.bsfms.bs.bspojo.AdmissionPojo;
//        import com.hyva.bsfms.bs.bspojo.StudentPojo;
//        import org.springframework.http.*;
//        import org.springframework.stereotype.Service;
//        import org.springframework.web.client.RestTemplate;
//
//@Service
//public class PusherService {
//    public String  SavePusher(StudentPojo saveStudentDetails) {
//        AdmissionPojo admissionPojo=new AdmissionPojo();
//        admissionPojo.setDOB("-----");
//        admissionPojo.setFk_levelid(saveStudentDetails.getGradeMaster().getGradeName());
//       admissionPojo.setFk_classid();
//        admissionPojo.setFk_organisationid();
//        admissionPojo.setUid();
//        admissionPojo.setFk_branchid("MainBranch");
//        admissionPojo.setAdmission_date(saveStudentDetails.getDateOfAdmission().toString());
//        admissionPojo.setAdmissionno(saveStudentDetails.getAdmissionFormNo());
//        admissionPojo.setStudentname(saveStudentDetails.getStudentName());
//        admissionPojo.setBloodgroup(saveStudentDetails.getBloodGroup());
//        admissionPojo.setAcademicyear(saveStudentDetails.getAcdYearId().toString());
//        admissionPojo.setJoiningdate(saveStudentDetails.getDateOfJoining().toString());
//        admissionPojo.setFathersname(saveStudentDetails.getFatherName());
//        admissionPojo.setFathersemail(saveStudentDetails.getFatherEmailId());
//        admissionPojo.setFathersmobile(saveStudentDetails.getFatherContactNo());
//        admissionPojo.setFathersoccupation(saveStudentDetails.getFatherOccupation());
//        admissionPojo.setMothername(saveStudentDetails.getMotherName());
//        admissionPojo.setMotheremail(saveStudentDetails.getMotherEmailId());
//        admissionPojo.setMothermobile(saveStudentDetails.getMotherContactNo());
//        admissionPojo.setMotheroccupation(saveStudentDetails.getMotherOccupation());
//        admissionPojo.setPrimarycontact(saveStudentDetails.getPrimaryContactNo());
//        admissionPojo.setPlace(saveStudentDetails.get);
//       admissionPojo.setGender();
//       admissionPojo.setStatus();
//        String jsonInString = saveStudentDetails.toString();
//        String Url = "https://www.hiaccounts.in/karts/edms/api/School/admission";
//        String strUrl = "http://localhost:8090/pusher/savePusher";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(Url, HttpMethod.POST, entity, String.class);
//        return  jsonInString;
//    }
//
//}




