
app.controller('formsetupCtrl',
    function ($scope,$http,Notification) {
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.bshimServerURL = "/bs";
        $scope.supplier = 1;
        $scope.formsetupId="";
        $scope.typePrefix="";
        $scope.nextRef="";
        $scope.editForm = function(data) {
            $scope.typePrefix=data.typeprefix;
            $scope.formsetupId = data.formsetupId;
            $scope.nextRef=data.nextref;
            $("#edit_modal").modal('show');
        },function (error) {
            Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});

        };
        $scope.addNewFormSetupPopulate = function () {
            $scope.title = "Add FormSetup";
            $('#formsetup-title').text("Add ItemCategory");
            $("#submit").text("Save");
            $("#edit_modal").modal('show');
        }
        $scope.removeFormSetUp = function () {
            $scope.typename ="";
            $scope.typeprefix = "";
            $scope.nextref = "";
            $scope.currentre="";
            $scope.formsetupId = "";
            $scope.typeprefix = "";
            $scope.transtype = "";
        };

        $scope.BackServices=function(){
            window.location.href='#!/configuration';
        };

        $scope.saveOrUpdateformsetup= function () {
            if (angular.isUndefined($scope.typename)||$scope.typename === '') {
                Notification.warning({message: 'Type Name can not be empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.typeprefix)||$scope.typeprefix === '') {
                Notification.warning({message: 'Type Prefix can not be empty', positionX: 'center', delay: 2000});
            }
            else if (angular.isUndefined($scope.currentref)||$scope.currentref === '') {
                Notification.warning({message: 'Refernce No can not be empty', positionX: 'center', delay: 2000});
            }
            else {
                var saveFormSetupDetails;
                saveFormSetupDetails = {
                    typename: $scope.typename,
                    typeprefix: $scope.typeprefix,
                    nextref: $scope.currentref,
                    formsetupId: $scope.formsetupId,
                    typeprefix: $scope.typeprefix,
                    transactionType: $scope.transtype

                };
                $http.post($scope.bshimServerURL + '/saveformsetup', angular.toJson(saveFormSetupDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    } else {
                        $scope.removeFormSetUp();
                        $scope.formsetList();
                        $("#edit_modal").modal('hide');
                        Notification.success({
                            message: 'FormSetup Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                    }

                });
            }


        };
        $scope.editFormsetupPopulate = function (typename) {
            $http.post($scope.bshimServerURL + '/editFormSetupMethod?typeName='+typename).then(function (response) {
                var data = response.data;
                $scope.formsetupId = data.formsetupId;
                $scope.typename = data.typename;
                $scope.typeprefix = data.typeprefix;
                $scope.currentref = data.nextref;
                $scope.typeprefix = data.typeprefix;
                $scope.transtype = data.transactionType;
                $('#formsetup-title').text("Edit FormSetUP");
                $("#submit").text("Update");
                $("#edit_modal").modal('show');
            });
        }

        $scope.formsetList = function (page) {
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
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
            if (angular.isUndefined($scope.formSetupSearchText)) {
                $scope.formSetupSearchText = "";
            }
            $http.post($scope.bshimServerURL + '/getPaginatedFormsetupList?&searchText=' + $scope.formSetupSearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                $scope.formsetupList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
            },function (error){
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
        };
        $scope.formsetList();
        $scope.termForm = function (formsetupId,termsDesc) {
            console.log('termform :: ');
            console.log(formsetupId+' ### '+termsDesc);
            $scope.setupId = formsetupId;
            $scope.desc = termsDesc;
            $("#term_desc").modal('show');

        };

        $scope.saveFormsetupDesc = function(){
            var saveDescription;
            console.log($scope.desc);
            saveDescription = {
                termsDesc : $scope.desc,
                formsetupId:$scope.setupId
            };
            console.log('SAVE DESC ::');
            console.log(saveDescription);
            $http.post($scope.bshimServerURL +'/saveDescription', angular.toJson(saveDescription, "Create")).then(function (response) {
                $("#term_desc").modal('hide');
                var data = response.data;
                // $scope.formsetupList();
                $scope.formsetList();
            })
        };



        /***************ANJANA***************/

        $(function () {
            function initToolbarBootstrapBindings() {
                var fonts = ['Arial',
                        'Arial Black',
                        'Courier',
                        'Courier New',
                        'Comic Sans MS',
                        'Helvetica',
                        'Impact',
                        'Lucida Sans',
                        'Tahoma',
                        'Times',
                        'Times New Roman',
                        'Verdana'],
                    fontTarget = $('[title=Font]').siblings('.dropdown-menu');
                $.each(fonts, function (idx, fontName) {
                    fontTarget.append($('<li><a data-edit="fontName ' + fontName + '" style="font-family:\'' + fontName + '\'">' + fontName + '</a></li>'));
                });
                $('a[title]').tooltip({container: 'body'});
                $('.dropdown-menu input').click(function () {
                    return false;
                })
                    .change(function () {
                        $(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');
                    })
                    .keydown('esc', function () {
                        this.value = '';
                        $(this).change();
                    });

                $('[data-role=magic-overlay]').each(function () {
                    var overlay = $(this), target = $(overlay.data('target'));
                    overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
                });
                if ("onwebkitspeechchange" in document.createElement("input")) {
                    var editorOffset = $('#editor').offset();
                    $('#voiceBtn').css('position', 'absolute').offset({
                        top: editorOffset.top,
                        left: editorOffset.left + $('#editor').innerWidth() - 35
                    });
                } else {
                    $('#voiceBtn').hide();
                }
            };

            function showErrorAlert(reason, detail) {
                var msg = '';
                if (reason === 'unsupported-file-type') {
                    msg = "Unsupported format " + detail;
                } else {
                    console.log("error uploading file", reason, detail);
                }
                $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>File upload error</strong> ' + msg + ' </div>').prependTo('#alerts');
            }
            ;
            initToolbarBootstrapBindings();
            $('#editor').wysiwyg({fileUploadError: showErrorAlert});
            window.prettyPrint && prettyPrint();
        });

    }
);