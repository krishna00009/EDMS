app.controller('feeCollectedReportController',
    function ($scope, $http, $location, $filter, Notification, ngTableParams, $timeout, $window, $rootScope) {
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
            $scope.totalCashAmt=0;
            $scope.totalCardAmt=0;
            $scope.totalBankAmt=0;
            $scope.totalOnlineAmt=0;
            $scope.totalAmt=0;
            angular.forEach($scope.list,function (val,key) {
                $scope.totalCardAmt+=val.cardAmt;
                $scope.totalCashAmt+=val.cashAmt;
                $scope.totalOnlineAmt+=val.onlineAmt;
                $scope.totalBankAmt+=val.bankAmt;
                $scope.totalAmt+=val.totalAmt;
            })
        }
        $scope.getGradeList();
        $scope.getStudentFeeCollectedList=function () {
            var details;
            details={
                academicYearMaster:$scope.academicYearId,
                toDate:$scope.toDate,
                fromDate:$scope.fromDate,
                gradeIds:$scope.grades
            }
            $http.post($scope.bshimServerURL + '/getReportCollected',angular.toJson(details)).then(function (response) {
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
        $scope.getStudentFeeCollectedList();
    });