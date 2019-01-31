package com.hyva.bsfms.bs.bsservice;

import com.hyva.bsfms.bs.Quartz.DynamicJob;
import com.hyva.bsfms.bs.Quartz.QuartzConfiguration;
import com.hyva.bsfms.bs.bspojo.MailSchedulerData;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Component
public class SchedulerService {
    public static final String DAILY = "DAILY";
    public static final String YEARLY = "YEARLY";
    @Autowired
    private SchedulerFactoryBean schedFactory;
    public String schedule(MailSchedulerData mailSchedulerData) {
        String scheduled = "Job is Scheduled!!";
        try {
            JobDetailFactoryBean jdfb = QuartzConfiguration.createJobDetail(DynamicJob.class);
            jdfb.setBeanName(getJobDetailName(mailSchedulerData));
            jdfb.afterPropertiesSet();
            JobDataMap jobDataMap = jdfb.getJobDataMap();
            jobDataMap.put("jobData", mailSchedulerData);
            String cronTriggerString = createCronTrigger(mailSchedulerData);
            CronTriggerFactoryBean cronTrigger = QuartzConfiguration.createCronTrigger(jdfb.getObject(), cronTriggerString);
            cronTrigger.setBeanName(getTriggerName(mailSchedulerData));
            cronTrigger.afterPropertiesSet();
            unschedule(mailSchedulerData);
            //Register trigger with scheduler
            schedFactory.getScheduler().scheduleJob(jdfb.getObject(), cronTrigger.getObject());
        } catch (Exception e) {
            scheduled = "Could not schedule a job. " + e.getMessage();
        }
        return scheduled;
    }
    public String unschedule(MailSchedulerData mailSchedulerData) {
        String scheduled = "Job is Unscheduled!!";
        TriggerKey tkey = new TriggerKey(getTriggerName(mailSchedulerData));
        JobKey jkey = new JobKey(getJobDetailName(mailSchedulerData));
        try {
            schedFactory.getScheduler().unscheduleJob(tkey);
            schedFactory.getScheduler().deleteJob(jkey);
        } catch (SchedulerException e) {
            scheduled = "Error while unscheduling " + e.getMessage();
        }
        return scheduled;
    }
    private String getJobDetailName(MailSchedulerData mailSchedulerData) {
        return mailSchedulerData.getReportName() + mailSchedulerData.getDbKeyword() + "jbdtls";
    }

    private String getTriggerName(MailSchedulerData mailSchedulerData) {
        return mailSchedulerData.getReportName() + mailSchedulerData.getDbKeyword() + "trigger";
    }
    private String createCronTrigger(MailSchedulerData mailSchedulerData) {
        String scheduleType = mailSchedulerData.getScheduleType();
        String time = mailSchedulerData.getScheduleTime();
        String date = mailSchedulerData.getScheduleDate();
        StringBuilder space = new StringBuilder(" ");
        StringBuilder tempString = new StringBuilder("0");//seconds
        tempString.append(space);
        String[] split = time.split(":");
        String[] date1 = date.split("/");
        tempString.append(split[1]);//appending minutes
        tempString.append(space);
        tempString.append(split[0]);//appending hours;
        tempString.append(space);
        if (scheduleType.equalsIgnoreCase(DAILY)) {
            tempString.append("1/1");//everyDay
            tempString.append(space);
            tempString.append("*");
            tempString.append(space);
            tempString.append("?");
            tempString.append(space);
            tempString.append("*");
        }else if(scheduleType.equalsIgnoreCase(YEARLY)) {
            tempString.append(date1[0]);//month
            tempString.append(space);
            tempString.append(date1[1]);//date
            tempString.append(space);
            tempString.append("?");
            tempString.append(space);
            tempString.append(date1[2]);//year
        }
        return tempString.toString();
    }

}
