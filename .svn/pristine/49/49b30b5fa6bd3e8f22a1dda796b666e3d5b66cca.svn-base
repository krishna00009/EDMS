package com.hyva.bsfms.bs.Quartz;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class TenantContext {
    private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());
    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        logger.debug("Setting tenant to " + tenant);
        currentTenant.set(tenant);
    }
    public static void clear() {
        currentTenant.set(null);
    }
    public static String getCurrentTenant() {
        String tenantId = currentTenant.get();
        if (StringUtils.isBlank(tenantId)){
            return "BFMS";
        }
        return tenantId;
    }

}
