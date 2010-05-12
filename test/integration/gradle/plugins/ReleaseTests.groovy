package gradle.plugins

import grails.test.*
import org.apache.commons.io.IOUtils;
import org.apache.shiro.subject.Subject
import org.apache.shiro.util.ThreadContext
import org.apache.shiro.SecurityUtils

import plugin.Plugin;
import plugin.Release;
import plugin.ReleaseState;

/**
 * Mocks away shiro as described here: 
 * http://agileice.blogspot.com/2009/12/coding-around-shiro-security-in-grails.html
 */
class ReleaseTests extends GrailsUnitTestCase {
	def rc
	
    protected void setUp() {
        super.setUp()
		rc = new ReleaseController()
		
		mockShiro()
    }
	
	def mockShiro() {
		
		def subject = [ getPrincipal: { "iamauser" },
		isAuthenticated: { true },
		login: {}
		] as Subject
		
		
		ThreadContext.put( ThreadContext.SECURITY_MANAGER_KEY,
		[ getSubject: { subject } ] as SecurityManager )

		SecurityUtils.metaClass.static.getSubject = { subject }
	}

    protected void tearDown() {
        super.tearDown()
    }

    void testUnauthorized() {
    	rc.add()
		assertEquals 'Unauthorized: No credentials or wrong credentials haven been supplied', rc.response.contentAsString
    }
	
	void testUnavailablePlugin() {
		rc.request.addHeader('username', 'dummy')
		rc.request.addHeader('password', 'dummy')
		rc.request.addParameter('pluginName', 'abc')
		rc.add()
		assertEquals 'The plugin abc does not exist', rc.response.contentAsString
	}
	
	void testPublishedRelease() {
		def r = new Release(versionNumber: '1.1', jar: new File('.'), state: ReleaseState.PUBLISHED)
		def p = new Plugin(name:'abc', description: 'test')
		p.addToReleases(r)
		p.save(flush: true)

		rc.request.addHeader('username', 'dummy')
		rc.request.addHeader('password', 'dummy')
		rc.request.addParameter('pluginName', 'abc')
		rc.request.addParameter('version', '1.1')
		rc.add()
		assertEquals 'The release 1.1 for plugin abc has already been published/released, it is not possible to override it', rc.response.contentAsString
	}
	
	void testEmptyZip() {
		def r = new Release(versionNumber: '1.1', jar: new File('.'), state: ReleaseState.STAGING)
		def p = new Plugin(name:'abc', description: 'test')
		p.addToReleases(r)
		p.save(flush: true)
		
		rc.request.addHeader('username', 'dummy')
		rc.request.addHeader('password', 'dummy')
		rc.request.addParameter('pluginName', 'abc')
		rc.request.addParameter('version', '1.1')
		rc.request.contentType = 'application'
		rc.request.content = IOUtils.toByteArray(new File('resources/test/emptyZip.zip').newInputStream())
		rc.add()
		assertEquals 'The uploaded plugin is not valid: The plugin-Zip that has been provided does not contain a pom-file', rc.response.contentAsString
	}
	
	void testSuccessfulUpload() {
		def r = new Release(versionNumber: '1.1', jar: new File('.'), state: ReleaseState.STAGING)
		def p = new Plugin(name:'abc', description: 'test')
		p.addToReleases(r)
		p.save(flush: true)
		
		rc.request.addHeader('username', 'dummy')
		rc.request.addHeader('password', 'dummy')
		rc.request.addParameter('pluginName', 'abc')
		rc.request.addParameter('version', '1.1')
		rc.request.contentType = 'application'
		rc.request.content = IOUtils.toByteArray(new File('resources/test/zipWithPom.zip').newInputStream())
		rc.add()
		assertEquals 'Successfully uploaded the plugin abc to the staging area of the plugin portal. It must be published in order to be visible for the public.', rc.response.contentAsString
	}
}
