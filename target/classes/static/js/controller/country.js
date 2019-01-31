/**
 * Created by azgar.h on 7/1/2017.
 */

app.controller('countryCtrl',
    function ($scope, $timeout, $http, $location, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.CountryNameText = "";
        $scope.StatusText = "";
        $scope.operation = 'Create';
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;

        $scope.inactiveStatus = "Active";
        $scope.removeCountryDetails = function () {
            $scope.countryId="";
            $scope.CountryNameText = "";
            $scope.StatusText = "";
            $scope.listStatus = "";
            $scope.operation = "";
        };
        $scope.companyLogoPath = "";


        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveCountry = function (val) {
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

            $scope.getPaginatedCountryList();
        };

        $scope.backServices=function(){
            window.location.href='#!/services_masters';
        }

        $scope.importPopup = function(){
            $("#import_Country").modal('show');
        }

        $scope.saveCountryImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("countryDetails");
            var countryDetails = new FormData(formElement);
            $http.post($scope.bshimServerURL + '/countryImportSave',countryDetails,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_Country").modal('hide');
                    $scope.getPaginatedCountryList();
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

        $scope.getCountryList = function (type,page) {
            if(angular.isUndefined(type,page)){
                type = "";
            }
            $http.post($scope.bshimServerURL + '/getCountryList?&type=' + type).then(function (response) {
                var data = response.data;
                var i = 0;
                $scope.countryList = data;
                $scope.listStatus = true;
                angular.forEach($scope.countryList, function (value, key) {
                    if (value.countryName.toUpperCase() == type.toUpperCase()) {
                        if (value.status == 'InActive') {
                            i++;
                        }
                    }
                })
                if (i > 0) {
                    Notification.warning({
                        message: 'country Became InActive',
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


        $scope.getPaginatedCountryList = function (page) {
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
            if (angular.isUndefined($scope.searchText)) {
                $scope.searchText = "";
            }
            $http.post($scope.bshimServerURL + '/getPaginatedCountryList?&searchText=' + $scope.searchText + "&type=" + $scope.inactiveStatus , angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                $scope.countryList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                $scope.listStatus = true;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getPaginatedCountryList();
        $scope.editCountry = function (name) {
            $http.post($scope.bshimServerURL + '/editCountry?countryName=' + name).then(function (response) {
                var data = response.data;
                $scope.setupobj = data;
                $scope.countryId = data.countryId;
                $scope.CountryNameText = data.countryName;
                $scope.StatusText = data.status;
                $scope.operation = 'Edit';
                $('#country-title').text("Edit Country");
                $("#add_new_country_modal").modal('show');
                $("#submit").text("Update");
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
        $scope.addNewcountryPopulate = function () {
            $('#country-title').text("Add Country");
            $scope.StatusText = "Active";
            $("#submit").text("Save");
            $("#add_new_country_modal").modal('show');
        };

        $scope.saveCountry = function () {
            if ($scope.CountryNameText === ''||$scope.CountryNameText==null||angular.isUndefined($scope.CountryNameText)) {
                Notification.warning({message: 'Enter Country Name', positionX: 'center', delay: 2000});
            }
            else {
                var saveCountryDetails;
                saveCountryDetails = {
                    countryId: $scope.countryId,
                    countryName: $scope.CountryNameText,
                    status: $scope.StatusText
                };
                $http.post($scope.bshimServerURL + '/saveCountry', angular.toJson(saveCountryDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removeCountryDetails();
                        $scope.getPaginatedCountryList();
                        $("#add_new_country_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.countrySearchText = "";
                        }
                        Notification.success({
                            message: 'Country Created  successfully',
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
            ;
        };
        $scope.deleteCountry = function (data) {
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
                        $http.post($scope.bshimServerURL + '/deleteCountry?countryName=', + data).then(function (response) {
                            var data = response.data;
                            if (data == "") {
                                $scope.getPaginatedCountryList();
                                Notification.error({
                                    message: 'It Is Already In Use Cant be Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $("#add_new_country_modal").modal('hide');
                                if (!angular.isUndefined(data) && data !== null) {
                                    $scope.countrySearchText = "";
                                }
                                if ($scope.StatusText == "InActive") {
                                    $scope.getCountryList("", "InActive");
                                    Notification.success({
                                        message: 'Status has been changed to Active',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                                else {
                                    $scope.getCountryList("", "");
                                    Notification.success({
                                        message: 'Status has been changed to InActive',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
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