package com.hyva.bsfms.bs.bsmapper;

import com.hyva.bsfms.bs.bsentities.CartMaster;
import com.hyva.bsfms.bs.bspojo.CartMasterPojo;

public class BsCartMasterMapper {
    public static CartMaster mapPojoToEntity(CartMasterPojo pojo){
        CartMaster cartMaster=new CartMaster();
        cartMaster.setCartmasterId(pojo.getCartmasterId());
        cartMaster.setEmail(pojo.getEmail());
        cartMaster.setPassword(pojo.getPassword());
        cartMaster.setUserName(pojo.getUserName());
        cartMaster.setHiConnectCompanyRegNo(pojo.getHiConnectCompanyRegNo());
        return cartMaster;
    }
}
