 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			serviceObject=itemCatService.add( $scope.entity  );//增加 
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
		itemCatService.dele( $scope.selectIds ).success(
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
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//根据上级id查询下级列表
	$scope.findByParentId = function (parentId) {
		itemCatService.findByParentId(parentId).success(function (response) {
			$scope.list = response;
		})
	}

	//面包屑(定义 默认是一级分类)
	$scope.classify = 1;

	//定义变更级别的方法
	$scope.setClassify = function (value) {
		$scope.classify = value;
	}

	//读取列表
	$scope.selectList = function (itemCat) {
		//如果是1级
		if ($scope.classify == 1){
			$scope.itemCat_1 = null;
			$scope.itemCat_2 = null;
		}

		//如果是2级
		if ($scope.classify == 2){
			$scope.itemCat_1 = itemCat;
			$scope.itemCat_2 = null;
		}

		//如果是3级
		if ($scope.classify == 3){
			$scope.itemCat_2 = itemCat;
		}

		//查询下一级
		$scope.findByParentId(itemCat.id);
	}
    
});	
