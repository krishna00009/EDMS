package com.hyva.bsfms.bs.bsentities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SchedulerData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String reportName;
    private String time;
    private String date;
    private String phoneNumber;
    private String databaseKeyWord;
    private String scheduleType;
    private String toEmailId;
    private String student;
    private String installmentsId;

}
