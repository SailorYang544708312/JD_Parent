app.service("uploadService",function ($http) {

    //默认的Content-Type是application/json  设置undefined后就可以上传附件 类型就是multipart/form-data
    //transformRequest:angular.identity  将表单提交的数据进行二进制序列化 （如果是图片，音乐，等文件必须采用）
    this.uploadFile=function () {
        //创建表单数据对象
        var formData = new FormData();
        formData.append("file",file.files[0]);
        return $http({
           method:'post',
           url:'../upload',
           data:formData,
           headers:{'Content-Type':undefined},
           transformRequest:angular.identity
        });
    }
});