package com.hyva.bsfms.pusher.pusherPojo;

import lombok.Data;

@Data
 public class MasterPojo {
        private String masterFlag;
        private String subscriptionType;



        //Masters
//    private List<CurrencyDTO> currencyDTOList = new ArrayList<>();
//    private List<StateDTO> stateDTOList = new ArrayList<>();
//    private List<CountryDTO> countryDTOList = new ArrayList<>();
//    List<EmployeeDTO> employeeDTOList;
//    List<ItemDTO> itemDTOList;
//    List<CustomerDTO> customerDTOList;
        private String customerId;
        //    private ItemDTO itemId;
        private String status;
        private String locationId;
        private String useraccount_id;
        private String AccountCode;
        private String currencyName;
        private String countryId;
        private String defaultType;
        private String stateName;
        private String itemName;
        private String perCurrency;
        private String prefix;
        private String itemCode;
        private String customerName;
        private String customerID;
        private String message;
        private String checkBoxValue;
        private String brandName;
        private String assestsCategoryName;
        private String notificationFlag;
        private String ledgerINV;
        private String ledgerCOGS;
        private String ledgerIncome;
        private String ledgerAdjustment;
        private String perOrder;
        private String perProduct;
        private String perCurrencyPoints;
        private String perOrderPoints;
        private String perProductPoints;
        private String amountOrPercent;
        private String saasStatus;
        private String bankName;
        private String accountNo;
        private String iFSCCode;
        private String branchName;
        private String cmpyCountry;
        private String supplierName;
        private String website;
        private String phoneNumber1;
        private String phoneNumber2;
        private String generalNote;
        private String state;
        private String personIncharge;
        private String currencyId;
        private String panNO;
        private String contactPerson;
        private String gstIn;
        private String billingAddress;
        private String faxTelex;
        private String vehicleSeries;
        private String subject;
        private String employeeName;
        private String address;
        private String itemId;
        private String itemID;
        private String brandId;
        private String terms;
        private String supplierId;
        private String id;
        private String attributeConfiguratorId;
        private String paymentmethodId;
        private String pgId;
        private String employeeId;
        private String effectiveDate;
        private String fromDate;
        private String toString;
        private String price;
        private String percentage;
        private String stock;
        private String amountordiscount;
        private String flag;
        private String itemStatus;


        //TableDataPojo
        private String currTableName;
        private String currTableId;
        private String prevTableName;
        private String prevTableId;
//    private String customerId;

        //TableConfigurationDTO
        private String tableconfigid;
        private String configurationname;
        private String rowtableconfig;
        private String columntableconfig;

        //BankDTO
//    List<AccountMasterDTO> linkedAccountMasterDTOList;
//    List<AccountMasterDTO> linkedBankChargesAccountMasterDTOList;
        private String bankId;
        //    private String bankName;
//    private String address;
//    private String accountNo;
//    private String iFSCCode;
        private String bankEmail;
        //    private String branchName;
        private String bankPhoneNo;
        private String bankAccountNo;
//        private AccountMasterDTO linkedAccount;
//        private AccountMasterDTO linkedAccountForBankCharges;
        //    private String description;
//    private String locationId;
//    private String useraccount_id;
//    private String status;
        private String lglinkedAccount;
        private String lglinkedAccountForBankCharges;

        //Agent DTO
//    List<AccountMasterDTO> commissionExpenseList;
//    List<AccountMasterDTO> commissionPayList;
        private String AgentId;
        private String AgentName;
        private String AccountNo;
        private String agentAccountCode;
        private String Email;
        private String Mobile;
        private String Address;
        private String Commission;
        //    private Date effectiveDate;
//    private String status;
//        private AccountMasterDTO commissionExpense;
//        private AccountMasterDTO commissionPay;
        private String gstinNo;
        private String ecommerce;


        //CurrencyDTO
//    private String currencyId;
//    private String currencyName;
        private String currencyCode;
        private String currencySymbol;
        private String currencyDescription;
        private String currencySymbolPlace;
        //    private String AccountCode;
//    private String locationId;
//    private String useraccount_id;
//    private String status;
        private String defaultCurrency;


        //EmployeeDTO
        private String EmployeeId;
        private String EmployeeCode;
        private String EmployeeName;
        private String EmployeeAddr;
        private String EmployeePhone;
        private String EmployeeDOJ;
        private String EmployeeDOB;
        private String Gender;
        //    private String status;
//    private String locationId;
//    private String useraccount_id;
//    private String AccountCode;
        private String EmployeeAccountMaster;
        //    private Date effectiveDate;
        private String status1;

        //countryDTO
//    private String countryId;
        private String countryName;
//    private Boolean flag;
//    private String locationId;
//    private String useraccount_id;
//    private String status;


        //ExchangeRate DTO
//    List<CurrencyDTO> currencyDTOList;
        private String exchangeRateId;
        //    private CurrencyDTO currency;
        private String exchangeRateEffectiveDate;
        private String exchangeRateValue;
        private String customsexchangeRateValue;
//    private String locationId;
//    private String useraccount_id;
//    private String status;
//    private Long currencyId;
//    private String currencyName;

        //ProjectDTO
        private String projectId;
        private String projectName;
        private String projectDesc;
        private String startDate;
        //    private String status;
        private String openingBalance;
//    private String locationId;
//    private String useraccount_id;


        //ShippingMethodDTO
        private String shippingMethodId;
        private String shippingMethodName;
        private String shippingMethodDescription;
//    private String locationId;
//    private String useraccount_id;
//    private String status;

        //EmailServer
//    private long id;
        private String userName;
        private String password;
        private String smtpHost;
        private String port;
        private String forMail;
        private String logoUrl;
//    private String status;

        //PaymentMethodDTO
//    List<AccountMasterDTO> accountMasterDTOList;
//    private Long paymentmethodId;
        private String paymentmethodName;
        private String paymentmethodDescription;
        private String paymentmethodType;
        private String companyId;
        private String accountMaster;
        //    private String status;
//    private String defaultType;
        private String accountMasterId;
        private String validateVoucher;


        //ServiceChanrgeDTO
        private String serviceChargeId;
        private String serviceChargeName;
        private String servicePercentage;
//    private Date effectiveDate;
//    private String locationId;
//    private String useraccount_id;


        //StateDTO
//    private Long id;
        private String stateCode;
        //    private String stateName;
//    private String vehicleSeries;
//    private String status;
        private String countryid;

        //CountryDTO type replaced to String
//    private CountryDTO countryId;
//    private String message;


        //BrandDTO
//    private Long brandId;
//    private String brandName;
        private String brandDescription;
//    private String status;

        //CategoryDTO
        private String itemCategoryId;
        private String itemCategoryCode;
        private String itemCategoryName;
        private String itemCategoryDesc;
//    private String ledgerINV;
//    private String ledgerCOGS;
//    private String ledgerIncome;
//    private String ledgerAdjustment;
//    private String defaultType;
//    private String itemCode;
//    private String status;


        //UOMConvertorDTO
        private String uomConvertorId;
        private String unitOfMeasurement;
        private String uomConvertorEffectiveDate;
        private String uomValue;
        //    private String locationId;
//    private String useraccount_id;
//    private String status;
        private String uomConvertedName;
        private String unitOfMeasurementId;

        //AttributeDTO
        private String attributeId;
        private String attributeName;
        private String attributeConfigurator;
        private String attributeDescription;
        private String attributeStatus;

//    private CompanyInfoDTO companyId;
//    private String locationId;
//    private String useraccount_id;
//    private Long attributeConfiguratorId;
//    private String status;

        //AttributeConfiguratorDTO
//    private Long attributeConfiguratorId;
        private String attributeConfiguratorEffectiveDate;
        private String attributeConfiguratorValue;
        private String attributeConfiguratorName;
        private String attributeConfiguratorDescription;

        //CompanyInfo type replaced to String
//    private CompanyInfo companyId;
//    private String locationId;
//    private String useraccount_id;
//    private String status;
  //      private List<AttributeDTO> attributeDTOList;

        //HsnCodeDTO
        private String code;
//    private String description;
//    private String status;


        //TaxDTO
        private String taxid;
        private String taxString;
        private String taxTypeId;
        private String taxClassId;
        private String linkedAccountId;
        private String taxGroup;
        private String taxCode;
        private String taxName;
        private String taxPercentage;
        //    private boolean flag;
//    private String effectiveDate;
        private String expiryDate;
        private String taxStatus;
        private String accountCode;
        private String taxDescription;

        //PaymentVoucherDTO
        private String pvId;
        private String pvno;
        private String pvdate;
        private String reason;
        private String amount;
        private String taxamount;
        private String totalamount;
        //    private String flag;
//    private String status;
//    private Date fromDate;
//    private Date toDate;
        private String voucherNo;
//    private Long paymentmethodId;


        //inventoryLocation
//    List<StateDTO> StateDTOList;
        private String inventoryLocationId;
        private String inventoryLocationName;
        private String inventoryLocationAddress;
        private String inventoryLocationContactPerson;
        private String inventoryLocationContact;
        private String inventoryLocationFaxNo;
        private String inventoryLocationEmail;
        private String inventoryLocationStatus;
       // private String gSTIN;
        //    private String stateName;
        private String companyIdForLoc;
        private String supplierIdForLoc;
        private String customerIdForLoc;
        private String stateIdForLoc;
        private String nextRef;
//    private String prefix;
//    private String cmpyCountry;
//    private String status;

        //SalesPricingDTO
//    List<CustomerDTO> customerDTOList;
//    List<ItemDTO> itemDTOList;
        private String salesPriceId;
        private String accountid;
        private String accountName;
        //    private double price;
//    private CustomerDTO customerId;
//    private String customerName;
//    private String itemName;
//    private String customerID;
//    private String itemID;
        private String itemid;
        //    private String currencyName;
//    private ItemDTO itemId;
        private String custId;
        //    private Long currencyId;
        private String InventoryLocationId;
//    private int id;

        //PurchasePricingDTO
//    List<SupplierDTO> supplierDTOList;
//    List<ItemDTO> itemDTOList;
        private String purchasePriceId;
        //    private String itemName;
//    private Long supplierId;
//    private Long itemId;
//    private String supplierName;
        private String supplierID;
        //    private String itemID;
//    private double price;
        private String supplierCode;

        //InventoryLocationTransferDTO
//    private List<InventoryLocation> locList;
//    private List<InventoryMovement> moveList;
        private String ltNo;
        private String Date;
        //    private String prefix;
        private String ilocTransDetailsId;
//    private LocationTransfer ltdetails;

        //Object type replaced to String
//    private Item itemId;

        //    private String itemCode;
        private String itemDesc;
        private String qtyTransferred;
        private String units;
        //    private String status;
        private String memo;
//    private String locationId;
//    private String useraccount_id;
//    private double stock;

        //SalesDiscountConfigurationDTO
//List<CustomerDTO> customerDTOList;
//    List<ItemDTO> itemDTOList;
        private String discountId;
        //    private Date fromDate;
//    private Date toDate;
//    private Double amountordiscount;
//    private CustomerDTO customerId;
//    private String customerName;
//    private String itemName;
//    private ItemDTO itemId;
//    private String checkBoxValue;
//    private String customerID;
//    private Long itemID;
        private String promotionName;
        private String minPurchaseAmt;
        private String promoCode;
        private String useCode;
        private String percentageFixed;
        private String maxDiscount;
        private String freeProduct;
        private String freeProductQty;
        //    private String status;
        private String qty;
        private String quantity;
        private String discountValue;

        //LoyaltyDTO
//    List<ItemDTO> itemDTOList;
        private String loyaltyId;
        private String loyaltyProgramName;
        //    private String perCurrency;
//    private String perOrder;
//    private String perProduct;
        private String loyaltyProgramNamePoints;
        //    private String perCurrencyPoints;
//    private String perOrderPoints;
//    private String perProductPoints;
//    private String itemID;
//    private ItemDTO itemId;
        private String loyaltyType;
        //    private Date fromDate;
//    private Date toDate;
//    private String message;
//    private String amountOrPercent;
//    private double percentage;
        private String minQty;
        private String type;
//    private String status;

        //ItemCountAdjustDTO
//    private String quantity;
//    private String price;
        private String item;
        //    private String itemCode;
//    private String itemName;
//    private double stock;
        private String count;
        private String variance;
        private String varcost;
        private String counted;
        private String costVariance;
        private String cost;
        private String scrapCounted;
        private String scrapItemId;
        //    private Long itemId;
        private String tablesId;
        private String serializeNo;
        private String dt;

        //ItemCommissionDTO
//    List<CustomerDTO> customerDTOList;
//    List<ItemDTO> itemDTOList;
        private String itemCommId;
//    private Date fromDate;
//    private Date toDate;
//    private Double amountordiscount;
//    private CustomerDTO customerId;
//    private String customerName;
//    private String itemName;
//    private ItemDTO itemId;
//    private String checkBoxValue;
//    private String customerID;
//    private Long itemID;
//    private String status;

        //AssestsDTO
//    List<SerializableItemsDTO> serializableItemsDTOList;
//    List<AssestsCategoryDTO> assestCategoryDTOList;
//    List<ItemTypeDTO> itemTypeDTOList;
//    List<ItemUOMTypeDTO> itemUOMTypeDTOList;
//    List<ItemMSICDTO> itemMSICDTOList;
//    List<ItemBrandDTO> itemBrandDTOList;
//    List<ItemCountTypeDTO> itemCountTypeDTOList;
//    List<ItemIPTaxDTO> itemIPTaxDTOList;
//    List<ItemOPTaxDTO> itemOPTaxDTOList;
//    List<UomConvertorDTO> uomConvertorDTOList;
        private String assestId;
        private String assestCode;
        private String assestName;
        private String assestDesc;
        private String taxId;
        private String outputTaxId;
        //    private double stock;
        private String discountAmountRpercent;
        private String itemTypeName;
        private String isDiscountInPercent;
        private String salesPrice;
        private String purchasePrice;
        private String assestTypeName;
        //    private String assestsCategoryName;
//    private String brandName;
        private String Status;
        private String unitPrice;
        private String serializableItemId;
        private String serializableStatus;
        private String certificateno;
        private String countType;
        private String assestCategoryId;
        private String itemTypeId;
        private String UnitOfMeasurementId;
        private String mscid;
        private String inputTaxId;
        //    private Long brandId;
        private String inventoryMovementId;
        private String fileName;
        private String cess;
        private String hsnCode;
        private String uomName;
        private String inclusiveJSON;

        //AssestsConfiguratorDTO
        private String assestsCategoryId;
        private String assestsCategoryCode;
        //    private String assestsCategoryName;
        private String assestsCategoryDesc;
        //    private String ledgerINV;
//    private String ledgerCOGS;
//    private String ledgerIncome;
//    private String ledgerAdjustment;
        private String restaurentStatus;
//    private String status;

        //RedemptionDTO
        private String redeemId;
        private String redeemProgramName;
        //    private String perCurrency;
//    private String perOrder;
//    private String perProduct;
        private String redeemProgramNamePoints;
        //    private String perCurrencyPoints;
//    private String perOrderPoints;
//    private String perProductPoints;
        private String redeemType;
        //    private String amountOrPercent;
//    private double percentage;
//    private String itemID;
//    private Date fromDate;
//    private Date toDate;
//    private String message;
        private String maxQty;
//    private String status;

        //FeatureAccessRightsDTO
//    private Long id;
        private String featuresName;
        private String featuresUsers;
        private String featuresStartDate;
        private String featuresEndDate;
        private String featuresValidity;
        private String featuresBehaviour;
        private String featuresStatus;


        //PermissionGroupDTO
//    private Long pgId;
        private String featureName; //Sales
        //    private String description;
        private String pgkey;
//    private String saasStatus;

        //PermissionMasterDTO
        private String pmId;
        //    private String description;
//    private Long pgId;
        private String level2;
        private String pMasterId;
        private String permissionMasterkey;
//    private List<PermissionMasterDTO> level1List;
//    private List<PermissionMaster2DTO> level2List;
//    private String saasStatus;


        //OtherContactsDTO
//    List<EmployeeDTO> employeeDTOList;
        private String otherContactId;
        private String fullName;
        private String prefixName;
        private String firstName;
        private String middleName;
        private String lastName;
        private String contactNumber;
        private String dob;
        private String companyName;
        //    private String address;
        private String email;
        //    private String phoneNumber1;
//    private String phoneNumber2;
//    private String faxTelex;
//    private String contactPerson;
//    private String generalNote;
//    private String website;
        private String gstCode;
        //    private String panNO;
//    private String bankName;
//    private String accountNo;
//    private String iFSCCode;
//    private String branchName;
//    private String personIncharge;
        private String imageFile;
        //    private String locationId;
//    private String useraccount_id;
        private String hiConnectStatus;
        private String hiConnectCompnyRegNo;
        private String country;
        private String currency;
        //    private String state;
//    private List<StateDTO> stateDTOList = new ArrayList<>();
//    private List<CountryDTO> countryDTOList = new ArrayList<>();
//    private List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        private String selectedContact;
        //    private String subject;
//    private Long employeeId;
//    private String description;
//    private String cmpyCountry;
        private String contStatus;
        //    private String employeeName;
        private String filterApplied;

        //SupplierDTO
//    private Long supplierId;
//    private String supplierName;
        private String supplierNumber;
        private String supplierEmail;
        //    private String billingAddress;
//    private String website;
//    private String phoneNumber1;
//    private String phoneNumber2;
        private String fax;
        private String contactName;
        //    private String generalNote;
//    private String gstIn;
//    private String state;
//    private String bankName;
//    private String accountNo;
//    private String iFSCCode;
//    private String branchName;
//    private String personIncharge;
//    private String countryId;
//    private String currencyId;
//    private String panNO;
//    private String cmpyCountry;
//    private List<StateDTO> stateDTOList = new ArrayList<>();
//    private List<CountryDTO> countryDTOList = new ArrayList<>();
//    private List<CurrencyDTO> currencyDTOList = new ArrayList<>();
//    private Long terms;
//    private String contactPerson;
        private String creditLimit;
        private String creditDesc;
//    private String status;

        //CustomerDTO
//    private String customerId;
//    private String customerName;
        private String customerNumber;
        private String customerCode;
        private String customerEmail;
        private String customerContact;
        //    private String gstIn;
//    private String state;
//    private String bankName;
//    private String accountNo;
//    private String iFSCCode;
//    private String branchName;
//    private String personIncharge;
//    private String countryId;
//    private String currencyId;
//    private String panNO;
//    private String website;
//    private String billingAddress;
        private String loyaltyPoints;
        private String totalLoyaltyPoints;
        private String phoneNumber;
        //    private String address;
//    private String contactPerson;
        private String companyNumber;
        //    private String faxTelex;
        private String creditedLimit;
        //    private Long terms;
//    private String status;
        private String pincode;
//    private String vehicleSeries;

        //TermsAndConditionDTO
        private String termsAndConditionId;
        private String termsAndConditionName;
        private String termsAndConditionDesc;
        //    private String discountId;
        private String earlyPaymentId;
        private String latePaymentCharges;
        //    private String status;
        private String duedays;

        //EventCrmDTO
//    List<EmployeeDTO> employeeDTOList;
        private String eventId;
        private String eventName;
        private String eventDate;
        //    private String description;
//    private String subject;
//    private String status;
//    private Long employeeId;
        private String activityType;
        private String location;
        private String notification;
        private String priority;
//    private String employeeName;

        //Patient
        private String patientId;
        private String hospitalName;
        private String icNo;
        private String doctorName;
        private String patientName;
        private String patientAccountCode;
        private String mobile;
        private String commission;

        private String typeDoc;
        private String type_flag;
        private String from_reg;
        private String to_reg;
        private String fromCompname;
        private String toCompname;
        private String transcationStatus;

}
