angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8888/market';
    $scope.authorized = false;
    $scope.currentUser;

    $scope.fillTable = function (pageIndex) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                p: pageIndex
            }
        }).then(function (response) {
            $scope.currentPage = pageIndex;
            $scope.ProductsPage = response.data;
            $scope.PaginationArray = $scope.generatePagesIndex(1, $scope.ProductsPage.totalPages);
            });
    };

        $scope.deleteProductById = function (id) {
                $http({
                    url: contextPath + '/api/v1/products/delete/' + id,
                    method: 'DELETE'
                }).then(function(response) {
                $scope.fillTable($scope.currentPage);
                });
            };

    $scope.submitCreateNewProduct = function () {
            $http.post(contextPath + '/api/v1/products', $scope.newProduct)
                .then(function (response) {
                    $scope.newProduct = null;
                    $scope.fillTable($scope.currentPage);
                });
        };

    $scope.generatePagesIndex = function (startPage, endPage) {
        let arr = [];
        for (i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
     };

     $scope.showCart = function () {
             $http({
                 url: contextPath + '/api/v1/carts',
                 method: 'GET'
             }).then(function (response) {
                 $scope.Cart = response.data;
             });
         };

     $scope.addToCart = function (productId) {
             $http.get(contextPath + '/api/v1/carts/add/' + productId)
                 .then(function (response) {
                     $scope.showCart();
                 });
        }

$scope.clearCart = function () {
        $http.get(contextPath + '/api/v1/carts/clear')
            .then(function (response) {
                $scope.showCart();
            });
    }

$scope.decProduct = function (productId) {
        $http.get(contextPath + '/api/v1/carts/delete/' + productId)
            .then(function (response) {
                $scope.showCart();
            });
    }

    $scope.tryToAuth = function () {
            $http.post(contextPath + '/auth', $scope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $scope.currentUser = $scope.user.username;
                        $scope.user.username = null;
                        $scope.user.password = null;
                        $scope.authorized = true;
                        $scope.fillTable();
                    }
                }, function errorCallback(response) {
                    window.alert("Error");
                });
        };


});