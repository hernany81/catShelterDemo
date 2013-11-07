Cat Shelter Demo
================

Cat Shelter Demo application (Grails + AngularJS + Bootstrap)

Technologies Used
=================
- Grails 2.2.4
- kickstartWithBootstrap - 0.9.6 (Grails plugin for Twitter Bootstrap integration)
- angularScaffolding - 1.0-SNAPSHOT (Grails plugin to support scaffolding with AngularJS)
- MySQL 5.6
- JDK 1.7 64-bits

Running the application
=======================
For development and testing environments HSQL DB is used. For production environment MySQL is used.

To run production environment: grails prod run-app

In MySQL you need to create a local empty database named cat_shelter, the application is configured to use demo/demo as user/password.

Application Highlights
======================
- It uses GORM optimistic locking to avoid last write win issues
- Modified scaffolding.js to adjust CRUD behavior in AngularJS
- Integration of jquery plugins with AngularJS, the directives definitions can be found in web-app/js/custom-widgets.js
	- Selectize is used as a combobox with look ahead features for breed and coat properties.
	- DatePicker to capture cats arrival dates.
- The whole application style is managed with LESS (check ApplicationResources.groovy) so you can change overall look & feel just changing variables.less