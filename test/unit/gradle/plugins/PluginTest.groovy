package gradle.plugins;

import plugin.Plugin;
import plugin.Release;
import plugin.ReleaseState
import grails.test.GrailsUnitTestCase;
import groovy.util.GroovyTestCase;

class PluginTest extends GrailsUnitTestCase {
	
	public void testPublished() {
		mockDomain Plugin
		
		assertFalse(new Plugin().published())
		
		def p = new Plugin()
		p.addToReleases(new Release())
		assertFalse(p.published())
		
		p.releases.iterator().next().state = ReleaseState.PUBLISHED
		assertTrue(p.published())
	}
	
	public void testCurrentRelease() {
		mockDomain Plugin
		
		assertNull(new Plugin().currentRelease())
		
		def p = new Plugin()
		def r = new Release()
		p.addToReleases(r)
		assertNull(new Plugin().currentRelease())
		
		p.releases.iterator().next().state = ReleaseState.PUBLISHED
		assertSame(r, p.currentRelease())
	}
	
	public void testCurrentReleaseSortOrder() {
		mockDomain Plugin
		
		def p = new Plugin()
		def r1 = new Release(versionNumber: "1.2", state: ReleaseState.PUBLISHED)
		def r2 = new Release(versionNumber: "1.1.1", state: ReleaseState.PUBLISHED)
		p.addToReleases(r1)
		p.addToReleases(r2)

		assertSame(r2, p.currentRelease())
	}
	
	
}
