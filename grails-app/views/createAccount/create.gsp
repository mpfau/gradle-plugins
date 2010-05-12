<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'shiroUser.label', default: 'User')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
<body>
	<div class="body">
	  <g:if test="${flash.message}">
	    <div class="message">${flash.message}</div>
	  </g:if>
		<g:hasErrors bean="${shiroUserInstance}">
		<div class="errors">
		    <g:renderErrors bean="${shiroUserInstance}" as="list" />
		</div>
		</g:hasErrors>
	  
	  <g:form action="save">
	    <input type="hidden" name="targetUri" value="${targetUri}" />
	        <div class="dialog">
	            <table>
	                <tbody>
	                    <tr class="prop">
	                        <td valign="top" class="name">
	                            <label for="username"><g:message code="shiroUser.username.label" default="Username" /></label>
	                        </td>
	                        <td valign="top" class="value ${hasErrors(bean: shiroUserInstance, field: 'username', 'errors')}">
	                            <g:textField name="username" value="${shiroUserInstance?.username}" />
	                        </td>
	                    </tr>
	                
	                    <tr class="prop">
	                        <td valign="top" class="name">
	                            <label for="mail"><g:message code="shiroUser.mail.label" default="Mail" /></label>
	                        </td>
	                        <td valign="top" class="value ${hasErrors(bean: shiroUserInstance, field: 'mail', 'errors')}">
	                            <g:textField name="mail" value="${shiroUserInstance?.mail}" />
	                        </td>
	                    </tr>
	                
	                    <tr class="prop">
	                        <td valign="top" class="name">
	                            <label for="password"><g:message code="shiroUser.password.label" default="Password" /></label>
	                        </td>
	                        <td valign="top" class="value ${hasErrors(bean: shiroUserInstance, field: 'password', 'errors')}">
	                            <g:passwordField name="password" value="${shiroUserInstance?.password}" />
	                        </td>
	                    </tr>
	                    
	                    <tr class="prop">
	                        <td valign="top" class="name">
	                            <label for="mail"><g:message code="shiroUser.captcha.label" default="Approval code" /></label>
	                        </td>
	                        <td valign="top" class="value ${hasErrors(bean: shiroUserInstance, field: 'captcha', 'errors')}">
	                        	<img src="<g:createLink controller='captcha'/>" alt="captcha" />
	                        	<br>
	                            <g:textField name="captcha" value="" />
	                        </td>
	                    </tr>
	                
	                </tbody>
	            </table>
	        </div>
	        <div class="buttons">
	            <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
	        </div>
	 </div>  
  </g:form>
</body>
</html>
