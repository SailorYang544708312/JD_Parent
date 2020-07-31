 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller,brandService,specificationService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		typeTemplateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(response){
				$scope.entity= response;
				$scope.entity.brandIds = JSON.parse($scope.entity.brandIds);//将字符串转换成对象
				$scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);
				$scope.entity.specIds = JSON.parse($scope.entity.specIds);
			}
		);
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			//从前端调试页面可以看出添加的时候 品牌，规格和扩展属性 都是一个数组形式 是不可以添加到数据库的
			//需要将其装换成string格式
			$scope.entity.brandIds = JSON.stringify($scope.entity.brandIds);//将对象转换成字符串
			$scope.entity.customAttributeItems = JSON.stringify($scope.entity.customAttributeItems);
			$scope.entity.specIds = JSON.stringify($scope.entity.specIds);
			serviceObject=typeTemplateService.add( $scope.entity);//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){
		//获取选中的复选框			
		typeTemplateService.dele($scope.selectIds).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//格式列子: $scope.brandList = {data:[{id: 1,text: '三星'},{id: 2,text: '华为'},{id: 3,text: '小米'}]};
	//定义品牌列表的select2需要用到的数据
	$scope.brandList = {data: []};
	//定义规格列表的select2格式
	$scope.specificationList = {data: []};


    //从后台抓取品牌列表数据
	$scope.findBrandList = function () {
		brandService.selectBrandList().success(function (response) {
			$scope.brandList = {data: response};
		});
	}

	//从后台抓取规格列表数据
	$scope.findSpecificationList = function () {
		specificationService.selectSpecificationList().success(function (response) {
			$scope.specificationList = {data:response};
		})
	}


	//新增扩展属性
	$scope.addExtendRow = function () {
		$scope.entity.customAttributeItems.push({});
	}

	//删除一行扩展熟悉
	$scope.delExtendRow = function (index) {
		$scope.entity.customAttributeItems.splice(index,1);
	}

	//对于json字符串，通过写一个方法只将指定的key的value取出，最后拼接成一个字符串
	//参数一 一个json字符串  参数二 字符串里面的key
	$scope.jsonToString = function (jsonString,key) {
		//将json字符串转成一个json对象
		var json =  JSON.parse(jsonString);
		var value = "";
		for (var i = 0; i < json.length; i++) {
			if (i>0){
				value += "; ";
			}
			value += json[i][key];
		}
		return value;
	}
});	
