

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'plugin.label', default: 'Plugin')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    	<g:javascript>
    		$(document).ready(function(){
				// make rows clickable
			   $("tr").click(function(){
			      window.location = $(this).attr("url");
			   });
				
			});
		</g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'plugin.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'plugin.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'plugin.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pluginInstanceList}" status="i" var="pluginInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" url="<g:createLink action='show' id='${pluginInstance.id}'/>" style="cursor:pointer;">
                        
                            <td>${fieldValue(bean: pluginInstance, field: "id")}</td>
                        
                            <td>${fieldValue(bean: pluginInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: pluginInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pluginInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
