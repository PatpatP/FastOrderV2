var app = angular.module('fastOrder', []);

app.controller('mainCtrl', function ($scope, $http, $window) {
	$scope.filtre;
	$scope.prixTotal=0;
	$scope.shopsLists = [];
	$scope.products = [];
	$scope.bracket = [];
	$scope.bracketId = [];
	$scope.listErrors = [];
	$scope.lisAdress = [];
	
	$scope.getBracket = function(){
		if($window.sessionStorage != null){
			$scope.bracketStorage = $window.sessionStorage.bracket;
		}else{
			$scope.bracketStorage = [];
		}
	}
	
	
	
	var i=0;
	
	$scope.getAdress = function(listadress){
		$scope.lisAdress = listadress;
		console.log("Liste Adress : ", listadress);
	}
	
	$scope.setShops = function(listShop){
		$scope.shopsList = listShop;
	}
	
	$scope.setErrors = function(errors){
		$scope.listErrors = errors;
		console.log('Error : ', $scope.listErrors);
	}
	
	$scope.setProducts = function(listProduct){
		console.log("produits : ", listProduct);
		$scope.products = listProduct;
	}
	
	$scope.addToBracket = function(id, name, price){
		var indice=-1, pos;
		$scope.prixTotal = $scope.prixTotal + parseFloat(price, 10);
		
		if($scope.bracket.length > 0){
			for(pos=0; pos<$scope.bracket.length;pos++){
				if($scope.bracket[pos].id == id){
					indice = pos;
					break;
				}
			}
		}
		
		$scope.bracketId[i]=id;
		i += 1;
		
		if(indice != -1){
			$scope.bracket[indice].quantity += 1;
		}else{
			$scope.bracket.push({
				name:name,
				id:id,
				price:price,
				quantity:1
			});			
		}
	
		$window.sessionStorage.setItem('bracket', JSON.stringify($scope.bracket));
		$scope.bracketStorage = JSON.parse($window.sessionStorage.bracket);
		console.log("bracketStorage : ",$scope.bracketStorage);
		
	}
	
	$scope.removeProduct = function(id){
		var index;
		for(pos=0; pos<$scope.bracket.length;pos++){
			if($scope.bracket[pos].id == id){
				index = pos;
				break;
			}
		}
		
		if(index >= 0){
			if($scope.bracket[index].quantity > 1){
				$scope.bracket[index].quantity -= 1; 
				$scope.prixTotal = $scope.prixTotal - $scope.bracket[index].price;
				$scope.bracketId[index]=-1;
			}else{
				$scope.prixTotal = $scope.prixTotal - $scope.bracket[index].price;
				$scope.bracket.splice(index,1);
				$scope.bracketId[index]=-1;
			}
			
			$window.sessionStorage.setItem('bracket', JSON.stringify($scope.bracket));
			$scope.bracketStorage = JSON.parse($window.sessionStorage.bracket);
			console.log("bracketStorage : ",$scope.bracketStorage);
		}
		
	}
	
});

app.controller('myspaceCtrl', function ($scope, $http) {
	$scope.listShop =[];
	$scope.infoUser = [];
	$scope.listProducts = [];
	$scope.shopType = [];
	$scope.userOrders = [];
	$scope.shopOrders = [];
	$scope.idProductToDelete;
	
	$scope.setIdProductToDelete = function(id){
		$scope.idProductToDelete = id;
	}
	
	$scope.getShopType = function(listType){
		$scope.shopType = listType;
		console.log("listType : ", listType);
	}
	
	$scope.setInfoUser = function(users){
		$scope.infoUser = users;
	}
	
	$scope.setShops = function(shops){
		$scope.listShop = shops;
		console.log("listShop : ", $scope.listShop);
	}
	
	$scope.showOrder = function(event){
		$('.tabBrackets').toggle();
	}
	
	$scope.manageShopProducts = function(listProduct){
		$scope.listProducts = listProduct;
		console.log('listProducts : ', listProduct);
	}
	
	$scope.setUserOrders = function(listOrders){
		$scope.userOrders = listOrders;
		console.log('user orders : ', $scope.userOrders);
	}
	
	$scope.setShopOrders = function(listOrders){
		$scope.shopOrders = listOrders;
		console.log('shop orders : ', $scope.shopOrders);
	}
	
});
