

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'shiroUser.label', default: 'ShiroUser')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'shiroUser.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'shiroUser.username.label', default: 'Username')}" />
                        
                            <g:sortableColumn property="mail" title="${message(code: 'shiroUser.mail.label', default: 'Mail')}" />
                        
                            <g:sortableColumn property="passwordHash" title="${message(code: 'shiroUser.passwordHash.label', default: 'Password Hash')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${shiroUserInstanceList}" status="i" var="shiroUserInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${shiroUserInstance.id}">${fieldValue(bean: shiroUserInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: shiroUserInstance, field: "username")}</td>
                        
                            <td>${fieldValue(bean: shiroUserInstance, field: "mail")}</td>
                        
                            <td>${fieldValue(bean: shiroUserInstance, field: "passwordHash")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${shiroUserInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
