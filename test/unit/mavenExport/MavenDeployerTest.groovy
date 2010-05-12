package mavenExport
;

import junit.framework.TestCase;

import org.apache.tools.ant.BuildException;
import org.custommonkey.xmlunit.DetailedDiff 
import org.custommonkey.xmlunit.Diff 
import org.custommonkey.xmlunit.XMLUnit;




class MavenDeployerTest extends TestCase {
	def repo = new File('target/mavenTestRepo')
	
	void setUp() {
		def ant = new AntBuilder()
		ant.delete dir: repo
		repo.mkdirs()
	}
	
	void testPublishArtifact() {
		def deployer = new MavenDeployer(repository: repo.toURI().toString(), user: '', password: '')
		def pom = new File('resources/test/tapestry-upload-5.0.19.pom')
		def file = new File('resources/test/tapestry-upload-5.0.19.jar')
		deployer.publish pom, file
		
		assertTrue new File('target/mavenTestRepo/org/apache/tapestry/tapestry-upload/5.0.19/tapestry-upload-5.0.19.jar').exists()

		def uploadedPom = new File('target/mavenTestRepo/org/apache/tapestry/tapestry-upload/5.0.19/tapestry-upload-5.0.19.pom')
		compareXml 'Did not upload correct POM', pom.text, uploadedPom.text
		
	}
	
	static void compareXml(errorMessage, expectedXml, testXml) {
		XMLUnit.setIgnoreWhitespace true
		DetailedDiff myDiff = new DetailedDiff(new Diff(expectedXml, testXml))
		myDiff.allDifferences.each { 
			println "DIFF> $it" 
		}
		
		assertTrue(errorMessage, myDiff.identical())
	}
	
	void testPublishToNonExistingRepository() {
		def deployer = new MavenDeployer(repository: 'http://www.a.de', user: '', password: '')
		def pom = new File('resources/test/tapestry-upload-5.0.19.pom')
		def file = new File('resources/test/tapestry-upload-5.0.19.jar')
		try {
			deployer.publish pom, file
			fail('Upload to non existing repository should fail!')
		} catch(BuildException e) {
		}
		
	}

}
