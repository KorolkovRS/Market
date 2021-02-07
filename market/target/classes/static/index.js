angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8888/market';
    $scope.authorized = false;
    $scope.currentUser;
    $scope.repeatPassword;

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

                        $localStorage.marketUsername = $scope.user.username;
                        $localStorage.marketTokenWithBearerPrefix = 'Bearer ' + response.data.token;

                        $scope.currentUser = $localStorage.marketUsername;
                        $scope.user.username = null;
                        $scope.user.password = null;
                        $scope.authorized = true;

                        $scope.fillTable();
                        $scope.updateOrders();
                        $scope.showCart();

                    }
                }, function errorCallback(response) {
                    window.alert("Error");
                });
        };

$scope.createOrder = function (address) {
        $http.post(contextPath + '/api/v1/order/createOrder', address)
            .then(function successCallback(response) {
                $scope.showCart();
                $scope.updateOrders();
             }, function errorCallback(response) {
                    window.alert("Error");
            });
    }

$scope.updateOrders = function () {
        $http.get(contextPath + '/api/v1/order/allOrders')
            .then(function (response) {
                $scope.Orders = response.data;

            });
    }

$scope.logout = function () {
        $http.get(contextPath + '/logout')
        $http.defaults.headers.common.Authorization = null;
        delete $localStorage.marketUsername;
        delete $localStorage.marketTokenWithBearerPrefix;
        $scope.authorized = false;
    }

    $scope.registerNewUser = function () {
            if($scope.newUser.password == $scope.repeatPassword) {
                $http.post(contextPath + '/addUser', $scope.newUser)
                .then(function successCallback(response) {
                        $scope.user = new Object();
                        $scope.user.username = $scope.newUser.username;
                        $scope.user.password = $scope.newUser.password;
                        $scope.newUser = null;
                        $scope.repeatPassword = null;
                        $scope.tryToAuth();
                }, function errorCallback(response) {
                    window.alert("Error");
                });
            } else {
                window.alert("Error");
            }
        };

     if ($localStorage.marketUsername) {
                 $http.defaults.headers.common.Authorization = $localStorage.marketTokenWithBearerPrefix;
                 $scope.currentUser = $localStorage.marketUsername;
                 $scope.fillTable();
                 $scope.updateOrders();
                 $scope.showCart();
                 $scope.authorized = true;
             }
    
});