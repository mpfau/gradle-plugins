package mavenExport
;

import groovy.util.AntBuilder;

import java.io.File;


class MavenDeployer {
	def ant = new AntBuilder()
	
	String repository
	String user
	String password
	
	/**
	 * publishes any artifact to a maven repository 
	 * @param pom the pom used for publishing
	 * @param file the artifact itself
	 */
	private publish(File pom, File artifact) {
		ant.typedef(resource:"org/apache/maven/artifact/ant/antlib.xml")
		ant.pom(id:"mypom", file: pom)	 
		ant.deploy(file: artifact) {
			ant.remoteRepository(url: repository) {
				ant.authentication(username: user, password: password)
			}
			ant.pom(refid:'mypom')
		}
	}
}
