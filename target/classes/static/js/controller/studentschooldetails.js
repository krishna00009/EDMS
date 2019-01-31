app.controller('studentschooldetailsController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/bs";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.operation = 'Create';



        $scope.saveSchoolsDetails = function () {
            if (angular.isUndefined($scope.branchCode) || $scope.branchCode == '') {
                Notification.warning({message: 'Branch Code can not be Empty', positionX: 'center', delay: 2000});

            }
            else if(angular.isUndefined($scope.branchName) || $scope.branchName == ''){
                Notification.warning({message: 'Branch Name can not be Empty', positionX: 'center', delay: 2000});
            }
            // else if(angular.isUndefined($scope.emailId) || $scope.emailId == ''){
            //     Notification.warning({message: 'Email cannot be allow spaces', positionX: 'center', delay: 2000});
            // }

            else {
                var saveBranchDetails;
                saveBranchDetails = {
                    schoolBranchId: $scope.schoolBranchId,
                    branchCode: $scope.branchCode,
                    branchName: $scope.branchName,
                    address: $scope.address,
                    city: $scope.city,
                    pinCode: $scope.pinCode,
                    state: $scope.state,
                    phoneNumber: $scope.phoneNumber,
                    emailId: $scope.emailId,
                    receiptNo: $scope.receiptNo,
                    termsAndConditions: $scope.termsandconditions,
                    receiptFooter: $scope.receiptfooterdesc
                };


                $http.post($scope.bshimServerURL + "/saveSchoolBranchDetails", angular.toJson(saveBranchDetails)).then(function (response) {
                    var data = response.data;


                    console.log(data);
                    if (data == "") {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        Notification.success({
                            message: 'SchoolBranchDetails is Created  successfully',
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


        $scope.getSchoolBranchDetailsList = function () {
            $http.post($scope.bshimServerURL + '/getSchoolBranchDetailsList').then(function (response) {
                var data = response.data.object;
                for(var i=0; i<data.length;i++) {
                    $scope.schoolBranchId = data[i].schoolBranchId;
                    $scope.branchCode = data[i].branchCode;
                    $scope.branchName = data[i].branchName;
                    $scope.address = data[i].address;
                    $scope.city = data[i].city;
                    $scope.pinCode = data[i].pinCode;
                    $scope.state = data[i].state;
                    $scope.phoneNumber = data[i].phoneNumber;
                    $scope.emailId = data[i].emailId;
                    $scope.receiptNo = data[i].receiptNo;
                    $scope.termsandconditions = data[i].termsAndConditions;
                    $scope.receiptfooterdesc = data[i].receiptFooter;
                }
                // $scope.gradeList = data;
                // $scope.searchText = val;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getSchoolBranchDetailsList();

        $scope.feeconfigurationList=function () {
            $window.location.href = '/home#!/configuration';

        };







    });
