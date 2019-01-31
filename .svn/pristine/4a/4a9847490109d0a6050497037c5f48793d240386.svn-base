package com.hyva.bsfms.pusher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class MasterDetailsGettingService {
    @Value("${php_domainame}")
    String php_domainame;
    public String getGradeDetailsFromPhp() {
        String url = php_domainame+"getGrade";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("organisationid","21");
        objectNode.put("uid", "159");
        // Spring Rest Client
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(objectNode.toString(), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        Gson gson = new Gson();

//        CompanySearchResponse companySearchResponse=gson.fromJson(responseEntity.getBody(), CompanySearchResponse.class);
//        ArrayList users = (ArrayList) companySearchResponse.getObject();//converting response object into arraylist
//        Map<String,String> map=(Map<String, String>)users.get(0);
//        String json=map.get("permission");//getting only PermissionsString
//        HashMap<String, String> map1 = new HashMap<String, String>();
//        JSONObject jObject = new JSONObject(json);
        return null;


    }
}
