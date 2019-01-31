package com.hyva.bsfms.pusher.pusherPojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TransactionPojo extends AbstractResponse implements Serializable {
    private List<SelectedItem> selectedItemsList = new ArrayList<>();
       private List<MultiPartialPayment> multiPartialPaymentList = new ArrayList<>();
//    private CompanyInfo companydupData;
//    private CompanyInfoDto companyData;
    private CashPayment cashPayment;
    private CreditCardPayment creditPayment;
   private VoucherPayment voucherPayment;
    private MultiPayment multiPayment;
    private BankPayment bankPayment;




    //    private RedeemPoints redeemPayment;
//    private CustomerDTO customer;
    private String subscriptionType;
    private String operation;
    private float hiPosServiceCharge;
    private String paymentType;
    private double totalCheckOutamt;
    private double totalTaxAmt;
    private double cessTotalTaxAmt;
    private int itemCount;
    private long customerId;
    private String taxType;
    private long siid;
    private String customerEmail;
    private double totalServiceCharge;
    private String siNo;
    private String srlnNo;


    private double balanceAmount;

    private double discountAmount;
    private double totalTenderedAmount;
    private String userName;
    private String printType;
    private String billToAddress;
    private String shipToAddress;
    private String email;
    private String phoneNumber;
    private String inventoryAddress;
    private String inventoryContactNo;
    private String inventoryEmail;
    private String inventoryFax;
    private String advancepayment;
    private String formNo;
    private String totalCashPymtAmtReturned;
    private String totalChequePymtAmtReturned;
    private String totalVoucherPymtAmtReturned;
    private String chequeNumber;
    private String voucherNumber;
    private String salesOrderNo;
    private String salesDeliveryOrderNO;
    private double totalActualWeight;
    private double totalSellableWeight;
    private String from_reg;
    private String to_reg;
    private String user_id;
    private String  typeDoc;
    private String  type_flag;
    private String recieptFooter;
    private String companyLogoPath;
    private String cmpyLocation;
    private String custLocation;
    private int termsId;
    private String dateOfInvoice;
    private String agentIdOfInvoice;
    private String projectIdOfInvoice;
    private String termCondIdOfInvoice;
    private String currencyIdOfInvoice;
    private String exchangeRateIdOfInvoice;
    private double totalCashPayment;
    private double totalVoucherPayment;
    private double totalCardPayment;
    private String customerAddress;
    private String customerState;
    private String customerGst;
    private String locationAddress;
    private String locationState;
    private String locationGst;
    private BigDecimal ARBalance;
    private String salesOrderId;
    private String salesOrderDetailsId;
    private String memo;
    private String returnReason;
    private String totalRemaininBalance;
    private Date shippingDate;
    private String shippingDateString;
    private String shippingReferenceNo;
    private String referenceNo;
    private String shippingmethodId;
    private String salesQuotationId;
    private String proFormaId;
    private String proFormaDetailsId;
    private double totalVoucherAmt;
    private double totalCreditCardAmt;
    private double totalCashAmt;
    private String patientId;
    private String serviceCharge;
    private String agentName;
    private String exchangerateValue;
    private String tc_value;
    private String salesQuotationDetailsId;
    private String otherContactId;
    private Date invoiceDate;
    private double invoiceAmt;
    private double incrementAmt;
    private String employeeId;
    private double tokenNo;
    private String table;
    private String invoiceNo;
    private Date formDate;
    private String projectName;
    private String shippingMethodName;
    private Long invokeOrderId;
    private String invokeOrderName;
    private String generatedVoucherNo;
    private String customerPo;
    private String serviceDeliveryId;
    private String exportInvoice;
    private double invokeRemaningQty;
    private double amtToBePaid;
    private String siStatus;
    private boolean checkForHoldStock;
    private String emailSub;
    private String emailBody;
    private String emailInvoice;
    private String discountType;
    private String piStatus;
    private String customerName;
    private String custNotiId;


    private long supplierId;
    private long agentId;
    private long exchangeRateId;
    private double apBalance;
    private long piid;
    private String supplierEmail;
    private String piNo;
    private String prlnNo;
    private String supplierName;
    private String supplierInvNo;
    private String cmpyLoc;
    private String suppLoc;
    private double amountReturned;
    private String selfBuildInvoice;
    private String supplierAddress;
    private String supplierState;
    private String supplierGst;
    private String locationName;
    private String locationSupplierName;
    private String exchangerateId;
    private String currencyId;
    private String projectId;
    private String termsandConditionsId;
    private String purchaseQuotationId;
    private String purchaseQuotationDetailsId;
    private Long poId;
    private String purchaseOrderId;
    private Long receiveItemId;
    private Date date;
    private String typeOfInvoice;
    private String cashAmtForReturn;
    private String chequeAmtForReturn;
    private double dutyAmount;
    private double frightCharges;
    private String otherCharges;
    private Long transactionId;
    private Long notificationId;
    private String totaltax;
    private String fromCompname;
    private String toCompname;


    @Data  public static class CashPayment {
        private String paymentType;
        private double totalCPAmountRefunded;
        private double totalCPDiscount;
        private double totalCPAmountTendered;
        private List<MultiCashPayment> multiCashPaymentList;

    }
    @Data   public static class CreditCardPayment {
        private double totalCCPAmountRefunded;
        private double totalCCPDiscount;
        private double totalCCPAmountTendered;
        private Date chequeDate;
        private String chequeBankName;
        private String transactionNo;
        private String totalCCPAfterDiscount;
        private List<MultiCardPayment> cardPaymentList;

    }
    @Data  public static class MultiCashPayment {
        private String paymentType;
        private double totalCPAmountRefunded;
        private double totalCPDiscount;
        private double cashAmt;


    }
    @Data    public class MultiCardPayment {
        private String cardNo;
        private double cardAmt;
        private String cardDate;
        private String cardBankName;

    }
    @Data  public static class BankPayment {
        private double totalBPAmountRefunded;
        private double totalBPDiscount;
        private double totalBPAmountTendered;
        private String bankName;
        private double amount;
        private String invoiceNo;
        private Date bankDate;
        private String paymentType;
        private String bankAccount;
        private List<MultiBankPayment> multiBankPaymentList;

    }
    @Data  public class MultiBankPayment {
        private String bankName;
        private double amount;
        private String invoiceNo;
        private Date date;
        private String paymentType;
        private String transactionNo;
        private String bankAccount;
        private Long bankAccountId;
        private Long bankId;
        private Long bankChargesId;

    }
    @Data public class MultiPayment {
        private double totalVPBeforDiscount;
        private double totalVPDiscount;
        private String voucherNo;
        private Date voucherDate;
        private double totalVoucherAmt;
        private double totalVPAfterAllDeductions;
        private double totalVPAmountTendered;
        private double totalVPAmountRefunded;
        private double cardAmount;

    }
    @Data
    public static class VoucherPayment {

        private double totalVPBeforDiscount;
        private double totalVPDiscount;
        private String voucherNo;
        private Date voucherDate;
        private double totalVoucherAmt;
        private double totalVPAfterAllDeductions;
        private double totalVPAmountTendered;
        private double totalVPAmountRefunded;
        private double cardAmount;
        private List<MultiVoucherPayment> multiVoucherPayments;


    }
    @Data
    public class MultiVoucherPayment {
        private String voucherNo;
        private double voucherAmt;

    }
    @Data
    public static class SelectedItem {
        public String itemName;
        private long purchaseInvoiceDetailID;
        private long itemId;
        private double unitPrice;
        private double qty;
        private double returnQty;
        private double remainingQty;
        private double amtexclusivetax;
        private long  taxid;
        private long  inputTaxid;
        private double taxpercent;
        private double taxamt;
        private double amtinclusivetax;
        private double discountAmt;
        private String itemDescription;
        private String taxName;
        public String serializableItemId;
        public String serializableNumbers;
        public String itemCode;
        private double makingCharge;
        private double actualWeight;
        private double cess;
        private String message;
        private double cgstsgstsplitvalues;
        private double taxPercentageSplit;
        private double cessTaxAmt;
        private String hsnCode;
        private String salesOrderId;
        private String salesOrderDetailsId;
        private double posamtexclusivetax;
        private double posamtinclusivetax;
        private boolean receiveItemFlag;
        private String receiveItemId;
        private String receiveItemFormNo;
        private  String taxCode;
        private Long ProFormaSalesInvoiceId;
        private Long ProFormaSalesInvoiceDetailsId;
        private Long salesQuotationId;
        private Long salesQuotationDetailsId;
        private String inclusiveJSON;
        private Long purchaseQuotationId;
        private Long purchaseOrderId;
        private Long salesOrder;
        private String batchNo;
        private String  batchExpDate;
        private String  batchManfDate;
        private String serializableIMEINo;

        private String uom;
        private String uomName;
        private Long uomConvertorId;
        private String  uomValue;
        private double convertedReturnQty;
        private Long uomId;
        private Long sIItemId;
        private String convertedName;
        private Long assertId;

        private String customerName;
        private Long id;
        private double deliverdQuantity;
        private String siid;
        private Date batchDate;
        private double convertedQuantity;
        private String itemType;
        private double fifoAmount;
        private String salesDeliverOrderId;
        private String salesDeliverOrderDetailsId;
        private double received;
        private double qtytotalSent;
        private double discPercent;
        private double itemAmount;
        private String delStatus;
        private String uomConvertedName;
        private Long receiveItemDetailsId;
        private Long customerId;
        private String tablesId;
        private String a1;
        private String a2;
        private String a3;
        private String a4;
        private String a5;
        private String a6;
        private String a7;
        private String a8;
        private String a9;
        private String a10;
        public String locationId;
        public String useraccount_id;
        private Date expireDate;
        private Date purchaseDate;
        private Date manufactureDate;
        private String sORbNumbers;
        private String serializableImeiNo;
        private double itemCommisionAmount;
        private double itemCommisionConfigAmount;
        private String  type;
        private double discountConfigAmt;
        private double serviceRemainingQty;
        private Date fromDate;
        private Date toDate;
        private String serializableStatus;
        private Long itemCategoryId;
        private String itemCategoryName;
        private double pORemaningQty;
        private String status;
        private Long purchaseOrderDetailsId;
        private Long salesOrderDetailId;
        private double deliveredQuantity;
        private double QtySent;
        private String productMergerSubItemNames;
        private String otherCharges;
        private String piNo;
        private long salesInvoiceDetailsId;
        private String transactionId;
        private String notificationId;
        private String cartId;



//    private List<UomConvertorDTO> uomConvertorList;
//    private List<ItemSerializeDTO> serializeNoList;
//    private List<SerializableItemsDTO> serializableItemsDTOs;
//    List<SerializableItemsDTO> serializableItemsDTOList;
//    private UomConvertor uomConvertor;
//    private List<BatchItemDto> batchNoList;


        private String serviceDescription;



//    public List<UomConvertorDTO> getUomConvertorList() {
//        return uomConvertorList;
//    }
//
//    public void setUomConvertorList(List<UomConvertorDTO> uomConvertorList) {
//        this.uomConvertorList = uomConvertorList;
//    }
    }
@Data
    public class MultiPartialPayment {
        private String formNo;
        private String supplierName;
        private double amount;
        private double apBalance;
        private double amountPaid;
    }
}