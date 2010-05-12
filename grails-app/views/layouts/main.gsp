<%@ page import="org.apache.shiro.SecurityUtils" %>

<html>
    <head>
        <title><g:layoutTitle default="Gradle Plugin Portal" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'grails.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'base.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'gradle.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:javascript library="jquery"/>
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
<div id="top">
    <div id="headerContainer">
        <div id="header">
            <div id="logo">
                <a href="<g:createLink />">
                    <div id="title">Gradle</div>
                    <div id="quote"><span>Plugin Portal</span></div>
                </a>
            </div>

            <div id="navbar">
                <ul>

    <li><a href="<g:createLink controller='home'/>" <g:if test="${controllerName == 'home'}">class="selected"</g:if>>HOME</a></li>

    <li><a href="<g:createLink controller='plugin'/>" <g:if test="${controllerName == 'plugin'}">class="selected"</g:if>>PUBLISH</a></li>

	<g:if test="${SecurityUtils.subject.isPermitted('shiroUser')}">
		<li><a href="<g:createLink controller="shiroUser" />" <g:if test="${controllerName == 'shiroUser'}">class="selected"</g:if>>ADMINISTRATE USERS</a></li>
	</g:if>
    
    <li><a href="/documentation.gsp" <g:if test="${request.forwardURI == '/documentation.gsp'}">class="selected"</g:if>>DOCUMENTATION</a></li>

    <shiro:isLoggedIn><li><a href="<g:createLink controller="auth" action="signOut" />" class="">LOG OUT</a></li></shiro:isLoggedIn>

                </ul>
            </div>
        </div>
    </div>
</div>
	    
<div id="middle">
    <div class="contentTop"></div>
    <div class="contentContainer">
        <div class="content">

			<g:layoutBody />
			
        </div>
    </div>
    <div class="contentBottom"></div>
</div>
<div id="bottom">
    <div id="footerContainer">

        <div id="footer">
        </div>
    </div>
</div>

        
    </body>
</html>