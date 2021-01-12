angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8888/market';
    $scope.productsPerPage = 5;

    $scope.fillTable = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.currentPage = 0;
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

    $scope.submitCreateNewProduct = function () {
            $http.post(contextPath + '/products', $scope.newProduct)
                .then(function (response) {
                    $scope.newProduct = null;
                    $scope.fillTable();
                });
        };

    $scope.nextPage = function () {
        $scope.currentPage++;
        $scope.Products = $scope.ProductsList.slice($scope.currentPage * $scope.productsPerPage, ($scope.currentPage + 1) * $scope.productsPerPage);
     };

    $scope.prevPage = function () {
        $scope.currentPage--;
        $scope.Products = $scope.ProductsList.slice($scope.currentPage * $scope.productsPerPage, ($scope.currentPage + 1) * $scope.productsPerPage);
     };

     $scope.firstPage = function () {
        return $scope.currentPage <= 0;
     }

     $scope.lastPage = function () {
        return $scope.currentPage >= $scope.pageCounter - 1;
     }

    $scope.fillTable();
});
