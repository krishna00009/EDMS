
/**
 * Created by azgar.h on 6/22/2017.
 */
app.controller('loginController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope){
        console.log("aaaaaaaaaaaaa");
        $('#sidebar-menu').hide();
        $('#logoutButton').hide();
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.registrationFormData = {};
        $scope.loginFormData = {};
        $scope.forgetPwdFormData = {};
        // $scope.currentPage = PAGE_NAME.trim();
        $scope.loginId = $scope.currentPage == "license" ? "signup" : "signin";

        $scope.changeTab = function (tab) {
            $scope.loginId = tab;
        }

        $scope.changeLanguage = function (key) {

            $translate.use(key)
        };

        function createCookie(name, value, days) {
            if (days) {
                var date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                var expires = "; expires=" + date.toGMTString();
            }
            else var expires = "";
            document.cookie = name + "=" + value + expires + ";";
        }

        $(document).ready(function () {
            $(".btn-login").click(function () {
                $(".bigicon").addClass("fa-spin");
            });
        });


        $scope.login = function () {
            var loginData = {
                companyEmail: $scope.loginFormData.email,
                userName: $scope.loginFormData.name,
                password: $scope.loginFormData.password
            };
            $http.post("hipos/login", JSON.stringify(loginData)).then(function success(response) {
                console.log(response.data['accessToken']);
                var returnToken = response.data['accessToken'];
                if (returnToken=='invalid'){
                    alert('Invalid credentials');
                    return false;
                }else if (returnToken=='session-present'){
                    alert("User already logged in other system");
                    return false;
                }
                createCookie('accessToken', response.data['accessToken'], {});
            // else {
            //     $cookies.remove('accessToken');
            //     Notification.error({
            //         message: 'Your session expired.Please login again',
            //         positionX: 'center',
            //         delay: 20
            //     });
            //     $window.location.href = '#!/login';
            // }
                // window.location.href = '#!/login';
                // window.location.reload();
            }, function error() {
            });
        };

        $scope.register = function () {
            $rootScope.showFullPageLoading = true;

            $http.get("company/getCompanyRegistration?license_key=" + $scope.registrationFormData.license_key).then(
                function success() {
                    $rootScope.showFullPageLoading = false;
                    window.location.reload();
                }, function error(response) {
                    $rootScope.showFullPageLoading = false;
                    if (response.data) {
                        Notification.error({message: response.data['message'], positionX: 'center', delay: 2000});
                    }
                }
            );
        };

        $scope.forgetPwd = function () {
            $http.post("company/forgotPassword", JSON.stringify($scope.forgetPwdFormData)).then(
                function success() {
                    window.location.reload();
                }, function error(response) {
                    if (response.data) {
                        Notification.error({message: response.data['message'], positionX: 'center', delay: 2000});
                    }
                }
            );
        }

        $(".loginBody").css("background", "url('resource/posimages/hinext_new_op_bg.jpg') center center / cover no-repeat rgb(238, 238, 238)");
        $(".hinext_new, .bg_row_info, .bg_sub_row_info, .right_col").css("background", "transparent");

        $scope.removeuserDetails = function () {
            $scope.userEmailText = "";
            $scope.userNameText = "";
            $scope.pswdText = "";
            $scope.branchCodeText = "";
        }
        $scope.disabled=false;
        $scope.saveuservalidate = function () {
            var saveUserDetails;
            saveUserDetails = {
                branchCode: $scope.branchCodeText,
                userName: $scope.userNameText,
                passwordUser: $scope.pswdText
            };
            document.cookie = "email="+$scope.userNameText;
            $http.post("/bs/userValidate", angular.toJson(saveUserDetails)).then(function (response) {
                var data = response.data;
                $scope.disabled=true;
                $rootScope.userName=data.full_name;
                if(data==""){
                    $('#sidebar-menu').hide();
                    $scope.removeuserDetails();
                    Notification.error({
                        message: 'Invalid Authentication',
                        positionX: 'center',
                        delay: 2000
                    });
                }
                else{
                    $window.location.href = '/home#!/dashboard';
                    $('#sidebar-menu').show();
                    $('#logoutButton').show();
                    $scope.removeuserDetails();
                    Notification.success({
                        message: 'Sucess',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.logoutButton=true;
                }
                $scope.disabled=false;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }
        $scope.isDisabled=true;

    });