

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'plugin.label', default: 'Plugin')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
		<g:include view="/shared/adminActions.gsp"/>
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
                        
                            <g:sortableColumn property="description" title="${message(code: 'plugin.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'plugin.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="releases" title="${message(code: 'plugin.releases.label', default: 'Releases')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pluginInstanceList}" status="i" var="pluginInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${pluginInstance.id}">${fieldValue(bean: pluginInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: pluginInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: pluginInstance, field: "name")}</td>
                        
                            <td>
                            	<ul>
                            	<g:each in="${pluginInstance.releases}" status="j" var="release">
                            		<li><g:link controller="release" action="show" id="${release.id}">${fieldValue(bean: release, field: "versionNumber")} [${fieldValue(bean: release, field: "state")}]</g:link></li>
                            	</g:each>
                            	</ul>
                            </td>
                        
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
