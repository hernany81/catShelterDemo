
<%@ page import="org.catshelter.domain.Cat" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="ng-app">
        <g:set var="entityName" value="${message(code: 'cat.label', default: 'Cat')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>

        <r:require module="cat-scaffolding"/>
    </head>
    <body data-ng-app="scaffolding" data-base-url="${createLink(uri: '/cat/')}"
    	data-entity-name="${entityName}">
    	
    	<div id="breedBaseUrlDiv" data-base-url="${createLink(uri: '/breed/')}" style="display: none;"></div>
    	<div id="coatBaseUrlDiv" data-base-url="${createLink(uri: '/coat/')}" style="display: none;"></div>
    	
        <div class="content" role="main" data-ng-view>
        </div>
    </body>
</html>
