app.factory('employeeService', function($http) {
    
    var observers = [];

    var notifyObservers = function() {
        angular.forEach(observers, function(observer, index) 
        {
            observer.notify();
        });
    };

    var serviceInstance = 
    { 
        employee: {}, 
        currentEmployee: {}, 
        error: "" ,
        submitTimeOffRequest: function(newrequest) {
            var promise = $http({
                method: 'POST',
                url: '/api/employee/timeoff',
                data: newrequest
            })
            .then(
                function successCallback(response) {              
                    // TODO: Do something here?                    
                }, 
                function errorCallback(response) {
                    
                }
            );
 
            return promise;
        },
        getEmployee: function(employeeId) {
            
            // accepts an employee ID (integer) and returns a promise to deliver an employee object

            var promise = $http({
                method: 'GET',
                url: '/api/employee/' + employeeId
            })
            .then(
                function successCallback(response) {              
                    serviceInstance.employee = response.data;
                    notifyObservers();
                }, 
                function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    error = response.error;
                }
            );

            return promise;
        },
        getCurrentEmployee: function() {
            
            // accepts an employee ID (integer) and returns a promise to deliver an employee object

            var promise = $http({
                method: 'GET',
                url: '/api/employee/current'
            })
            .then(
                function successCallback(response) {              
                    serviceInstance.currentEmployee = response.data;
                    notifyObservers();
                }, 
                function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    error = response.error;
                }
            );

            return promise;
        },
        getEmployees: function() {
             // accepts a guide ID (integer) and returns a promise to deliver a message mapping guide object.

             var promise = $http({
                method: 'GET',
                url: '/api/employee'
            })
            .then(
                function successCallback(response) {              
                    serviceInstance.employees = response.data;
                    notifyObservers();
                }, 
                function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    error = response.error;
                }
            );

            return promise;
        },
        updateEmployee: function(employee) {

            var promise = $http({
               method: 'PUT',
               url: '/api/employee',
               data: employee
           })
           .then(
               function successCallback(response) {              
                   // TODO: Do something here?
                   notifyObservers();
               }, 
               function errorCallback(response) {
                   
               }
           );

           return promise;
       },
        registerObserver : function(observer) {
            var observersSet = new Set(observers);
            if (observersSet.has(observer)) {
                console.warn("Observer already exists");
            }
            else {
                observers.push(observer);
            }
        }
    };
    
    return serviceInstance;
});