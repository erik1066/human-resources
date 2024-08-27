app.controller('ManagerController', function ($scope, $route, $routeParams, $location, $window, employeeService) {
    console.log("Manager Controller initialized");
        $scope.sortType     = 'Rank'; // set the default sort type
        $scope.sortReverse  = false;  // set the default sort order
        $scope.searchEmployee   = '';     // set the default search/filter term

        $scope.employee = {};

        var selectedEmployeeId = $routeParams.selectedEmployeeId;

        employeeService.getEmployee(selectedEmployeeId);

        employeeService.registerObserver(this);
        
        this.notify = function() {
            $scope.employee = employeeService.employee; 
        }
        
        $scope.name = [
          { id : 'f1c0641e-9c3b-4a25-a1e6-8e22c11d3e3c', FirstName: 'Chet', LastName: 'Dawson', Department: "HR", Rank: 6 },
          { FirstName: 'L', LastName: 'J', Department: "HR", Rank: 2 },
          { FirstName: 'E', LastName: 'K', Rank: 1 },
          { FirstName: 'R', LastName: 'C', Rank: 4 },
          { FirstName: 'S', LastName: 'B', Rank: 3 },
          { FirstName: 'N', LastName: 'P', Rank: 5 }
        ];

        $scope.evaluations = function() {
          $location.url('/manager/Evaluations');
        }
        $scope.EmployeeProfile = function() {
          $location.url('/manager/EmployeeProfile');
        }
        $scope.SalaryChange = function() {
          $location.url('/manager/SalaryAdjustments');
        }
        $scope.DeleteRequest = function() {
            $window.alert("Sending a Termination Request to HR.");
        }
        
      });
