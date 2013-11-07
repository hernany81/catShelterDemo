<!-- 
This menu is used to show function that can be triggered on the content (an object or list of objects).
-->

<%-- Only show the "Pills" navigation menu if a controller exists (but not for home) --%>
<g:if test="${	params.controller != null
			&&	params.controller != ''
			&&	params.controller != 'home'
}">
	<ul id="Menu" class="nav nav-pills">

		<g:set var="entityName" value="${message(code: params.controller+'.label', default: params.controller.substring(0,1).toUpperCase() + params.controller.substring(1).toLowerCase())}" />
		
		<li class="{{ appData.getClassForAction('list') }}">
			<a href="#list"><i class="icon-th-list"></i> <g:message code="default.list.label" args="[entityName]"/></a>
		</li>
		<li class="{{ appData.getClassForAction('create') }}">
			<a href="#create"><i class="icon-plus"></i> <g:message code="default.new.label"  args="[entityName]"/></a>
		</li>
		
	</ul>
</g:if>
