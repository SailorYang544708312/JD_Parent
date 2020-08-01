app.controller("indexController",function ($scope,loginService) {
    //读取Security中当前登录的用户名
    $scope.showLoginName = function () {
        loginService.loginName().success(function (response) {
            $scope.loginName = response.loginName;
        });
    }
});