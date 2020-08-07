 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,uploadService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
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
		goodsService.dele( $scope.selectIds ).success(
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
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//不用上面的save方法做添加 新写一个add方法
	$scope.add = function () {
		//获取富文本编辑器中的内容
		$scope.entity.goodsDesc.introduction = editor.html();
		//将json格式转成string
		$scope.entity.goodsDesc.itemImages = JSON.stringify($scope.entity.goodsDesc.itemImages);
		//商品扩展属性转成json字符串
		$scope.entity.goodsDesc.customAttributeItems = JSON.stringify($scope.entity.goodsDesc.customAttributeItems);
		//商品的规格详情转成json字符串
		$scope.entity.goodsDesc.specificationItems = JSON.stringify($scope.entity.goodsDesc.specificationItems);
		goodsService.add($scope.entity).success(function (response) {
			if (response.success){
				//新增后清空添加框中的信息
				alert(response.message);
				//清除富文本编辑器中的内容
				editor.html("");
				$scope.entity = {goods:{},goodsDesc:{}};

			}else {
				alert(response.message);
			}
		});
	}

	//上传图片
	$scope.uploadFile = function () {
		uploadService.uploadFile().success(function (response) {
			if (response.success){
				//成功
				$scope.image_entity.url = response.obj;
			}else {
				alert(response.message);
			}
		}).error(function () {
			alert("上传出错");
		});
	}

	//定义页面实体结构
	$scope.entity = {goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};

	//添加图片列表
	$scope.add_image_entity = function () {
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	}

	//列表中移除图片
	$scope.remove_image_entity = function (index) {
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}

	//读取一级分类
	$scope.selectItemCat1List = function () {
		itemCatService.findByParentId(0).success(function (response) {
			$scope.itemCat1List = response;
		})
	}

	//当一级分类发生改变的时候，查询二级分类
	//$watch 可以监听（监听的值，方法（新值，旧值））
	$scope.$watch("entity.goods.category1Id",function (newValue,oldValue) {
		//alert("新"+newValue+"旧的"+oldValue);
		//根据选择的一级分类来查询二级分类
		itemCatService.findByParentId(newValue).success(function (response) {
			$scope.itemCat2List = response;
		})
	});

	//当二级分类发生改变的时候，查询三级分类
	$scope.$watch("entity.goods.category2Id",function (newValue,oldValue) {
		itemCatService.findByParentId(newValue).success(function (response) {
			$scope.itemCat3List = response;
		})
	});

	//当三级分类选中后，读取模板id
	$scope.$watch("entity.goods.category3Id",function (newValue,oldValue) {
		itemCatService.findOne(newValue).success(function (response) {
			$scope.entity.goods.typeTemplateId =response.typeId;
		});
	});

	//得到模板id后，查询到品牌列表
	$scope.$watch("entity.goods.typeTemplateId",function (newValue,oldValue) {
		typeTemplateService.findOne(newValue).success(function (response) {
			//获取到类型模板
			$scope.typeTemplate = response;
			//通过类型模板，得到品牌列表
			$scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
			//通过类型模板，得到扩展属性列表
			$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
		});

		//一旦确定了模板id 就可以通过模板id查询出 关联的规格 和 规格选项
		typeTemplateService.findSpecList(newValue).success(function (response) {
			$scope.specList = response;
		})
	});

	//当勾选规格列表的时候，更新数组
	$scope.updateSpecAttribute = function ($event,name,value) {
		var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,"attributeName",name);
		if (object!=null){
			//表示集合存在 那么就继续往 集合里面加
			if ($event.target.checked){
				//如果勾选才加
				object.attributeValue.push(value);
			}else {
				//如果取消勾选 就移除
				object.attributeValue.splice(object.attributeValue.indexOf(value),1);
				//如果所有的选项都取消了 那么要移除所有的记录
				if (object.attributeValue.length ==0){
					$scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object,1));
				}
			}

		}else {
			//集合不存在 我们就创建新的集合模板
			$scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
			//格式例子:
			//[{"attributeName":"网络","attributeValue":["移动3G","联通4G"]}]
		}
	}


	//创建sku列表
	$scope.createItemList = function () {
		//定义sku列表(初始化)  price价格 num库存 status商品状态[0-请求审核 1-正常，2-下架，3-删除]  idDefault是否设置默认展示[0-否 1-是]
		$scope.entity.itemList = [{spec:{},price:0,num:0,status:"0",isDefault:"0"}];
		var items = $scope.entity.goodsDesc.specificationItems;
		for (var i = 0; i <items.length ; i++) {
			//深克隆
			$scope.entity.itemList = addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
		}
	}

	//（用于深克隆）添加咧
	addColumn = function (list,columnName,columnValues) {
		var newList = [];
		for (var i = 0; i < list.length; i++) {
			var oldRow = list[i];
			for (var j = 0; j < columnValues.length; j++) {
				//深克隆就是把老的行先转成字符串 再转成json对象 就完成了深克隆
				var newRow =  JSON.parse(JSON.stringify(oldRow));
				newRow.spec[columnName] = columnValues[j];
				newList.push(newRow);
			}
		}
		return newList;
	}

});	
