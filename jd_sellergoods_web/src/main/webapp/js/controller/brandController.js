//自定义控制器
app.controller("brandController",function ($scope,$controller,brandService) {
    //控制器继承 引入baseController
    //$controller是angularJs的内置服务，通过这个内置服务可以实现伪继承--实际上就是与baseController共享$scope
    $controller("baseController",{$scope:$scope});

    //读取数据库表绑定到表单中
    $scope.findAll = function () {
        brandService.findAll().success(function (response) {
            $scope.list = response;
        });
    }


    $scope.findPage = function (pageNum,pageSize) {
        brandService.findPage(pageNum,pageSize).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems=response.total;
        })
    }

    //保存
    $scope.save = function () {
        var object = null;
        if ($scope.entity.id != null){
            object = brandService.update($scope.entity);
        }else {
            object = brandService.add($scope.entity);
        }

        object.success(function (response) {
            if (response.success){
                $scope.reloadList();
            }else {
                alert(response.message);
            }
        });
    }

    //修改
    $scope.findOne  = function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    }

    //删除
    $scope.delete = function () {
        brandService.delete($scope.selectIds).success(function (response) {
            if (response.success){
                $scope.reloadList();
                $scope.selectIds = [];
                alert(response.message);
            }else {
                alert(response.message);
            }
        })
    }

    //条件查询的对象
    $scope.searchEntity = {};
    //条件查询
    $scope.search = function (pageNum,pageSize) {
        brandService.search(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        });
    }
});