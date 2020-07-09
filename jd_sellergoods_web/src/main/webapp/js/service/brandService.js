//自定义服务
app.service("brandService",function ($http) {
    //读取列表数据
    this.findAll = function () {
        return $http.get("../brand/show");
    }

    //分页查询
    this.findPage = function (pageNum,pageSize) {
        return $http.get("../brand/findPage?pageNum="+pageNum+"&pageSize="+pageSize);
    }

    //添加
    this.add = function (entity) {
        return $http.post("../brand/add",entity);
    }

    //修改
    this.update = function (entity) {
        return $http.post("../brand/update",entity);
    }

    this.findOne = function (id) {
        return $http.get("../brand/findOne?id="+id);
    }

    this.delete = function (ids) {
        return $http.get("../brand/delete?ids="+ids);
    }

    this.search = function (pageNum,pageSize,searchEntity) {
        return $http.post("../brand/search?pageNum="+pageNum+"&pageSize="+pageSize,searchEntity);
    }
});