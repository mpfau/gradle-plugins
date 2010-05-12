

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'plugin.label', default: 'Plugin')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:include view="/shared/adminActions.gsp"/>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${pluginInstance}">
            <div class="errors">
                <g:renderErrors bean="${pluginInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${pluginInstance?.id}" />
                <g:hiddenField name="version" value="${pluginInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="plugin.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pluginInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${pluginInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="plugin.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pluginInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${pluginInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="releases"><g:message code="plugin.releases.label" default="Releases" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pluginInstance, field: 'releases', 'errors')}">
                                    
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
