app.controller('EmployeeHomeController', function ($scope, $route, $routeParams, $location, employeeService) {
    employeeService.registerObserver(this);

    $scope.employee = {};

    employeeService.getEmployee($routeParams.employeeId).then(function() {
        $scope.employee = employeeService.employee;
    });

    this.notify = function() {
        $scope.employee = employeeService.employee;
    }
});