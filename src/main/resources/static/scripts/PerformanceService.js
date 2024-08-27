app.factory('performanceService', function($http) {
    
    var observers = [];

    var notifyObservers = function() {
        angular.forEach(observers, function(observer, index) 
        {
            observer.notify();
        });
    };

    var serviceInstance = 
    { 
        evaluation: {}, 
        evaluations: [], 
        error: "" ,
        getEvaluation: function(evaluationId) {
            
            var promise = $http({
                method: 'GET',
                url: '/api/evaluation/' + evaluationId
            })
            .then(
                function successCallback(response) {              
                    serviceInstance.evaluation = response.data;
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
        getEvaluations: function() {
             // accepts a guide ID (integer) and returns a promise to deliver a message mapping guide object.

             var promise = $http({
                method: 'GET',
                url: '/api/evaluation'
            })
            .then(
                function successCallback(response) {              
                    serviceInstance.evaluations = response.data;
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