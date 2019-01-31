app.controller('feeReceiptNoReportController',
    function ($scope, $http, $location, $filter, Notification) {
        $scope.openDate1 = function () {
            $scope.popup1.opened = true;
        };
        $scope.bshimServerURL = "/bs";
        $scope.format = 'dd/MM/yyyy';
        $scope.popup1 = {
            opened: false
        };
        $scope.openDate2 = function () {
            $scope.popup2.opened = true;
        };
        $scope.popup2 = {
            opened: false
        };
        $scope.setDates=function(){
            if($scope.dateTypeFilter=='Today'){
                $scope.fromDate=new Date();
                $scope.toDate=new Date();
            }else if($scope.dateTypeFilter=='Yesterday'){
                var previousDate = new Date()
                previousDate.setDate(previousDate.getDate() - 1);
                $scope.fromDate=previousDate;
                $scope.toDate=previousDate;
            }else if($scope.dateTypeFilter=='ThisWeek'){
                var monday = new Date();
                monday.setDate(monday.getDate() - monday.getDay() + 1);
                var sunday = new Date();
                // sunday.setDate(sunday.getDate() - sunday.getDay() + 7);
                $scope.fromDate=monday;
                $scope.toDate=sunday;
            }else if($scope.dateTypeFilter=='PreviousWeek'){
                var date= new Date();
                var mndy= new Date();
                var dummy =  date.getDay();
                dummy = dummy + 7;
                mndy.setDate(mndy.getDate() - dummy );
                $scope.fromDate=mndy.setDate(mndy.getDate()+1);
                var sunday =mndy;
                sunday.setDate(sunday.getDate() - sunday.getDay() + 7);
                $scope.toDate=new Date(sunday);
            }else if($scope.dateTypeFilter=='CurrentMonth'){
                var date = new Date();
                $scope.fromDate= new Date(date.getFullYear(), date.getMonth(), 1);
                // $scope.toDate= new Date(date.getFullYear(), date.getMonth()+1 , 0);
                $scope.toDate= new Date();
            }else if($scope.dateTypeFilter=='PreviousMonth') {
                var date = new Date();
                $scope.fromDate = new Date(date.getFullYear(), date.getMonth()-1, 1);
                $scope.toDate = new Date(date.getFullYear(), date.getMonth() - 1+1, 0);
            }
        }
        var expanded = false;

        $scope.showCheckboxes=function() {
            var checkboxes = document.getElementById("checkboxes");
            if (!expanded) {
                checkboxes.style.display = "block";
                expanded = true;
            } else {
                checkboxes.style.display = "none";
                expanded = false;
            }
        }
        $scope.orderByField = 'total';
        $scope.reverseSort = false;
        $scope.getAcademicYearList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + '/getAcdemicYearList2?searchText=' + val+'&checkboxStatus='+"false").then(function (response) {
                var data = response.data.object;
                $scope.academicYearList= data;
                }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.selectedGrades = [];
        $scope.pushSelectedGrades = function (id) {
            var index = $scope.selectedGrades.indexOf(id);
            if (parseInt(index) >= 0) {
                $scope.selectedGrades.splice(index, 1);
            }else {
                $scope.selectedGrades.push(id);
            }
            $scope.grades=$scope.selectedGrades;
        };
        $scope.getAcademicYearList();
        $scope.getGradeList = function (val,checkboxForInActive) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            if (angular.isUndefined(checkboxForInActive)) {
                checkboxForInActive = "false";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + '/getGradeList2?searchText=' + val+'&checkboxForInActive='+checkboxForInActive).then(function (response) {
                var data = response.data.object;
                $scope.gradeList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.dateTypeFilter='CurrentMonth';
        $scope.setDates();
        $scope.getTotal=function(){
            $scope.totalAmt=0;
            angular.forEach($scope.list,function (val,key) {
                $scope.totalAmt+=val.paidAmt;
            })
        }
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

        $scope.getGradeList();
        $scope.getReceiptNoReport=function () {
            var details;
            details={
                academicYearMaster:$scope.academicYearId,
                toDate:$scope.toDate,
                fromDate:$scope.fromDate,
                gradeIds:$scope.grades
            }
            $http.post($scope.bshimServerURL + '/receiptNoReport',angular.toJson(details)).then(function (response) {
                var data = response.data;
                $scope.list = data;
                $scope.length=$scope.list.length;
                $scope.getTotal();
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }
        $scope.getReceiptNoReport();
    });