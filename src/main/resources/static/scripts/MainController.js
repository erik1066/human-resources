app.controller('MainController', function ($scope, $route, $routeParams, $location, employeeService) {

    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;

    employeeService.registerObserver(this);
    
    $scope.currentEmployee = {};
    $scope.selectedEmployee = {};

    var currentRole = "Employee";

    $scope.setCurrentUserRole = function(role) {
        
        currentRole = role;
        if (currentRole === 'Sysadmin')
        {
            window.location = "#!/sysadmin/search";
        }
        if (currentRole === 'Employee')
        {
            window.location = "#!/employee";
        }
        if (currentRole === 'Manager')
        {
            window.location = "#!/manager";
        }
        if (currentRole === 'HR')
        {
            window.location = "#!/hr";
        }
    }

    $scope.getCurrentUserRole = function () {
        return currentRole;
    }

    employeeService.getCurrentEmployee(); //.then(function() {
        // $scope.currentEmployee = employeeService.currentEmployee;
    //});
    this.notify = function() {
        $scope.currentEmployee = employeeService.currentEmployee;
        $scope.selectedEmployee = employeeService.employee;
        
        // console.log($scope.currentEmployee.id);
        // console.log($scope.selectedEmployee.id);
    }
});