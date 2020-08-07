app.controller("baseController",function ($scope) {
    //切换页面的时候，更新数据
    $scope.reloadList = function(){
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }

    /*currentPage: 当前页码
    totalItems:	总记录数
    itemsPerPage:	每页显示的记录条数
    perPageOptions:	每页显示多少条数据的消息
    onChange:	当前页面发生变化时执行的事件
    */
    $scope.paginationConf={
        currentPage:1,
        totalItems:10,
        itemsPerPage:10,
        perPageOptions:[10,20,30,40,50],
        onChange:function () {
            $scope.reloadList();
        }
    }

    //定义选中的数组
    $scope.selectIds = [];

    //当选择后，更新数组
    $scope.updateSelection = function ($event,id) {
        if ($event.target.checked){
            $scope.selectIds.push(id);
        }else {
            var idx = $scope.selectIds.indexOf(id);//得到要删除的id在数组中的下标
            //根据下标删除数组中的元素
            $scope.selectIds.splice(idx,1);
        }
    }

    //判断前端页面查询集合中key是否存在
    //设计格式例 [{"attributeName":"网络","attributeValue":["移动3G","联通4G"]}，{"id":35,"text":"机身内存",options:["16G","32G","64G"]}]
    $scope.searchObjectByKey = function (list,key,value) {
        for (var i = 0; i < list.length; i++) {
            if (list[i][key]==value){
                return list[i];
            }
        }
        return null;
    }
});