app.controller('headerCtrl',
    function($rootScope, $scope, $http) {

        if(!isCookieSet){
            $rootScope.hidesideMenu = false;
        }else{
            $rootScope.hidesideMenu = false;
        }

    });