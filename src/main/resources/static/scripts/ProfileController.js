app.controller('ProfileController', function ($scope, $route, $routeParams, $location, employeeService) {

    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;

    $scope.employee = {};
    $scope.request = {};

    var requestId = $routeParams.requestId;
    var selectedEmployeeId = $routeParams.selectedEmployeeId;

    employeeService.getEmployee(selectedEmployeeId);

    employeeService.registerObserver(this);

    this.notify = function() {
        $scope.employee = employeeService.currentEmployee; 
    }

    if ($scope.employee == undefined || (employeeService.currentEmployee !== undefined && employeeService.currentEmployee.id !== undefined && employeeService.currentEmployee.id !== $scope.employee.id)) {
        this.notify();
    }

    $scope.updateEmployee = function() {
        console.log("UPDATE");
        employeeService.updateEmployee($scope.employee);
    };

    $scope.filterTimeOff = function(request)
    {
        // Do some tests
    
        if(request.type === 'AnnualLeave' || request.type === 'SickLeave')
        {
            return true; // this will be listed in the results
        }
    
        return false; // otherwise it won't be within the results
    };

    $scope.currentRequest = function() {

        var currentRequest = {};

        angular.forEach($scope.employee.requests, function(request) {
            if (request.id == requestId)
            {
                currentRequest = request;
            }
        });

        return currentRequest;
    }

    $scope.isApproved = function() { return false; }

    $scope.requestTypeStringTransformer = function(request) {
        switch(request.type) {
            case 'AnnualLeave':
                return 'Annual leave'
            case 'SickLeave':
                return 'Sick leave';
            case 'SelfTermination':
                return 'Self-termination';
            default:
                return request.type;
        }
    }

    $scope.submitTimeOffRequest = function(newrequest) {
        employeeService.submitTimeOffRequest(newrequest).then(function() {
            employeeService.getEmployee($routeParams.employeeId);
            window.location = "#!/employee/timeoff";
        });
    }

    $scope.leaveFilter = function(item) { 
        switch(request.type) {
            case 'AnnualLeave':
            case 'AnnualLeaveBeyondAccrual':
            case 'SickLeave':
            case 'SickLeaveBeyondAccrual':
                return true;
            default:
                return false;
        }
    }
});