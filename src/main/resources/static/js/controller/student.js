app.controller('studentController',
    function ($scope, $rootScope, $http, $location, $window, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.totalPaid = 0;
        $scope.today = new Date();
        $scope.selectedFeeList = [];
        $scope.next_wizardEdit = function (acId, grdID) {
            if (acId == null || grdID == null || acId == "" || grdID == "") {
                Notification.error({
                    message: 'Select Grade and Academic Year',
                    positionX: 'center',
                    delay: 2000
                });
            } else {
                $("#sub_step12").removeClass("in active");
                $("#sub_step22").addClass("tab-pane fade in active");
                $("#next").hide();
                $("#back").show();
            }

        }
        $scope.back_wizardEdit = function () {
            $("#sub_step22").removeClass("in active");
            $("#sub_step12").addClass("tab-pane fade in active");
            $("#next").show();
            $("#back").hide();
        }
        $scope.today = new Date();
        $scope.today1 = function () {
            $scope.dateOfBirth = new Date();
            $scope.dobAdmission = new Date();
            $scope.joinDate = new Date();
        };
        $scope.today1();
        $scope.format = 'dd/MM/yyyy';
        $scope.openDate1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.popup1 = {
            opened: false
        };

        $scope.openDate2 = function () {
            $scope.popup2.opened = true;
        };

        $scope.popup2 = {
            opened: false
        };
        $scope.openDate3 = function () {
            $scope.popup3.opened = true;
        };
        $scope.popup3 = {
            opened: false
        };
        var searchText = $scope.SearchText;
        var grade = $scope.gradeNameText;
        var student = $scope.studentText;
        $scope.getStudentList = function (searchText, grade, student,checkboxStatusForStudent) {

            if (angular.isUndefined(searchText)) {
                searchText = "";
            }
            else if (searchText == null) {
                searchText = "";
            }
            if (grade=="undefined") {
                grade = "";
            }
            else if (grade == null) {
                grade = "";
            }
            if (student=="undefined") {
                student = "";
            }
            else if (student == null) {
                student = "";
            }
            if (angular.isUndefined(checkboxStatusForStudent)) {
                checkboxStatusForStudent = "false";
            }

            $http.post($scope.bshimServerURL + '/getStudentList?searchText=' + searchText + "&grade=" + grade + "&student=" + student+ "&checkboxStatusForStudent=" + checkboxStatusForStudent).then(function (response) {
                var data = response.data.object;
                console.log(data);
                $scope.studentList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getStudentList();

        $scope.removeStudentDetails = function () {
            $scope.admFrmNo = "";
            $scope.studName = "";
            $scope.dateOfBirth = "";
            $scope.acdYearId = null;
            $scope.gradeId = null;
            $scope.dobAdmission = "";
            $scope.joinDate = "";
            $scope.fatherName = "";
            $scope.fatherEmailId = "";
            $scope.fatherMobile = "";
            $scope.fatherOccupation = "";
            $scope.motherName = "";
            $scope.motherMobile = "";
            $scope.motherOccupation = "";
            $scope.contactNo = "";
            $scope.bloodGroup = "";
            $scope.motherEmailId = "";
            $scope.localGuardianName = "";
            $scope.parentsAnnualIncome = "";
            $scope.presentAddress = "";
            $scope.permanentAddress = "";
            $scope.religion = "";
            $scope.studentPhysicalCondition = "";
            $scope.aadhaarNo ="";
            $scope.StatusText = "";
            $scope.guardianNo="";
            document.getElementById("checkboxForStudent").checked = false;
            // $scope.fileToUpload=data.documentUpload,
        };
        $scope.getStudentList();
        $scope.importPopup = function(){
            $("#import_student").modal('show');
        }


        $scope.saveStudentImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("studentDetails");
            var studentDetails = new FormData(formElement);
            $http.post($scope.bshimServerURL + '/studentImportSave',studentDetails,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_student").modal('hide');
                    $scope.getStudentList();
                    $scope.isDisabled= false;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        }

        $scope.saveFeeImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("studentFeeDetails");
            var studentDetails = new FormData(formElement);
            $http.post($scope.bshimServerURL + '/feeImportSave',studentDetails,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_student").modal('hide');
                    $scope.getStudentList();
                    $scope.isDisabled= false;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        }

        $scope.addNewStudent = function () {
            $scope.admFrmNo = "";
            $scope.studName = "";
            $scope.dateOfBirth = "";
            $scope.acdYear = "";
            $scope.value = "";
            $scope.grade = "";
            $scope.gradeId = null;
            $scope.stuId = null;
            $scope.studentId = null;
            $scope.acdYearId = null;
            $scope.dobAdmission = "";
            $scope.totalPaid = 0;
            $scope.joinDate = "";
            $scope.fatherName = "";
            $scope.fatherEmailId = "";
            $scope.motherEmailId = "";
            $scope.fatherContact = "";
            $scope.fatherOccupation = "";
            $scope.localGuardianName = "";
            $scope.parentsAnnualIncome = "";
            $scope.motherName = "";
            $scope.motherMobile = "";
            $scope.motherOccupation = "";
            $scope.presentAddress = "";
            $scope.religion = "";
            $scope.permanentAddress = "";
            $scope.studentPhysicalCondition = "";
            $scope.fatherMobile = "";
            $scope.contactNo = "";
            $scope.bloodGroup = "";
            $scope.aadhaarNo ="";
            // $scope.statusText = "";
            $scope.statusText=true;
            $scope.guardianNo="";
            $scope.operation = 'Create';
            $scope.selectedFeeList = [];
            $scope.getAcademicYearList();
            $scope.getGradeList();
            $scope.back_wizardEdit();
            $scope.today1();
            // $scope.getFeeTypeMasterList();
            // installmentList(1,feeTypeMasterList.get(0),0);
            $('#student-title').text("ADD STUDENT");
            $("#add_new_Student_modal").modal('show');
        };

        var grade = $scope.gradeNameText;
        $scope.getStudentListBasedOnGrade = function (grade) {
            $scope.studentText=null;
            if (grade != null) {
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
        $scope.getGradeList();
        $scope.studentFeeDetailsList = function (student) {
            $(".loader").css("display", "block");
            if (angular.isUndefined(student.studentId)) {
                student.studentId = "";
            }
            $http.post($scope.bshimServerURL + "/getStudentDetails?studentId=" + student.studentId+'&type='+"Student").then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.totalPaid = data.totalPaid;
                $scope.FeeDetailsList = data.studentFeeDetailsPojoList;
                $scope.editStudent(student, $scope.FeeDetailsList)
            })
        };
        $scope.ViewStudentFeeDetailsList = function (student) {
            $(".loader").css("display", "block");
            if (angular.isUndefined(student.studentId)) {
                student.studentId = "";
            }
            $http.post($scope.bshimServerURL + "/getStudentDetails?studentId=" + student.studentId+'&type='+"Student").then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.FeeDetailsList = data.studentFeeDetailsPojoList;
                $scope.viewStudent(student, $scope.FeeDetailsList)
            })
        };

        $scope.getFeeTypeMasterList = function (acId, grdID) {
            if (grdID != null && acId != null) {
                $(".loader").css("display", "block");
                $http.post($scope.bshimServerURL + "/getFeeList?academicId=" + acId + "&gradeId=" + grdID).then(function (response) {
                    var data = response.data.object;
                    console.log("yyyyyy:" + data);
                    $scope.feeTypeMasterList = data;
                    $scope.selectedFeeList = [];
                    angular.forEach($scope.feeTypeMasterList, function (data) {
                        $scope.selectedFeeList.push({
                            feeTypeId: data.feeTypeId,
                            feeTypeName: data.feeTypeName,
                            feeAmount: data.feeAmount,
                            status: data.status,
                            installmentsAmount: data.installmentsAmount,
                            installments: data.installments,
                            acdyrmaster: data.acdyrmaster,
                            dueDate: data.dueDate,
                            discountRemarks: null,
                            gradeMaster: data.gradeMaster,
                            value: data.value,
                            payingFee: data.payingFee,
                            paidAmt: parseInt(0),
                            installmentsPojosList: data.installmentsPojosList,
                            checkBox: true,
                            feeTypeStatus: true
                        });
                    })
                    $scope.setInstallments();
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            }
        };
        $scope.setInstallments = function () {
            angular.forEach($scope.selectedFeeList, function (val, key) {
                val.installments = 1;
                val.installmentsPojosList.push({
                    installmentsAmount: val.feeAmount,
                    dueDate: new Date()
                })

            })
            $scope.getTotal();
        };
        $scope.installmentList = function (data, len, discount) {
            $scope.selectedFeeList[len].installmentsPojosList = [];
            if (discount == null) {
                discount = 0;
            }
            if(data==""){
                data=1;
                $scope.selectedFeeList[len].installments=data;
            }
            var amt= ($scope.selectedFeeList[len].feeAmount - parseInt(discount)) % parseInt(data);
            for (var i = 0; i < data; i++) {
                $scope.selectedFeeList[len].installmentsPojosList.push({'installmentsAmount': '', 'dueDate': ''});
                $scope.selectedFeeList[len].installmentsPojosList[i].installmentsAmount = parseInt(($scope.selectedFeeList[len].feeAmount - parseInt(discount)) / parseInt(data));
                if ($scope.selectedFeeList[len].installmentsPojosList[i].dueDate == null || $scope.selectedFeeList[len].installmentsPojosList[i].dueDate == "") {
                    $scope.selectedFeeList[len].installmentsPojosList[i].dueDate = new Date();
                }
            }
            $scope.selectedFeeList[len].installmentsPojosList[0].installmentsAmount=$scope.selectedFeeList[len].installmentsPojosList[0].installmentsAmount+amt;
            $scope.editSelectedFeeList($scope.selectedFeeList[len], len, 0);
        };
        $scope.editSelectedFeeList = function (data, index, index1) {
            $scope.selectedFeeList[index].feeAmount = parseInt(data.feeAmount);
            $scope.selectedFeeList[index].installments = data.installments;
            $scope.selectedFeeList[index].installmentsAmount = parseInt(data.installmentsAmount);
            $scope.selectedFeeList[index].acdyrmaster = data.acdyrmaster;
            $scope.selectedFeeList[index].dueDate = data.dueDate;
            $scope.selectedFeeList[index].feeTypeId = data.feeTypeId;
            $scope.selectedFeeList[index].feeTypeName = data.feeTypeName;
            $scope.selectedFeeList[index].gradeMaster = data.gradeMaster;
            $scope.selectedFeeList[index].status = data.status;
            $scope.selectedFeeList[index].discount = data.discount;
            $scope.instalmentAmt = parseInt(0);
            angular.forEach($scope.selectedFeeList[index].installmentsPojosList, function (val, key) {
                if(val.installmentsAmount!="")
                    $scope.instalmentAmt = parseInt(val.installmentsAmount) + $scope.instalmentAmt;
                else
                    val.installmentsAmount=0;
            })
            if ($scope.instalmentAmt > $scope.selectedFeeList[index].payingFee + 1) {
                Notification.error({
                    message: 'Installment Amt should not exceed Total Payable',
                    positionX: 'center',
                    delay: 2000
                })
                angular.forEach($scope.selectedFeeList[index].installmentsPojosList, function (val, key) {
                    if(index1!=key){
                        val.installmentsAmount=0;
                    }else {
                        if(val.installmentsAmount>parseInt($scope.selectedFeeList[index].payingFee)){
                            val.installmentsAmount = parseInt($scope.selectedFeeList[index].payingFee);
                        }
                    }
                })
                // $scope.installmentList($scope.selectedFeeList[index].installments,index,$scope.selectedFeeList[index].discount);
                // $scope.selectedFeeList[index].installmentsPojosList[index1].installmentsAmount = parseInt($scope.selectedFeeList[index].payingFee / $scope.selectedFeeList[index].installments);
            }
            $scope.getTotal();
        };
        $scope.discount = parseInt(0);
        $scope.configDiscount = function (disConfig, index) {
            if (disConfig != null && disConfig != "") {
                $scope.discType = disConfig.toString().slice(-1);
                if ($scope.discType == "%") {
                    $scope.selectedFeeList[index].discPercent = parseInt(disConfig.toString().slice(0, -1));
                    $scope.selectedFeeList[index].discount = parseInt((($scope.selectedFeeList[index].feeAmount * $scope.selectedFeeList[index].discPercent) / 100));
                    $scope.selectedFeeList[index].payable = parseInt(($scope.selectedFeeList[index].feeAmount - $scope.selectedFeeList[index].discount));
                    $scope.selectedFeeList[index].payingFee = parseInt(($scope.selectedFeeList[index].feeAmount - $scope.selectedFeeList[index].discount));
                }
                else {
                    $scope.feeAmt = parseInt($scope.selectedFeeList[index].feeAmount);
                    $scope.discountAmt = parseInt(disConfig);
                    if ($scope.discountAmt > $scope.feeAmt) {
                        Notification.error({
                            message: 'Discount should not be Greater than Fee amount',
                            positionX: 'center',
                            delay: 2000
                        })
                        disConfig = parseInt(0);
                    }
                    $scope.selectedFeeList[index].discount = parseInt(disConfig);
                    $scope.selectedFeeList[index].payable = parseInt($scope.selectedFeeList[index].feeAmount - disConfig);
                    $scope.selectedFeeList[index].payingFee = parseInt($scope.selectedFeeList[index].feeAmount - disConfig);
                }
            } else {
                $scope.selectedFeeList[index].discount = parseInt(0);
                $scope.selectedFeeList[index].payable = parseInt($scope.selectedFeeList[index].feeAmount);
                $scope.selectedFeeList[index].payingFee = parseInt($scope.selectedFeeList[index].feeAmount);

            }
            $scope.installmentList($scope.selectedFeeList[index].installments, index, $scope.selectedFeeList[index].discount);
            $scope.getTotal();
        }
        $scope.selectedYearGrade = function (academicID) {
            $scope.gradeId = null;
            $http.post($scope.bshimServerURL + "/getAcademicGradeList?academicId=" + academicID).then(function (response) {
                var data = response.data.object;
                $scope.gradeList = data;
            })
        }
        $scope.getAcademicYearList = function () {
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + "/getacdemicYearList").then(function (response) {
                var data = response.data.object;
                $scope.academicYearList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getAcademicYearList();
        $scope.opened = [];
        $scope.openCustom = function ($event, $index) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.opened[$index] = true;
        };
        $scope.open = function ($event, dt) {
            $event.preventDefault();
            $event.stopPropagation();
            dt.opened = true;
        };
        $scope.getStudentList();
        $scope.saveStudent = function () {
            if (angular.isUndefined($scope.acdYearId) || $scope.acdYearId == '') {
                Notification.warning({message: 'Academic Name can not be Empty', positionX: 'center', delay: 2000});

            }
            else if (angular.isUndefined($scope.gradeId) || $scope.gradeId == '') {
                Notification.warning({message: 'Grade Name cannot be Empty', positionX: 'center', delay: 2000});

            }
            else if (angular.isUndefined($scope.dobAdmission) || $scope.dobAdmission == '') {
                Notification.warning({message: 'Date of Admission cannot be Empty', positionX: 'center', delay: 2000});

            }
            else if (angular.isUndefined($scope.studName) || $scope.studName == '') {
                Notification.warning({message: 'Student Name Cannot be Empty', positionX: 'center', delay: 2000});

            }
            else if (angular.isUndefined($scope.value) || $scope.value == '') {
                Notification.warning({message: 'Gender cannot be Empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.dateOfBirth) || $scope.dateOfBirth == '') {
                Notification.warning({message: 'Date Of Birth cannot be Empty', positionX: 'center', delay: 2000});

            }
            else if (angular.isUndefined($scope.fatherName) || $scope.fatherName == '') {
                Notification.warning({message: 'Father Name cannot be Empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.motherName) || $scope.motherName == '') {
                Notification.warning({message: 'Mother Name cannot be Empty', positionX: 'center', delay: 2000});
            }
            else if (!$scope.word.test($scope.fatherEmailId)&&!angular.isUndefined($scope.fatherEmailId)&&$scope.fatherEmailId!="") {
                Notification.warning({message: 'Father Email is Invalid', positionX: 'center', delay: 2000});
            }
            else if (!$scope.word.test($scope.motherEmailId)&&!angular.isUndefined($scope.motherEmailId)&&$scope.motherEmailId!="") {
                Notification.warning({message: 'Mother Email is Invalid', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.contactNo) || $scope.contactNo == '') {
                Notification.warning({
                    message: 'Primary Contact Number cannot be Empty',
                    positionX: 'center',
                    delay: 2000
                });
            }
            else {
                $scope.saveStu = true;
                $scope.dueDateStatus = true;
                angular.forEach($scope.selectedFeeList, function (val, key) {
                    angular.forEach(val.installmentsPojosList, function (value, key) {
                        if (angular.isUndefined(value.dueDate) || value.dueDate === null || value.dueDate == "") {
                            $scope.dueDateStatus = false;
                        }
                    })
                })
                if ($scope.totalPayableAmt != parseInt($scope.totalInstallmentsAmt)) {
                    Notification.error({
                        message: 'Instalment amount should be equal to paying amt',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.saveStu = false;
                }
                if ($scope.dueDateStatus == false) {
                    Notification.error({
                        message: 'please fill the due date',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.saveStu = false;
                }
                if ($scope.saveStu === true) {
                    var dateofjoining = $scope.joinDate;
                    var dateofadmission = $scope.dobAdmission;
                    if (dateofadmission > dateofjoining) {
                        Notification.error({
                            message: 'Give a valid joining date',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                    else {
                        var saveStudentDetails;
                        saveStudentDetails = {
                            studentId: $scope.studentId,
                            admissionFormNo: $scope.admFrmNo,
                            studentName: $scope.studName,
                            dateofbirth: $scope.dateOfBirth,
                            acdYearId: $scope.acdYearId,
                            gradeId: $scope.gradeId,
                            // academicBoard:$scope.acdBoard,
                            dateOfAdmission: $scope.dobAdmission,
                            dateOfJoining: $scope.joinDate,
                            fatherName: $scope.fatherName,
                            fatherEmailId: $scope.fatherEmailId,
                            fatherContactNo: $scope.fatherMobile,
                            fatherOccupation: $scope.fatherOccupation,
                            motherName: $scope.motherName,
                            motherContactNo: $scope.motherMobile,
                            motherOccupation: $scope.motherOccupation,
                            primaryContactNo: $scope.contactNo,
                            bloodGroup: $scope.bloodGroup,
                            motherEmailId: $scope.motherEmailId,
                            gender: $scope.value,
                            feeTypeMasterPojoList: $scope.selectedFeeList,
                            gaurdianName: $scope.localGuardianName,
                            annualIncome: $scope.parentsAnnualIncome,
                            presentAddress: $scope.presentAddress,
                            permanentAddress: $scope.permanentAddress,
                            religion: $scope.religion,
                            physicalCondition: $scope.studentPhysicalCondition,
                            documentUpload: $scope.fileToUpload,
                            aadhaarNo :$scope.aadhaarNo,
                            studentStatus: $scope.statusText,
                            gaurdianNumber:$scope.guardianNo
                        };
                        $http.post($scope.bshimServerURL + "/saveNewStudent", angular.toJson(saveStudentDetails)).then(function (response) {
                            var data = response.data;
                            if (data == "") {
                                Notification.error({
                                    message: 'Student Already exists',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $("#add_new_Student_modal").modal('hide');
                                if ($scope.operation != 'Edit') {
                                    Notification.success({
                                        message: 'Student is Created  successfully',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                } else {
                                    Notification.success({
                                        message: 'Student is Updated successfully',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }

                                $scope.removeStudentDetails();
                                $scope.getStudentList();
                                $("#back").show();
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
            }
        };

        $scope.getTotal = function () {
            $scope.totalFeeValue = parseInt(0);
            $scope.totalDiscountAmt = parseInt(0);
            $scope.totalPayableAmt = parseInt(0);
            $scope.noOfInstallments = parseInt(0);
            $scope.totalInstallmentsAmt = parseInt(0);
            angular.forEach($scope.selectedFeeList, function (value, key) {
                if (value.checkBox === true) {
                    $scope.totalFeeValue += parseInt(value.feeAmount);
                    if (parseInt(value.discount) > 0)
                        $scope.totalDiscountAmt += parseInt(value.discount);
                    $scope.totalPayableAmt += parseInt(value.payingFee);
                    $scope.noOfInstallments += parseInt(value.installments);
                    angular.forEach(value.installmentsPojosList, function (val, key) {
                        $scope.totalInstallmentsAmt += parseInt(val.installmentsAmount);
                    })
                }
                else {
                    value.installmentsPojosList = [];
                    value.installments = 1;
                    value.installmentsPojosList.push({'installmentsAmount': '', 'dueDate': ''});
                    if (angular.isUndefined(value.discount)) {
                        value.discount = parseInt(0);
                    }
                    value.installmentsPojosList[0].installmentsAmount = parseInt(value.feeAmount - parseInt(value.discount));
                    value.installmentsPojosList[0].dueDate = new Date();
                }
                if (isNaN($scope.totalDiscountAmt)) {
                    $scope.totalDiscountAmt = parseInt(0);
                }
                if (isNaN($scope.totalPayableAmt)) {
                    $scope.totalPayableAmt = parseInt(0);
                }
            });
        }

        $scope.editStudent = function (data, feeDetailsList) {
            $("#back").hide();
            $scope.setupobj = data;
            $scope.studentId = data.studentId;
            $scope.stuId = data.studentId;
            $scope.admFrmNo = data.admissionFormNo;
            $scope.studName = data.studentName;
            $scope.dateOfBirth = new Date(data.dateofbirth);
            $scope.acdYearId = data.acdYearId;
            $scope.gradeId = data.gradeId;
            $scope.dateOfAdmission = data.dateOfAdmission;
            $scope.dobAdmission = new Date(data.dateOfAdmission);
            $scope.dateOfJoining = new Date(data.dateOfJoining);
            $scope.joinDate = new Date(data.dateOfJoining);
            $scope.fatherName = data.fatherName;
            $scope.fatherEmailId = data.fatherEmailId;
            $scope.fatherMobile =data.fatherContactNo;
            $scope.fatherOccupation = data.fatherOccupation;
            $scope.motherName = data.motherName;
            $scope.motherMobile = data.motherContactNo;
            $scope.motherEmailId = data.motherEmailId;
            $scope.motherOccupation = data.motherOccupation;
            $scope.contactNo = data.primaryContactNo;
            $scope.bloodGroup = data.bloodGroup;
            $scope.value = data.gender;
            $scope.localGuardianName = data.gaurdianName;
            $scope.parentsAnnualIncome = data.annualIncome;
            $scope.presentAddress = data.presentAddress;
            $scope.permanentAddress = data.permanentAddress;
            $scope.religion = data.religion;
            $scope.studentPhysicalCondition = data.physicalCondition;
            $scope.aadhaarNo =data.aadhaarNo;
            if(data.studentStatus=='Active'){
                $scope.statusText=true;
            }else {
                $scope.statusText=false;
            }
            $scope.guardianNo= data.gaurdianNumber;
            $scope.operation = 'Edit';
            $scope.selectedFeeList = [];
            angular.forEach(feeDetailsList, function (val, key) {
                $scope.list = [];
                angular.forEach(val.installmentsPojos, function (val1, key) {
                    $scope.list.push({
                        checkBox: val1.checkBox,
                        dueDate: new Date(val1.dueDate),
                        paidAmt: parseInt(val1.paidAmt),
                        installmentsAmount: parseInt(val1.dueAmt)
                    })
                })
                $scope.selectedFeeList.push({
                    feeTypeId: val.feeTypeId,
                    studentFeeDetailsId: val.studentFeeDetailsId,
                    feeTypeName: val.feeTypeName,
                    feeAmount: val.feeTypeAmount,
                    status: val.status,
                    installmentsAmount: val.installmentsAmount,
                    installments: val.noOfInstallments,
                    acdyrmaster: val.acdyrmaster,
                    discountRemarks: val.discountRemarks,
                    dueDate: val.dueDate,
                    payable: val.payable,
                    discount: val.discount,
                    gradeMaster: val.gradeMaster,
                    paidAmt: val.paidAmount,
                    value: val.value,
                    payingFee: val.payable,
                    installmentsPojosList: $scope.list,
                    checkBox: val.checkBox,
                    feeTypeStatus: val.feeTypeStatus
                });
            });
            angular.forEach($scope.selectedFeeList, function (val, key) {
                if (val.checkBox == false) {
                    val.check = false;
                } else {
                    val.check = true;
                }
                if(val.installments>1&&val.value=="false"){
                    val.value="true";
                }
            })
            if($scope.selectedFeeList.length==0){
                $scope.getFeeTypeMasterList($scope.acdYearId,$scope.gradeId,$scope.stuId);
            }
            $scope.getTotal();
            $scope.back_wizardEdit();
            $scope.operation = 'Edit';
            $('#student-title').text("EDIT STUDENT");
            $("#add_new_Student_modal").modal('show');
        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.deleteStudent = function (data) {
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
                            studentId: data.studentId,
                            admissionFormNo: data.admFrmNo,
                            studentName: data.studName,
                            dateOfBirth: data.dateOfBirth,
                            acdYearId: data.acdYearId,
                            gradeId: data.gradeId,
                            dateOfAdmission: data.dobAdmission,
                            dateOfJoining: data.joinDate,
                            fatherName: data.fatherName,
                            fatherEmailId: data.fatherEmailId,
                            fatherContact: data.fatherContact,
                            fatherOccupation: data.fatherOccupation,
                            motherName: data.motherName,
                            motherMobileNo: data.motherMobile,
                            motherOccupation: data.motherOccupation,
                            primaryContactNo: data.contactNo,
                            bloodGroup: data.bloodGroup
                        };
                        $http.post($scope.bshimServerURL + "/deleteStudent", angular.toJson(deleteDetails, "Create")).then(function (response, status, headers, config) {
                            var data = response.data;
                            if(data==true){
                                Notification.success({
                                    message: 'Successfully Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }else {
                                Notification.warning({
                                    message: 'Fee Already Collected',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            $scope.getStudentList();
                            $scope.removeStudentDetails();
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

        $scope.viewStudent = function (data, feeDetailsList) {
            $scope.viewStudentList =data;
            $scope.studentId =data.studentId;
            console.log(data);

            // $scope.setupobj = data;
            // $scope.studentId = data.studentId;
            // $scope.stuId = data.studentId;
            // $scope.admFrmNo = data.admissionFormNo;
            // $scope.studName = data.studentName;
            // $scope.dateOfBirth = new Date(data.dateofbirth);
            // $scope.acdYearId = data.acdYearId;
            // $scope.gradeId = data.gradeId;
            // $scope.dateOfAdmission = data.dateOfAdmission;
            // $scope.dobAdmission = new Date(data.dateOfAdmission);
            // $scope.dateOfJoining = new Date(data.dateOfJoining);
            // $scope.joinDate = new Date(data.dateOfJoining);
            // $scope.fatherName = data.fatherName;
            // $scope.fatherEmailId = data.fatherEmailId;
            // $scope.fatherMobile =data.fatherContactNo;
            // $scope.fatherOccupation = data.fatherOccupation;
            // $scope.motherName = data.motherName;
            // $scope.motherMobile = data.motherContactNo;
            // $scope.motherEmailId = data.motherEmailId;
            // $scope.motherOccupation = data.motherOccupation;
            // $scope.contactNo = data.primaryContactNo;
            // $scope.bloodGroup = data.bloodGroup;
            // $scope.value = data.gender;
            // $scope.localGuardianName = data.gaurdianName;
            // $scope.parentsAnnualIncome = data.annualIncome;
            // $scope.presentAddress = data.presentAddress;
            // $scope.permanentAddress = data.permanentAddress;
            // $scope.religion = data.religion;
            // $scope.studentPhysicalCondition = data.physicalCondition;
            // $scope.aadhaarNo =data.aadhaarNo;
            // $scope.statusText = data.studentStatus;

            $scope.selectedFeeList = [];
            angular.forEach(feeDetailsList, function (val, key) {
                $scope.list = [];
                angular.forEach(val.installmentsPojos, function (val1, key) {
                    $scope.list.push({
                        checkBox: val1.checkBox,
                        dueDate: new Date(val1.dueDate),
                        paidAmt: parseInt(val1.paidAmt),
                        installmentsAmount: parseInt(val1.dueAmt)
                    })
                })
                $scope.selectedFeeList.push({
                    feeTypeId: val.feeTypeId,
                    studentFeeDetailsId: val.studentFeeDetailsId,
                    feeTypeName: val.feeTypeName,
                    feeAmount: val.feeTypeAmount,
                    status: val.status,
                    installmentsAmount: val.installmentsAmount,
                    installments: val.noOfInstallments,
                    acdyrmaster: val.acdyrmaster,
                    discountRemarks: val.discountRemarks,
                    dueDate: val.dueDate,
                    payable: val.payable,
                    discount: val.discount,
                    gradeMaster: val.gradeMaster,
                    paidAmt: val.paidAmount,
                    value: val.value,
                    payingFee: val.payable,
                    installmentsPojosList: $scope.list,
                    checkBox: val.checkBox,
                    feeTypeStatus: val.feeTypeStatus
                });
            });
            angular.forEach($scope.selectedFeeList, function (val, key) {
                if (val.checkBox == false) {
                    val.check = false;
                } else {
                    val.check = true;
                }
            })
            $scope.getTotal();
            $scope.back_wizardEdit();
            $scope.operation = 'View';
            $('#student-title').text("VIEW STUDENT");
            $("#view_Student_modal").modal('show');
            // $("#saveId").hide();
        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };


    });





