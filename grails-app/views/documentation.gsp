<html>
    <head>
        <title>Welcome to Grails</title>
		<meta name="layout" content="main" />
    </head>
    <body>
		<div id="pageBody">
	        <h1>Gradle Plugin-Portal documentation</h1>
	        <p>TODO: write the Documentation</p>

	        <div id="controllerList" class="dialog">
				<h2>Available Controllers:</h2>
	            <ul>
	              <g:each var="c" in="${grailsApplication.controllerClasses}">
	                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
	              </g:each>
	            </ul>
	        </div>
		</div>
    </body>
</html>