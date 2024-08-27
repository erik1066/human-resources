var app = angular.module('hr', ['ngRoute'])
.config(        
    function($routeProvider, $locationProvider)
    {
        $routeProvider
            .when('/',
            {
                templateUrl: 'main.html',
                title: 'Home'
            })
            .when('/sysadmin/search',
            {
                templateUrl: 'system-admin-search.html',
                title: 'System Administrator Search',
                controller: 'SystemAdminSearchController'
            })
            .when('/sysadmin/maintenance',
            {
                templateUrl: 'system-admin-maintenance.html',
                title: 'System Administrator Maintenance',
                controller: 'SystemAdminMaintenanceController'
            })
            .when('/employee/requests',
            {
                templateUrl: 'employee-requests.html',
                title: 'My Requests',
                controller: 'ProfileController'
            })
            .when('/employee',
            {
                templateUrl: 'self-profile.html',
                title: 'My Profile',
                controller: 'ProfileController'
            })
            .when('/employee/profile/:selectedEmployeeId',
            {
                templateUrl: 'profile.html',
                title: 'Profile',
                controller: 'ProfileController'
            })
            .when('/employee/timeoff',
            {
                templateUrl: 'timeoff.html',
                title: 'My Time Off',
                controller: 'ProfileController'
            })
            .when('/employee/timeoff/request/:requestId',
            {
                templateUrl: 'timeoff-request-detail.html',
                title: 'Time Off Request Details',
                controller: 'ProfileController'
            })
            .when('/employee/timeoff/newrequest',
            {
                templateUrl: 'timeoff-request-new.html',
                title: 'Request Time Off',
                controller: 'ProfileController'
            })
            // .when('/employee/profile',
            // {
            //     templateUrl: 'profile.html',
            //     title: 'My Profile',
            //     controller: 'ProfileController'
            // })
            .when('/employee/performance',
            {
                templateUrl: 'performance.html',
                title: 'My Performance',
                controller: 'PerformanceController'
            })
            .when('/employee/performance/review/:reviewId',
            {
                templateUrl: 'performance-review-detail.html',
                title: 'Performance Review',
                controller: 'PerformanceController'
            })
            .when('/manager',
            {
                templateUrl: 'manager-home.html',
                title: 'My Manager Profile',
                controller: 'ManagerController'
            })
            .when('/manager/EmployeeProfile/:selectedEmployeeId',
            {
                templateUrl: 'manager-EmployeeProfile.html',
                title: 'Employee Profile',
                controller: 'ManagerController'
            })
            .when('/manager/Evaluations',
            {
                templateUrl: 'Evaluations.html',
                title: 'Evaluations',
                controller: 'ManagerController'
            })
            .when('/manager/SalaryAdjustments',
            {
                templateUrl: 'SalaryAdjustments.html',
                title: 'Salary Changes',
                controller: 'ManagerController'
            })
            .when('/hr',
            {
                templateUrl: 'hr-home.html',
                title: 'Human Resources Profile',
                controller: 'HrLandingController'
            });
        })
        .controller('SystemAdminSearchController', function($scope){
            $scope.sortType     = 'firstName'; 
            $scope.sortType     = 'lastName'; 
            $scope.sortType     = 'email';  
           $scope.sortType     = 'department'; 
           $scope.sortReverse  = false;  
           $scope.searchEmployee   = '';   
            $scope.employees = [
           {'lastName' : 'P', 'firstName' : 'N', 'department' : 'Marketing', 'email' : 'N@gmail.com'},
           {'lastName' : 'K', 'firstName' : 'E', 'department' : 'HR', 'email' : 'E@gmail.com'},
           {'lastName' : 'J', 'firstName' : 'L', 'department' : 'Marketing', 'email' : 'L@gmail.com' },
           {'lastName' : 'C', 'firstName' : 'R', 'department' : 'HR', 'email' : 'R@gmail.com'},
           {'lastName' : 'B', 'firstName' : 'S', 'department' : 'HR', 'email' : 'S@gmail.com'}
           ];
           
        })
        .controller('SystemAdminMaintenanceController', function($scope, $window){
            $scope.schedule = new Date();
            $scope.message = ""
            $scope.ShowAlert = function () {
                if (typeof ($scope.message) == "undefined" || $scope.message == "") {
                    $window.alert("Please enter your boardcast massage");
                    return;
                }
                $window.alert("Boardcast message: " + $scope.message);
    }
        });