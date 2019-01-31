app.controller('feeController',
    function ($scope, $rootScope, $http, $location, $window, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();
        $scope.selectedStudentFeeList = [];
        $scope.feedetails = [];
        var searchText = $scope.SearchText;
        var grade = $scope.gradeNameText;
        var student = $scope.studentText;
        $scope.getStudentFeeList = function (searchText, grade, student,type) {
            if (angular.isUndefined(searchText)) {
                searchText = "";
            }
            else if (searchText == null) {
                searchText = "";
            }
            if (angular.isUndefined(grade)) {
                grade = "";
            }
            else if (grade == null) {
                grade = "";
            }
            if (angular.isUndefined(student)) {
                student = "";
            }
            else if (student == null) {
                student = "";
            }
            if(type=='grade'){
                student="";
            }
            $http.post($scope.bshimServerURL + '/getStudentFeeList?searchText=' + searchText + "&grade=" + grade + "&student=" + student).then(function (response) {
                var str = response.data.object;
                var data = JSON.parse(str);
                $scope.studentFeeList = data;
                angular.forEach($scope.studentFeeList,function (val,key) {
                    val.receiptList=[];
                })
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getStudentFeeList();
        $scope.checkAmt=function(type,index){
            $scope.payingFee=parseInt(0);
            $scope.totalDiscount=parseInt(0);
            $scope.totalFee=parseInt(0);
            $scope.totalPayable=parseInt(0);
            $scope.totalPaid=parseInt(0);
            $scope.totalDueAmt=parseInt(0);
            angular.forEach($scope.studentFeeDetails.studentFeeDetailsPojoList,function (value,key1) {
                $scope.totalDiscount=$scope.totalDiscount+parseInt(value.discount);
                $scope.totalFee=$scope.totalFee+parseInt(value.feeTypeAmount);
                $scope.totalPayable=$scope.totalPayable+parseInt(value.payable);
                angular.forEach(value.installmentsPojos,function (val,key) {
                    if(val.installmentsAmount<val.payingAmt){
                        Notification.error({
                            message: 'Paying Amt should not be Greater than Due Amt',
                            positionX: 'center',
                            delay: 2000
                        });
                        val.payingAmt=val.installmentsAmount;
                    }else {
                        if((value.checkBox==true||val.checkBox==true)){
                            if(index==key1){
                                if(value.checkBox==true&&type==true&&val.installmentsAmount>0){
                                    val.checkBox=true;
                                }
                            }
                            if(val.payingAmt==0){
                                val.payingAmt=val.installmentsAmount;
                            }
                        }
                        if(val.checkBox==false){
                            $scope.totalDueAmt=$scope.totalDueAmt-parseInt(val.installmentsAmount);
                            val.payingAmt=0;
                        }else if (value.checkBox==false){
                            val.checkBox=false;
                            $scope.totalDueAmt=$scope.totalDueAmt-parseInt(val.installmentsAmount);
                            val.payingAmt=0;
                        }
                    }
                    $scope.payingFee=$scope.payingFee+parseInt(val.payingAmt);
                    $scope.totalDueAmt=$scope.totalDueAmt+parseInt(val.installmentsAmount);
                })
                if(value.paidAmount>0)
                $scope.totalPaid=$scope.totalPaid+parseInt(value.paidAmount);
            })
            $scope.paymentTypeAmount();
        }
        $scope.paymentTypeAmount=function(){
            $scope.payingFee=parseInt($scope.payingFee);
            if($scope.paymentTypeText=='Cash'){
                $scope.totalCPAmountTendered=$scope.payingFee;
            }else if($scope.paymentTypeText=='Card'){
                $scope.cardAmount=$scope.payingFee;
            }else if($scope.paymentTypeText=='Bank'||$scope.paymentTypeText=='Online'){
                $scope.bankAmount=$scope.payingFee;
            }
        }
        $scope.removeItemPopUpDetails = function () {
            $("html,body").hide();
            $window.location.reload();
            $scope.getStudentFeeList();
        };

        $scope.studentFeeCollectDetails = function (studentId) {
            $scope.totalDueAmount=studentId.dueAmount;
            $http.post($scope.bshimServerURL + "/getStudentDetails?studentId="+studentId.student.studentId+'&type='+"Fee").then(function (response) {
            $scope.studentFeeDetails=response.data;
                $("#add_new_Student_Fee_modal").modal('show');
                $scope.payingFee = parseInt(0);
                angular.forEach($scope.studentFeeDetails.studentFeeDetailsPojoList, function (val, key) {
                    $scope.checkAmt(val.installmentsPojos);
                })
                $scope.paymentTypeText="";
                $scope.bank=[];
                $scope.card=[];
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };

        $scope.getSchoolBranchDetailsList = function () {
            $http.post($scope.bshimServerURL + '/getSchoolBranchDetailsList').then(function (response) {
                var data = response.data.object;
                for(var i=0; i<data.length;i++) {
                    $scope.schoolBranchId = data[i].schoolBranchId;
                    $scope.branchCode = data[i].branchCode;
                    $scope.branchName = data[i].branchName;
                    $scope.address = data[i].address;
                    $scope.city = data[i].city;
                    $scope.pinCode = data[i].pinCode;
                    $scope.state = data[i].state;
                    $scope.phoneNumber = data[i].phoneNumber;
                    $scope.emailId = data[i].emailId;
                    $scope.receiptNo = data[i].receiptNo;
                    $scope.termsandconditions = data[i].termsAndConditions;
                    $scope.receiptfooterdesc = data[i].receiptFooter;
                }
                // $scope.gradeList = data;
                // $scope.searchText = val;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getSchoolBranchDetailsList();

        var grade = $scope.gradeNameText;
        $scope.getStudentListBasedOnGrade = function (grade) {
            $scope.studentText=null;
            if(grade!=null){
                if (angular.isUndefined(grade)) {
                    grade = "";
                }
                $http.post($scope.bshimServerURL + "/getStudentListBasedOnGrade?searchText=" + grade).then(function (response) {
                    var data = response.data.object;
                    console.log(data);
                    $scope.studentListBasedOnGrade = data;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            }
        };
        $scope.getStudentListBasedOnGrade();
        $scope.getGradeList = function () {
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + "/getGradeList").then(function (response) {
                var data = response.data.object;
                console.log(data);
                $scope.gradeList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.bank=[];
        $scope.card=[];
        $scope.getGradeList();
        $scope.paymentDate=new Date();
        $scope.dateOptions1 = {
            maxDate : new Date()
        };
        $scope.format = 'MM/dd/yyyy';
        $scope.openDate1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.popup1 = {
            opened: false
        };

        $scope.saveStudentFee=function(){
            document.getElementById("saveButton").disabled = true;
            if($scope.payingFee<0 || $scope.payingFee==0){
                document.getElementById("saveButton").disabled = false;
                 Notification.error({
                     message: 'Payment Amount cant be Zero',
                     positionX: 'center',
                     delay: 2000
                 });
             }else if($scope.paymentTypeText==null||$scope.paymentTypeText==""){
                document.getElementById("saveButton").disabled = false;
                 Notification.error({
                     message: 'Please Select Payment Type',
                     positionX: 'center',
                     delay: 2000
                 });
             }
             else {
                 bootbox.confirm({
                     title: "Alert",
                     message: "Do you want to Continue?",
                     buttons: {
                         confirm: {
                             label: 'OK'
                         },
                         cancel: {
                             label: 'Cancel'
                         }
                     },
                     callback: function (result) {
                         if (result == true) {
                             document.getElementById("saveButton").disabled = true;
                             $scope.CARD_PAYMENT_DETAILS = [];
                             $scope.BANK_PAYMENT_DETAILS = [];
                             if ($scope.paymentTypeText == 'Cash') {
                                 $scope.studentFeeDetails.cashAmt = $scope.totalCPAmountTendered;
                             } else if ($scope.paymentTypeText == 'Card') {
                                 $scope.CARD_PAYMENT_DETAILS.push({
                                     'cardNo': $scope.card.cardNo,
                                     'cardAmt': $scope.cardAmount,
                                     'cardTransactionNo': $scope.card.cardTransactionNo
                                 });
                                 $scope.studentFeeDetails.cardDetails = angular.toJson($scope.CARD_PAYMENT_DETAILS);
                                 $scope.studentFeeDetails.cardAmt = $scope.cardAmount;
                                 $scope.studentFeeDetails.cardNo = $scope.card.cardNo;
                                 $scope.studentFeeDetails.approvalCode = $scope.card.cardTransactionNo;
                             } else if ($scope.paymentTypeText == 'Bank'||$scope.paymentTypeText == 'Online') {
                                 $scope.BANK_PAYMENT_DETAILS.push({
                                     'bankName': $scope.bank.bankName,
                                     'chequeNo': $scope.bank.chequeNo,
                                     'amount': $scope.bankAmount,
                                     'date': $scope.chequeDate
                                 });
                                 $scope.studentFeeDetails.bankDetails = angular.toJson($scope.BANK_PAYMENT_DETAILS);
                                 $scope.studentFeeDetails.bankAmt = $scope.bankAmount;
                                 $scope.studentFeeDetails.chequeNo = $scope.bank.chequeNo;
                                 $scope.studentFeeDetails.bankName = $scope.bank.bankName;
                                 $scope.studentFeeDetails.chequeDate = $scope.bank.chequeDate;
                             }
                             $scope.studentFeeDetails.payingFee = $scope.payingFee;
                             $scope.studentFeeDetails.paymentType = $scope.paymentTypeText;
                             $scope.studentFeeDetails.paymentDate = $scope.paymentDate;
                             $scope.studentFeeDetails.totalDueAmount = $scope.totalDueAmount;
                             $http.post($scope.bshimServerURL + "/saveStudentFee", angular.toJson($scope.studentFeeDetails)).then(function (response) {
                                 var data = response.data;
                                 $scope.totConfiguredFee =$scope.totalFee;
                                 $scope.totDiscount =$scope.totalDiscount;
                                 $scope.totalPaidAmt =$scope.totalPaid+$scope.payingFee;
                                 $scope.dueAmt =$scope.totalPayable-$scope.totalPaidAmt;
                                 $scope.studentFeeDetails.cardNo=data.cardNo;
                                 document.getElementById("saveButton").disabled = false;
                                 $scope.studentFeeDetailsPojoList = [];
                                 angular.forEach(data.studentFeeDetailsPojoList, function (val, key) {
                                     if (val.checkBox == true) {
                                         if(val.payingAmount>0)
                                         $scope.studentFeeDetailsPojoList.push({
                                             payingAmt: val.payingAmount,
                                             checkBox: true,
                                             feeTypeName: val.feeTypeName
                                         })
                                     }
                                 })
                                 $scope.date=new Date($scope.paymentDate);
                                 $scope.numberToWord=toWords(parseInt($scope.payingFee));
                                 $("#receiptPaymentA5").modal('show');
                                 console.log("receiptData" + data)
                                 $("#add_new_Student_Fee_modal").modal('hide');
                                 Notification.success({
                                     message: 'Student Fee is Saving successfully',
                                     positionX: 'center',
                                     delay: 2000
                                 });
                                 $scope.getStudentFeeList();
                             }, function (error) {
                                 Notification.error({
                                     message: 'Something went wrong, please try again',
                                     positionX: 'center',
                                     delay: 2000
                                 });
                             });
                         }else {
                             document.getElementById("saveButton").disabled = false;
                         }
                     }
                 });
             }
         }
        $scope.saveisDisabled=false;
        $scope.receiptList=[];
        $scope.screenColor=[];
         $scope.getReceiptDetails=function(studentFeeId,index){
             angular.forEach($scope.studentFeeList,function (val,key) {
                 if(studentFeeId==val.studentFeeId){
                     index=key;
                 }
             })
             if($scope.studentFeeList[index].showDetails==false){
                 $scope.studentFeeList[index].showDetails=true;
                 $scope.studentFeeList[index].screenColor='#ffccff';

             }else {
                 $scope.studentFeeList[index].screenColor='#ffffff';
                 $scope.studentFeeList[index].showDetails=false;
             }
             $http.post($scope.bshimServerURL + "/getReceiptDetails?id="+studentFeeId).then(function (response) {
                 var data = response.data;
                 $scope.receiptList=data;
                 $scope.studentFeeList[index].receiptList=$scope.receiptList;
             }, function (error) {
                 Notification.error({
                     message: 'Something went wrong, please try again',
                     positionX: 'center',
                     delay: 2000
                 });
             });
         }
        $scope.reloadPage = function () {
            $window.location.reload();
        };
         $scope.getDuplicatePrint=function(repId,date){
             $http.post($scope.bshimServerURL + "/getDuplicateReceipt?id="+repId).then(function (response) {
                 $scope.studentDetails=response.data;
                 console.log($scope.studentDetails);
                 $scope.studentFeeDetailsPojoList=[];
                 angular.forEach($scope.studentDetails.feeTypeMasterPojoList,function (val,key) {
                     if(val.payable>0){
                         $scope.studentFeeDetailsPojoList.push({
                             'feeTypeName':val.feeTypeName,
                             'payingAmt':val.payable,
                             'checkBox':true
                         })
                     }
                 })
                 $scope.date=date;
                 $scope.payingFee=$scope.studentDetails.totalPaid;
                 $scope.studentFeeDetails=$scope.studentDetails.studentPojo;
                 $scope.paymentTypeText=$scope.studentDetails.paymentType;
                 $scope.dueAmt =$scope.studentDetails.studentFee.dueAmount;
                 $scope.totConfiguredFee =$scope.studentDetails.studentFee.totalFeeAmount;
                 $scope.totDiscount =$scope.studentDetails.studentFee.totalFeeAmount -$scope.studentDetails.studentFee.totalPayable;
                 $scope.totalPaidAmt=$scope.studentDetails.studentFee.paidAmount;
                 if ($scope.paymentTypeText == 'Cash') {
                     $scope.studentFeeDetails.cashAmt = $scope.payingFee;
                 } else if ($scope.paymentTypeText == 'Card') {
                     $scope.studentFeeDetails.cardAmt = $scope.payingFee;
                     $scope.studentFeeDetails.cardNo = $scope.studentDetails.cardNo;
                     $scope.studentFeeDetails.approvalCode = $scope.studentDetails.approvalCode;
                 } else if ($scope.paymentTypeText == 'Bank'||$scope.paymentTypeText == 'Online') {
                     $scope.studentFeeDetails.bankAmt = $scope.payingFee;
                     $scope.studentFeeDetails.chequeNo = $scope.studentDetails.transactionNo;
                     $scope.studentFeeDetails.bankName = $scope.studentDetails.bankName;
                     $scope.studentFeeDetails.chequeDate = $scope.studentDetails.chequeDate;
                 }
                 $scope.studentFeeDetailsPojoList=$scope.studentFeeDetailsPojoList;
                 $scope.numberToWord=toWords(parseInt($scope.payingFee));
                 $("#receiptPaymentA5").modal('show');
             }, function (error) {
                 Notification.error({
                     message: 'Something went wrong, please try again',
                     positionX: 'center',
                     delay: 2000
                 });
             });
         }
        $scope.openDate1 = function () {
            $scope.popup1.opened = true;
        };
         $scope.openDate = function () {
            $scope.popup.opened = true;
        };
        $scope.format = 'dd/MM/yyyy';
        $scope.bank.chequeDate=new Date();
        $scope.popup1 = {
            opened: false
        };
        $scope.popup = {
            opened: false
        };
        $scope.printDivA5 = function (divName) {
            var printContents = document.getElementById(divName).innerHTML;
            var popupWin = window.open('', '_blank', 'width=300,height=300');
            popupWin.document.open();
            popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" media="print" href="poscss/recept_print.css"><link href="css/bootstrap.css" rel="stylesheet"></head><body style="width:595px;" onload="window.print()">' + printContents + '</body></html>');
            popupWin.document.close();
            $("#close").hide();
            $("#receiptPaymentA5").modal('hide');
        };

    });
