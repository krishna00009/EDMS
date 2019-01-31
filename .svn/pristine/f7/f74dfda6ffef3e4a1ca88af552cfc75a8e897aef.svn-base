app.controller('grademasterController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/bs";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();
        $scope.removeGradeMaster = function () {
            $scope.gradeId = "";
            $scope.gradename = "";
            $scope.gradedesc = "";
            $scope.StatusText = "";
            document.getElementById("checkbox").checked = false;
        };
        $scope.importPopup = function(){
            $("#import_gradeMaster").modal('show');
        }

        $scope.saveGradeMasterImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("details");
            var details = new FormData(formElement);
            $http.post($scope.bshimServerURL  + '/saveGradeMasterImport',details,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_gradeMaster").modal('hide');
                    $scope.getGradeList();
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
        $scope.EditGrade = function (data) {
            $scope.gradename = data.gradeName;
            $scope.gradedesc = data.gradeDescription;
            $scope.statusText = data.gradeStatus;
            $scope.gradeId = data.gradeId;
            $scope.operation = 'Edit';
            $('#student-title').text("Edit GradeMaster");
            $("#add_grade_master").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

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

        // $scope.getGradeListBasedOnInActive = function () {
        //     $(".loader").css("display", "block");
        //     $http.post($scope.bshimServerURL + '/getGradeListBasedOnInactive').then(function (response) {
        //         var data = response.data.object;
        //         $scope.gradeList = data;
        //         if(data==""){
        //             Notification.warning({
        //                 message: 'No Records are Available with InActive Status.',
        //                 positionX: 'center',
        //                 delay: 2000
        //             });
        //             $scope.getGradeList();
        //         }
        //
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        //
        // };


        $scope.feeconfigurationList=function () {
            $window.location.href = '/home#!/configuration';
        };

        $scope.addgrademaster = function () {
            $scope.statusText = "Active";
            $scope.operation='create';
            $scope.removeGradeMaster();
            $('#student-title').text("Add Grade");
            $("#add_grade_master").modal('show');

        };
        $scope.saveGrateMaster = function () {
            if (angular.isUndefined($scope.gradename) || $scope.gradename == '') {
                Notification.warning({message: 'Grade Name can not be Empty', positionX: 'center', delay: 2000});

            } else {
                var saveGradeDetails;
                saveGradeDetails = {
                    gradeId: $scope.gradeId,
                    gradeName: $scope.gradename,
                    gradeDescription: $scope.gradedesc,
                    gradeStatus: $scope.statusText
                };
                $http.post($scope.bshimServerURL + "/saveNewGradeMaster", angular.toJson(saveGradeDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_grade_master").modal('hide');
                        if($scope.operation=='Edit'){
                            Notification.success({
                                message: 'GradeMaster is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }else {
                            Notification.success({
                                message: 'GradeMaster is Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }
                        $scope.removeGradeMaster();
                        $scope.getGradeList();
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
        $scope.DeleteGrade = function (data) {
            // if (data.gradeStatus == "InActive" && data.gradeStatus != "Active") {
            //     Notification.warning({
            //         message: 'Cannot Delete Aleady in use',
            //         positionX: 'center',
            //         delay: 2000
            //     });
            // }
            // else {
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
                                gradeName: data.gradeName,
                                gradeDescription: data.gradeDescription,
                                gradeStatus: data.gradeStatus,
                                gradeId: data.gradeId
                            };
                            $http.post($scope.bshimServerURL + "/deleteGradeMaster", angular.toJson(deleteDetails, "Create")).then(function (response, status, headers, config) {
                                var data = response.data;
                                $scope.getGradeList();
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
                                Notification.warning({
                                    message: 'Cannot delete Already in Use',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            });
                        }
                    }
                });
            };
    });
