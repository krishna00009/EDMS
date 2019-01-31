package com.hyva.bsfms.bs.Quartz;
import com.hyva.bsfms.bs.bsentities.Installments;
import com.hyva.bsfms.bs.bsentities.Student;
import com.hyva.bsfms.bs.bsentities.StudentFee;
import com.hyva.bsfms.bs.bspojo.MailSchedulerData;
import com.hyva.bsfms.bs.bsrespositories.BsInstallmentsRepository;
import com.hyva.bsfms.bs.bsrespositories.BsStudentFeeRepository;
import com.hyva.bsfms.bs.bsrespositories.BsStudentRepository;
import com.hyva.bsfms.bs.bsservice.MailService;
import com.hyva.bsfms.bs.bsservice.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;
import javax.mail.MessagingException;


@Component
public class DynamicJob implements Job {
    private final Logger log = Logger.getLogger(getClass());
    @Autowired
    SmsService smsService;
    @Autowired
    BsStudentRepository bsStudentRepository;
    @Autowired
    BsInstallmentsRepository bsInstallmentsRepository;
    @Autowired
    BsStudentFeeRepository bsStudentFeeRepository;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Map<String, Object> mergedJobDataMap = jobExecutionContext.getMergedJobDataMap().getWrappedMap();
        MailSchedulerData schedulerProps = (MailSchedulerData) mergedJobDataMap.get("jobData");
        Student stud=bsStudentRepository.findOne(Long.parseLong(schedulerProps.getStudent()));
        Installments installments=bsInstallmentsRepository.findOne(Long.parseLong(schedulerProps.getInstallmentsId()));
        if(installments.getPaidAmt()!=installments.getInstallmentsAmount()) {
            Calendar cal = Calendar.getInstance();
            String[] monthName = {"January", "February",
                    "March", "April", "May", "June", "July",
                    "August", "September", "October", "November",
                    "December"};
            String month = monthName[cal.get(Calendar.MONTH)];
            String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + 1);

            String sentense = "Dear Parents," + "\n School fee for " + month + "  has to be paid before the " + day + "th of " + month + ". \n Ignore if you have paid already.\n\nThe Principle " +
                    "\nBrainyStars";
            String message = "Dear Parents," + " School fee for " + month + "  has to be paid before the " + day + "th of " + month + ".  Ignore if you have paid already." +
                    "The Principle " +
                    "BrainyStars";

            if (StringUtils.isNotEmpty(stud.getFatherContactNo())) {
                smsService.sendSms(stud.getFatherContactNo(), message);
            }
            if (StringUtils.isNotEmpty(stud.getMotherContactNo())) {
                smsService.sendSms(stud.getMotherContactNo(), message);
            }
            if (StringUtils.isNotEmpty(stud.getPrimaryContactNo())) {
                smsService.sendSms(stud.getPrimaryContactNo(), message);
            }
            log.info("Executing Job = " + jobExecutionContext.getJobDetail().getKey());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TenantContext.setCurrentTenant(schedulerProps.getDbKeyword());
            String filename = null;

            try {
                MailService.sendMailWithAttachment(schedulerProps.getFromMail(),
                        schedulerProps.getToEmail(), "", "Brainy Stars",
                        sentense, schedulerProps.getReportType(),
                        outputStream.toByteArray(), filename);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
