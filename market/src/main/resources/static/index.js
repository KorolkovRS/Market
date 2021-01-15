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

    $scope.fillTable();
});
