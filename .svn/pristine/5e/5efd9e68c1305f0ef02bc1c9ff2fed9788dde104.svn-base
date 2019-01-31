/**
 * Created by harshitha on 18/06/06.
 */
package com.hyva.bsfms.bs.bsservice;
import com.hyva.bsfms.bs.bsentities.SMSServer;
import com.hyva.bsfms.bs.bsrespositories.BsStudentRepository;
import com.hyva.bsfms.bs.sms.SMSServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.net.URLConnection;

@Service
public class SmsService {
    @Autowired
    BsStudentRepository bsStudentRepository;
    @Autowired
    SMSServerRepository smsServerRepository;
    public String sendSms(String phoneNumber,String message) {
        try{
            SMSServer smsServer=smsServerRepository.findOne(1L);
            URL url = new URL(smsServer.getSmsUrl() + "?method=sms" + "&api_key=" + smsServer.getApiKey() + "&to=" + phoneNumber + "&sender=" + smsServer.getSenderId() + "&message=" + message);
            URLConnection conn = url.openConnection();
            conn.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
