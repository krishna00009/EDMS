package com.hyva.bsfms.bs.bsmapper;

import com.hyva.bsfms.bs.bspojo.FeeTypeMasterPojo;
import com.hyva.bsfms.bs.bsentities.FeeTypeMaster;

public class BsFeeTypeMasterMapper {

    public static FeeTypeMaster mapPojoToEntity(FeeTypeMasterPojo pojo){
        FeeTypeMaster master=new FeeTypeMaster();
        master.setFeeTypeId(pojo.getFeeTypeId());
        master.setFeeAmount(pojo.getFeeAmount());
        master.setFeeTypeName(pojo.getFeeTypeName());
        master.setPayingFee(pojo.getFeeAmount());
        master.setStatus(pojo.getStatus());
        master.setValue(pojo.getValue());

        return master;
    }

}
