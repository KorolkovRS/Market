angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8888/market';
    $scope.currentPage = 0;
    $scope.productsPerPage = 5;

    $scope.fillTable = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
//            params: {
//                min_price: $scope.filter ? $scope.filter.min_price : null,
//                max_price: $scope.filter ? $scope.filter.max_price : null
//            }
        }).then(function (response) {
            $scope.ProductsList = response.data;
            $scope.pageCounter = Math.ceil($scope.ProductsList.length / $scope.productsPerPage);
            $scope.Products = $scope.ProductsList.slice($scope.currentPage * $scope.productsPerPage, ($scope.currentPage + 1) * $scope.productsPerPage);
        });
    };

        $scope.deleteProductById = function (id) {
                console.log('deleteProduct' + id);
                $http({
                    url: contextPath + '/products/delete/' + id,
                    method: 'DELETE'
                }).then(function(response) {
                $scope.fillTable();
                });
            };

    $scope.nextPage = function () {
        if($scope.currentPage < $scope.pageCounter - 1) {
             $scope.currentPage++;
        }
        $scope.Products = $scope.ProductsList.slice($scope.currentPage * $scope.productsPerPage, ($scope.currentPage + 1) * $scope.productsPerPage);
     };

    $scope.prevPage = function () {
        if($scope.currentPage > 0) {
            $scope.currentPage--;
        }
        $scope.Products = $scope.ProductsList.slice($scope.currentPage * $scope.productsPerPage, ($scope.currentPage + 1) * $scope.productsPerPage);
     };


    $scope.fillTable();
});
