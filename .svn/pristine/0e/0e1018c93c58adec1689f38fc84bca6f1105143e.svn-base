/**
 * Created by azgar.h on 7/1/2017.
 */

app.controller('emailserverCtrl',
    function ($scope, $http, $location, $filter, Notification, ngTableParams, $timeout, $window) {

        $scope.removeMailDetails = function () {
            $scope.EmailIdText="0";
            $scope.PasswordText="0";
            $scope.SMTPPortText="0";
            $scope.SSMTPServerText="0";
            $scope.ForMailText="0";
            $scope.operation = "";
        };

        $scope.getMailList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $http.get($scope.hiposServerURL + "/" + $scope.customer + '/getPaginatedMailList?mailSearchText=' + val,{
                params: {
                    "firstPage": $scope.firstPage,
                    "lastPage": $scope.lastPage,
                    "pageNo": $scope.pageNo,
                    "prevPage": $scope.prevPage,
                    "prevPage": $scope.isPrev,
                    "nextPage": $scope.isNext
                }
            }).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.mailList = data.data;
                /**/
                $scope.first = data.first;
                $scope.last = data.last;
                $scope.prev = data.prev;
                $scope.next = data.next;
                $scope.pageNo = data.pageNo;
                /**/
                $scope.mailSearchText = val;
                $scope.searchText = val;
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
        };
        $scope.addNewEmailServer = function () {
            $(".loader").css("display", "block");
                $scope.id="";
                $scope.EmailIdText ="";
                $scope.PasswordText="";
                 $scope.SMTPPortText="";
                $scope.SSMTPServerText="";
                  $scope.ForMailText="";
                $('#mail-title').text("Add Mail");
                $("#add_new_addNewEmailServer_modal").modal('show');
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.editMail = function(data) {
            $scope.projectObj = data;
            $scope.id = data.id;
            $scope.EmailIdText=data.userName;
            $scope.PasswordText=data.password;
            $scope.SSMTPServerText=data.smtpHost;
            $scope.SMTPPortText=data.port;
            $scope.ForMailText=data.forMail;
            $scope.operation='Edit';
            $('#mail-title').text("Edit Mail");
            $scope.getMailList();
            $("#add_new_addNewEmailServer_modal").modal('show');
        },function (error) {
            Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});
        };
        $scope.deleteMail = function (data) {
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
                    //  alert("delete");
                    if(result == true){
                        $scope.id = data.id;
                        $scope.EmailIdText=data. userName;
                        $scope.PasswordText=data.password;
                        $scope.SMTPPortText=data.port;
                        $scope.SSMTPServerText=data.smtpHost;
                        $scope.ForMailText=data.forMail;
                        var deleteDetails = {
                            id : $scope.id,
                            userName: $scope.userName,
                            password: $scope.password,
                            port:$scope.port,
                            smtpHost:$scope.smtpHost,
                            forMail:$scope.forMail
                        };
                        $http.post($scope.hiposServerURL + "/" + $scope.customer + '/deleteMail', angular.toJson(deleteDetails, "Create")).then(function (response, status, headers, config) {
                            var data = response.data;
                            if(data=="") {
                                $scope.getMailList();
                                Notification.success({message: 'It Is Already In Use Cant be Deleted', positionX: 'center', delay: 2000});
                            }
                            else {
                                $scope.removeMailDetails();
                                $scope.getMailList();
                                $("#add_new_addNewEmailServer_modal").modal('hide');
                                if (!angular.isUndefined(data) && data !== null) {
                                    $scope.mailSearchText = "";
                                }
                                Notification.success({
                                    message: 'Successfully Deleted',
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

        $scope.saveMail = function () {
            var mailId=true;
            var mail=$scope.EmailIdText;
            var mailRegex=/([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)\S+/;
            if ($scope.EmailIdText === "" || angular.isUndefined($scope.EmailIdText)) {
                Notification.warning({message: 'Email Server can not be empty', positionX: 'center', delay: 2000});
                mailId=false;
            }
            if ($scope.EmailIdText!=="") {
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
                $scope.isDisabled= true;
                $timeout(function(){
                    $scope.isDisabled= false;
                }, 3000)
                 var saveMailDetails;
                saveMailDetails = {
                    id:$scope.id,
                    userName: $scope.EmailIdText,
                    password: $scope.PasswordText,
                    port: $scope.SMTPPortText,
                    smtpHost: $scope.SSMTPServerText,
                    forMail: $scope.ForMailText

                };
                $http.post("/bs" + "/"  + '/saveMail', angular.toJson(saveMailDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if(data==""){
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removeMailDetails();
                        $scope.getMailList();
                        $("#add_new_addNewEmailServer_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.mailSearchText = "";
                        }
                        Notification.success({message: 'Mail Created  successfully', positionX: 'center', delay: 2000});
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            };
        }
    });
