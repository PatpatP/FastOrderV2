var app = angular.module('fastOrder', []);

app.controller('mainCtrl', function ($scope, $http, $window, $q) {
	$scope.filtre;
	$scope.prixTotal=0;
	$scope.shopsList = [];
	$scope.products = [];
	$scope.bracket = [];
	$scope.bracketId = [];
	$scope.listErrors = [];
	$scope.lisAdress = [];
	$scope.size = 0;
	$scope.searchHome = "";
	$scope.bracketTmp = [];

	$scope.prepareReset = function(params){
		var deferred = $q.defer();
		if(params!=null){
			deferred.resolve(params);
		}else{
			deferred.reject('KO');
		}
		return deferred.promise;
	}
	
	$scope.resetBracket = function(params){
		var tmp = [];
		var promise = $scope.prepareReset(params);
		promise.then(function(data) {
			tmp = data;
			$scope.bracket = [];
			$scope.bracketId = [];
			$scope.size = 0;
			$window.sessionStorage.removeItem('price');
			$window.sessionStorage.removeItem('bracket');
			$window.sessionStorage.removeItem('bracketId');
			$window.sessionStorage.removeItem('size');
			location.href="validateBracket?bracket=["+tmp+"]";
			console.log(tmp);
		}, function(err) {
		   console.log("Erreur : ", err);
		});
	}
	
	$scope.getBracket = function(){
		if($window.sessionStorage != null){
			$scope.bracketStorage = JSON.parse($window.sessionStorage.bracket);
			$scope.bracket = JSON.parse($window.sessionStorage.bracket);
			$scope.prixTotal = JSON.parse($window.sessionStorage.price);
			$scope.size = JSON.parse($window.sessionStorage.size);
		}else{
			$scope.bracketStorage = [];
		}
	}
	
	
	var i=0;
	
	$scope.getAdress = function(listadress){
		$scope.lisAdress = listadress;
	}
	
	$scope.setShops = function(listShop){
		$scope.shopsList = listShop;
		console.log("Shop : ", listShop);
	}
	
	$scope.setErrors = function(errors){
		$scope.listErrors = errors;
	}
	
	$scope.setProducts = function(listProduct){
		$scope.products = listProduct;
	}
	
	$scope.addToBracket = function(id, name, price){
		var indice=-1, pos;
		$scope.prixTotal = $scope.prixTotal + parseFloat(price, 10);
		
		if($scope.bracket.length > 0){
			for(pos=0; pos<$scope.bracket.length;pos++){
				if($scope.bracket[pos].id === id){
					indice = pos;
					break;
				}
			}
		}
		
		$scope.bracketId[i]=id;
		i += 1;
		
		if(indice !== -1){
			$scope.bracket[indice].quantity += 1;
			$scope.size = $scope.size + 1;
		}else{
			$scope.bracket.push({
				name:name,
				id:id,
				price:price,
				quantity:1
			});	
			$scope.size = $scope.size + 1;
		}
	
		$window.sessionStorage.setItem('bracket', JSON.stringify($scope.bracket));
		$window.sessionStorage.setItem('bracketId', JSON.stringify($scope.bracketId));
		$window.sessionStorage.setItem('price', JSON.stringify($scope.prixTotal));
		$window.sessionStorage.setItem('size', JSON.stringify($scope.size));
		$scope.bracketStorage = JSON.parse($window.sessionStorage.bracket);
		$scope.size = JSON.parse($window.sessionStorage.size);
		
	}
	
	$scope.removeProduct = function(id){
		var index;
		for(pos=0; pos<$scope.bracket.length;pos++){
			if($scope.bracket[pos].id === id){
				index = pos;
				break;
			}
		}
		
		if(index >= 0){
			if($scope.bracket[index].quantity > 1){
				$scope.bracket[index].quantity -= 1; 
				$scope.prixTotal = $scope.prixTotal - $scope.bracket[index].price;
				$scope.bracketId[index]=-1;
				$scope.size = $scope.size - 1;
			}else{
				$scope.prixTotal = $scope.prixTotal - $scope.bracket[index].price;
				$scope.bracket.splice(index,1);
				$scope.bracketId[index]=-1;
				$scope.size = $scope.size - 1;
			}
			
			$window.sessionStorage.setItem('bracket', JSON.stringify($scope.bracket));
			$window.sessionStorage.setItem('bracketId', JSON.stringify($scope.bracketId));
			$window.sessionStorage.setItem('price', JSON.stringify($scope.prixTotal));
			$window.sessionStorage.setItem('size', JSON.stringify($scope.size));
			$scope.bracketStorage = JSON.parse($window.sessionStorage.bracket);
			$scope.size = JSON.parse($window.sessionStorage.size);
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
	}
	
	$scope.setInfoUser = function(users){
		$scope.infoUser = users;
		console.log(users);
	}
	
	$scope.setShops = function(shops){
		$scope.listShop = shops;
	}
	
	$scope.showOrder = function(event){
		$('.tabBrackets').toggle();
	}
	
	$scope.manageShopProducts = function(listProduct){
		$scope.listProducts = listProduct;
	}
	
	$scope.setUserOrders = function(listOrders){
		$scope.userOrders = listOrders;
	}
	
	$scope.setShopOrders = function(listOrders){
		$scope.shopOrders = listOrders;
	}
	
});
