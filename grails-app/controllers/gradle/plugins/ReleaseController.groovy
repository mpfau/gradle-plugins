package gradle.plugins


import groovy.util.AntBuilder;
import java.io.File;
import mavenExport.MavenDeployer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.tools.ant.BuildException;

import plugin.Plugin
import plugin.Release
import plugin.ReleaseState 

class ReleaseController {
	

	// the delete, save and update actions only accept POST requests
	static allowedMethods = [add:'POST']

	def workingDir = 'tmp/'
	def ant
	ReleaseController() {
		ant =  new AntBuilder()
	}
	
	def index = { }

	def logAndRespond(def status, def msg) {
		log.info "[${SecurityUtils.subject.principal}] $msg"
		render msg
	}
	
	def add = {
		//authenticate 
		try{
			def username = request.getHeader('username')
			def password = request.getHeader('password')
			if (!username || !password) {
				throw new AuthenticationException()
			}
			def authToken = new UsernamePasswordToken(username, password)
			SecurityUtils.subject.login(authToken)
		}
		catch (AuthenticationException ex){
			logAndRespond(401, 'Unauthorized: No credentials or wrong credentials haven been supplied')
			return
		}
		
		log.info "adding release ${params.pluginName}: ${params.version}"
		
		def plugin = Plugin.findByName(params.pluginName)
		if (!plugin) {
			logAndRespond(404, "The plugin ${params.pluginName} does not exist")
			return
		}
		
		def release = plugin.releases.find { it.versionNumber == params.version}
		if (!release) {
			release = new Release(versionNumber: params.version, plugin: plugin)
		}  else if (release.state == ReleaseState.PUBLISHED) {
			logAndRespond(409, "The release ${params.version} for plugin ${params.pluginName} has already been published/released, it is not possible to override it")
			return
		}
		
		release.jar = storeJarFile(request, release)
		try {
			checkJar(release.jar)
		} catch (RuntimeException e) {
			logAndRespond 422, "The uploaded plugin is not valid: ${e.message}"
			return
		}
		
		plugin.releases << release
		if (!plugin.save(flush: true)) {
			def errorMessage = ""
			plugin.errors.each {
				errorMessage += it
			}
			logAndRespond 422, "Could not save the release: $errorMessage"
			return
		}
		
		response.status = 201
		logAndRespond(201, "Successfully uploaded the plugin ${params.pluginName} to the staging area of the plugin portal. It must be published in order to be visible for the public.")
	}
	
	/**
	 * checks the supplied jar file for validity and throws an Exception if it is not valid
	 */
	void checkJar(File jar) {
		def pom = extractPom(jar)
		if (!pom.exists()) {
			throw new RuntimeException('The plugin-Zip that has been provided does not contain a pom-file')
		}
	}
	
	File extractPom(File jar) {
		def dir = workingDir + jar.name.substring(0, jar.name.length() - 4 as int)
		ant.delete(dir: dir)
		ant.mkdir(dir: dir)
		ant.unzip(src: jar, dest: dir) {
			patternset {
				include(name: 'META-INF/pom.xml')
			}
		}
		return new File(dir, "META-INF/pom.xml")
	}
	
	def storeJarFile(def request, release) {
		ant.mkdir(dir: workingDir)
		def byte[] content = new byte[request.contentLength]
		def input = new DataInputStream(request.inputStream)
		input.readFully(content)
		def jar = new File(workingDir + release.plugin.name + '-' + release.versionNumber + '.jar')
		jar.newDataOutputStream().write content, 0, content.length
		return jar
	}
	
	
	def publish = {
		def repositoryUrl = grailsApplication.config.gradle.repository.url
		def user = grailsApplication.config.gradle.repository.user
		def password = grailsApplication.config.gradle.repository.password		
		
		def releaseInstance = Release.get( params.id )
		if(!releaseInstance) {
			flash.message = "Release not found with id ${params.id}"
			redirect(controller:plugin, action:list)
		}
		
		def deployer = new MavenDeployer(repository: repositoryUrl, user: user, password: password)
		try {
			deployer.publish(extractPom(releaseInstance.jar), releaseInstance.jar)
		} catch(BuildException e) {
			log.error "Error during publishing of release ${releaseInstance.versionNumber} of plugin ${releaseInstance.plugin.name}: " + e
			flash.message = "${message(code: 'default.published.message.error', args: [releaseInstance.versionNumber, releaseInstance.plugin.name])}"
			redirect(action: "show", id: releaseInstance.id)
			return
		}
		releaseInstance.state = ReleaseState.PUBLISHED
		releaseInstance.save(flush: true)
		flash.message = "${message(code: 'default.published.message', args: [releaseInstance.versionNumber, releaseInstance.plugin.name])}"
		redirect(action: "show", id: releaseInstance.id)
	}
	
	
	def show = {
		def releaseInstance = Release.get( params.id )
		if(!releaseInstance) {
			flash.message = "Release not found with id ${params.id}"
			redirect(controller:plugin, action:list)
		}
		else { return [ releaseInstance : releaseInstance] }
	}
	

	
}
