app.controller('HrLandingController', function ($scope, $route, $routeParams, $location) {
    console.log("HR Controller initialized");
        
        $scope.employees = [
          { 
            FirstName: 'John', 
            LastName: 'Smith',
            Department: 'Support', 
            Rank: 4,
            VCHours: 80
          },
          { 
            FirstName: 'Billy', 
            LastName: 'Thor', 
            Rank: 5, 
            Department: 'IT',
            VCHours: 80
          },
          { 
            FirstName: 'Glen', 
            LastName: 'Stevens', 
            Rank: 9, 
            Department: 'IT',
            VCHours: 80
          },
          { 
            FirstName: 'Sally', 
            LastName: 'Booker', 
            Rank: 6, 
            Department: 'Human Resources',
            VCHours: 80
          },
          { 
            FirstName: 'Joe', 
            LastName: 'Marks', 
            Rank: 8, 
            Department: 'Legal',
            VCHours: 80
          }
        ];
        
      });
