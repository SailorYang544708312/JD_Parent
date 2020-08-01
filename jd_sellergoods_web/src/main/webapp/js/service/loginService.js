app.service("loginService",function ($http) {
    //读取Security中的当前登录的用户
    this.loginName = function () {
        return $http.get("../login/name");
    }
});