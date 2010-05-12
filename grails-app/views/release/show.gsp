<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="plugin.Release" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'release.label', default: 'Release')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="release.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: releaseInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="release.jar.label" default="Jar" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: releaseInstance, field: "jar")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="release.state.label" default="State" /></td>
                            
                            <td valign="top" class="value">${releaseInstance?.state?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="release.versionNumber.label" default="Version Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: releaseInstance, field: "versionNumber")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="release.plugin.label" default="Plugin" /></td>
                            
                            <td valign="top" class="value"><g:link controller="plugin" action="show" id="${releaseInstance?.plugin?.id}">${releaseInstance?.plugin?.name.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${releaseInstance?.id}" />
                    <g:if test="${SecurityUtils.subject.isPermitted('release:publish' + releaseInstance?.id)}">
                    	<span class="button"><g:actionSubmit class="edit" action="publish" value="${message(code: 'default.button.publish.label', default: 'Publish')}" /></span>
                    </g:if>
                </g:form>
            </div>
        </div>
    </body>
</html>
