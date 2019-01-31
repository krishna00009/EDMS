// var taskManagerModule = angular.module('taskManagerApp', ['ngRoute','ngAnimate']);
//

var app = angular.module('myApp',['ngRoute', 'ngAnimate',
    'ngSanitize','ui-notification','ngTable',
    'ngCookies', 'angular.filter','ui.bootstrap']);


app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when("/login", {
            templateUrl: "partials/login.html",
            controller: "loginController"
        })

        .when("/dashboard", {
            templateUrl: "partials/dashboard.html",
            controller: "dashboardController"
        })
        .when("/student", {
            templateUrl: "partials/student.html",
            controller: "studentController"
        })
        .when("/fee", {
            templateUrl: "partials/fee.html",
            controller: "feeController"
        })

        .when("/configuration", {
            templateUrl: "partials/configuration.html",
            // controller: "configurationController"
        })
        .when("/support", {
            templateUrl: "partials/support.html",
            controller: "supportController"
        })
        .when("/reports", {
            templateUrl: "partials/reports.html"
        })
        .when("/feeDueReport", {
            templateUrl: "partials/feeDueReports.html",
            controller: "feeDueReportController"
        })
        .when("/feeCollectedReport", {
            templateUrl: "partials/feeCollectedReports.html",
            controller: "feeCollectedReportController"
        })
        .when("/receiptReport", {
            templateUrl: "partials/feeReceiptNoReport.html",
            controller: "feeReceiptNoReportController"
        })
        .when("/feetypemaster", {
            templateUrl: "partials/feetypemaster.html",
            controller: "feetypemasterController"
        })
        .when("/grademaster", {
            templateUrl: "partials/grademaster.html",
            controller: "grademasterController"
        })
        .when("/emailServer", {
            templateUrl: "partials/emailserver.html",
            controller: "emailserverCtrl"
        })
        .when("/reportMailScheduler", {
            templateUrl: "partials/ReportMailScheduler.html",
            controller: "reportMailSchedulerCtrl as rest"
        })
        .when("/academicyearmaster", {
            templateUrl: "partials/academicyearmaster.html",
            controller: "academicyearmasterController"
        })
        .when("/studentschooldetails", {
            templateUrl: "partials/studentschooldetails.html",
            controller: "studentschooldetailsController"
        })
        .when("/smsServer", {
            templateUrl: "partials/SmsServer.html",
            controller: "messageServerCtrl"
        })
        // .when("/controlPanel", {
        //     templateUrl: "partials/controlPanel.html",
        //     controller: "controlPanelController"
        // })
        // .when("/currency", {
        //     templateUrl: "partials/currency.html",
        //     controller: "currencyCtrl as rest"
        // })
        // .when("/paymentmethod", {
        //     templateUrl: "partials/paymentmethod.html",
        //     controller: "paymentMethodCtrl as rest"
        // })
        // .when("/versioncontrol", {
        //     templateUrl: "partials/versioncontrol.html",
        //     controller: "versionCtrl as rest"
        // })
        // .when("/subscription", {
        //     templateUrl: "partials/subscription.html",
        //     controller: "subscriptionCtrl as rest"
        // })
        // .when("/packages", {
        //     templateUrl: "partials/packages.html",
        //     controller: "packageCtrl as rest"
        // })
        // .when("/customer", {
        //     templateUrl: "partials/customer.html",
        //     controller: "customerCtrl as rest"
        // })
        // .when("/PractitionerRegistration", {
        //     templateUrl: "partials/PractitionerRegister.html",
        //     controller: "PractitionerRegistrationCtrl as rest"
        // })
        // .when("/addon", {
        //     templateUrl: "partials/addOn.html",
        //     controller: "addOnCtrl as rest"
        // })
        // .when("/Practitionerorders", {
        //     templateUrl: "partials/Practitionerorders.html",
        //     controller: "PractitionerordersCtrl as rest"
        // })
        // .when("/cartRegistration", {
        //     templateUrl: "partials/cartRegistration.html",
        //     controller: "cartRegCtrl as rest"
        // })
        .when("/user", {
            templateUrl: "partials/user.html",
            controller: "userCtrl as rest"
        })
        // .when("/cartcustomerlink", {
        //     templateUrl: "partials/cartcustomerlink.html",
        //     controller: "cartcustomerlinkCtrl as rest"
        // })
        // .when("/Hiconnector", {
        //     templateUrl: "partials/Hiconnector.html",
        //     controller: "HiconnectorCtrl as rest"
        // })
        // .when("/customernotification", {
        //     templateUrl: "partials/customernotif.html",
        //     controller: "customernotifCtrl as rest"
        // })
        // .when("/transactionsdata", {
        //     templateUrl: "partials/transdata.html",
        //     controller: "transdataCtrl as rest"
        // })
        .when("/cartMaster",{
            templateUrl:"partials/cartMaster.html",
            controller:"cartMasterCtrl as rest"
        })
        .otherwise({redirectTo:'/login'});
}]);

app.directive("limitTo", [function () {
    return {
        restrict: "A",
        link: function (scope, elem, attrs) {
            var limit = parseInt(attrs.limitTo);
            angular.element(elem).on("keypress", function (e) {
                if (this.value.length === limit)
                    e.preventDefault();
            });
        }
    };
}]);


/* for only  Alpha without space function
 */
app.directive('alphaWithoutSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^a-zA-Z]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});
/* validation
 * for only number with space function
 */
app.directive('numWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^\s0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});

/* validation
 * It allows number,plus,hypen with space function
 */
app.directive('mobileNumWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^- ^+^0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});
app.directive('numWithOutSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});
/* validation
 * It allows number,plus,hypen with space function
 */
app.directive('number', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});



/* for only Alpha with space function
 */
app.directive('alphaWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^\s^a-zA-Z]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});


/* for only Alpha and number with space function
 */
app.directive('alphanumWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^\s^a-zA-Z^0-9]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});


/* for only Alpha and number without space function
 */
app.directive('alphanumWithoutSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^a-zA-Z^0-9]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

/* for only two digit decimal Function
 */

app.directive('twoDigitsDec', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                if (angular.isUndefined(val)) {
                    var val = '';
                }

                var clean = val.replace(/[^-0-9\.]/g, '');
                var negativeCheck = clean.split('-');
                var decimalCheck = clean.split('.');
                if(!angular.isUndefined(negativeCheck[1])) {
                    negativeCheck[1] = negativeCheck[1].slice(0, negativeCheck[1].length);
                    clean =negativeCheck[0] + '-' + negativeCheck[1];
                    if(negativeCheck[0].length > 0) {
                        clean =negativeCheck[0];
                    }

                }

                if(!angular.isUndefined(decimalCheck[1])) {
                    decimalCheck[1] = decimalCheck[1].slice(0,2);
                    clean =decimalCheck[0] + '.' + decimalCheck[1];
                }

                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

app.directive('noSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

// Change text to uppercase and add dash every 5 char
app.directive('capitalizeWithDash', function() {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, modelCtrl) {
            var capitalize = function(inputValue) {
                var number = 5;
                if (inputValue == undefined) inputValue = '';
                var inputUpper = inputValue.toUpperCase();
                var capitalizedArray = [];
                for(var i=0; i<inputUpper.length; i+= number){
                    if(inputUpper[i] == "-"){
                        capitalizedArray.push(inputUpper.substr(i+1,number))
                        i += 1;
                    }else {
                        capitalizedArray.push(inputUpper.substr(i, number))
                    }
                }
                var capitalized = capitalizedArray.join("-");
                if (capitalized !== inputValue) {
                    modelCtrl.$setViewValue(capitalized);
                    modelCtrl.$render();
                }
                return capitalized;
            }
            modelCtrl.$parsers.push(capitalize);
            capitalize(scope[attrs.ngModel]);
        }
    };
});

// $('.main_container').bind('keypress', function(e) {
//     var code = e.keyCode || e.which;
//     if(code == 13) { //Enter keycode
//         //Do something
//     }
// });




// angular.module("core").directive('hnEnterKey', function() {
//     return function(scope, element, attrs) {
//        ("keydown keypress", function(event) {
//
//             var code = e.keyCode || e.which;
//             if(code == 13) { //Enter keycode
//                 //Do something
//             }
//
//
//             var keyCode = event.which || event.keyCode;
//             if (keyCode === 13) {
//                 scope.$apply(function() {
//                     scope.$eval(attrs.hnEnterKey);
//                 });
//
//                 event.preventDefault();
//             }
//         });
//     };
// });


// app.config(['$httpProvider', function ($httpProvider) {
//     var $cookies;
//     angular.injector(['ngCookies']).invoke(['$cookies', function (_$cookies_) {
//         $cookies = _$cookies_;
//     }]);
//     $httpProvider.defaults.headers.common['Authorization'] = $cookies.get('accessToken');
// }]);


app.filter('setDecimal', function ($filter) {
    return function (input, places) {
        if (isNaN(input))
            return input;
        // If we want 1 decimal place, we want to mult/div by 10
        // If we want 2 decimal places, we want to mult/div by 100, etc
        // So use the following to create that factor
        var factor = "1" + Array(+(places > 0 && places + 1)).join("0");
        return Math.round(input * factor) / factor;
    };
});

app.filter('firstLetter', function () {
    return function (input, key, letter) {
        input = input || [];
        var out = [];
        input.forEach(function (item) {
            console.log('item: ', item[key][0].toLowerCase());
            console.log('letter: ', letter);
            if (item[key][0].toLowerCase() == letter.toLowerCase()) {
                out.push(item);
            }
        });
        return out;
    }
});

var isSessionExpireFirstAlert = true;
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push(function ($q, $window) {
        return {
            'response': function (response) {
                //do something with success response
                isSessionExpireFirstAlert = true;
                return response;
            },
            'responseError': function (response) {
                //do something with error response
                if (response.status && response.status === 401) {
                    if (!/\!\/(login|manageCompanies|superAdminLogin)$/.test($window.location.href.split("?")[0])) {
                        if (isSessionExpireFirstAlert) {
                            alert('Your Session Expired');
                            isSessionExpireFirstAlert = false;
                            // $window.location.reload();
                            $window.location.href = '/home#!/login';
                        }
                    }
                }
                return $q.reject(response);
            }
        };
    });

});
