
/**
 * The main custom widgets module.
 */
var customWidgetsModule = angular.module('custom-widgets', []);

customWidgetsModule.directive('mySelectize', function($timeout){
	return {
        // Restrict it to be an attribute in this case
        restrict: 'A',
        // responsible for registering DOM listeners as well as updating the DOM
        link: function(scope, element, attrs) {
            $timeout(function() {
                element.selectize(scope.$eval(attrs.selectize));
            }, 100);
        }
    };
});

customWidgetsModule.directive('myDatepicker', function($timeout){
	return {
        // Restrict it to be an attribute in this case
        restrict: 'A',
        require: 'ngModel', // get a hold of NgModelController
        
        // responsible for registering DOM listeners as well as updating the DOM
        link: function(scope, element, attrs, ngModel) {
            $timeout(function() {
                var datePicker = $(element).datepicker(scope.$eval(attrs.datepicker));
                	
                // This fix is needed because datepicker doesn't detect 
                // changes made by script just manual changes
                datePicker.on('changeDate', function() {
            		ngModel.$setViewValue(element.val());
            		scope.$digest();
            	});
                
                $timeout(function() {
                	// This is required because of the mismatch in Date string format 
                	// representation used by Grails to represent Dates and what how 
                	// the plugin formats dates 
                	var modelVal = ngModel.$modelValue;
                	
                	if(modelVal) {
                		var dateJs = new Date(Date.parse(modelVal));
                		datePicker.datepicker('setValue', dateJs);
                		ngModel.$setViewValue(element.val());
                		scope.$digest();
                	}
                });
            });
        }
    };
});

customWidgetsModule.directive('myModal', function($timeout){
	return {
        // Restrict it to be an attribute in this case
        restrict: 'A',
        
        // Copy from bootstrap.js with little changes
        link: function(scope, element, attrs) {
            $timeout(function() {
            	element.on('click', function(e) {
            		var $this = $(this);
            		var href = $this.attr('href');
            		var $target = $($this.attr('data-target') || (href && href.replace(/.*(?=#[^\s]+$)/, ''))); //strip for ie7
            		var option = $target.data('modal') ? 'toggle' : $.extend({ remote:!/#/.test(href) && href }, $target.data(), $this.data());
            		
            		e.preventDefault();
            		
            		$target.modal(option).one('hide', function () {
            			$this.focus()
            		});
            	});
            });
        }
    };
});