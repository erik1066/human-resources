app.controller('PerformanceController', function ($scope, $route, $routeParams, $location, employeeService) {

    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;

    var reviewId = $routeParams.reviewId;

    $scope.review = {};
    $scope.employee = {};

    employeeService.registerObserver(this);

    this.notify = function() {
        $scope.employee = employeeService.currentEmployee;

        if (reviewId !== undefined)
        {
            //console.log(reviewId);
            angular.forEach($scope.employee.reviews, function(review, index) 
            {
                console.log(reviewId);
                console.log(review.id);
                if (review.id == reviewId)
                {
                    $scope.review = review;
                }
            });
        }
    }

    if ($scope.employee == undefined || (employeeService.currentEmployee !== undefined && employeeService.currentEmployee.id !== undefined && employeeService.currentEmployee.id !== $scope.employee.id)) {
        this.notify();
    }
});