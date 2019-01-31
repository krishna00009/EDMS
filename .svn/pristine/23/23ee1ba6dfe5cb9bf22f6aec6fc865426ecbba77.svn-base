app.controller('dashboardController',
    function ($scope, $rootScope, $http, $interval, Notification) {
        $scope.colors = [ '#46BFBD', '#803690', '#009933', '#FDB45C', '#00ADF9', '#0066cc', '#990000'];
        $scope.showLine = false;
        $scope.purchaseRemaining = 0;
        $scope.salesRemaining = 0;
        $scope.hiposServerURL1 =  "/hipos/";
        $scope.customer1 = 1;

        $("#dashboard").addClass('active');
        $('#dashboard').siblings().removeClass('active');
        $('#sidebar-menu ul li ul li').removeClass('active');
        $('#sidebar-menu ul li ul').css('display','none');

        // $scope.getPageLoadData = function () {
        //     $http.get($scope.hiposServerURL1 + "/" + $scope.customer1 + '/getPageLoadData').then(function (response) {
        //         var data = response.data;
        //         if (!angular.isUndefined(data) && data !== null && data !== 'invalid') {
        //             $scope.fullUserName = data.fullUserName;
        //             $scope.user_access_rights=data.userAccessRights;
        //
        //
        //             console.log(data.userAccessRights);
        //             $scope.cmpyinvLocationList = data.cmpylocationList;
        //         }
        //     });
        // };
        // $scope.getPageLoadData();

        $scope.drawPieChart = function(){
            var totalBalance = Math.abs($scope.cashInBank) + Math.abs($scope.cashInHand);
            $scope.pieInHandBalanceLabel = ["Total Cash In Hand", "Total Balance"];
            $scope.pieInHandBalanceData = [Math.abs($scope.cashInHand), totalBalance];
            $scope.pieBankBalanceLabel = ["Total Cash In Bank", "Total Balance"];
            $scope.pieBankBalanceData = [Math.abs($scope.cashInBank), totalBalance];
            $scope.pieInHandBalanceOption = {
                legend: {
                    display: false
                },
                title: {
                    display: true,
                    text: 'Cash in hand out of total'
                }
            }
            $scope.pieBankBalanceOption = {
                legend: {
                    display: false
                },
                title: {
                    display: true,
                    text: 'Cash in bank out of total'
                }
            }
        };

        $scope.drawLineChart = function(){
            var salesLineData = {};
            var purchaseLinedata = {};
            $scope.lineLabel = [];
            $scope.lineOption = {
                legend: {
                    display: true
                },
                title: {
                    display: true,
                    text: 'Daily Sales/Purchase'
                }
            };

            angular.forEach($scope.salesDashBoardDataDtoList, function (sales) {
                if(salesLineData.hasOwnProperty(sales.salesDate)){
                    salesLineData[sales.salesDate] += parseFloat(sales.totalRecievable);
                }else{
                    salesLineData[sales.salesDate] = parseFloat(sales.totalRecievable);
                }
                if($scope.lineLabel.indexOf(sales.salesDate) == -1){
                     $scope.lineLabel.push(sales.salesDate);
                }

                $scope.salesRemaining += parseFloat(sales.arBalance);
            });

            angular.forEach($scope.purchaseDashBoardDataDtoList, function (purchase) {
                $scope.purchaseRemaining += parseFloat(purchase.apBalance);
                if(purchaseLinedata.hasOwnProperty(purchase.purchaseDate)){
                    purchaseLinedata[purchase.purchaseDate] += parseFloat(purchase.totalPayable);
                }else{
                    purchaseLinedata[purchase.purchaseDate] = parseFloat(purchase.totalPayable);
                }

                if($scope.lineLabel.indexOf(purchase.purchaseDate) == -1){
                    $scope.lineLabel.push(purchase.purchaseDate)
                }
            });

            $scope.lineLabel.sort();

            var salesArray = [];
            var purchaseArray = [];

            for(var i=0; i< $scope.lineLabel.length; i++ ){
                if(!salesLineData[$scope.lineLabel[i]]){
                    salesArray.push(0);
                }else{
                    salesArray.push(salesLineData[$scope.lineLabel[i]])
                }

                if(!purchaseLinedata[$scope.lineLabel[i]]){
                    purchaseArray.push(0);
                }else{
                    purchaseArray.push(purchaseLinedata[$scope.lineLabel[i]])
                }
            }

            $scope.lineData = [salesArray, purchaseArray];
            $scope.lineSeries = ['Sales', 'Purchase'];


        };

        $scope.doughnutChart = function(){
            $scope.doughnutTotalAmountLabel = ["Total Receivable", "Total Payable", "Remaining Sales Balance", "Remaining Purchase Balance"];
            $scope.doughnutTotalAmountData = [Math.abs($scope.totalRecievable), Math.abs($scope.totalPayable), Math.abs($scope.salesRemaining), Math.abs($scope.purchaseRemaining)];

            $scope.doughnutTotalAmountOption = {
                legend: {
                    display: true
                }
            }
        };

        $scope.drawGraph = function() {
            $scope.drawLineChart();
            $scope.drawPieChart();
            $scope.doughnutChart(); // must be after line chart
        };

        $scope.displayDateTime = function() {
            var d = new Date(),
                seconds = d.getSeconds().toString().length == 1 ? '0'+d.getSeconds() : d.getSeconds(),
                minutes = d.getMinutes().toString().length == 1 ? '0'+d.getMinutes() : d.getMinutes(),
                hours = d.getHours().toString().length == 1 ? '0'+d.getHours() : d.getHours(),
                ampm = d.getHours() >= 12 ? 'PM' : 'AM',
                months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
                days = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];

            $scope.currentTime = hours + ' : ' + minutes + ' : ' + seconds + " " + ampm;
            $scope.currentDate = days[d.getDay()]+' '+months[d.getMonth()]+' '+d.getDate()+' '+d.getFullYear();
        }

        $interval($scope.displayDateTime, 1000);

    //    Api Call
    //     $scope.onLoadDashBoardData = function (locationId) {
    //         $rootScope.showFullPageLoading = true;
    //         if(locationId==null){locationId = "";}
    //         if (angular.isUndefined(locationId)) {
    //             locationId = "";
    //         }
    //         $http.post('/getOnLoadDashBoard?locationId=' + locationId).then(function (response) {
    //             var data = response.data;
    //             $scope.cashInHand =  data.cashInHand;
    //             $scope.cashInBank = data.cashInBank;
    //             $scope.totalRecievable = data.totalRecievable;
    //             $scope.totalPayable = data.totalPayable;
    //             $scope.purchaseDashBoardDataDtoList = data.purchaseDashBoardDataDtoList;
    //             $scope.salesDashBoardDataDtoList = data.salesDashBoardDataDtoList;
    //
    //             $scope.drawGraph();
    //             $rootScope.showFullPageLoading = false;
    //
    //         }, function (response) {
    //             $rootScope.showFullPageLoading = false;
    //             Notification.error({message: "Something went wrong.", positionX: 'center', delay: 2000});
    //         });
    //     };
    //     $scope.onLoadDashBoardData("");

    });