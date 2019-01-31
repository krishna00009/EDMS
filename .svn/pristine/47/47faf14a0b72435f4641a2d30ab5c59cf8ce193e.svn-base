package com.hyva.bsfms.bs.sms;

import com.hyva.bsfms.bs.bsentities.SMSServer;
import com.hyva.bsfms.bs.bspojo.SMSServerDTO;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {
    public static SMSServer mapSMSServerPojoToEntity(SMSServerDTO smsServerDTO){
        SMSServer smsServer = new SMSServer();
        smsServer.setMessageId(smsServerDTO.getId());
        smsServer.setSmsUrl(smsServerDTO.getSmsURL());
        smsServer.setApiKey(smsServerDTO.getApiKey());
        smsServer.setSenderId(smsServerDTO.getSenderId());
        return smsServer;
    }
    public static List<SMSServerDTO> mapSMSServerEntityToPojo(List<SMSServer> smsServers){
        List<SMSServerDTO> smsServerDTOS=new ArrayList<>();
        for(SMSServer smsServer:smsServers) {
            SMSServerDTO smsServerDTO = new SMSServerDTO();
            smsServerDTO.setId(smsServer.getMessageId());
            smsServerDTO.setSmsURL(smsServer.getSmsUrl());
            smsServerDTO.setApiKey(smsServer.getApiKey());
            smsServerDTO.setSenderId(smsServer.getSenderId());
            smsServerDTOS.add(smsServerDTO);
        }
        return smsServerDTOS;
    }
}
