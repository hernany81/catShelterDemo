modules = {

	// This is an override of angular-scaffolding to enhance behavior of controllers
		
	'angular-custom-scaffolding' {
		dependsOn 'jquery', 'angular-resource'
		resource id: 'js', url: [plugin: 'angular-scaffolding', dir: 'js', file: 'grails-default.js']
		resource id: 'js', url: 'js/scaffolding.js'
		resource id: 'css', url: [plugin: 'angular-scaffolding', dir: 'css', file: 'scaffolding.css']
	}

	'custom-widgets' {
		dependsOn 'jquery', 'angular-resource'
		
		resource url: 'js/selectize.min.js'
		resource url: 'js/bootstrap-datepicker.js'
		resource url: 'js/custom-widgets.js'
		resource url: 'less/selectize.bootstrap2.less'
		resource url: 'less/datepicker.less'
		resource url: "less/dummy.css" // empty css: see https://github.com/paulfairless/grails-lesscss-resources/issues/25
	}
	
	'cat-scaffolding' {
		dependsOn 'angular-custom-scaffolding'
		
		resource url: 'js/cat-scaffolding.js'
	}
	
	'cat-shelter' {
		dependsOn 'bootstrap_utils'
		
		resource url: 'css/main.css'
	}
}