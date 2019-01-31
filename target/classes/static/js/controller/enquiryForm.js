
app.controller('enquiryFormController',
    function ($scope, $rootScope, $http, $location, $filter, Notification) {
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.bshimServerURL = "/bs";


        $scope.format = 'dd/MM/yyyy';

        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };
        $scope.popup1 = {
            opened: false
        };

        $scope.open2 = function () {
            $scope.popup2.opened = true;
        };
        $scope.popup2 = {
            opened: false
        };

        $scope.open3 = function () {
            $scope.popup3.opened = true;
        };
        $scope.popup3 = {
            opened: false
        };

        $scope.getStudentByName=function(type) {
            $http.post($scope.hospitalServerURL + "/getStudentByName?name=" + type).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.phoneNumber=data.phoneNumber;
                $scope.studentFullName=data.studentFullName;
                $scope.fatherFullName=data.fatherFullName;
                $scope.fatherOccupation=data.fatherOccupation;
                $scope.fatherIncome=data.fatherIncome;
                $scope.motherFullName=data.motherFullName;
                $scope.motherOccupation=data.motherOccupation;
                $scope.motherIncome=data.motherIncome;
                $scope.gender=data.gender;
                $scope.dob=data.dateOfBirth;
                $scope.age=data.age;
                $scope.fatherMobile=data.fatherMobile;
                $scope.motherMobile=data.motherMobile;
                $scope.fatherEmail=data.fatherEmailId;
                $scope.motherEmail=data.motherEmailId;
                $scope.area=data.residentialAddress;
                $scope.area1=data.area;
                $scope.grade1=data.nonBsimGrade;
                $scope.board=data.nonBsimboard;
                $scope.media=data.media;
                $scope.board=data.nonBsimboard;
                $scope.siblings=data.siblings;
                $scope.siblingsFullName=data.siblingsFullName;
                $scope.siblingsGender=data.siblingsGender;
                $scope.siblingsBoard=data.siblingsBoard;
                $scope.siblingsGrade=data.siblingsGrade;
                $scope.siblingsDate=data.siblingsdate;
                $scope.currentSchool=data.currentSchool;
                $scope.proremarks=data.proRemarks;
                $scope.managementremarks=data.managementRemarks;
                $scope.attended=data.attended;
                $scope.dateofc=data.attended;

                $scope.countryName=data.countryName;
                $scope.stateName=data.stateName;
                $scope.city=data.date;
                $scope.enquiry=data.enquiryNo;

            });
        };



        $scope.saveStudent = function () {
            $http.post($scope.bshimServerURL + '/getFormSetupValue').then(function (response) {
                var data = response.data.message;
                $scope.enquiry = data;

                if (angular.isUndefined($scope.studentFullName) || $scope.studentFullName == ''||$scope.studentFullName==null) {
                    Notification.error({message: ' studentName cannot be empty', positionX: 'center', delay: 2000});
                }
                // else if (angular.isUndefined($scope.student) || $scope.student == ''||$scope.student==null) {
                //     Notification.warning({message: ' Please select Student', positionX: 'center', delay: 2000});
                // }
                else if (angular.isUndefined($scope.fatherFullName) || $scope.fatherFullName == ''||$scope.fatherFullName==null) {
                    Notification.error({message: ' fatherName cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.motherFullName) || $scope.motherFullName == ''||$scope.motherFullName==null) {
                    Notification.error({message: 'motherName cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.fatherMobile) || $scope.fatherMobile == ''||$scope.fatherMobile==null) {
                    Notification.error({message: ' fatherMobile cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.motherMobile) || $scope.motherMobile == ''||$scope.motherMobile==null) {
                    Notification.error({message: ' motherMobile cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.area) || $scope.area == ''||$scope.area==null) {
                    Notification.error({message: 'residentialAddress cannot be empty', positionX: 'center', delay: 2000});
                }

                else if (angular.isUndefined($scope.siblingsFullName) || $scope.siblingsFullName == ''||$scope.siblingsFullName==null) {
                    Notification.error({message: 'siblings name cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.currentSchool) || $scope.currentSchool == ''||$scope.currentSchool==null) {
                    Notification.error({message: 'current school cannot be empty', positionX: 'center', delay: 2000});
                }
else {
                    var savestudentDetails;
                    savestudentDetails = {
                        studentFullName: $scope.studentFullName,
                        fatherFullName: $scope.fatherFullName,
                        fatherOccupation: $scope.fatherOccupation,
                        fatherIncome: $scope.fatherIncome,
                        motherFullName: $scope.motherFullName,
                        motherOccupation: $scope.motherOccupation,
                        motherIncome: $scope.motherIncome,
                        // Income:$scope.mincome,
                        gender: $scope.gender,
                        dateOfBirth: $scope.dob,
                        d: $scope.dob,
                        age: $scope.age,
                        fatherMobile: $scope.fatherMobile,
                        motherMobile: $scope.motherMobile,
                        fatherEmailId: $scope.fatherEmail,
                        motherEmailId: $scope.motherEmail,
                        residentialAddress: $scope.area,
                        area: $scope.area1,
                        grade: $scope.grade,
                        pincode: $scope.pincode,
                        schoolName: $scope.schoolName,
                        nonBsimGrade: $scope.grade1,
                        nonBsimboard: $scope.board,
                        media: $scope.media,
                        enquiryNo: $scope.enquiry,
                        date: $scope.dateofc,

                        // $scope.residentialarea,
                        // $scope.area,
                        country: $scope.countryName,
                        city: $scope.city,
                        state: $scope.stateName,
                        board: $scope.board,
                        siblings: $scope.siblings,
                        siblingsFullName: $scope.siblingsFullName,
                        siblingsGender: $scope.siblingsGender,
                        siblingsBoard: $scope.siblingsBoard,
                        siblingsGrade: $scope.siblingsGrade,
                        siblingsdate: $scope.siblingsDate,
                        currentSchool: $scope.currentSchool,
                        proRemarks: $scope.proremarks,
                        managementRemarks: $scope.managementremarks,
                        attended: $scope.attended,
                    }

                    $http.post($scope.bshimServerURL + "/saveNewEnquiry", angular.toJson(savestudentDetails)).then(function (response) {
                        var data = response.data;
                        if (data == "") {
                            Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                        } else {
                            // window.location.reload();
                            $scope.getEnquiry();
                            $("#add_enquiry_master").modal('hide');

                            // data.phoneNumber = $scope.phoneNumber;
                            // $scope.sendSMS(data, "AppointmentNumber");
                            if ($scope.operation == 'Edit') {
                                Notification.success({
                                    message: 'EnquiryForm is Updated successfully',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            } else {
                                Notification.success({
                                    message: 'EnquiryForm is Created  successfully',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                        }
                        $scope.appointmentDisabled = false;
                    }, function (error) {
                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });

                    });
                }
            });
            // }
        };
        $scope.saveOldStudent = function () {
            // $http.post($scope.bshimServerURL + '/getFormSetupValue').then(function (response) {
            //     var data = response.data.message;
            //     $scope.enquiry = data;

                if (angular.isUndefined($scope.studentFullName) || $scope.studentFullName == ''||$scope.studentFullName==null) {
                    Notification.error({message: ' studentName cannot be empty', positionX: 'center', delay: 2000});
                }
                // else if (angular.isUndefined($scope.student) || $scope.student == ''||$scope.student==null) {
                //     Notification.warning({message: ' Please select Student', positionX: 'center', delay: 2000});
                // }
                else if (angular.isUndefined($scope.fatherFullName) || $scope.fatherFullName == ''||$scope.fatherFullName==null) {
                    Notification.error({message: ' fatherName cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.motherFullName) || $scope.motherFullName == ''||$scope.motherFullName==null) {
                    Notification.error({message: 'motherName cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.fatherMobile) || $scope.fatherMobile == ''||$scope.fatherMobile==null) {
                    Notification.error({message: ' fatherMobile cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.motherMobile) || $scope.motherMobile == ''||$scope.motherMobile==null) {
                    Notification.error({message: ' motherMobile cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.area) || $scope.area == ''||$scope.area==null) {
                    Notification.error({message: 'residentialAddress cannot be empty', positionX: 'center', delay: 2000});
                }

                else if (angular.isUndefined($scope.siblingsFullName) || $scope.siblingsFullName == ''||$scope.siblingsFullName==null) {
                    Notification.error({message: 'siblings name cannot be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.currentSchool) || $scope.currentSchool == ''||$scope.currentSchool==null) {
                    Notification.error({message: 'current school cannot be empty', positionX: 'center', delay: 2000});
                }
                else {
                    var savestudentDetails;
                    savestudentDetails = {
                        studentFullName: $scope.studentFullName,
                        fatherFullName: $scope.fatherFullName,
                        fatherOccupation: $scope.fatherOccupation,
                        fatherIncome: $scope.fatherIncome,
                        motherFullName: $scope.motherFullName,
                        motherOccupation: $scope.motherOccupation,
                        motherIncome: $scope.motherIncome,
                        // Income:$scope.mincome,
                        gender: $scope.gender,
                        dateOfBirth: $scope.dob,
                        d: $scope.dob,
                        age: $scope.age,
                        fatherMobile: $scope.fatherMobile,
                        motherMobile: $scope.motherMobile,
                        fatherEmailId: $scope.fatherEmail,
                        motherEmailId: $scope.motherEmail,
                        residentialAddress: $scope.area,
                        area: $scope.area1,
                        grade: $scope.grade,
                        pincode: $scope.pincode,
                        schoolName: $scope.schoolName,
                        nonBsimGrade: $scope.grade1,
                        nonBsimboard: $scope.board,
                        media: $scope.media,
                        oldEnquiryNo:$scope.oldenquiry,
                        date: $scope.dateofc,

                        // $scope.residentialarea,
                        // $scope.area,
                        country: $scope.countryName,
                        city: $scope.city,
                        state: $scope.stateName,
                        board: $scope.board,
                        siblings: $scope.siblings,
                        siblingsFullName: $scope.siblingsFullName,
                        siblingsGender: $scope.siblingsGender,
                        siblingsBoard: $scope.siblingsBoard,
                        siblingsGrade: $scope.siblingsGrade,
                        siblingsdate: $scope.siblingsDate,
                        currentSchool: $scope.currentSchool,
                        proRemarks: $scope.proremarks,
                        managementRemarks: $scope.managementremarks,
                        attended: $scope.attended,
                    }

                    $http.post($scope.bshimServerURL + "/saveoldEnquiry", angular.toJson(savestudentDetails)).then(function (response) {
                        var data = response.data;
                        if (data == "") {
                            Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                        } else {
                            // window.location.reload();
                            $scope.getEnquiry();
                            $("#add_enquiry_master").modal('hide');

                            // data.phoneNumber = $scope.phoneNumber;
                            // $scope.sendSMS(data, "AppointmentNumber");
                            if ($scope.operation == 'Edit') {
                                Notification.success({
                                    message: 'EnquiryForm is Updated successfully',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            } else {
                                Notification.success({
                                    message: 'EnquiryForm is Created  successfully',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                        }
                        $scope.appointmentDisabled = false;
                    }, function (error) {
                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });

                    });
                }
            // });
            // }
        };

        $scope.calculateAge = function () {
                var ageDif = Date.now() - $scope.dob.getTime();
                var ageDate = new Date(ageDif); // miliseconds from epoch
                $scope.age= Math.abs(ageDate.getUTCFullYear() - 1970);
            }



        $scope.getCountryList = function () {
            $http.post($scope.bshimServerURL + "/getCountryList").then(function (response) {
                var data = response.data;
                $scope.countryList= data;
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
        };
        $scope.getCountryList();
        $scope.getstateList = function () {
            $http.post($scope.bshimServerURL + "/stateList").then(function (response) {
                var data = response.data;
                $scope.stateList= data;
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
        };

        $scope.getstateList();


        $scope.getCityList = function () {
            $http.post($scope.bshimServerURL + "/CityList").then(function (response) {
                var data = response.data;
                $scope.cityList= data;
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
        };

        $scope.getCityList();



        $scope.feeconfigurationList=function () {
            $window.location.href = '/home#!/configuration';
        };



        // $scope.addEnquiry = function () {
        //     $scope.getCountryList();
        //     $("#title").text("Add");
        //     $("#add_enquiry_master").modal('show');
        // };




        $scope.addOldStudent = function () {

            $scope.studentFullName = "";
            $scope.fatherFullName = "";
            $scope.fatherOccupation = "";
            $scope.fatherIncome = "";
            $scope.motherFullName = "";
            $scope.motherIncome = "";
            $scope.gender = "";
            $scope.dob = "";
            $scope.age = "";
            $scope.fatherMobile = "";
            $scope.motherMobile = "";
            $scope.fatherEmail = "";
            $scope.motherEmail = "";
            $scope.area = "";
            $scope.area1 = "";
            $scope.board = "";
            $scope.media = "";
            // $scope.enquiry = "";
            $scope.dateofc = "";
            $scope.board = "";
            $scope.siblings = "";
            $scope.siblingsFullName = "";
            $scope.siblingsGender = "";
            $scope.siblingsBoard = "";
            $scope.siblingsGrade = "";
            $scope.currentSchool = "";
            $scope.proremarks = "";
            $scope.managementremarks = "";
            $scope.attended = "";
            $scope.countryName = "India";
            $scope.stateName = "Karnataka";
            $scope.city = "Bengaluru";

                $("#add_new_student").modal('show');
        };


        $scope.getStudenttList = function (searchText) {
            if (angular.isUndefined(searchText)) {
                $scope.searchText = "";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + '/getStudentList').then(function (response) {
                var data = response.data.object;
                $scope.userList = data;
                console.log($scope.userList);
                // $scope.searchText = val;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };

        $scope.addEnquiry = function () {
            $scope.student="new";
            $scope.studentFullName = "";
            $scope.fatherFullName = "";
            $scope.fatherOccupation = "";
            $scope.fatherIncome = "";
            $scope.motherFullName = "";
            $scope.motherIncome = "";
            $scope.gender = "";
            $scope.dob = "";
            $scope.age = "";
            $scope.fatherMobile = "";
            $scope.motherMobile = "";
            $scope.fatherEmail = "";
            $scope.motherEmail = "";
            $scope.area = "";
            $scope.area1 = "";
            $scope.board = "";
            $scope.media = "";
            // $scope.enquiry = "";
            $scope.dateofc = "";
            $scope.board = "";
            $scope.siblings = "";
            $scope.siblingsFullName = "";
            $scope.siblingsGender = "";
            $scope.siblingsBoard = "";
            $scope.siblingsGrade = "";
            $scope.currentSchool = "";
            $scope.proremarks = "";
            $scope.managementremarks = "";
            $scope.attended = "";
            $scope.countryName = "India";
            $scope.stateName = "Karnataka";
            $scope.city = "Bengaluru";

                $("#add_enquiry_master").modal('show');
        };
        $scope.getEnquiry = function () {
            $http.post($scope.bshimServerURL + "/getEnquiry").then(function (response) {
                var data = response.data.object;
                $scope.enquiryList= data;
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
        };

        $scope.getEnquiry();

        $scope.editEnquiry = function (data) {
            $scope.studentFullName = data.studentFullName;
            $scope.fatherFullName = data.fatherFullName;
            $scope.fatherOccupation = data.fatherOccupation;
            $scope.fatherIncome = data.fatherIncome;
            $scope.motherFullName = data.motherFullName;
            $scope.motherOccupation = data.motherOccupation;
            $scope.motherIncome = data.motherIncome;
            $scope.gender = data.gender;
            $scope.dob = new Date(data.dateOfBirth);
            $scope.age = data.age;
            $scope.fatherMobile = data.fatherMobile;
            $scope.motherMobile = data.motherMobile;
            $scope.fatherEmail = data.fatherEmailId;
            $scope.motherEmail = data.motherEmailId;
            $scope.area = data.residentialAddress;
            $scope.area1 = data.area;
            $scope.grade = data.grade;
            $scope.pinCode = data.pinCode;
            $scope.schoolName = data.schoolName;
            $scope.grade1 = data.nonBsimGrade;
            $scope.board = data.nonBsimboard;
            $scope.media = data.media;
            $scope.dateofc = new Date(data.date);
            $scope.countryName = data.country;
            $scope.stateName = data.state;
            $scope.city = data.city;
            $scope.board = data.board;
            $scope.siblings = data.siblings;
            $scope.siblingsFullName = data.siblingsFullName;
            $scope.siblingsGender = data.siblingsGender;
            $scope.siblingsBoard = data.siblingsBoard;
            $scope.siblingsGrade = data.siblingsGrade;
            $scope.siblingsDate = new Date(data.siblingsdate);
            $scope.currentSchool = data.currentSchool;
            $scope.proremarks = data.proRemarks;
            $scope.managementremarks = data.managementRemarks;
            $scope.attended = data.attended;
                $('#enquiry-title').text("Edit StudentDetails");
                $("#submit").text("Update");
                $("#add_enquiry_master").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            // });
        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };
    });
