

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'plugin.label', default: 'Plugin')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:include view="/shared/adminActions.gsp"/>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plugin.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pluginInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plugin.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pluginInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plugin.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pluginInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plugin.releases.label" default="Releases" /></td>
                            
                            <td valign="top" class="value">
                            	<ul>
                            	<g:each in="${pluginInstance.releases}" status="j" var="release">
                            		<li><g:link controller="release" action="show" id="${release.id}">${fieldValue(bean: release, field: "versionNumber")} [${fieldValue(bean: release, field: "state")}]</g:link></li>
                            	</g:each>
                            	</ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${pluginInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
