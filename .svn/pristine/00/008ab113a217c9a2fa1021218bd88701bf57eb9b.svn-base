package com.hyva.bsfms.bs.sms;

import com.hyva.bsfms.bs.bsentities.SMSServer;
import com.hyva.bsfms.bs.bspojo.SMSServerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
      @Autowired
      SMSServerRepository smsServerRepository;


    @Transactional

    public List<SMSServerDTO> getSmsServerList() {
        List<SMSServer> list = smsServerRepository.findAll();
        List<SMSServerDTO> smsServerDTOList = MessageMapper.mapSMSServerEntityToPojo(list);
        return smsServerDTOList;
    }
    public SMSServer saveSMSDetails(SMSServerDTO smsServerDTO) {
        SMSServer smsServer = MessageMapper.mapSMSServerPojoToEntity(smsServerDTO);
        smsServerRepository.save(smsServer);
        return smsServer;
    }
    public List<SMSServerDTO> editSMSDetails() {
        List<SMSServer> smsServer = new ArrayList<>();
        smsServer = smsServerRepository.findAll();
        List<SMSServerDTO> smsServerList = MessageMapper.mapSMSServerEntityToPojo(smsServer);
        return smsServerList;
    }
    public void deleteSMSDetails(Long id) {
        smsServerRepository.delete(id);
    }
}
