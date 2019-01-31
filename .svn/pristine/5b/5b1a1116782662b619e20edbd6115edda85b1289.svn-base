app.controller('feetypemasterController',
    function ($scope, $http, $location, $filter, Notification, ngTableParams, $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/bs";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        // $scope.gradeNameArray = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();


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
        $scope.getFeeTypeMasterList = function (val,checkboxforInActive) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            if (angular.isUndefined(checkboxforInActive)) {
                checkboxforInActive = "false";
            }
            $http.post($scope.bshimServerURL + '/getFeeTypeMasterList2?searchText=' + val+'&checkboxforInActive='+checkboxforInActive).then(function (response) {
                var data = response.data.object;

                $scope.feeTypeMasterList = data;
                $scope.searchText = val;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getFeeTypeMasterList();

        $scope.getAcademicYearList = function (val,checkboxStatus) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + '/getAcdemicYearList2?searchText=' + val+'&checkboxStatus='+"false").then(function (response) {
                var data = response.data.object;
                $scope.academicYearList = data;
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

        $scope.removeFeeType = function () {
            $scope.feeTypeId = "";
            $scope.feetypename = "";
            $scope.feeamount = "";
            $scope.statusText = "";
            $('#checkbox').val("");
            $scope.acdyrId = null;
            $scope.gradeId = null;
            document.getElementById("checkboxforInActive").checked = false;

            // $scope.getFeeTypeMasterList();
        };
        $scope.getFeeTypeMasterList();

        $scope.importPopup = function(){
            $("#import_feetype").modal('show');
        }

        $scope.saveFeeTypeImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("details");
            var details = new FormData(formElement);
            $http.post($scope.bshimServerURL  + '/saveFeeTypeImport',details,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_feetype").modal('hide');
                    $scope.getFeeTypeMasterList();
                    $scope.isDisabled= false;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.isDisabled= false;
                }
            )
        }
        $scope.addFeeType = function () {
            $scope.removeFeeType();
            $scope.acdyrId = null;
            $scope.gradeId = null;
            $scope.operation='create';
            // $scope.acdyrName="";
            // $scope.gradeName="";
            $scope.statusText = "Active";
            $scope.value = false;
            $('#student-title').text("Add FeeType");
            $("#add_fee_type_master").modal('show');

        };

        $scope.saveFeeType = function () {
            if (angular.isUndefined($scope.feetypename) || $scope.feetypename === "") {
                Notification.warning({message: 'FeeTypeName can not be Empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.feeamount) || $scope.feeamount == "") {
                Notification.warning({message: 'fee Amount can not be Empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.acdyrId) || $scope.acdyrId == ''|| $scope.acdyrId == null) {
                Notification.warning({message: 'Academic year can not be Empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.gradeId) || $scope.gradeId == '' ||$scope.gradeId == null) {
                Notification.warning({message: 'Grades can not be Empty', positionX: 'center', delay: 2000});
            } else {
                var saveFeeDetails;
                saveFeeDetails = {
                    feeTypeId: $scope.feeTypeId,
                    feeTypeName: $scope.feetypename,
                    feeAmount: $scope.feeamount,
                    status: $scope.statusText,
                    // acdId:$scope.acdyrId,
                    // gradeId:$scope.gradeId,
                    acdyrName: $scope.acdyrId,
                    gradeName: $scope.gradeId,
                    // gradeNameArray: $scope.gradeId,
                    value: $scope.value
                };
                $http.post($scope.bshimServerURL + "/saveNewFeeMaster", angular.toJson(saveFeeDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: ' FeeType is Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_fee_type_master").modal('hide');
                        if($scope.operation=='Edit'){
                            Notification.success({
                                message: 'FeeMaster is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }else {
                            Notification.success({
                                message: 'FeeMaster is Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }
                        $scope.getFeeTypeMasterList();
                        $scope.removeFeeType();
                        // $scope.reloadPage();
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });

            }
        };

        $scope.editFee = function (data) {
            $scope.feeTypeId = data.feeTypeId;
            $scope.feetypename = data.feeTypeName;
            $scope.feeamount = data.feeAmount;
            $scope.statusText = data.status;
            if (data.value == "true") {
                $scope.value = true;
            }
            else {
                $scope.value = false;
            }
            $scope.acdyrId = data.acdyrmaster.acdyrName;
            $scope.gradeId = data.gradeMaster.gradeName;
            // $scope.todate=data.toDate;
            $scope.operation = 'Edit';
            $('#student-title').text("Edit Fee");
            $("#add_fee_type_master").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});

        };
        $scope.removeFeeType();

        $scope.feeconfigurationList = function () {
            $window.location.href = '/home#!/configuration';
        };

        $scope.reloadPage = function () {
            $window.location.reload();
        };

        $scope.deleteFee = function (data) {
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
                        var deleteDetails = {
                            feeTypeName: data.feeTypeName,
                            feeAmount: data.feeAmount,
                            status: data.status,
                            feeTypeId: data.feeTypeId,
                            acdyrmaster: data.acdyrmaster,
                            gradeMaster: data.gradeMaster
                        };
                        $http.post($scope.bshimServerURL + "/deleteFeeType", angular.toJson(deleteDetails, "Create")).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.getFeeTypeMasterList();
                            if(data==true){
                                Notification.success({
                                    message: 'Successfully Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }else {
                                Notification.warning({
                                    message: 'Cannot delete Already in Use',
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