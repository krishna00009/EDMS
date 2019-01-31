app.controller('cartMasterCtrl',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/bs";




        $scope.removecartMaster=function()
        {
          $scope.cartmasterId="";
          $scope.hiconnectRegNo="";
          $scope.username="";
          $scope.email="";
          $scope.password="";

        };

        $scope.adddcartMaster=function(){
            $scope.removecartMaster();
            $('#title').text("Add CartMaster");
            $("#add_cart_master").modal('show');

        };
        $scope.getCartmasterList = function () {
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + "/getCartmasterList").then(function (response) {
                var data = response.data.object;
                console.log(data);
                $scope.cartMasterList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getCartmasterList();

        $scope.savecartMaster=function(){

            var savecartmaster;
            savecartmaster={
                cartmasterId:$scope.cartmasterId,
                hiConnectCompanyRegNo:$scope.hiConnectCompanyRegNo,
                email:$scope.email,
                userName:$scope.userName,
                password:$scope.password

            };
            $http.post($scope.bshimServerURL + "/savecartmasterdetails", angular.toJson(savecartmaster)).then(function (response) {
                var data = response.data;
                if (data == "") {
                    Notification.warning({message: ' Invalid credentials', positionX: 'center', delay: 2000});
                }
                else {
                    $("#add_cart_master").modal('hide');
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
                    $scope.removecartMaster();
                    $scope.getCartmasterList();
                }
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }


    });