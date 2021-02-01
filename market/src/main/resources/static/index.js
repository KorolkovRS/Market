angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8888/market/api/v1';

    $scope.fillTable = function (pageIndex) {
        $http({
            url: contextPath + '/products',
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
                    url: contextPath + '/products/delete/' + id,
                    method: 'DELETE'
                }).then(function(response) {
                $scope.fillTable($scope.currentPage);
                });
            };

    $scope.submitCreateNewProduct = function () {
            $http.post(contextPath + '/products', $scope.newProduct)
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
                 url: contextPath + '/carts',
                 method: 'GET'
             }).then(function (response) {
                 $scope.Cart = response.data;
             });
         };

     $scope.addToCart = function (productId) {
             $http.get(contextPath + '/carts/add/' + productId)
                 .then(function (response) {
                     $scope.showCart();
                 });
        }

$scope.clearCart = function () {
        $http.get(contextPath + '/carts/clear')
            .then(function (response) {
                $scope.showCart();
            });
    }

$scope.decProduct = function (productId) {
        $http.get(contextPath + '/carts/delete/' + productId)
            .then(function (response) {
                $scope.showCart();
            });
    }

    $scope.fillTable();
});




//angular.module('app', []).controller('indexController', function ($scope, $http) {
//    const contextPath = 'http://localhost:8189/happy/api/v1';
//
//    $scope.fillTable = function (pageIndex = 1) {
//        $http({
//            url: contextPath + '/products',
//            method: 'GET',
//            params: {
//                title: $scope.filter ? $scope.filter.title : null,
//                min_price: $scope.filter ? $scope.filter.min_price : null,
//                max_price: $scope.filter ? $scope.filter.max_price : null,
//                p: pageIndex
//            }
//        }).then(function (response) {
//            $scope.ProductsPage = response.data;
//
//            let minPageIndex = pageIndex - 2;
//            if (minPageIndex < 1) {
//                minPageIndex = 1;
//            }
//
//            let maxPageIndex = pageIndex + 2;
//            if (maxPageIndex > $scope.ProductsPage.totalPages) {
//                maxPageIndex = $scope.ProductsPage.totalPages;
//            }
//
//            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
//        });
//    };
//
//    $scope.showCart = function () {
//        $http({
//            url: contextPath + '/cart',
//            method: 'GET'
//        }).then(function (response) {
//            $scope.Cart = response.data;
//        });
//    };
//
//    $scope.generatePagesIndexes = function(startPage, endPage) {
//        let arr = [];
//        for (let i = startPage; i < endPage + 1; i++) {
//            arr.push(i);
//        }
//        return arr;
//    }
//
//    $scope.submitCreateNewProduct = function () {
//        $http.post(contextPath + '/products', $scope.newProduct)
//            .then(function (response) {
//                $scope.newProduct = null;
//                $scope.fillTable();
//            });
//    };
//
//    $scope.deleteProductById = function (productId) {
//        $http.delete(contextPath + '/products/' + productId)
//            .then(function (response) {
//                $scope.fillTable();
//            });
//    }
//
//    $scope.addToCart = function (productId) {
//        $http.get(contextPath + '/cart/add/' + productId)
//            .then(function (response) {
//                $scope.showCart();
//            });
//    }
//
//    $scope.clearCart = function () {
//        $http.get(contextPath + '/cart/clear')
//            .then(function (response) {
//                $scope.showCart();
//            });
//    }
//
//    $scope.fillTable();
//    $scope.showCart();
//});
