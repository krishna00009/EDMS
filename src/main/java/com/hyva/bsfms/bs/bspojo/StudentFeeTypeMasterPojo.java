package com.hyva.bsfms.bs.bspojo;

import com.hyva.bsfms.bs.bsentities.FeeTypeMaster;
import com.hyva.bsfms.bs.bsentities.Student;
import lombok.Data;

import java.util.List;
@Data
public class StudentFeeTypeMasterPojo {
    public List<Student> studentList;
    public List<FeeTypeMaster> feetypeList;
    }
