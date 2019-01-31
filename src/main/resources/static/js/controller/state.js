app.controller('stateCtrl',
    function ($scope, $http, $location, $filter, $timeout, Notification) {// body...
        $scope.bshimServerURL = "/bs";
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.inactiveStatus = "Active";
        /***Method which sets all values to null**/
        $scope.removeStateDetails = function () {
            $scope.stateNameText = "";
            $scope.countryId = null;
            $scope.StatusText = "";
        };
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveState = function () {

            if ($scope.clicked == false) {
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";
                var page = "Page";
            }
            else {
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
                var page = "";
            }
            $scope.clicked = !$scope.clicked;
            $scope.getStateList($scope.inactiveStatus,'firstPage');


        };

        $scope.backServices=function(){
            window.location.href='#!/services_masters';
        }

        $scope.importPopup = function(){
            $("#import_state").modal('show');
        }

        $scope.saveStateImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("stateDetails");
            var stateDetails = new FormData(formElement);
            $http.post($scope.bshimServerURL + '/stateImportSave',stateDetails,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity
                }).then(function (response) {
                    $("#import_state").modal('hide');
                    $scope.getStateList();
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


        /****Method for getting the State List*******/
        $scope.getStateList = function (type, page) {
            if (angular.isUndefined(type)) {
                type = "";
            }
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'nextPage':
                    $scope.isNext = true;
                    $scope.isPrev = false;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.pageNo = $scope.pageNo + 1;
                    break;
                case 'prevPage':
                    $scope.isPrev = true;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.pageNo = $scope.pageNo - 1;
                    break;
                default:
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
            }
            var paginationDetails;
            paginationDetails = {
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.prevPage,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if (angular.isUndefined($scope.stateSearchText)) {
                $scope.stateSearchText = "";
            }
            $http.post($scope.bshimServerURL + '/getStateList?type=' + $scope.inactiveStatus + '&searchText=' + $scope.stateSearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.stateList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                /**/
                var i = 0;
                $scope.listStatus = true;
                angular.forEach($scope.stateList, function (value, key) {
                    if (value.stateName.toUpperCase() == type.toUpperCase()) {
                        if (value.status == 'InActive') {
                            i++;
                        }
                    }
                })

                if (i > 0) {
                    Notification.error({
                        message: 'State is InActive',
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
            })
        };

        /*****Method for getting the country List*****/
        $scope.getCountryList = function () {
            $http.post($scope.bshimServerURL + '/getCountryList?type='+"Active").then(function (response) {
                var data = response.data;
                $scope.country = angular.copy(data);
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };


        /***Method for add POPUP*******/
        $scope.addNewState = function () {
            $scope.StatusText = "Active";
            $("#submit").text("Save");
            $('#state-title').text("Add State");
            $("#add_new_State_modal").modal('show');
            // $scope.getStateList();

        };

        /******edit Popup with values for state*******/
        $scope.editState = function (name) {
            $http.post($scope.bshimServerURL + '/editState?stateName=' + name).then(function (response) {
                var data = response.data;
                $scope.countryId = data.country;
                $scope.stateObj = data;
                $scope.id = data.id;
                $scope.stateNameText = data.stateName;
                $scope.StatusText = data.status;
                $('#state-title').text("Edit State");
                $("#submit").text("Update");
                $("#add_new_State_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        /*****Method for saving the state Details*******/
        $scope.saveState = function () {
            if (angular.isUndefined($scope.countryId) || $scope.countryId == ''||$scope.countryId==null) {
                Notification.warning({message: ' Please select Country', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.stateNameText) || $scope.stateNameText == ''||$scope.stateNameText==null) {
                Notification.warning({message: ' Please Enter the StateName', positionX: 'center', delay: 2000});
            }

            else {
                var saveStateDetails;
                saveStateDetails = {
                    id: $scope.id,
                    status: $scope.StatusText,
                    stateName: $scope.stateNameText,
                    country: $scope.countryId
                };
                $http.post($scope.bshimServerURL + '/saveState', angular.toJson(saveStateDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removeStateDetails();
                        $scope.getStateList();
                        $("#add_new_State_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.stateNameText = "";
                        }
                        Notification.success({
                            message: 'State  Created  successfully',
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
        };


        $scope.deleteState = function (data) {
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
                        $scope.id = data.id;
                        $http.post($scope.bshimServerURL + '/deleteState?id=' + $scope.id).then(function (response, status, headers, config) {
                            var data = response.data;

                            if ($scope.StatusText == "InActive") {
                                $scope.getStateList("", "InActive");
                                Notification.success({
                                    message: 'Status has been changed to Active',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $scope.getStateList("", "");
                                Notification.success({
                                    message: 'Status has been changed to InActive',
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

        $scope.getStateList();
        $scope.getCountryList();

    });
