// /**
//  * Created by azgar.h on 6/22/2017.
//  */
// app.controller('goldsalesCtrl',[
//     '$scope',
//     '$http',
//     '$location',
//     '$filter',
//     'Notification',
//     'ngTableParams',
//     '$timeout',
//     '$window',
//     '$cookies',
//     function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $cookies){
// // body...
//         $scope.hiposServerURL = "hiposrestapi/hipos";
//         $scope.retailServerURL = "hiposrestapi/retail";
//         $scope.returnQty = parseFloat('0.00');
//         $scope.totalBeforDiscount = parseFloat('0.00');
//         $scope.totalDiscount = parseFloat('0.00');
//         $scope.totalAfterDiscount = parseFloat('0.00');
//         $scope.totalTaxAmt = parseFloat('0.00');
//         $scope.customerId = 1;
//         $scope.countVal = 0;
//         $scope.customerEmail = "";
//         $scope.userRights = [];
//         $scope.operation = 'Create';
//         $scope.cursorPosVal = 0;
//         $scope.customer = 1;
//         $scope.SIId = 0;
//         $scope.taxList = [];
//         $scope.serializableItemsList = [];
//         $scope.itemList = [];
//         $scope.posSalesList = [];
//         $scope.posAdvanceSalesList = [];
//         $scope.SIList = [];
//         $scope.invokeOrderId = '';
//         $scope.InvokeOrderList = [];
//         $scope.selectedItemsList = [];
//         $scope.retailPostData = [];
//         $scope.customerSearchText = "";
//         $scope.cardPayementList = [];
//         $scope.voucherPayementList = [];
//         $scope.removePayments = "";
//         $scope.checkbox = "";
//         $scope.totaldupltax = parseFloat(0);
//         $scope.totaltaxdupltax = parseFloat(0);
//         $scope.serialNo = "";
//         $scope.today = new Date();
//         $scope.companyName = "";
//         $scope.cashPayment = parseFloat(0);
//         $scope.taxSummaryList = [];
//         $scope.companyAddress = "";
//         $scope.companyPhoneNo = "";
//         $scope.companyFax = "";
//         $scope.companyGstNo = "";
//         $scope.cutomerName = "";
//         $scope.balanceAmt = parseFloat(0);
//         $scope.inventoryadress = "";
//         $scope.inventoryphone = "";
//         $scope.inventoryfax = "";
//         $scope.formNo="";
//         $scope.returnType="";
//         $scope.salesOrderList=[];
//         $scope.receiptPaymentList=[];
//         $scope.disableButtons=false;
//         $scope.fullUserName="";
//         // $scope.tax = "SimplifiedTax";
//         // $scope.fullSimplTax ='Full Tax';
//         // $scope.simplifiedTax ='Full Tax';
//         var location = window.location.origin;
//         $scope.taxTypes = [
//             {name: 'FullTax', value: 'FullTax'},
//             {name: 'SimplifiedTax', value: 'SimplifiedTax'},
//         ];
//         $scope.notHide = "";
//         $scope.updatesimplifiedTax = function (newCustVal) {
//             $scope.simplifiedTax = newCustVal.fullSimplTax;
//         };
//         $scope.updateCustomerId = function (newCustVal) {
//             $scope.customer = newCustVal.customerId;
//             $scope.removeAllItems();
//         }
//         $scope.barCodeInputfocus = function () {
//             $("#barcodeInput").focus();
//         };
//         $scope.barCodeInputfocus();
//         $scope.serialBarCodeInputfocus = function () {
//             $("#serialBarcodeInput").focus();
//         };
//         $scope.serialBarCodeInputfocus();
//         $scope.companyLogoPath = "";
//         $scope.getPageLoadData = function () {
//             $("#multiprintreceipt").modal('hide');
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getPageLoadData').then(function (response) {
//                 var data = response.data;
//                 if (!angular.isUndefined(data) && data !== null) {
//                     $scope.taxList = data.taxList;
//                     $scope.userRights = data.userRights;
//                     $scope.customerList = data.customers;
//                     $scope.companyLogoPath = location + "/" + data.companyLogoPath;
//                     $scope.customerId = $scope.customerList[0].customerId;
//                     $scope.fullSimplTax = $scope.taxTypes[0].value;
//                     $scope.customerSearchText = "Cash Customer|01";
//                     $scope.companyName = data.companyName;
//                     $scope.fullUserName = data.fullUserName;
//                     console.log($scope.companyLogoPath)
//                 }
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.getPageLoadData();
//         // $scope.getItemList = function () {
//         //     $(".loader").css("display", "block");
//         //     $http.get($scope.hiposServerURL+ "/" + $scope.customer  + '/getItemList').then(function (data) {
//         //         $scope.itemList = angular.copy(data);
//         //         $("#selectItem").modal('show');
//         //         $scope.searchText = $scope.itemSearchText;
//         //     }).error(function (data, status, header, config) {
//         //         Notification.error({message: 'Somthing went wrong, please try again', positionX: 'center', delay: 2000});
//         //     });
//         // };
//
//         $scope.getItemList = function (val) {
//             $(".loader").css("display", "block");
//             if (angular.isUndefined(val)) {
//                 val = "";
//             }
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getItemList?itemSearchText=' + val).then(function (response) {
//                 var data = response.data;
//                 $scope.itemList = angular.copy(data);
//                 $("#selectItem").modal('show');
//                 $scope.itemSearchText = val;
//                 $scope.searchText = val;
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.getItemListForRepalce = function () {
//             $(".loader").css("display", "block");
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getItemList').then(function (response) {
//                 var data = response.data;
//                 $scope.itemList = angular.copy(data);
//                 $("#selectReplaceItem").modal('show');
//                 $scope.searchText = $scope.itemSearchText;
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         // $scope.getCustomerList = function () {
//         //     $(".loader").css("display", "block");
//         //     $http.get($scope.hiposServerURL+ "/" + $scope.customer  + '/getCustomerList').then(function (data) {
//         //         $scope.customerList = angular.copy(data);
//         //         $("#selectCustomer").modal('show');
//         //         $scope.searchCustomerText ='';
//         //     }).error(function (data, status, header, config) {
//         //         Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//         //     });
//         // };
//         $scope.getCustomerListSearch = function (val) {
//             $(".loader").css("display", "block");
//             if (angular.isUndefined(val)) {
//                 val = "";
//             }
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getCustomerListSearch?searchCustomerText=' + val).then(function (response) {
//                 var data = response.data;
//                 $scope.customerList = angular.copy(data);
//                 $("#selectCustomer").modal('show');
//                 $scope.searchCustomerText = val;
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         //Chinmay
//         //Function is used to get complete POS Invoice List, when we Select POS Invoices Button
//         //Start function
//         // $scope.getPosInvoiceList=function () {
//         //     $(".loader").css("display", "block");
//         //     $http.get($scope.retailServerURL+"/" + $scope.customer  +'/getPosInvoices').then(function (data) {
//         //     });
//         // };
//         //End
//         $scope.customer = 0;
//         $scope.getSerialItem = function (itemCode) {
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getSerialItem?itemCode=' + itemCode).then(function (response) {
//                 var data = response.data;
//                 if (data.length > 0) {
//                     $scope.addSelectedItemList(data[0]);
//                 } else {
//                     Notification.info({
//                         message: 'Item not found with barcode ' + itemCode,
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                 }
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.customer = 0;
//         $scope.getItem = function (itemCode) {
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getItem?itemCode=' + itemCode).then(function (response) {
//                 var data = response.data;
//                 if (data.length > 0) {
//                     $scope.addSelectedItemList(data[0]);
//                 } else {
//                     Notification.info({
//                         message: 'Item not found with barcode ' + itemCode,
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                 }
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.customer = 0;
//         $scope.getItemBarcode = function (itemCode) {
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getItem?itemCode=' + itemCode).then(function (response) {
//                 var data = response.data;
//                 if (!angular.isUndefined(data) && data !== null && data !== "") {
//                     $scope.addSelectedItemList(data[0]);
//                 } else {
//                     Notification.info({
//                         message: 'Item not found with barcode ' + itemCode,
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                 }
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         // $scope.customer=0;
//         // $scope.getSerializableItems = function (serializableItemCode,keyEvent) {
//         //     $http.get($scope.hiposServerURL+ "/" + $scope.customer  + '/getSerializableItems?serializableItemCode=' +serializableItemCode).then(function (data) {
//         //         if (!angular.isUndefined(data) && data !== null && data !== "")
//         //         {
//         //             $scope.serializableItemsList[keyEvent]=data;
//         //             // $scope.addSelectedItemList(data[0]);
//         //         } else {
//         //             Notification.info({message: 'Serializable Item not found with barcode ' + itemCode, positionX: 'center', delay: 2000});
//         //         }
//         //     }).error(function (data, status, header, config) {
//         //         Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//         //     });
//         //     $('#qty_'+0).attr('readonly', true);
//         // };
//         $scope.addCustomer = function () {
//             $(".loader").css("display", "block");
//             $http.get($scope.hiposServerURL + "/" + $scope.customer + '/addCustomer').then(function (response) {
//                 var data = response.data;
//                 $scope.customerAccountMasterDTOList = angular.copy(data);
//                 $("#addCustomer").modal('show');
//                 // $scope.searchText = $scope.itemSearchText;
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.saveCustomer = function () {
//             if (angular.isUndefined($scope.customerNameText) || $scope.customerNameText == '') {
//                 Notification.warning({message: 'customer Name can not be empty', positionX: 'center', delay: 2000});
//             }
//             // else   if( $scope.customerEmailText != '') {
//             //     Notification.warning({message: 'Enter Valid Email Id', positionX: 'center', delay: 2000});
//             // }
//             // angular.
//             // else   if (angular.isUndefined($scope.customerContactText) || $scope.customerContactText == '') {
//             //     Notification.warning({message: 'Enter Valid Contact Number', positionX: 'center', delay: 2000});
//             // }
//             // else   if ( $scope.customerNameText != '') {
//             //     var saveCustomerDetails;
//             //     saveCustomerDetails={
//             //         customerName:$scope.customerNameText,
//             //         customerEmail:$scope.customerEmailText,
//             //         customerContact:$scope.customerContactText,
//             //         customerAddress:$scope.customerAddressText,
//             //         //  accountid:$scope.customerAccountId,
//             //     };
//             //     $http.post($scope.retailServerURL + '/duplicateCustomerCheck', angular.toJson(saveCustomerDetails, "Create")).then(function (data, status, headers, config) {
//             //         if (angular.isUndefined(data) && data == null) {
//             //             Notification.warning({message: 'Customer Name Already Exist ', positionX: 'center', delay: 2000});
//             //         }
//             //     })
//             // }
//
//             else {
//                 $scope.customerName = "";
//                 $scope.customerEmail = "";
//                 $scope.customerContact = "";
//                 $scope.customerAddress = "";
//                 var saveCustomerDetails;
//                 saveCustomerDetails = {
//                     customerName: $scope.customerNameText,
//                     customerEmail: $scope.customerEmailText,
//                     customerContact: $scope.customerContactText,
//                     customerAddress: $scope.customerAddressText,
//                     //  accountid:$scope.customerAccountId,
//                 };
//                 $http.post($scope.retailServerURL + '/saveCustomer', angular.toJson(saveCustomerDetails, "Create")).then(function (response) {
//                     var data = response.data;
//                     $scope.removeCustomerDetails();
//                     $("#addCustomer").modal('hide');
//                     if (!angular.isUndefined(data) && data !== null) {
//                         $scope.customerSearchText = data.customerName;
//                         $scope.customerId = data.customerId;
//                         $scope.customerEmail = data.customerEmail;
//                     }
//                     Notification.success({message: 'Customer Created  successfully', positionX: 'center', delay: 2000});
//                 },function (error) {
//                     Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});
//                 })
//
//                 //     .error(function (data, status, header, config) {
//                 //     Notification.error({
//                 //         message: 'Something went wrong, please try again',
//                 //         positionX: 'center',
//                 //         delay: 2000
//                 //     });
//                 // });
//             }
//             ;
//         };
//         $scope.clearInvokeOrder = function () {
//             $http.post($scope.retailServerURL + '/clearInvokeOrder').then(function (response) {
//                 var data = response.data;
//                 Notification.info({message: 'Invoke Orders cleared successfully', positionX: 'center', delay: 2000});
//             },function (error) {
//                 Notification.error({message: 'Failed to  clear invoke orders', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Failed to  clear invoke orders', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.getSIList = function () {
// //        $http.get($scope.retailServerURL + '/getSIList').then(function (data) {
// //            if (!angular.isUndefined(data) && data !== null && data.length > 0) {
// //                $scope.SIList = data;
//             $("#returnSIList").modal('show');
// //            } else {
// //                Notification.warning({message: 'There is no invoices found to return', positionX: 'center', delay: 2000});
// //            }
// //        }).error(function (data, status, header, config) {
// //            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
// //        });
//         };
//         $scope.openRepairItem = function () {
//             $scope.getTotalAmtForSelectedItems();
//             $scope.totalItemQtyRepaired = parseFloat($scope.returnQty.toFixed(2));
//             $("#repairItem").modal('show');
//         };
//         $scope.openReplaceItem = function () {
//             $scope.getTotalAmtForSelectedItems();
//             $scope.totalItemQtyReplaced = parseFloat($scope.returnQty.toFixed(2));
//             $("#repalceItem").modal('show');
//         };
//         $scope.getInvokeOrderList = function () {
//             $http.get($scope.retailServerURL + '/getInvokeOrderList').then(function (response) {
//                 var data = response.data;
//                 if (!angular.isUndefined(data) && data !== null && data.length > 0) {
//                     $scope.InvokeOrderList = data;
//                     $("#selectInvokeOrderList").modal('show');
//                 } else {
//                     Notification.warning({
//                         message: 'There is no invoke orders are selected',
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                 }
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.saveSI = function (paymentType) {
//             if (!$scope.validatePayment(paymentType)) {
//                 return;
//             }
//             $http.post($scope.retailServerURL + '/save',
//                 angular.toJson($scope.populateSaveSIData(paymentType), "Create")).then(function (response) {
//                 var data = response.data;
//                 $("#cashpayment").modal('hide');
//                 $("#creditcardpayment").modal('hide');
//                 $("#voucherPayment").modal('hide')
//                 $("#multiPayment").modal('hide');
//                 $scope.removeAllItems();
//                 $scope.populateSIResponceData(data, paymentType);
//                 Notification.success({message: 'Order has been saved successfully', positionX: 'center', delay: 2000});
//
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.saveMultiPaySI = function (paymentType) {
//             if (!$scope.validatePayment(paymentType)) {
//                 return;
//             }
//             $http.post($scope.retailServerURL + '/saveGold',
//                 angular.toJson($scope.populateSaveSIMultiPayData(paymentType), "Create")).then(function (response) {
//                 var data = response.data;
//                 $("#cashpayment").modal('hide');
//                 $("#creditcardpayment").modal('hide');
//                 $("#voucherPayment").modal('hide')
//                 $("#multiPayment").modal('hide');
//                 $scope.removeAllItems();
//                 $scope.populateSIResponceData(data, paymentType);
//                 Notification.success({message: 'Order has been saved successfully', positionX: 'center', delay: 2000});
//                 $("#paymentNew").modal('hide');
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.saveSIandEmail = function (paymentType) {
//             if (!$scope.validatePayment(paymentType)) {
//                 return;
//             }
//             //First time it will be empty.Condition will be passed.
//             //It will try to fetch from customer list data
//             //second time. After filling data no need search from Customer list. email won't be empty.
//             if (!$scope.customerEmail) {
//                 $scope.customerEmail = $scope.getEmailOfCustmr($scope.customerList, $scope.customer);
//             }
//             //The data fetched from Customer list is also empty.Then it will show error presented with box.
//             // Then they will fill it.
//             //If customer is having mail id already this both conditions will be surpassed.
//             if (!$scope.customerEmail) {
//                 Notification.error({
//                     message: 'Email is not configured for customer please enter email id',
//                     positionX: 'center',
//                     delay: 2000
//                 })
//                 $scope.showEmailBox = true;
//                 return;
//             } else {
//                 $scope.showEmailBox = false;
//             }
//             $scope.isDisabled = true;
//             $http.post($scope.retailServerURL + '/saveAndEmail', angular.toJson($scope.populateSaveSIMultiPayData(paymentType),
//                 "Create")).then(function (response) {
//                 var data = response.data;
//                 if (data.result == 'no mail server') {
//                     Notification.error({message: 'Please Configure Email Server', positionX: 'center', delay: 2000});
//                     $scope.showEmailBox = true;
//                     $scope.isDisabled = false;
//                     return;
//                 }
//                 $("#cashpayment").modal('hide');
//                 $("#creditcardpayment").modal('hide');
//                 $("#voucherPayment").modal('hide');
//                 $scope.clearMultiPayment();
//                 $scope.showEmailBox = false;
//                 $scope.isDisabled = false;
//                 Notification.success({
//                     message: 'Order has been saved and Mailed successfully',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//                 $("#paymentNew").modal('hide');
//
//             },function (error) {
//                 $scope.isDisabled = false;
//                   Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     $scope.isDisabled = false;
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.getEmailOfCustmr = function (arrayOfCustomers, customerId) {
//             if (customerId != 0) {
//                 var Customer = $filter('filter')(arrayOfCustomers, {'customerId': customerId});
//                 return Customer[0].customerEmail;
//             }
//         };
//         $scope.validatePayment = function (paymentType) {
//             if ("cashPayment" === paymentType) {
//                 if (angular.isUndefined($scope.totalCPAmountTendered) || $scope.totalCPAmountTendered === ""
//                     || !(angular.isNumber(parseFloat($scope.totalCPAmountTendered))) || parseFloat($scope.totalCPAmountTendered) <= 0) {
//                     Notification.error({message: 'Amount tendered should not be zero', positionX: 'center', delay: 2000});
//                     return false;
//                 } else if (parseFloat($scope.totalCPAmountTendered) < parseFloat($scope.totalCPAfterDiscount)) {
//                     Notification.error({
//                         message: 'Amount tendered should not be less than payment amount',
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                     return false;
//                 }
//             } else if ("creditPayment" === paymentType) {
//                 if (angular.isUndefined($scope.totalCCPAfterDiscount) || $scope.totalCCPAfterDiscount === ""
//                     || !(angular.isNumber(parseFloat($scope.totalCCPAfterDiscount))) || parseFloat($scope.totalCCPAfterDiscount) <= 0) {
//                     Notification.error({message: 'Payment Amount should not be zero', positionX: 'center', delay: 2000});
//                     return false;
//                 } else if (angular.isUndefined($scope.totalCCPTransactionNo) ||
//                     $scope.totalCCPTransactionNo === "") {
//                     Notification.error({message: 'Transaction No should not be black', positionX: 'center', delay: 2000});
//                     return false;
//                 }
//             } else if ("voucherPayment" === paymentType) {
//                 if (angular.isUndefined($scope.totalVPAmountTendered) || $scope.totalVPAmountTendered === ""
//                     || !(angular.isNumber(parseFloat($scope.totalVPAmountTendered)))
//                     || parseFloat($scope.totalVPAmountTendered) < 0) {
//                     Notification.error({message: 'Amount tendered  should not be zero', positionX: 'center', delay: 2000});
//                     return false;
//                 }
//
//
//                 // else if (parseFloat( $scope.totalVPAfterDiscount) >0) {
//                 //     Notification.error({message: 'Amount  should not be less than payment amount', positionX: 'center', delay: 2000});
//                 //     return false;
//                 // }
//                 else if (parseFloat($scope.totalVPAmountTendered) < parseFloat($scope.totalCPAfterDiscount)) {
//                     Notification.error({
//                         message: 'Amount tendered should not be less than payment amount',
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                     return false;
//                 } else if (angular.isUndefined($scope.voucherNo) || $scope.voucherNo === "") {
//                     Notification.error({message: 'Please enter voucherNo.', positionX: 'center', delay: 2000});
//                     return false;
//                 } else if (angular.isUndefined($scope.totalVoucherAmt) || $scope.totalVoucherAmt === "") {
//                     Notification.error({message: 'Please enter voucher amount', positionX: 'center', delay: 2000});
//                     return false;
//                 }
//             }
//             return true;
//         };
//         $scope.returnSI = function (paymentType) {
//             if (angular.isUndefined($scope.selectedItemsList) || $scope.selectedItemsList.length <= 0) {
//                 Notification.warning({
//                     message: 'At lest One item has to be selected to return',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//             } else {
//                 $http.post($scope.retailServerURL + '/return',
//                     angular.toJson($scope.populateReturnSIData($scope.SIId, 'Return', paymentType))).then(function (response) {
//                     var data = response.data;
//                     $("#returnCashpayment").modal('hide');
//                     $scope.populateSRResponseData(data)
//                     $scope.removeAllItems();
//
//                     Notification.success({
//                         message: 'Order has been modified successfully',
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                 },function (error) {
//                     Notification.error({
//                         message: 'Something went wrong, please try again',
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                 })
//
//
//                 //     .error(function (data, status, header, config) {
//                 //     Notification.error({
//                 //         message: 'Something went wrong, please try again',
//                 //         positionX: 'center',
//                 //         delay: 2000
//                 //     });
//                 // });
//             }
//         };
//         $scope.returnRecievePaymentSI = function (paymentType,returnType) {
//             $http.post($scope.retailServerURL + '/returnRecievePayment/'+returnType,
//                 angular.toJson($scope.populateReturnSIData($scope.SIId, 'Return', paymentType))).then(function (response) {
//                 var data = response.data;
//                 $("#returnCashpayment").modal('hide');
//                 $scope.populateSRResponseData(data)
//                 $scope.removeAllItems();
//
//                 Notification.success({
//                     message: 'Order has been modified successfully',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//             },function (error) {
//                 $("#returnCashpaymentRecievePayment").modal('hide');
//                 $("#PosInvoiceAdvanceList").modal('hide');
//                 Notification.success({
//                     message: 'Order has been modified successfully',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     $("#returnCashpaymentRecievePayment").modal('hide');
//             //     $("#PosInvoiceAdvanceList").modal('hide');
//             //     Notification.success({
//             //         message: 'Order has been modified successfully',
//             //         positionX: 'center',
//             //         delay: 2000
//             //     });
//             // });
//         };
//         $scope.saveInvokeOrder = function (paymentType) {
//             if (angular.isUndefined($scope.selectedItemsList) || $scope.selectedItemsList.length <= 0) {
//                 Notification.warning({message: 'At lest One item has to be selected', positionX: 'center', delay: 2000});
//             } else if ($scope.operation === 'Return') {
//                 Notification.warning({message: 'Return order can not be invoked', positionX: 'center', delay: 2000});
//             } else {
//                 if ($scope.isUndefinedOrNull($scope.invokeOrderId)) {
//                     $scope.invokeOrderId = -1;
//                 }
//                 $http.post($scope.retailServerURL + '/saveInvokeOrder?invokeOrderName=' + $scope.invokeOrderName + '&invokeOrderId=' + $scope.invokeOrderId,
//                     angular.toJson($scope.populateSaveSIData(paymentType, "InvokeOrder")))
//                     .success(function (data, status, headers, config) {
//                         $scope.removeAllItems();
//                     },function (error) {
//                         Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});
//                     })
//
//             }
//         };
//         $scope.getInvokeOrder = function (invokeOrderId) {
//             $http.get($scope.retailServerURL + '/getInvokeOrder?invokeOrderId=' + invokeOrderId).then(function (response) {
//                 var data = response.data;
//                 $scope.removeAllItems();
//                 $scope.operation = 'InvokeOrder';
//                 $scope.invokeOrderId = parseFloat(invokeOrderId);
//                 $scope.InvokeOrderIndex = $scope.InvokeOrderIndexOf($scope.InvokeOrderList, invokeOrderId);
//                 $scope.invokeOrderName = $scope.InvokeOrderList[$scope.InvokeOrderIndex].invokeOrderNo;
//                 $scope.selectedItemsList = data.selectedItemsList;
//                 $scope.getTotalAmtForSelectedItems();
//                 $("#selectInvokeOrderList").modal('hide');
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.salesInvoice = "";
// //       Get's the salesinvoice details for return
//         $scope.getSalesInvoice = function () {
//             if ($scope.salesInvoice === "") {
//                 Notification.info({message: "Please Enter Invoice No", positionX: 'center', delay: 2000});
//                 return;
//             }
//             $http.get($scope.retailServerURL + '/getSalesInvoiceReturn/' + $scope.salesInvoice).then(function (response) {
//                 var data = response.data;
//                 $scope.removeAllItems();
//                 $scope.selectedItemsList = data.selectedItemsList;
//                 $scope.getTotalAmtForSelectedItems();
//                 $scope.SIId = data.siid;
//                 $scope.operation = data.operation;
//                 $("#returnSIList").modal('hide');
//             },function (error) {
//                 Notification.error({message: "Something went wrong in server", positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     if (status == 500) {
//             //         Notification.error({message: "Something went wrong in server", positionX: 'center', delay: 2000});
//             //     } else {
//             //         Notification.error({message: data.message, positionX: 'center', delay: 2000});
//             //     }
//             // });
//         };
//         //Chinmay
//         //Function is used to get complete POS Invoice List, when we Select POS Invoices Button
//         //Start function
//         $scope.getInvoiceList = function (val) {
//             $(".loader").css("display", "block");
//             if (angular.isUndefined(val)) {
//                 val = "";
//             }
//             $http.get($scope.retailServerURL + '/getPosInvoices?itemSearchText=' + val).then(function (response) {
//                 var data = response.data;
//                 $scope.posSalesList = angular.copy(data);
//                 $("#PosInvoiceList").modal('show');
//                 $scope.searchText = val;
//                 $scope.itemSearchText=val;
//                 },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.getprintlist = function () {
//             $("#print_list_modal").modal('show');
//         };
//         $scope.getDuplicateInvoiceList = function (val) {
//             $(".loader").css("display", "block");
//             if (angular.isUndefined(val)) {
//                 val = "";
//             }
//             $http.get($scope.retailServerURL + '/getPosInvoices?itemSearchText=' + val).then(function (response) {
//                 var data = response.data;
//                 $scope.posSalesList = angular.copy(data);
//                 $("#print_list_modal").modal('hide');
//                 $("#PosInvoiceDuplicatePrint").modal('show');
//                 $scope.searchText = val;
//                 $scope.itemSearchText=val;
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.getAdvancePaymentList = function () {
//             $http.get($scope.retailServerURL + '/getAdvancePosInvoices').then(function (response) {
//                 var data = response.data;
//                 $scope.posAdvanceSalesList = angular.copy(data);
//                 $("#PosInvoiceAdvanceList").modal('show');
//                 $scope.searchText = $scope.itemSearchText;
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.remove = function () {
//             $scope.text = "";
//             $scope.searchCustomerText="";
//
//         }
//         $scope.partialRecievePayment = function (val) {
//             $(".loader").css("display", "block");
//             if (angular.isUndefined(val)) {
//                 val = "";
//             }
//             $http.get($scope.retailServerURL + '/getPartialRecievePayment?itemSearchText=' + val).then(function (response) {
//                 var data = response.data;
//                 $scope.posAdvanceSalesList = angular.copy(data);
//                 $("#PosInvoiceRecievePaymentList").modal('show');
//                 $scope.searchText = val;
//                 $scope.itemSearchText=val;
//             }, function (error) {
//                 Notification.error({
//                     message: 'Something went wrong, please try again',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//             })
//             //     .error(function (data, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.saveSalesOrder = function () {
//             if($scope.selectedItemsList.length<1){
//                 Notification.warning({
//                     message: 'Check Out Amount Canot Be Zero',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//                 return false;
//             }
//             $http.post($scope.retailServerURL + '/saveSalesOrder',angular.toJson($scope.populateSaveSIData('',''))).then(function (response) {
//                 var data = response.NotificationData;
//                 $scope.removeAllItems();
//                 Notification.info({message: 'Sales Order Created Successfully', positionX: 'center', delay: 2000})
//             },function (error) {
//                 Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (NotificationData, status, header, config) {
//             //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.openSalesOrder=function () {
//             $http.post($scope.retailServerURL+'/getSalesOrderList/'+$scope.customerId).then(function (response) {
//                 var data = response.data;
//                 $scope.salesOrderList=data;
//                 $("#invokeSalesOrder").modal('show');
//             });
//
//         };
//         $scope.invokeSalesOrder=function (salesOrderNo) {
//             $http.post($scope.retailServerURL+'/invokeSalesOrder/'+salesOrderNo).then(function (response) {
//                 var data = response.data;
//                 $scope.selectedItemsList=data;
//                 $("#invokeSalesOrder").modal('hide');
//             });
//         };
//         $scope.CreateInvoice=function (formNo) {
//             $http.get($scope.retailServerURL + '/createadvancePosInvoices/'+formNo).then(function (response) {
//                 var data = response.data;
//                 if(data=="success"){
//                     Notification.error({message: 'Invoice Is Created Successfully', positionX: 'center', delay: 2000});
//                 }
//             },function (error) {
//                 $("#PosInvoiceAdvanceList").modal('hide');
//                 Notification.info({message: 'Invoice Is Created Successfully', positionX: 'center', delay: 2000});
//             })
//
//             //     .error(function (data, status, header, config) {
//             //     $("#PosInvoiceAdvanceList").modal('hide');
//             //     Notification.info({message: 'Invoice Is Created Successfully', positionX: 'center', delay: 2000});
//             // });
//         };
//         $scope.appendPosSalesInvoice = function (salesInvoice) {
//             $scope.notHide = "posListClick";
//             $http.get($scope.retailServerURL + '/getSalesInvoice/' + salesInvoice).then(function (response) {
//                 var data = response.data;
//                 $scope.removeAllItems();
//                 $scope.operation = 'Return';
//                 $scope.selectedItemsList = data.selectedItemsList;
//                 $scope.getTotalAmtForSelectedItems();
//                 $scope.SIId = data.siid;
//                 // $scope.operation = data.operation;
//                 $("#PosInvoiceList").modal('hide');
//             },function (error) {
//                 Notification.error({message: "Something went wrong in server", positionX: 'center', delay: 2000});
//             })
//             //     .error(function (data, status, header, config) {
//             //     if (status == 500) {
//             //         Notification.error({message: "Something went wrong in server", positionX: 'center', delay: 2000});
//             //     } else {
//             //         Notification.error({message: data.message, positionX: 'center', delay: 2000});
//             //     }
//             // });
//         };
//         $scope.printDuplicateReceipt = function (salesInvoice) {
//             $scope.notHide = "posListClick";
//             $http.get($scope.retailServerURL + '/getSalesInvoiceDuplicateReciept/' + salesInvoice).then(function (response) {
//                 var data = response.data;
//                 $scope.removeAllItems();
//                 $scope.selectedItemsList = data.selectedItemsList;
//                 $scope.getTotalAmtForSelectedItems();
//                 $scope.SIId = data.siid;
//                 $scope.totaldupltax = data.totalCheckOutamt;
//                 $scope.totaltaxdupltax = data.totalTaxAmt;
//                 $scope.companyName = data.companydupData.companyName;
//                 $scope.serialNo = data.srlnNo;
//                 $scope.voucherPayementList = data.voucherPayment.multiVoucherPayments;
//                 $scope.cardPayementList = data.creditPayment.cardPaymentList;
//                 $scope.cashPayment = data.cashPayment.totalCPAmountTendered;
//                 $scope.taxSummaryList = data.taxSummaryList;
//                 $scope.companyAddress = data.companyData.address;
//                 $scope.companyPhoneNo = data.companyData.phone;
//                 $scope.companyFax = data.companyData.fax;
//                 $scope.companyGstNo = data.companyData.registerNo;
//                 $scope.companyGstNo = data.companyData.registerNo;
//                 $scope.cutomerName = data.cutomerName;
//                 $scope.balanceAmt = data.balanceAmount;
//                 // $scope.operation = data.operation;
//                 $("#PosInvoiceList").modal('hide');
//                 $("#duplicate").modal('show');
//             },function (error) {
//                 Notification.error({message: "Something went wrong in server", positionX: 'center', delay: 2000});
//             })
//             //     .error(function (data, status, header, config) {
//             //     if (status == 500) {
//             //         Notification.error({message: "Something went wrong in server", positionX: 'center', delay: 2000});
//             //     } else {
//             //         Notification.error({message: data.message, positionX: 'center', delay: 2000});
//             //     }
//             // });
//         };
//         var mailStatus = false;
//         $scope.mailDuplicateInvoice = function (salesInvoice,customerEmail){
//             $scope.salesInvoice = salesInvoice;
//             if (customerEmail == null || customerEmail == '') {
//                 $("#mailDuplicateInvoiceModal").modal('show');
//                 Notification.error({message:"Customer mail id is not configured", positionX: 'center', delay: 2000});
//                 return false;
//             }else{
//                 $("#mailDuplicateInvoiceModal").modal('hide');
//             }
//
//             if (mailStatus) {
//                 Notification.error({message:"Please wait for previous mail to receive", positionX: 'center', delay: 2000});
//                 return false;
//             }
//             mailStatus = true;
//             $http.get($scope.retailServerURL + '/mailDuplicateInvoice/' + salesInvoice + "?customerEmail=" + customerEmail)
//                 .success(function (data, status, headers, config) {
//                     Notification.info({message: data, positionX: 'center', delay: 2000});
//                     mailStatus = false;
//                 },function (error) {
//                     Notification.info({message: data, positionX: 'center', delay: 2000});
//                     mailStatus = false;
//                 })
//
//
//             //     .error(function (data, status, header, config) {
//             //     Notification.info({message: data, positionX: 'center', delay: 2000});
//             //     mailStatus = false;
//             // });
//         };
//         $scope.appendCustomer = function (customerId) {
//             $scope.customerSearchText = customerId.customerName;
//             $scope.customerId = customerId.customerId;
//             $scope.customer = $scope.customerId;
//             $scope.showEmailBox = false;
//             $scope.removeAllItemsWithoutCustomer();
//             $("#selectCustomer").modal('hide');
//
//         }
//         $scope.addItem = function (itemCode, keyEvent) {
//
//             if (itemCode.serializableStatus == 'Serialize') {
//                 var localItemCode;
//                 if (!angular.isUndefined(itemCode.barcodeInput)) {
//                     localItemCode = itemCode.barcodeInput;
//                 }
//                 else {
//                     localItemCode = itemCode.itemCode;
//                 }
//                 $scope.getItem(localItemCode);
//                 // $scope.getSerializableItems(localItemCode,keyEvent);
//                 $scope.barcodeInput = "";
//             }
//             else {
//                 var localItemCode;
//                 if (!angular.isUndefined(itemCode.barcodeInput)) {
//                     localItemCode = itemCode.barcodeInput;
//                 }
//                 else {
//                     localItemCode = itemCode.itemCode;
//                 }
//                 if (keyEvent === 13 && ($scope.barcodeInput === "" || angular.isUndefined($scope.barcodeInput))) {
//                     return false;
//                 }
//                 $('#barcodeInput').val('');
//                 $scope.iteamIndex = $scope.itemIndexOfItemCode($scope.selectedItemsList, localItemCode);
//                 if (!angular.isUndefined($scope.iteamIndex) && $scope.iteamIndex !== null && $scope.iteamIndex !== -1) {
//                     $scope.currentQty = $scope.selectedItemsList[$scope.iteamIndex].qty;
//                     $scope.selectedItemsList[$scope.iteamIndex].qty = parseFloat($scope.currentQty) + 1;
//                     $scope.editSelectedItemList($scope.selectedItemsList[$scope.iteamIndex], $scope.selectedItemsList[$scope.iteamIndex].taxid,$scope.iteamIndex);
//                 } else {
//                     $scope.getItem(localItemCode);
//                 }
//                 $scope.barcodeInput = "";
//             }
//         };
//         $scope.addSerialItem = function (serialItemCode, keyEvent) {
//             $scope.selectedSerialNumberFilter = $scope.serialBarcodeInput;
//             if (serialItemCode.serializableStatus == 'Serialize') {
//                 var localItemCode;
//                 if (!angular.isUndefined(serialItemCode.serialBarcodeInput)) {
//                     localItemCode = serialItemCode.serialBarcodeInput;
//                 }
//                 else {
//                     localItemCode = serialItemCode.itemCode;
//                 }
//                 $scope.getSerialItem(localItemCode);
//                 // $scope.getSerializableItems(localItemCode,keyEvent);
//                 $scope.serialBarcodeInput = "";
//             }
//             else {
//                 var localItemCode;
//                 if (!angular.isUndefined(serialItemCode.serialBarcodeInput)) {
//                     localItemCode = serialItemCode.serialBarcodeInput;
//                 }
//                 else {
//                     localItemCode = serialItemCode.itemCode;
//                 }
//                 if (keyEvent === 13 && ($scope.serialBarcodeInput === "" || angular.isUndefined($scope.serialBarcodeInput))) {
//                     return false;
//                 }
//                 $('#serialBarcodeInput').val('');
//                 $scope.iteamIndex = $scope.itemIndexOfItemCode($scope.selectedItemsList, localItemCode);
//                 if (!angular.isUndefined($scope.iteamIndex) && $scope.iteamIndex !== null && $scope.iteamIndex !== -1) {
//                     $scope.currentQty = $scope.selectedItemsList[$scope.iteamIndex].qty;
//                     $scope.selectedItemsList[$scope.iteamIndex].qty = parseFloat($scope.currentQty) + 1;
//                     $scope.editSelectedItemList($scope.selectedItemsList[$scope.iteamIndex], $scope.selectedItemsList[$scope.iteamIndex].taxid);
//                 } else {
//                     $scope.getSerialItem(localItemCode);
//                 }
//                 $scope.serialBarcodeInput = "";
//             }
//         };
//         $scope.addSelectedItemList = function (data) {
//             // var count=0;
//             var count = $scope.countVal;
//             $scope.taxIndex = $scope.taxIndexOf($scope.taxList, data.taxId);
//             // $scope.serializableItemsIndex=$scope.serializableItemsIndexOf(data.serializableItemsDTOList, data.serializableItemsDTOList[0].serializableItemCode);
//             //    $scope.serializableItemsIndex = $scope.serializableItemsIndexOf($scope.serializableItemsList, data.serializableitemId);
//             var taxPercent = $scope.taxList[$scope.taxIndex].taxString.split('|');
//             var unitPrice = data.salesPrice;
//             var qty = 1;
//
//             var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty));
//             var discountAmt = $scope.getDiscountAmt(data.discountAmountRpercent, data.isDiscountInPercent, amtinclusivetax);
//             var taxamt = (parseFloat(amtexclusivetax) - discountAmt) * ((parseFloat(taxPercent[0]) / 100));
//             var serializableItems = $scope.serializableItemsIndex;
//             var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt)) - parseFloat(discountAmt));
//             $scope.selectedItemsList.push({
//                 itemCode: data.itemCode,
//                 itemId: data.itemId,
//                 itemName: data.itemName,
//                 ItemDesc: data.ItemDesc,
//                 serializableStatus: data.serializableStatus,
//                 unitPrice: data.salesPrice,
//                 discountAmt: data.discountAmountRpercent,
//                 isDiscountInPercent: data.isDiscountInPercent,
//                 qty: 1,
//                 returnQty: 0,
//                 remainingQty: 1,
//                 makingCharge:0,
//                 actualWeight:0,
//                 amtexclusivetax: parseFloat(amtexclusivetax.toFixed(2)),
//                 taxid: data.taxId,
//                 taxpercent: taxPercent[0],
//                 taxName: taxPercent[1],
//                 taxamt: parseFloat(taxamt.toFixed(2)),
//                 amtinclusivetax: parseFloat(amtinclusivetax.toFixed(2)),
//             });
//
//             $scope.countVal = count + 1;
//             $scope.serializableItemsList[$scope.countVal] = data.serializableItemsDTOList;
//
//
//             //search item
//             var sel;
//             for (var sr = 0; sr < data.serializableItemsDTOList.length; sr++) {
//                 sel = data.serializableItemsDTOList[sr];
//                 if (sel.serializableNumbers == $scope.selectedSerialNumberFilter) {
//                     $scope.serialItemsList[0] = sel;
//                     $scope.selectedItemsList[0].serializableItemId = sel.serializableItemId;
//                     $scope.selectedItemsList[0].serializableNumbers = sel.serializableNumbers;
//                     $scope.serializableItemsList[$scope.countVal].splice(sr, 1);
//                     break;
//                 }
//             }
//
//             $scope.getTotalAmtForSelectedItems();
//         };
//         $scope.selectedSerialNumberFilter = "";
//         $scope.serialItemsList = {};
//         $scope.editSerializableItemsList = function (data, serializableItems, index) {
//             $scope.selectedItemsList[index].serializableItemId = serializableItems.serializableItemId;
//             $scope.selectedItemsList[index].serializableNumbers = serializableItems.serializableNumbers;
//             console.log($scope.selectedItemsList[index])
//             var foundIndex = $filter('filter')($scope.selectedItemsList, {
//                 itemName: data.itemName,
//                 serializableNumbers: data.serializableNumbers
//             });
//             if (foundIndex.length > 1) {
//                 Notification.info({message: 'Duplicate Serial Number Is Selected', positionX: 'center', delay: 2000});
//                 //azgar
//                 $scope.serialItemsList[index] = "";
//
//                 // $scope.selectedItemsList[index].serializableNumbers = "";
//
//                 $(this).selected().val(0);
//
//                 return false;
//             }
//         }
//         //     window.alert("88888");
//         //     $scope.iteamIndex = $scope.itemIndexOf($scope.selectedItemsList, data.itemName);
//         //     if (serializableItems !== -1) {
//         //         $scope.serializableItemsIndex = $scope.serializableItemsIndexOf($scope.serializableItemsList, serializableItems);
//         //         $scope.selectedItemsList[$scope.iteamIndex]. serializableitemid = serializableItems;
//         //     } else {
//         //         $scope.serializableItemsIndex = $scope.serializableItemsIndexOf($scope.serializableItemsList, data.serializableitemid);
//         //     }
//         //     // var serializableItemId = data.serializableitemid;
//         //     // window.alert(serializableItemId);
//         //         };
//         $scope.editSelectedItemList = function (data, tax, index) {
//             // if(data.serializableStatus=='Serialize') {
//             //     $scope.getItemBarcode("1242");
//             // }
//             // else {
//             $scope.iteamIndex = $scope.itemIndexOf($scope.selectedItemsList, data.itemName);
//             // $scope.iteamIndex = $scope.itemIndexOf($scope.selectedItemsList, data.ItemDesc);
//             if (tax != -1) {
//                 $scope.taxIndex = $scope.taxIndexOf($scope.taxList, tax);
//                 $scope.selectedItemsList[$scope.iteamIndex].taxid = tax;
//             } else {
//                 $scope.taxIndex = $scope.taxIndexOf($scope.taxList, data.taxid);
//             }
//             var taxPercent = $scope.taxList[$scope.taxIndex].taxString.split('|');
//             var taxName = taxPercent[1];
//             var unitPrice = data.unitPrice;
//             var amntEX = data.amtexclusivetax;
//             var qty = data.qty;
//             var disAmt = $scope.getDiscountAmt(data.discountAmt, data.isDiscountInPercent, amtexclusivetax);
//             if (parseFloat(amntEX) > 0) {
//                 if (parseFloat(disAmt) >= parseFloat(amntEX)) {
//                     Notification.error({
//                         message: 'Discount Amount Should Be Less Than Amount(EX) ',
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                     $scope.selectedItemsList[$scope.iteamIndex].discountAmt = 0;
//                     var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty));
//                     var discountAmt = $scope.getDiscountAmt(data.discountAmt, data.isDiscountInPercent, amtexclusivetax);
//                     var taxamt = (parseFloat(amtexclusivetax) - parseFloat(discountAmt)) * ((parseFloat(taxPercent[0]) / 100));
//                     var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt)) - parseFloat(discountAmt));
//                     $scope.selectedItemsList[index].taxamt = parseFloat(taxamt.toFixed(2));
//                     $scope.selectedItemsList[index].amtinclusivetax = parseFloat(amtinclusivetax.toFixed(2));
//                     return;
//                 }
//             }
//             if ($scope.operation === 'Return') {
//                 if (parseFloat(data.returnQty) > parseFloat(qty)) {
//                     Notification.error({
//                         message: 'Return quantity should be less or equal than quantity',
//                         positionX: 'center',
//                         delay: 2000
//                     });
//                     return;
//                 } else {
//                 }
//             }
//             if ($scope.operation !== 'Return') {
//                 $scope.selectedItemsList[index].qty = qty;
//                 $scope.selectedItemsList[index].remainingQty = qty;
//                 var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(qty));
//                 var makingCharge= data.makingCharge;
//                 var actualWeight=data.actualWeight;
//                 var discountAmt = $scope.getDiscountAmt(data.discountAmt, data.isDiscountInPercent, amtexclusivetax);
//                 var taxamt = (parseFloat(amtexclusivetax) +parseFloat(makingCharge)) * ((parseFloat(taxPercent[0]) / 100));
//                 //to do
//                 var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt))+parseFloat(makingCharge));
//                 $scope.selectedItemsList[index].taxpercent = taxPercent[0];
//                 $scope.selectedItemsList[index].amtexclusivetax = parseFloat(amtexclusivetax.toFixed(2));
//                 $scope.selectedItemsList[index].taxamt = parseFloat(taxamt.toFixed(2));
//                 $scope.selectedItemsList[index].amtinclusivetax = parseFloat(amtinclusivetax.toFixed(2));
//                 $scope.selectedItemsList[index].discountAmt = parseFloat(discountAmt.toFixed(2));
//                 $scope.selectedItemsList[index].taxName = taxName;
//             } else {
//                 qty = parseFloat(qty) - parseFloat(data.returnQty);
// //              $scope.selectedItemsList[$scope.iteamIndex].qty = parseFloat(data.returnQty);
//                 $scope.selectedItemsList[index].remainingQty = parseFloat(qty);
//                 $scope.selectedItemsList[index].returnQty = parseFloat(data.returnQty);
//                 var amtexclusivetax = (parseFloat(unitPrice) * parseFloat(data.returnQty));
//                 var taxamt = (parseFloat(amtexclusivetax) * (parseFloat(taxPercent[0]) / 100));
//                 //to do
//                 var discountAmt = $scope.getDiscountAmt(data.discountAmt, data.isDiscountInPercent, amtexclusivetax);
//                 var amtinclusivetax = ((parseFloat(amtexclusivetax) + parseFloat(taxamt)) - parseFloat(discountAmt));
//                 $scope.selectedItemsList[index].taxpercent = taxPercent[0];
//                 $scope.selectedItemsList[index].amtexclusivetax = parseFloat(amtexclusivetax.toFixed(2));
//                 $scope.selectedItemsList[index].taxamt = parseFloat(taxamt.toFixed(2));
//                 $scope.selectedItemsList[index].amtinclusivetax = parseFloat(amtinclusivetax.toFixed(2));
//                 $scope.selectedItemsList[index].discountAmt = parseFloat(discountAmt.toFixed(2));
//                 $scope.selectedItemsList[index].taxName = taxName;
//             }
//             $scope.getTotalAmtForSelectedItems();
//             // }
//         };
//         $scope.getDiscountAmt = function (discountAmtRPercent, IsDiscountPercent, amtinclusivetax) {
//             var discountAmt = 0.00;
//             if (IsDiscountPercent) {
//                 discountAmt = ((parseFloat(discountAmtRPercent) / 100) * parseFloat(amtinclusivetax));
//             } else {
//                 discountAmt = discountAmtRPercent;
//             }
//             return discountAmt;
//         };
//         $scope.getTotalAmtForSelectedItemsForReturn = function () {
//             var totalAmt = 0.00;
//             var totalTaxAmt = 0.00;
//             var totalDiscountAmt = 0.00;
//             var totalQty = 0.00;
//             angular.forEach($scope.selectedItemsList, function (item, index) {
//                 if(item.returnQty>0) {
//                     totalAmt += parseFloat(item.amtinclusivetax);
//                     totalTaxAmt += parseFloat(item.taxamt);
//                     totalDiscountAmt += parseFloat(item.discountAmt);
//                     totalQty += parseFloat(item.returnQty);
//                 }
//             });
//
//             var totalAfterDiscount = parseFloat(totalAmt);
//             $scope.totalCheckOutamt = parseFloat(totalAmt.toFixed(2));
//             $scope.totalBeforDiscount = parseFloat(totalAmt.toFixed(2));
//             $scope.totalDiscount = parseFloat(totalDiscountAmt.toFixed(2));
//             $scope.totalAfterDiscount = parseFloat(totalAfterDiscount.toFixed(2));
//             $scope.totalTaxAmt = parseFloat(totalTaxAmt.toFixed(2));
//             $scope.returnQty = parseFloat(totalQty.toFixed(2));
//         };
//         $scope.getTotalAmtForSelectedItems = function () {
//             var totalAmt = 0.00;
//             var totalTaxAmt = 0.00;
//             var totalDiscountAmt = 0.00;
//             var totalQty = 0.00;
//             angular.forEach($scope.selectedItemsList, function (item, index) {
//                 totalAmt += parseFloat(item.amtinclusivetax);
//                 totalTaxAmt += parseFloat(item.taxamt);
//                 totalDiscountAmt += parseFloat(item.discountAmt);
//                 totalQty += parseFloat(item.returnQty);
//             });
//
//             var totalAfterDiscount = parseFloat(totalAmt);
//             $scope.totalCheckOutamt = parseFloat(totalAmt.toFixed(2));
//             $scope.totalBeforDiscount = parseFloat(totalAmt.toFixed(2));
//             $scope.totalDiscount = parseFloat(totalDiscountAmt.toFixed(2));
//             $scope.totalAfterDiscount = parseFloat(totalAfterDiscount.toFixed(2));
//             $scope.totalTaxAmt = parseFloat(totalTaxAmt.toFixed(2));
//             $scope.returnQty = parseFloat(totalQty.toFixed(2));
//         };
//         $scope.getTotalAmtToBeReturned = function (oldValue) {
//             if (parseFloat($scope.totalCPAmountTendered) > 0) {
//                 $scope.totalCPAmountRefunded = parseFloat($scope.totalCPAmountTendered) - parseFloat($scope.totalCPAfterDiscount);
//             }
//         };
//         $scope.getTotalVoucherAmtToBeReturned = function () {
//             if ($scope.totalVPAmountTendered > $scope.totalVPAfterAllDeductions) {
//                 $scope.totalVPAmountRefunded = parseFloat($scope.totalVPAmountTendered) - parseFloat($scope.totalVPAfterAllDeductions);
//             } else {
//                 $scope.totalVPAmountRefunded = 0.00;
//             }
//         };
//         $scope.OnChangeCashDiscount = function (oldValue, currentValue) {
//             var onFocusedElement = $scope.onFocusElement.id;
//             switch (onFocusedElement) {
//                 case "totalCPDiscount":
//                     var tempTotalCPBeforDiscount = $scope.totalCPBeforDiscount;
//                     var tempTotalCPDiscount = $scope.totalCPDiscount;
//                     var tempTotalCPAfterDiscount = parseFloat(0.00);
//                     var tempTotalCPAmountTendered = parseFloat(0.00);
//                     var tempTotalCPAmountRefunded = parseFloat(0.00);
//                     if (!angular.isUndefined($scope.totalCPAmountTendered) || $scope.totalCPAmountTendered !== "" ||
//                         (angular.isNumber(parseFloat($scope.totalCPAmountTendered)))) {
//                         tempTotalCPAmountTendered = parseFloat($scope.totalCPAmountTendered);
//                     }
//                     if (angular.isUndefined(currentValue) || currentValue === "" || !(angular.isNumber(parseFloat(currentValue)))) {
//                         tempTotalCPDiscount = parseFloat(0.00);
//                     } else if (parseFloat(tempTotalCPDiscount) > parseFloat(tempTotalCPBeforDiscount)) {
//                         Notification.info({
//                             message: 'Discount can not be greater than to be paid amount',
//                             positionX: 'center',
//                             delay: 2000
//                         });
//                         $scope.totalCPDiscount = parseFloat(oldValue)
//                         tempTotalCPDiscount = oldValue;
//                     }
//                     if (parseFloat(tempTotalCPDiscount) > 0) {
//                         tempTotalCPAfterDiscount = parseFloat(tempTotalCPBeforDiscount) - parseFloat(tempTotalCPDiscount);
//                     } else {
//                         tempTotalCPAfterDiscount = parseFloat(tempTotalCPBeforDiscount);
//                     }
//                     if (parseFloat(tempTotalCPAmountTendered) > 0) {
//                         tempTotalCPAmountRefunded = parseFloat(tempTotalCPAmountTendered) - parseFloat(tempTotalCPAfterDiscount);
//                     }
//                     $scope.totalCPAfterDiscount = parseFloat(tempTotalCPAfterDiscount.toFixed(2));
//                     $scope.totalCPAmountRefunded = parseFloat(tempTotalCPAmountRefunded.toFixed(2));
//                     break;
//                 case "totalCPAmountTendered":
//                     var tempTotalCPAfterDiscount = $scope.totalCPAfterDiscount;
//                     var tempTotalCPAmountTendered = $scope.totalCPAmountTendered;
//                     var tempTotalCPAmountRefunded = parseFloat(0.00);
//                     if (angular.isUndefined(tempTotalCPAmountTendered) || tempTotalCPAmountTendered === ""
//                         || !(angular.isNumber(parseFloat(tempTotalCPAmountTendered)))) {
//                         tempTotalCPAmountTendered = parseFloat(0.00);
//                     }
//                     if (parseFloat(tempTotalCPAmountTendered) > 0) {
//                         tempTotalCPAmountRefunded = parseFloat(tempTotalCPAmountTendered) - parseFloat(tempTotalCPAfterDiscount);
//                     }
//                     $scope.totalCPAmountRefunded = parseFloat(tempTotalCPAmountRefunded).toFixed(2);
//                     break;
//             }
//         };
//         $scope.OnChangeCreditDiscount = function (oldValue) {
//             if ($scope.totalCCPDiscount > $scope.totalCCPBeforDiscount) {
//                 Notification.info({
//                     message: 'Discount cannot be greater than to be paid amount',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//                 $scope.totalCCPDiscount = oldValue;
//                 return;
//             }
//             var totalCCPAfterDiscount = parseFloat($scope.totalCCPBeforDiscount - $scope.totalCCPDiscount)
//             $scope.totalCCPAfterDiscount = parseFloat(totalCCPAfterDiscount.toFixed(2));
//         };
//         $scope.OnChangeMultiDiscount = function (oldValue) {
//             if (parseFloat($scope.totalVPDiscount) > parseFloat($scope.totalVPAfterDiscount)) {
//                 Notification.info({
//                     message: 'Discount cannot be greater than to be paid amount',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//                 $scope.totalVPDiscount = oldValue;
//                 return;
//             }
//             var totalCCPAfterDiscount = parseFloat($scope.amountWithoutDiscount - $scope.totalVPDiscount);
//             $scope.totalVPAfterDiscount = parseFloat(totalCCPAfterDiscount.toFixed(2));
//             $scope.totalVPBeforDiscount = parseFloat(totalCCPAfterDiscount.toFixed(2));
//
//             if (Math.abs($scope.totalVPAmountRefunded) > parseFloat(0) && $scope.totalVPDiscount != "") {
//                 // var discountplusdiscount=parseFloat($scope.amountWithoutDiscount)+parseFloat($scope.totalVPDiscount);
//                 var totalrefundamt = parseFloat($scope.totalPaidAmt) - parseFloat($scope.totalVPBeforDiscount);
//                 $scope.totalVPAmountRefunded = totalrefundamt.toFixed(2);
//             }
//         };
//         $scope.OnChangeVoucherDiscount = function (oldValue, currentValue) {
//             var onFocusedElement = $scope.onFocusElement.id;
//             switch (onFocusedElement) {
//                 case "totalVoucherAmt":
//                     var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
//                     // var tempTotalVPDiscount = $scope.totalVPDiscount;
//                     var tempTotalVPAfterDiscount = parseFloat(0.00);
//                     var tempTotalVPAmountTendered = parseFloat(0.00);
//                     // if (!angular.isUndefined($scope.totalVPAmountTendered) || $scope.totalVPAmountTendered !== "" ||
//                     //         (angular.isNumber(parseFloat($scope.totalVPAmountTendered)))) {
//                     //     tempTotalVPAmountTendered = parseFloat($scope.totalVPAmountTendered);
//                     // }
//                     var tempTotalVPAmountRefunded = parseFloat(0.00);
//                     if (angular.isUndefined(currentValue) || currentValue === "" ||
//                         !(angular.isNumber(parseFloat(currentValue)))) {
//                         $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                         return;
//                         //$scope.totalVoucherAmt = parseFloat(0.00);
//                     } else if (parseFloat($scope.totalVoucherAmt) > parseFloat($scope.totalVPBeforDiscount)) {
//                         Notification.info({
//                             message: 'Voucher Amount can not be greater than to be paid amount',
//                             positionX: 'center',
//                             delay: 2000
//                         });
//                         $scope.totalVoucherAmt = parseFloat(oldValue);
//                         $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                         return;
//                     }
//                     if (parseFloat(currentValue) > 0) {
//                         tempTotalVPAfterDiscount = parseFloat(tempTotalVPBeforDiscount) - parseFloat(currentValue);
//                     }
//                     if (parseFloat($scope.totalVPAmountTendered) > 0) {
//                         tempTotalVPAmountRefunded = parseFloat($scope.totalVPAmountTendered) - parseFloat(tempTotalVPAfterDiscount);
//                     }
//                     $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                     $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                     break;
//                 case "totalVPAmountTendered":
//                     var tempTotalVPAfterDiscount = $scope.totalVPAfterDiscount;
//                     var tempTotalVPAmountTendered = $scope.totalVPAmountTendered;
//                     var tempTotalVPAmountRefunded = parseFloat(0.00);
//                     if (angular.isUndefined(tempTotalVPAmountTendered) || tempTotalVPAmountTendered === ""
//                         || !(angular.isNumber(parseFloat(tempTotalVPAmountTendered)))) {
//                         tempTotalVPAmountTendered = parseFloat(0.00);
//                     }
//                     if (parseFloat(tempTotalVPAmountTendered) > tempTotalVPAfterDiscount) {
//                         tempTotalVPAmountRefunded = parseFloat(tempTotalVPAmountTendered) - parseFloat(tempTotalVPAfterDiscount);
//                     }
//                     else {
//                         tempTotalVPAmountRefunded = parseFloat(0.00);
//                     }
//                     $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                     break;
//             }
//         }
//         $scope.OnChangeMultiPayament = function (oldValue, currentValue) {
//             var onFocusedElement = $scope.onFocusElement.name;
//             var voucherTotalAmt = parseFloat(0.00);
//             var cardTotalAmt = parseFloat(0.00);
//             var cashTotalAmt = parseFloat(0.00);
//             var paymentAmount = parseFloat(0.00);
//             cashTotalAmt = $scope.totalCPAmountTendered;
//             if (angular.isUndefined(cashTotalAmt) || cashTotalAmt === "" ||
//                 !(angular.isNumber(parseFloat(cashTotalAmt)))) {
//                 cashTotalAmt = parseFloat(0);
//             }
//             if ($scope.removePayments == "removeField") {
//                 currentValue = parseFloat(1);
//                 $scope.removePayments = "";
//             }
//             angular.forEach($scope.voucherPayementList, function (value, key) {
//                 if (value.amt > 0) {
//                     voucherTotalAmt = parseFloat(voucherTotalAmt) + parseFloat(value.amt);
//                     $scope.checkbox = "";
//                     currentValue = parseFloat(1);
//                 }
//             });
//             angular.forEach($scope.cardPayementList, function (value, key) {
//                 if (value.cardAmount > 0 || $scope.checkbox == "unchecked") {
//                     cardTotalAmt = parseFloat(cardTotalAmt) + parseFloat(value.cardAmount);
//                     $scope.checkbox = "";
//                     currentValue = parseFloat(1);
//                 }
//             });
//             if (cardTotalAmt.toString() == "NaN") {
//                 cardTotalAmt = parseFloat(0);
//             }
//             paymentAmount = parseFloat(voucherTotalAmt) + parseFloat(cardTotalAmt) + parseFloat(cashTotalAmt);
//
//             switch (onFocusedElement) {
//                 case "totalVoucherAmt":
//                     var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
//                     var tempTotalVPAfterDiscount = parseFloat(0.00);
//                     var tempTotalVPAmountRefunded = parseFloat(0.00);
//                     if (angular.isUndefined(currentValue) || currentValue === "" ||
//                         !(angular.isNumber(parseFloat(currentValue)))) {
//                         $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                         return;
//                     } else if (parseFloat($scope.totalVoucherAmt) > parseFloat($scope.totalVPBeforDiscount)) {
//                         Notification.info({
//                             message: 'Voucher Amount can not be greater than to be paid amount',
//                             positionX: 'center',
//                             delay: 2000
//                         });
//                         $scope.totalVoucherAmt = parseFloat(oldValue);
//                         $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                         return;
//                     }
//                     if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
//                         tempTotalVPAfterDiscount = parseFloat(0);
//                         var reFoundAmt = parseFloat(0);
//                         reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = reFoundAmt.toFixed(2);
//                         $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
//                     }
//                     else {
//                         $scope.totalVPAmountRefunded = (parseFloat(paymentAmount.toFixed(2)) - parseFloat(tempTotalVPBeforDiscount.toFixed(2)) - parseFloat($scope.cardAmount.toFixed(2))).toFixed(2);
//                         $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
//                         // $scope.totalVPAmountRefunded=parseFloat(0);
//                     }
//                     // if (parseFloat(currentValue) > 0) {
//                     //     tempTotalVPAfterDiscount = parseFloat(paymentAmount)-parseFloat(tempTotalVPBeforDiscount) - parseFloat($scope.cardAmount);
//                     // }
//                     // if (parseFloat($scope.totalVPAmountTendered) > 0) {
//                     //     tempTotalVPAmountRefunded = parseFloat($scope.totalVPAmountTendered) - parseFloat(tempTotalVPAfterDiscount);
//                     // }
//                     // $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                     // $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                     break;
//                 case "cardAmt":
//                     var tempTotalVPBeforDiscount = $scope.totalVPBeforDiscount;
//                     var tempTotalVPAfterDiscount = parseFloat(0.00);
//                     var tempTotalVPAmountRefunded = parseFloat(0.00);
//                     if (angular.isUndefined(currentValue) || currentValue === "" ||
//                         !(angular.isNumber(parseFloat(currentValue)))) {
//                         $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                         return;
//                     } else if (parseFloat($scope.cardAmount) > parseFloat($scope.totalVPBeforDiscount)) {
//                         Notification.info({
//                             message: 'Card  Amount can not be greater than to be paid amount',
//                             positionX: 'center',
//                             delay: 2000
//                         });
//                         $scope.totalVoucherAmt = parseFloat(oldValue);
//                         $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                         return;
//                     }
//                     if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
//                         tempTotalVPAfterDiscount = parseFloat(0);
//                         var reFoundAmt = parseFloat(0);
//                         reFoundAmt = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
//                         $scope.totalVPAmountRefunded = reFoundAmt.toFixed(2);
//                         $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
//                     }
//                     else {
//                         $scope.totalVPAmountRefunded = (parseFloat(paymentAmount.toFixed(2)) - parseFloat(tempTotalVPBeforDiscount.toFixed(2)) - parseFloat($scope.totalVoucherAmt.toFixed(2))).toFixed(2);
//                         $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
//                         // $scope.totalVPAmountRefunded=parseFloat(0);
//                     }
//                     // if (parseFloat(currentValue) > 0) {
//                     //     tempTotalVPAfterDiscount = parseFloat(paymentAmount)-parseFloat(tempTotalVPBeforDiscount) -parseFloat($scope.totalVoucherAmt);
//                     // }
//                     // if (parseFloat($scope.totalVPAmountTendered) > 0) {
//                     //     tempTotalVPAmountRefunded = parseFloat($scope.totalVPAmountTendered) - parseFloat(tempTotalVPAfterDiscount);
//                     // }
//                     // $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                     // $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                     break;
//                 case "totalVPAmountTendered":
//                     var tempTotalVPAfterDiscount = $scope.totalVPAfterDiscount;
//                     var tempTotalVPAmountTendered = $scope.totalVPAmountTendered;
//                     var tempTotalVPAmountRefunded = parseFloat(0.00);
//                     if (angular.isUndefined($scope.totalCPAmountTendered) || $scope.totalCPAmountTendered === ""
//                         || !(angular.isNumber(parseFloat($scope.totalCPAmountTendered)))) {
//                         tempTotalVPAmountTendered = parseFloat(0.00);
//                     }
//                     if (parseFloat(paymentAmount) > $scope.totalVPBeforDiscount) {
//                         tempTotalVPAfterDiscount = parseFloat(0);
//                         $scope.totalVPAmountRefunded = parseFloat(paymentAmount.toFixed(2)) - parseFloat($scope.totalVPBeforDiscount.toFixed(2));
//                         $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
//                     }
//                     else {
//                         $scope.totalVPAmountRefunded = parseFloat(parseFloat(paymentAmount.toFixed(2)) - $scope.totalVPBeforDiscount.toFixed(2)).toFixed(2);
//                         $scope.totalPaidAmt = parseFloat(paymentAmount.toFixed(2));
//                         // $scope.totalVPAmountRefunded=parseFloat(0);
//                     }
//                     // if (parseFloat($scope.totalVPAfterDiscount) > parseFloat($scope.totalCPAmountTendered)  ) {
//
//                     // }
//                     // if (parseFloat($scope.totalCPAmountTendered) > parseFloat($scope.totalVPAfterDiscount)) {
//                     //     tempTotalVPAfterDiscount=parseFloat(0.00);
//                     //     tempTotalVPAmountRefunded = parseFloat(paymentAmount) - parseFloat($scope.totalVPBeforDiscount);
//                     // }
//                     // else{
//                     //     tempTotalVPAmountRefunded = parseFloat(0.00);
//                     // }
//                     // $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//                     $scope.totalVPAfterDiscount = parseFloat(tempTotalVPAfterDiscount.toFixed(2));
//                     break;
//             }
//         };
//         $scope.onFocusElement;
//         $scope.getSelectedTexElement = function ($event) {
//             $scope.onFocusElement = $event.target;
//             $scope.doGetCaretPosition($scope.onFocusElement);
//         };
//         $scope.makeSerialItemRead = function (id, val) {
//             if (val == 'Serialize') {
//                 $('#qty_' + id).prop('readonly', true);
//                 $('#qty_' + id).attr('title', 'Serialized Item Quantity Can Not Be Edited');
//             }
//         };
//         $scope.doGetCaretPosition = function (oField) {
//             var iCaretPos = 0;
//             var iCaretEndPos = 0;
//             if (document.selection) {
//                 oField.focus();
//                 var oSel = document.selection.createRange();
//                 oSel.moveStart('character', -oField.value.length);
//                 iCaretPos = oSel.text.length;
//             } else if (oField.selectionStart || oField.selectionStart === 0) {
//                 iCaretPos = oField.selectionStart;
//                 iCaretEndPos = oField.selectionEnd;
//             }
//             $scope.cursorCurPosVal = iCaretPos;
//             $scope.cursorEndPosVal = iCaretEndPos;
//         };
//         $scope.getCalculatorValue = function (value) {
//             if (value === 'BackSpace') {
//                 var val = ($scope.onFocusElement.value).substring(0, $scope.onFocusElement.value.length - 1);
//                 if (val === "" || val === undefined) {
//                     $scope.onFocusElement.value = 0.00;
//                 } else {
//                     $scope.onFocusElement.value = val;
//                 }
//                 angular.element($scope.onFocusElement).trigger('input');
//             } else if (value === 'CE') {
//                 $scope.onFocusElement.value = 0;
//                 angular.element($scope.onFocusElement).trigger('input');
//             } else {
//                 if ($scope.cursorEndPosVal > $scope.cursorCurPosVal) {
//                     var front = ($scope.onFocusElement.value).substring(0, $scope.cursorCurPosVal);
//                     var back = ($scope.onFocusElement.value).substring($scope.cursorEndPosVal, $scope.onFocusElement.value.length);
//                     if (front == parseFloat(0)) {
//                         $scope.onFocusElement.value = value + back;
//                     } else {
//                         $scope.onFocusElement.value = front + value + back;
//                     }
//                     angular.element($scope.onFocusElement).trigger('input');
//                     $scope.cursorCurPosVal = $scope.cursorCurPosVal + 1;
//                     $scope.cursorEndPosVal = 0;
//                 } else {
//                     var front = ($scope.onFocusElement.value).substring(0, $scope.cursorCurPosVal);
//                     var back = ($scope.onFocusElement.value).substring($scope.cursorCurPosVal, $scope.onFocusElement.value.length);
//                     if (front == parseFloat(0)) {
//                         $scope.onFocusElement.value = value + back;
//                     } else {
//                         $scope.onFocusElement.value = front + value + back;
//                     }
//                     angular.element($scope.onFocusElement).trigger('input');
//                     $scope.cursorCurPosVal = $scope.cursorCurPosVal + 1;
//                     $scope.cursorEndPosVal = 0;
//                 }
//             }
//         };
//         $scope.selectedItemListRemoval = {};
//         $scope.removeSelectedItems = function () {
//             if (angular.isUndefined($scope.selectedItemsList) || $scope.selectedItemsList.length <= 0) {
//                 Notification.error({message: 'At lest One item has to be selected', positionX: 'center', delay: 2000});
//             } else {
//                 $scope.selectedItemsList = $scope.selectedItemsList.filter(function (data, index) {
//                     return !($scope.selectedItemListRemoval[index] !== undefined && $scope.selectedItemListRemoval[index]);
//                 });
//                 $scope.selectedItemListRemoval = {};
//                 $scope.getTotalAmtForSelectedItems();
//             }
//         };
//         $scope.removeAllItems = function () {
//             $scope.totalCheckOutamt = parseFloat('0.00');
//             $scope.totalBeforDiscount = parseFloat('0.00');
//             $scope.totalDiscount = parseFloat('0.00');
//             $scope.totalAfterDiscount = parseFloat('0.00');
//             $scope.totalTaxAmt = parseFloat('0.00');
//             $scope.invokeOrderName = "";
//             $scope.invokeOrderId = "";
//             $scope.invokeorder = "";
//             $scope.selectedItemsList = [];
//             $scope.itemSearchText = "";
//             $scope.operation = 'Create';
//             $scope.customerEmail = "";
//             $scope.customerSearchText = "Cash Customer|01";
//             $scope.selectedSerialNumberFilter = "";
//         };
//         $scope.removeAllItemsWithoutCustomer = function () {
//             $scope.totalCheckOutamt = parseFloat('0.00');
//             $scope.totalBeforDiscount = parseFloat('0.00');
//             $scope.totalDiscount = parseFloat('0.00');
//             $scope.totalAfterDiscount = parseFloat('0.00');
//             $scope.totalTaxAmt = parseFloat('0.00');
//             $scope.invokeOrderName = "";
//             $scope.invokeOrderId = "";
//             $scope.invokeorder = "";
//             $scope.selectedItemsList = [];
//             $scope.itemSearchText = "";
//             $scope.operation = 'Create';
//             $scope.customerEmail = "";
//             $scope.serialItems = "";
//
//         };
//         $scope.removeCustomerDetails = function () {
//
//             $scope.customerNameText = "";
//             $scope.customerEmailText = "";
//             $scope.customerContactText = "";
//             $scope.customerContactText = "";
//             $scope.customerAddressText = "";
//
//         };
//         $scope.populateSaveSIData = function (paymentType, operation) {
//             var CPDetails;
//             var CCPDetails;
//             var VPDetails;
//             var MPDetails;
//             var fromReg="com1234";
//             var to="com5678";
//             var userId=$("#userObj").val();
//             var typeDoc="SO";
//             switch (paymentType) {
//                 case 'cashPayment' :
//                     CPDetails = {
//                         totalCPAmountRefunded: $scope.totalCPAmountRefunded,
//                         totalCPDiscount: $scope.totalCPDiscount,
//                         totalCPAmountTendered: $scope.totalCPAmountTendered,
//                     };
//                     break;
//                 case 'creditPayment' :
//                     CCPDetails = {
//                         totalCCPDiscount: $scope.totalCCPDiscount,
//                         totalCCPAmountTendered: $scope.totalCCPAmountTendered,
//                         transactionNo: $scope.totalCCPTransactionNo,
//                         totalCCPAfterDiscount: $scope.totalCCPAfterDiscount,
//                     };
//                     break;
//                 case 'voucherPayment' :
//                     VPDetails = {
//                         totalVPBeforDiscount: $scope.totalVPBeforDiscount,
//                         totalVPDiscount: $scope.totalVPDiscount,
//                         voucherNo: $scope.voucherNo,
//                         voucherDate: $scope.voucherDate,
//                         totalVoucherAmt: $scope.totalVoucherAmt,
//                         totalVPAfterAllDeductions: $scope.totalVPAfterAllDeductions,
//                         totalVPAmountTendered: $scope.totalVPAmountTendered,
//                         totalVPAmountRefunded: $scope.totalVPAmountRefunded,
//                     };
//                     break;
//                 case 'multiPayment':
//                     MPDetails = {
//                         totalVPBeforDiscount: $scope.totalVPBeforDiscount,
//                         totalVPDiscount: $scope.totalVPDiscount,
//                         voucherNo: $scope.voucherNo,
//                         voucherDate: $scope.voucherDate,
//                         totalVoucherAmt: $scope.totalVoucherAmt,
//                         totalVPAfterAllDeductions: $scope.totalVPAfterAllDeductions,
//                         totalVPAmountTendered: $scope.totalVPAmountTendered,
//                         totalVPAmountRefunded: $scope.totalVPAmountRefunded,
//                         cardAmount: $scope.cardAmount
//                     };
//                     break;
//             }
//             var data = {
//                 operation: operation,
//                 selectedItemsList: $scope.selectedItemsList,
//                 cashPayment: CPDetails,
//                 creditPayment: CCPDetails,
//                 voucherPayment: VPDetails,
//                 multiPayment: MPDetails,
//                 totalCheckOutamt: $scope.totalCheckOutamt,
//                 paymentType: paymentType,
//                 totalTaxAmt: $scope.totalTaxAmt,
//                 taxType: $scope.fullSimplTax,
//                 customerId: $scope.customerId,
//                 customerEmail: $scope.customerEmail,
//                 cutomerName: $scope.customerSearchText,
//                 userName: $('#userName').val(),
//                 from_reg :fromReg,
//                 to_reg: to,
//                 user_id:userId,
//                 type_doc:typeDoc
//             };
//             console.log(data);
//             return data;
//         };
//         $scope.populateSaveSIMultiPayData = function (paymentType, operation) {
//             var CPDetails;
//             var CCPDetails;
//             var VPDetails;
//             var cashPayment = $scope.cashcheck;
//             var cardPayment = $scope.card_checked;
//             var voucherPayment = $scope.voucher_checked;
//             if(angular.isUndefined(cashPayment) && angular.isUndefined(cardPayment) && angular.isUndefined(voucherPayment)){
//                 var confirm = $window.confirm("No Payment Type Is Selected Do You Want To Continue?");
//                 if (confirm == false) {
//                     return false;
//                 }
//             }
//             if(cashPayment==false || cardPayment==false || voucherPayment==false){
//                 var confirm = $window.confirm("No Payment Type Is Selected Do You Want To Continue?");
//                 if (confirm == false) {
//                     return false;
//                 }
//             }
//             if (cashPayment == "cashPayment") {
//                 CPDetails = {
//                     totalCPAmountRefunded: $scope.totalCPAmountRefunded,
//                     totalCPDiscount: $scope.totalCPDiscount,
//                     totalCPAmountTendered: $scope.totalCPAmountTendered,
//                 };
//             }
//             if (cardPayment == "creditPayment") {
//                 CCPDetails = {
//                     totalCCPDiscount: $scope.totalCCPDiscount,
//                     totalCCPAmountTendered: $scope.totalCCPAmountTendered,
//                     transactionNo: $scope.totalCCPTransactionNo,
//                     totalCCPAfterDiscount: $scope.totalCCPAfterDiscount,
//                     cardPaymentList: $scope.cards
//                 };
//             }
//             if (voucherPayment == "voucherPayment") {
//                 VPDetails = {
//                     totalVPBeforDiscount: $scope.totalVPBeforDiscount,
//                     totalVPDiscount: $scope.totalVPDiscount,
//                     voucherNo: $scope.voucherNo,
//                     voucherDate: $scope.voucherDate,
//                     totalVoucherAmt: $scope.totalVoucherAmt,
//                     totalVPAfterAllDeductions: $scope.totalVPAfterAllDeductions,
//                     totalVPAmountTendered: $scope.totalVPAmountTendered,
//                     totalVPAmountRefunded: $scope.totalVPAmountRefunded,
//                     multiVoucherPayments: $scope.vouchers
//                 };
//             }
//             var data = {
//                 operation: operation,
//                 selectedItemsList: $scope.selectedItemsList,
//                 cashPayment: CPDetails,
//                 creditPayment: CCPDetails,
//                 voucherPayment: VPDetails,
//                 totalCheckOutamt: $scope.totalVPBeforDiscount,
//                 paymentType: paymentType,
//                 totalTaxAmt: $scope.totalTaxAmt,
//                 taxType: $scope.fullSimplTax,
//                 customerId: $scope.customerId,
//                 customerEmail: $scope.customerEmail,
//                 cutomerName: $scope.customerSearchText,
//                 amountReturned: $scope.totalVPAmountRefunded,
//                 discountAmount: $scope.totalVPDiscount,
//                 totalTenderedAmount: $scope.totalPaidAmt,
//                 userName: $('#userName').val(),
//                 advancepayment:$scope.advancepayment
//             };
//             return data;
//         }
//         $scope.populateReturnSIData = function (SIId, operation, paymentType) {
//             // angular.forEach($scope.selectedItemsList, function (item, index) {
//             //     if (parseFloat(item.returnQty) === parseFloat(item.qty)) {
//             //         $scope.selectedItemsList.splice(index, 1);
//             //     }
//             // });
//             var CPDetails;
//             var data = {
//                 siid: SIId,
//                 operation: operation,
//                 selectedItemsList: $scope.selectedItemsList,
//                 cashPayment: CPDetails,
//                 totalCheckOutamt: $scope.totalCheckOutamt,
//                 paymentType: paymentType,
//                 totalTaxAmt: $scope.totalTaxAmt,
//                 taxType: $scope.tax,
//                 customerId: $scope.customerId,
//                 formNo:$scope.formNo,
//                 totalCashPymtAmtReturned:$scope.totalCashPymtAmtReturned
//             };
//             return data;
//         };
//         $scope.itemsNoStockList = [];
//         $scope.openSelectpayment = function () {
//             if (angular.isUndefined($scope.totalCheckOutamt) || $scope.totalCheckOutamt <= 0) {
//                 Notification.warning({message: 'Check out amount can not be zero', positionX: 'center', delay: 2000});
//             } else if (!$scope.isValidatedData()) {
//                 Notification.warning({
//                     message: 'Unit price and Qty should not be blank or zero',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//             } else {
//                 $http.post($scope.retailServerURL + '/validateCheckout?invokeOrderId=' + $scope.invokeOrderId,
//                     angular.toJson($scope.populateSaveSIData("", ""))).then(function (response) {
//                     var data = response.data;
//                     $scope.itemsNoStockList = data;
//                     if ($scope.itemsNoStockList !== null && $scope.itemsNoStockList.length > 0) {
//                         $("#ItemsNoStockListModel").modal('show');
//                     }
//                     else {
//                         $("#selectpayment").modal('show');
//                     }
//                 },function (error) {
//                     Notification.error({message: data.message, positionX: 'center', delay: 2000});
//                 })
//
//                     .error(function (data, status, header, config) {
//                     Notification.error({message: data.message, positionX: 'center', delay: 2000});
//                 });
//             }
//         };
//         $scope.itemsNoStockList = [];
//         $scope.openStockValidateMultipaymnet = function () {
//             if (!$scope.isValidatedData()) {
//                 Notification.warning({
//                     message: 'Unit price and Qty should not be blank or zero',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//             } else {
//                 $http.post($scope.retailServerURL + '/validateCheckout?invokeOrderId=' + $scope.invokeOrderId,
//                     angular.toJson($scope.populateSaveSIData("", ""))).then(function (response) {
//                     var data = response.data;
//                     $scope.itemsNoStockList = data;
//                     if ($scope.itemsNoStockList !== null && $scope.itemsNoStockList.length > 0 && $scope.advancepayment!=true) {
//                         $("#ItemsNoStockListModel").modal('show');
//                     }
//                     else {
//                         $scope.getTotalAmtForSelectedItems();
//                         $scope.totalVPBeforDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
//                         if (angular.isUndefined($scope.totalVPBeforDiscount) || $scope.totalVPBeforDiscount <= 0) {
//                             Notification.warning({message: 'Check out amount can not be zero', positionX: 'center', delay: 2000});
//                             return false;
//                         }
//                         $scope.amountWithoutDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
//                         $scope.totalVPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
//                         $scope.voucherNo = 0.00;
//                         $scope.cardAmount = 0.00;
//                         $scope.transactionNo = 0.00;
//                         $scope.totalVPAfterDiscount = parseFloat($scope.totalAfterDiscount.toFixed(2));
//                         $scope.totalVPAmountTendered = 0.00;
//                         $scope.totalVPAmountRefunded = 0.00;
//                         $scope.voucherAmt = 0.00;
//                         $scope.totalVoucherAmt = 0.00;
//                         $scope.totalPaidAmt = 0.00;
//                         $("#paymentNew").modal('show');
//                     }
//                 },function (error) {
//                     Notification.error({message: data.message, positionX: 'center', delay: 2000});
//                 })
//                 //     .error(function (data, status, header, config) {
//                 //     Notification.error({message: data.message, positionX: 'center', delay: 2000});
//                 // });
//             }
//         };
//         $scope.openSelectpaymentForReturn = function () {
//             $("#selectPaymentForReturn").modal('show');
//
//         };
//         $scope.printData;
//         $scope.openRecievePaymentReturn = function (returnAmount,returnType,formNo) {
//             $scope.totalCashPymtAmtReturned = parseFloat(returnAmount.toFixed(2));
//             $scope.returnType=returnType;
//             $scope.formNo=formNo;
//             $("#returnRemainingPayment").modal('show');
//         };
//         $scope.totalCreditCardAmt=0.00;
//         $scope.totalCashAmt=0.00;
//         $scope.totalVoucherAmt=0.00;
//         $scope.populateSIResponceData = function (data, paymentType) {
//             if (data.result === "SUCCESS") {
//                 $scope.printData = data;
//                 var totalCardAmount=0;
//                 var totalCashAmount=0;
//                 var totalVoucherAmount=0;
//                 if($scope.printData.siData.creditPayment!=null) {
//                     angular.forEach($scope.printData.siData.creditPayment.cardPaymentList, function (value, key) {
//                         totalCardAmount += parseFloat(value.cardAmount);
//                     });
//                     $scope.totalCreditCardAmt=totalCardAmount;
//                 }
//
//                 $scope.totalCashAmt=$scope.printData.siData.cashPayment.totalCPAmountTendered;
//
//                 if($scope.printData.siData.voucherPayment!=null) {
//                     angular.forEach($scope.printData.siData.voucherPayment.multiVoucherPayments, function (value, key) {
//                         totalVoucherAmount += parseFloat(value.amt);
//                     });
//                     $scope.totalVoucherAmt  =totalVoucherAmount;
//                 }
//                 if (paymentType === 'multiPayment' && data.siData.printType === 'normal') {
//                     $("#multiprintreceiptA4").modal('show');
//                 }
//                 else if (paymentType === 'multiPayment' && data.siData.printType === 'pos') {
//                     $("#multiprintreceipt").modal('show');
//                 }
//             } else {
//                 $scope.itemsNoStockList = data.itemDetils;
//                 $("#ItemsNoStockListModel").modal('show');
//             }
//             $scope.clearMultiPayment();
//         };
//         $scope.clearMultiPayment = function () {
//             $scope.selectedItemsList = [];
//             $scope.totalCPAmountTendered = parseFloat(0);
//             $scope.card_checked = false;
//             $scope.voucher_checked = false;
//             angular.forEach($scope.voucherPayementList, function (value, key) {
//                 if (value.amt > 0) {
//                     value.amt = parseFloat(0.00);
//                     value.voucherNo = "";
//                     $scope.voucherPayementList.key = value.amt;
//                     $scope.voucherPayementList.key = value.voucherNo;
//                 }
//             });
//             angular.forEach($scope.vouchers, function (value, key) {
//                 if (key != 0) {
//                     $scope.vouchers.splice(key, 1);
//                 }
//             });
//             angular.forEach($scope.cardPayementList, function (value, key) {
//                 if (value.cardAmount > 0) {
//                     value.cardAmount = parseFloat(0.00);
//                     value.transactionNo = "";
//                     $scope.cardPayementList.key = value.cardAmount;
//                     $scope.cardPayementList.key = value.transactionNo;
//                 }
//             });
//             angular.forEach($scope.cards, function (value, key) {
//                 if (key != 0) {
//                     $scope.cards.splice(key, 1);
//                 }
//             });
//             //----above newly added---
//             $scope.totalBeforDiscount = parseFloat('0.00');
//             $scope.totalDiscount = parseFloat('0.00');
//             $scope.totalAfterDiscount = parseFloat('0.00');
//             $scope.totalTaxAmt = parseFloat('0.00');
//             $scope.invokeOrderName = "";
//             $scope.invokeOrderId = "";
//             $scope.invokeorder = "";
//             $scope.itemSearchText = "";
//             $scope.operation = 'Create';
//             $scope.customerEmail = "";
//             $scope.customerSearchText = "Cash Customer|01";
//             $scope.selectedSerialNumberFilter = "";
//
//
//         }
//         $scope.duplicatePrint = function (salesInvoice) {
//             $scope.printDuplicateReceipt(salesInvoice);
//
//         }
//         $scope.printReturnData = [];
//         $scope.populateSRResponseData = function (data) {
//             $scope.printReturnData = data;
//             $("#printReturnReceipt").modal('show');
//
//         };
//         $scope.printDiv = function (divName) {
//             var printContents = document.getElementById(divName).innerHTML;
//             var popupWin = window.open('', '_blank', 'width=300,height=300');
//             popupWin.document.open();
//             popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" media="print" href="poscss/recept_print.css"></head><body onload="window.print()">' + printContents + '</body></html>');
//             popupWin.document.close();
//         };
//         $scope.printDivA4 = function (divName) {
//             var printContents = document.getElementById(divName).innerHTML;
//             var popupWin = window.open('', '_blank', 'width=300,height=300');
//             popupWin.document.open();
//             popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" media="print" href="poscss/recept_print.css"><link href="css/bootstrap.css" rel="stylesheet"></head><body onload="window.print()">' + printContents + '</body></html>');
//             popupWin.document.close();
//         };
//         $scope.printDivA5 = function (divName) {
//             var printContents = document.getElementById(divName).innerHTML;
//             var popupWin = window.open('', '_blank', 'width=300,height=300');
//             popupWin.document.open();
//             popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" media="print" href="poscss/recept_print.css"><link href="css/bootstrap.css" rel="stylesheet"></head><body style="width:595px;" onload="window.print()">' + printContents + '</body></html>');
//             popupWin.document.close();
//         };
//         $scope.openCashpaymentForReturn = function () {
//             $scope.totalCashPymtAmtReturned = parseFloat($scope.totalBeforDiscount.toFixed(2));
//             ;
//             $("#returnCashpayment").modal('show');
//         };
//         $scope.openCashpaymentForRecievePaymentReturn = function () {
//             $scope.totalCashPymtAmtReturned= parseFloat( $scope.totalCashPymtAmtReturned.toFixed(2));
//             $scope.returnType=$scope.returnType;
//             $scope.formNo=$scope.formNo;
//             $("#returnCashpaymentRecievePayment").modal('show');
//         };
//         $scope.openCashpayment = function () {
//             $scope.totalCPBeforDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
//             $scope.totalCPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
//             $scope.totalTaxAmt = parseFloat($scope.totalTaxAmt.toFixed(2));
//             $scope.totalCPAfterDiscount = parseFloat($scope.totalAfterDiscount.toFixed(2));
//             $scope.totalCPAmountTendered = 0.00;
//             $scope.totalCPAmountRefunded = 0.00;
//             $("#cashpayment").modal('show');
//         };
//         $scope.openCreditCardPayment = function () {
//             $scope.totalCCPBeforDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
//             $scope.totalCCPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
//             //$scope.totalCCPAfterDiscount = $scope.negara($scope.totalAfterDiscount);
//             $scope.totalTaxAmt = parseFloat($scope.totalTaxAmt.toFixed(2));
//             $scope.totalCCPTransactionNo = ""
//             $scope.totalCCPAfterDiscount = parseFloat($scope.totalAfterDiscount.toFixed(2));
//             $("#creditcardpayment").modal('show');
//         };
//         $scope.openVoucherpayment = function () {
//             $scope.totalVPBeforDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
//             //$scope.totalVPAfterAllDeductions = $scope.negara($scope.totalBeforDiscount);
//             $scope.totalVPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
//             $scope.voucherNo = 0.00;
//             $scope.totalVoucherAmt = 0.00;
//             $scope.totalVPAfterDiscount = parseFloat($scope.totalAfterDiscount.toFixed(2));
//             $scope.totalVPAmountTendered = 0.00;
//             $scope.totalVPAmountRefunded = 0.00;
//             $("#voucherPayment").modal('show');
//         }
//         $scope.openMultipayment = function () {
//             $scope.totalVPBeforDiscount = parseFloat($scope.totalBeforDiscount.toFixed(2));
//             $scope.totalVPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
//             $scope.voucherNo = 0.00;
//             $scope.cardAmount = 0.00;
//             $scope.transactionNo = 0.00;
//             $scope.totalVPAfterDiscount = parseFloat($scope.totalAfterDiscount.toFixed(2));
//             $scope.totalVPAmountTendered = 0.00;
//             $scope.totalVPAmountRefunded = 0.00;
//             $scope.voucherAmt = 0.00;
//             $scope.totalVoucherAmt = 0.00;
//             $("#multiPayment").modal('show');
//         };
//         $scope.openpayment = function () {
//             $scope.openStockValidateMultipaymnet();
//         };
//         $scope.openCreatInovkeOrder = function () {
//             $("#createInvokeOrder").modal('show');
//         };
//         $scope.itemIndexOfItemCode = function (array, searchVal) {
//             var itemIndex = -1;
//             if ($scope.isUndefinedOrNull(searchVal)) {
//                 itemIndex = -1;
//             } else {
//                 var foundIndex = $filter('filter')(array, {
//                     itemCode: searchVal
//                 }, true)[0];
//                 var foundIndex1 = $filter('filter')(array, {
//                     itemCode: searchVal
//                 }, true)[0];
//                 itemIndex = array.indexOf(foundIndex);
//                 if (!$scope.isUndefinedOrNull(foundIndex1)) {
//                     if (foundIndex1.serializableStatus == 'Serialize')
//                         itemIndex = -1
//                 }
//             }
//             return itemIndex;
//         };
//         $scope.itemIndexOf = function (array, searchVal) {
//             var itemIndex = -1;
//             if ($scope.isUndefinedOrNull(searchVal)) {
//                 itemIndex = -1;
//             } else {
//                 var foundIndex = $filter('filter')(array, {
//                     itemName: searchVal
//                 }, true)[0];
//                 itemIndex = array.indexOf(foundIndex);
//             }
//             return itemIndex;
//         };
//         $scope.taxIndexOf = function (array, searchVal) {
//             var taxIndex = -1;
//             if ($scope.isUndefinedOrNull(searchVal)) {
//                 taxIndex = -1;
//             } else {
//                 var foundIndex = $filter('filter')(array, {
//                     taxid: searchVal
//                 }, true)[0];
//                 taxIndex = array.indexOf(foundIndex);
//             }
//             return taxIndex;
//         };
//         $scope.serializableItemsIndexOf = function (array, searchVal) {
//             var serializableItemsIndex = -1;
//             if ($scope.isUndefinedOrNull(searchVal)) {
//                 serializableItemsIndex = -1;
//             } else {
//                 var foundIndex = $filter('filter')(array, {
//                     serializableItemCode: searchVal
//                 }, true)[0];
//                 serializableItemsIndex = array.indexOf(foundIndex);
//             }
//             return serializableItemsIndex;
//         };
//         $scope.InvokeOrderIndexOf = function (array, searchVal) {
//             var invokeOrderIndex = -1;
//             if ($scope.isUndefinedOrNull(searchVal)) {
//                 invokeOrderIndex = -1;
//             } else {
//                 var foundIndex = $filter('filter')(array, {
//                     id: searchVal
//                 }, true)[0];
//                 invokeOrderIndex = array.indexOf(foundIndex);
//             }
//             return invokeOrderIndex;
//         };
//         $scope.negara = function (number) {
//             var k = number;
//             if (!angular.isUndefined(number) && number !== null && number !== '') {
//                 var rm = number.toString().split(".")[1];
//                 var a = rm % 10;
//                 var j = parseFloat(number);
//                 if (a == 1 || a == 6) {
//                     k = (j - parseFloat(0.01)).toFixed(2);
//                 }
//                 if (a == 2 || a == 7) {
//                     k = (j - parseFloat(0.02)).toFixed(2);
//                 }
//                 if (a == 3 || a == 8) {
//                     k = (j + parseFloat(0.02)).toFixed(2);
//                 }
//                 if (a == 4 || a == 9) {
//                     k = (j + parseFloat(0.01)).toFixed(2);
//                 }
//                 return parseFloat(k);
//
//             } else {
//                 return k;
//             }
//         };
//         $scope.toFixedTwo = function (number) {
//             var k = number;
//             if (angular.isUndefined(number) || number === "" || !angular.isNumber(parseFloat(number))) {
//                 k = parseFloat(0.00).toFixed(2);
//             } else {
//                 k = parseFloat(number).toFixed(2);
//             }
//             $scope.onFocusElement.value = k;
//             angular.element($scope.onFocusElement).trigger('input');
//         };
//         $scope.isUndefinedOrNull = function (data) {
//             return (angular.isUndefined(data) || data === null || data === '' || data === 'null');
//         };
//         $scope.isValidatedData = function () {
//             $scope.isValide = true;
//             angular.forEach($scope.selectedItemsList, function (item, index) {
//                 if (angular.isUndefined(item.unitPrice) || item.unitPrice === '' || parseFloat(item.unitPrice) <= 0) {
//                     $scope.isValide = false;
//                 } else if (angular.isUndefined(item.qty) || item.qty === '' || parseFloat(item.qty) <= 0) {
//                     $scope.isValide = false;
//                 }
//             });
//             return $scope.isValide;
//         };
//         $scope.hideButton = function () {
//             $scope.showrtntype = false;
//         };
//         // $element.bind("keyup", function(event) {
//         //
//         //     var keyCode = event.which || event.keyCode;
//         //
//         //     // If enter key is pressed
//         //     if (keyCode === 27) {
//         //         $timeout(function () {
//         //             $scope.hideButton();
//         //             $scope.showrtntype = false;
//         //         }, 0);
//         //
//         //     }
//         //
//         // });
//         $('#PosInvoiceList').on('hidden.bs.modal', function () {
//             if ($scope.notHide != 'posListClick') {
//                 $timeout(function () {
//                     $scope.hideButton();
//                 }, 0);
//             }
//         });
//         check = 0;
//         $('#camera_icon').click(function () {
//             $scope.barcodeInput = "";
//             check = 0;
//             $('#barcodeModal').modal('show');
//             $('.custom_input label').click();
//         });
//         Quagga.onDetected(function (result) {
//             code = result.codeResult.code;
//             $('#barcodeModal').modal('hide');
//             //alert(code);
//             if (check == 0) {
//                 check = 1;
//                 $scope.barcodeInput = code;
//                 $('#barcodeInput').val(code);
//                 e = $.Event('keyup');
//                 e.keyCode = 13; // enter
//                 $('#barcodeInput').trigger(e);
//             }
//         });
//         check = 0;
//         $('#serial_icon').click(function () {
//             $scope.serialBarcodeInput = "";
//             check = 0;
//             $('#barcodeModal').modal('show');
//             $('.custom_input label').click();
//         });
//         Quagga.onDetected(function (result) {
//             code = result.codeResult.code;
//             $('#barcodeModal').modal('hide');
//             //alert(code);
//             if (check == 0) {
//                 check = 1;
//                 $scope.serialBarcodeInput = code;
//                 $('#serialBarcodeInput').val(code);
//                 e = $.Event('keyup');
//                 e.keyCode = 13; // enter
//                 $('#serialBarcodeInput').trigger(e);
//             }
//         });
//         // $scope.esc = function(keyEvent) {
//         //     if (keyEvent.which === 13)
//         //         alert('I am an alert');
//         //     $scope.showrtntype=false;
//         // }
//         Quagga.init({
//             inputStream: {
//                 name: "Live",
//                 type: "ImageStream",
//                 constraints: {
//                     width: 640,
//                     height: 480,
//                     facing: "environment"
//                 }
//             },
//             locator: {
//                 patchSize: "medium",
//                 halfSample: true
//             },
//             numOfWorkers: 4,
//             locate: false,
//             decoder: {
//                 readers: ["code_128_reader"]
//             }
//         }, function () {
//             Quagga.start();
//         });
//         $scope.openAdvancePartialPayment=function (arBalance,formNo) {
//             $scope.totalPaidAmt = 0.00;
//             $scope.totalVPAmountRefunded = 0.00;
//             $scope.totalVPAmountTendered = 0.00;
//             $scope.totalVPAmountRefunded = 0.00;
//             $scope.totalVPDiscount = parseFloat($scope.totalDiscount.toFixed(2));
//             $scope.totalVPBeforDiscount = parseFloat(arBalance.toFixed(2));
//             $scope.amountWithoutDiscount = parseFloat(arBalance.toFixed(2));
//             $scope.totalVPAfterDiscount = parseFloat(arBalance.toFixed(2));
//             $scope.formNo=formNo;
//             $scope.cardAmount = 0.00;
//             $scope.totalVoucherAmt = 0.00;
//             $("#advancePosPayment").modal('show');
//         }
//         $scope.printReceiptData;
//         $scope.date;
//         $scope.balanceAmount;
//         $scope.saveMultiAdvancePaySI=function () {
//             $http.post($scope.retailServerURL + '/saveAdavancePartialPayment/'+$scope.formNo,angular.toJson($scope.populateSaveSIAdvanceMultiPayData(),"")).then(function (response) {
//                 var data = response.data;
//                 $("#advancePosPayment").modal('show');
//                 $("#PosInvoiceRecievePaymentList").modal('hide');
//                 $("#PosInvoiceAdvanceList").modal('hide');
//                 $("#advancePosPayment").modal('hide');
//                 Notification.info({message: 'Recieve Payment SuccessFully Created', positionX: 'center', delay: 2000});
//                 $scope.printReceiptData=data;
//                 $scope.date = new Date();
//                 $scope.balanceAmount= Math.abs($scope.printReceiptData.balanceAmount);
//                 $("#receiptPaymentA5SaveAndPrint").modal('show');
//
//                 $scope.clearMultiPayment();
//             },function (error) {
//                 Notification.info({message: 'Something Went Wrong', positionX: 'center', delay: 2000});
//             })
//             //     .error(function (data, status, header, config) {
//             //     Notification.info({message: 'Something Went Wrong', positionX: 'center', delay: 2000});
//             //
//             // });
//
//         };
//         $scope.getPaymentDetails = function (val) {
//             $(".loader").css("display", "block");
//             if (angular.isUndefined(val)) {
//                 val = "";
//             }
//             $http.get($scope.retailServerURL + '/getReceiptDetails/?itemSearchText=' + val).then(function (response) {
//                 var data = response.data;
//                 $scope.receiptPaymentList = data;
//                 $("#print_list_modal").modal('hide');
//                 $("#RecievePaymentList").modal('show');
//                 $scope.searchText = val;
//                 $scope.itemSearchText=val;
//             }, function (error) {
//                 Notification.error({
//                     message: 'Something went wrong, please try again',
//                     positionX: 'center',
//                     delay: 2000
//                 });
//             })
//         };
//         $scope.printReceiptData;
//         $scope.date;
//         $scope.balanceAmount;
//         $scope.recieptPrint=function (paymentID) {
//             $http.post($scope.retailServerURL+'/printRecieptPayment/'+paymentID).then(function (response) {
//                 var data = response.data;
//                 $scope.printReceiptData=data;
//                 $scope.date = new Date();
//                 $scope.balanceAmount= Math.abs($scope.printReceiptData.totalAmount);
//                 $("#receiptPaymentA5").modal('show');
//             });
//         };
//         $scope.populateSaveSIAdvanceMultiPayData = function () {
//             var CPDetails;
//             var CCPDetails;
//             var VPDetails;
//             var cashPayment = $scope.cashcheck;
//             var cardPayment = $scope.card_checked;
//             var voucherPayment = $scope.voucher_checked;
//
//             if (cashPayment == "cashPayment") {
//                 CPDetails = {
//                     totalCPAmountRefunded: $scope.totalCPAmountRefunded,
//                     totalCPDiscount: $scope.totalCPDiscount,
//                     totalCPAmountTendered: $scope.totalCPAmountTendered
//                 };
//             }
//             if (cardPayment == "creditPayment") {
//                 CCPDetails = {
//                     totalCCPDiscount: $scope.totalCCPDiscount,
//                     totalCCPAmountTendered: $scope.totalCCPAmountTendered,
//                     transactionNo: $scope.totalCCPTransactionNo,
//                     totalCCPAfterDiscount: $scope.totalCCPAfterDiscount,
//                     cardPaymentList: $scope.cards
//                 };
//             }
//             if (voucherPayment == "voucherPayment") {
//                 VPDetails = {
//                     totalVPBeforDiscount: $scope.totalVPBeforDiscount,
//                     totalVPDiscount: $scope.totalVPDiscount,
//                     voucherNo: $scope.voucherNo,
//                     voucherDate: $scope.voucherDate,
//                     totalVoucherAmt: $scope.totalVoucherAmt,
//                     totalVPAfterAllDeductions: $scope.totalVPAfterAllDeductions,
//                     totalVPAmountTendered: $scope.totalVPAmountTendered,
//                     totalVPAmountRefunded: $scope.totalVPAmountRefunded,
//                     multiVoucherPayments: $scope.vouchers
//                 };
//             }
//             var data = {
//                 cashPayment: CPDetails,
//                 creditPayment: CCPDetails,
//                 voucherPayment: VPDetails,
//                 totalCheckOutamt: $scope.totalVPBeforDiscount,
//                 totalTaxAmt: $scope.totalTaxAmt,
//                 taxType: $scope.fullSimplTax,
//                 customerId: $scope.customerId,
//                 cutomerName: $scope.customerSearchText,
//                 amountReturned: $scope.totalVPAmountRefunded,
//                 discountAmount: $scope.totalVPDiscount,
//                 totalTenderedAmount: $scope.totalPaidAmt,
//                 userName: $('#userName').val()
//             };
//             return data;
//         };
//         $scope.vouchersEntity = function ($event) {
//             if (!$event) {
//                 $scope.vouchers.forEach(function (voucher, index) {
//                     if (index != 0) {
//                         $scope.vouchers.splice(index, 1);
//                         $scope.vouchersEntity($event);
//                     }
//                 });
//             }
//         }
//         $scope.CardEntity = function ($event) {
//             if (!$event) {
//                 $scope.cards.forEach(function (card, index) {
//                     if (index != 0) {
//                         $scope.cards.splice(index, 1);
//                         $scope.CardEntity($event);
//                     }
//                 });
//             }
//         }
//         $scope.vouchers = [{id: 'voucher_info1'}];
//         $scope.addNewVoucher = function () {
//             var newVoucherNo = $scope.vouchers.length + 1;
//             $scope.vouchers.push({'id': 'voucher' + newVoucherNo});
//         };
//         $scope.voucherPayementList = $scope.vouchers;
//         $scope.removeVoucher = function (index) {
//             $scope.vouchers.splice(index, 1);
//             $scope.voucherPayementList = $scope.vouchers;
//             $scope.removePayments = "removeField";
//             $scope.OnChangeMultiPayament();
//         };
//         $scope.cards = [{id: 'card_info1'}];
//         $scope.addNewCard = function () {
//             var newCard = $scope.cards.length + 1;
//             $scope.cards.push({'id': 'card' + newCard});
//         };
//         $scope.cardPayementList = $scope.cards;
//         $scope.removeCard = function (index) {
//             $scope.cards.splice(index, 1);
//             $scope.cardPayementList = $scope.cards;
//             $scope.removePayments = "removeField";
//             $scope.OnChangeMultiPayament();
//         };
//         $scope.getClearPaymentList = function (checkVal, paymentType) {
//             if (checkVal == false && paymentType == "amount") {
//                 var tempTotalVPAfterDiscount = $scope.totalVPAfterDiscount;
//                 var tempTotalVPAmountTendered = $scope.totalVPAmountTendered;
//                 var tempTotalVPAmountRefunded = parseFloat(0.00);
//                 if (parseFloat(tempTotalVPAmountTendered) > tempTotalVPAfterDiscount) {
//                     tempTotalVPAmountRefunded = parseFloat(0.00);
//                 }
//                 else {
//                     tempTotalVPAmountRefunded = parseFloat(0.00);
//                 }
//                 $scope.totalVPAmountTendered = parseFloat(0.00);
//                 $scope.totalVPAmountRefunded = parseFloat(tempTotalVPAmountRefunded.toFixed(2));
//             }
//             else if (checkVal == false && paymentType == "card") {
//                 angular.forEach($scope.cardPayementList, function (value, key) {
//                     if (value.cardAmount > 0) {
//                         value.cardAmount = parseFloat(0.00);
//                         value.transactionNo = "";
//                         $scope.cardPayementList.key = value.cardAmount;
//                         $scope.cardPayementList.key = value.transactionNo;
//                         $scope.checkbox = "unchecked";
//                         $scope.OnChangeMultiPayament();
//                     }
//                 });
//             }
//             else if (checkVal == false && paymentType == "voucher") {
//                 angular.forEach($scope.voucherPayementList, function (value, key) {
//                     if (value.amt > 0) {
//                         value.amt = parseFloat(0.00);
//                         value.voucherNo = "";
//                         $scope.voucherPayementList.key = value.amt;
//                         $scope.voucherPayementList.key = value.voucherNo;
//                         $scope.checkbox = "unchecked";
//                         $scope.OnChangeMultiPayament();
//                     }
//                 });
//             }
//             else if (checkVal == false && paymentType == "cash") {
//                 if ($scope.totalCPAmountTendered > parseFloat(0)) {
//                     $scope.totalCPAmountTendered = parseFloat(0);
//                     $scope.checkbox = "unchecked";
//                     $scope.OnChangeMultiPayament();
//                 }
//             }
//         }
//         $scope.reloadPage = function () {
//             $("html,body").hide();
//             $window.location.reload();
//
//         }
//         $scope.searchCustomerName = function (companyName, checkBoxVal) {
//             var parameter = JSON.stringify({company_name:companyName});
//             if (checkBoxVal == true) {
//                 $http.post($scope.retailServerURL + '/searchhiconnectcompanyName/'+parameter).then(function (response) {
//                     var data = response.data;
//                 });
//             }
//
//         }
//     }
// ]);