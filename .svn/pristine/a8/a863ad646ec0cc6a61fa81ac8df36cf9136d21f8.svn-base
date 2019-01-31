app.controller('feeDueReportController',
    function ($scope,$http,Notification,$filter) {
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
        $scope.getAcademicYearList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + '/getAcdemicYearList2?searchText=' + val+'&checkboxStatus='+"false").then(function (response) {
                var data = response.data.object;
                $scope.academicYearList= data;
                $scope.searchText = val;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

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
                $scope.searchText = val;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };

        $scope.getGradeList();
        $scope.dueList=[];
        $scope.screenColor=[];
        $scope.getReceiptDetails=function(studentId,index){
            if($scope.studentFeeList[index].showDetails==false){
                $scope.studentFeeList[index].showDetails=true;
                $scope.screenColor[index]='#ffccff';

            }else {
                $scope.screenColor[index]='#ffffff';
                $scope.studentFeeList[index].showDetails=false;
            }
            var details;
            details={
                academicYearMaster:$scope.academicYearId,
                toDate:$scope.toDate,
                studentId:studentId,
                fromDate:$scope.fromDate,
                gradeIds:$scope.grades
            }
            $http.post($scope.bshimServerURL + '/getReportReceiptDetails',angular.toJson(details)).then(function (response) {
                var data = response.data;
                $scope.dueList[index] = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }
        $scope.setSorting=function(){
            angular.forEach($scope.studentFeeList,function (val,key) {
                val.showDetails=false;
            })
        }
        $scope.dateTypeFilter='CurrentMonth';
        $scope.setDates=function(){
            if($scope.dateTypeFilter=='Today'){
                $scope.fromDate=new Date();
                $scope.toDate=new Date();
            }else if($scope.dateTypeFilter=='Tomorrow'){
                var previousDate = new Date();
                previousDate.setDate(previousDate.getDate() + 1);
                $scope.fromDate=previousDate;
                $scope.toDate=previousDate;
            }else if($scope.dateTypeFilter=='ThisWeek'){
                var monday = new Date();
                monday.setDate(monday.getDate() - monday.getDay() + 1);
                var sunday = new Date();
                sunday.setDate(sunday.getDate() - sunday.getDay() + 7);
                $scope.fromDate=monday;
                $scope.toDate=sunday;
            }else if($scope.dateTypeFilter=='NextWeek'){
                var lastSund = new Date();
                lastSund.setDate(lastSund.getDate() - lastSund.getDay() + 7);
                $scope.fromDate=lastSund.setDate(lastSund.getDate() + 1);
                $scope.toDate=lastSund.setDate(lastSund.getDate() + 6);
            }else if($scope.dateTypeFilter=='CurrentMonth'){
                var date = new Date();
                $scope.fromDate= new Date(date.getFullYear(), date.getMonth(), 1);
                $scope.toDate= new Date(date.getFullYear(), date.getMonth()+1 , 0);
            }else if($scope.dateTypeFilter=='NextMonth') {
                var date = new Date();
                $scope.fromDate = new Date(date.getFullYear(), date.getMonth()+1, 1);
                $scope.toDate = new Date(date.getFullYear(), date.getMonth() + 1+1, 0);
            }
        }
        $scope.setDates();
        $scope.getTotal=function(){
            $scope.totalFeeAmt=0;
            $scope.totalPaidAmt=0;
            $scope.totalDiscountAmt=0;
            $scope.totalDueAmt=0;
            angular.forEach($scope.studentFeeList,function (val,key) {
                if(val.dueAmount>0){
                    $scope.totalFeeAmt+=val.totalFeeAmount;
                    $scope.totalDiscountAmt+=(val.totalFeeAmount-val.totalPayable);
                    $scope.totalPaidAmt+=val.paidAmount;
                    $scope.totalDueAmt+=val.dueAmount;
                }
            })
        }
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
        $scope.orderByField = 'due';
        $scope.reverseSort = false;
        $scope.getStudentFeeDueList=function () {
            var details;
            details={
                academicYearMaster:$scope.academicYearId,
                toDate:$scope.toDate,
                fromDate:$scope.fromDate,
                gradeIds:$scope.grades
            }
            $http.post($scope.bshimServerURL + '/getStudentDueReportList',angular.toJson(details)).then(function (response) {
                var data = response.data;
                $scope.studentFeeList = data;
                $scope.length=0;
                angular.forEach($scope.studentFeeList,function (val,key) {
                    if(val.dueAmount>0){
                        $scope.length=$scope.length+1;
                    }
                })
                $scope.getTotal();
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }
        $scope.getStudentFeeDueList();
    });