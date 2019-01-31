app.controller('reportMailSchedulerCtrl',
    function ($scope, $rootScope, $http, Notification) {
        $scope.schedule = {};
        // fro getting List
        $scope.schedulerReportsList = function () {
            $http.post('/bs'+'/schedulerList').
            then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.schedulerList = data;
            });
        };
        $scope.disable=false;
        $scope.schedulerReportsList();
        $scope.saveSchedule = function () {
            var details;
            details={
                reportType:$scope.reportType,
                subject:$scope.emailSub,
                body:$scope.emailBody,
                fromDate:$scope.fromDate,
                toDate:$scope.toDate,
                reportName:$scope.reportModule,
                toEmail:$scope.toEmail
            }
            var mailId=true;
            var mail=$scope.toEmail;
            var mailRegex=/([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)\S+/;
            if ($scope.toEmail === "" || angular.isUndefined($scope.toEmail)) {
                Notification.warning({message: 'Email can not be empty', positionX: 'center', delay: 2000});
                mailId=false;
            }
            if ($scope.toEmail!=="") {
                if (mail.match(mailRegex)) {
                    console.log(mail);
                    mailId=true;
                }
                else {
                    Notification.error({message: 'Please Enter Valid Email ID', positionX: 'center', delay: 2000});
                    mailId=false;
                }
            }
            if(mailId==true){
                if($scope.reportModule==""||angular.isUndefined($scope.reportModule)){
                    Notification.warning({message: 'Please Select Report Module', positionX: 'center', delay: 2000});
                }else if($scope.reportType==""||angular.isUndefined($scope.reportType)){
                    Notification.warning({message: 'Please Select Report Type', positionX: 'center', delay: 2000});
                } else if($scope.toDate==""||angular.isUndefined($scope.toDate)||$scope.fromDate==""||angular.isUndefined($scope.fromDate)){
                    Notification.warning({message: 'Please Select From Date and ToDate', positionX: 'center', delay: 2000});
                }else if($scope.emailBody==""||angular.isUndefined($scope.emailBody)){
                    Notification.warning({message: 'Email Body cant be Empty', positionX: 'center', delay: 2000});
                }else if($scope.emailSub==""||angular.isUndefined($scope.emailSub)){
                    Notification.warning({message: 'Email Sub cant be Empty', positionX: 'center', delay: 2000});
                } else if(new Date($scope.toDate)<new Date($scope.fromDate)){
                    Notification.warning({message: 'FromDate cant be Less than ToDate', positionX: 'center', delay: 2000});
                }
                else {
                    $scope.disable=true;
                    $http.post('/bs'+'/saveScheduler', angular.toJson(details)).then(function (response) {
                        Notification.success({message: 'Mail Sent Successfully', positionX: 'center', delay: 2000});
                        $scope.removeDetails();
                    },function (errorResponse) {
                        Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});
                    })
                }
            }
        }
        $scope.removeDetails=function(){
            $scope.reportType="";
            $scope.emailSub="";
            $scope.emailBody="";
            $scope.fromDate="";
            $scope.toDate="";
            $scope.reportModule="";
            $scope.toEmail="";
            $scope.disable=false;
        }
        $scope.addScheduler=function () {
            $("#add_new_scheduler").modal('show');
        }
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
        $scope.deleteMailScheduler = function(mail) {
            bootbox.confirm({
                title: "Alert",
                message: "Do you want to Continue ?",
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
                        $http.post('/deleteMailScheduler?searchSchedulerText=' + mail.id).then(function (response) {
                            var data = response.data;
                            if (data == "") {
                                $scope.schedulerReportsList();
                                Notification.success({
                                    message: 'Report Mail Scheduler Record is Deleted Successfully',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                        }, function (error) {
                            Notification.error({
                                message: 'Something went wrong, please try again',
                                positionX: 'center',
                                delay: 2000
                            });
                        });
                    }
                }
            });
        };
    });