package com.hyva.bsfms.bs.sms;

import com.hyva.bsfms.bs.bspojo.SMSServerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sms")
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/getSMSList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getSMSList() {
        return ResponseEntity.status(200).body(messageService.getSmsServerList());
    }
    @RequestMapping(value = "/saveSMSDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveSMSDetails(@RequestBody SMSServerDTO smsServerDTO) {
        return ResponseEntity.status(200).body(messageService.saveSMSDetails(smsServerDTO));
    }
    @RequestMapping(value = "/editSmsDetails", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editSmsDetails() {
        return ResponseEntity.status(200).body(messageService.editSMSDetails());
    }
    @RequestMapping(value = "/deleteSMSDetails", method = RequestMethod.POST, produces = "application/json")
    public void deleteSMSDetails(@RequestParam(value = "id", required = false)Long  id) {
      messageService.deleteSMSDetails(id);
    }
}
