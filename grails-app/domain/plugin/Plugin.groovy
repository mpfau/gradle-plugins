package plugin

import user.ShiroUser;


class Plugin {
	String name
	String description
	static hasMany = [releases : Release, owners : ShiroUser]
	
	static constraints = {
		name(matches:/\p{Alnum}+/, blank:false, minSize: 3, unique: true)
        description(maxSize:1000)
	}
	
	static mapping = {
		description type:'text'
	}
	
	boolean published() {
		return releases.findAll({ it.state == ReleaseState.PUBLISHED }).size() > 0
	}

	/**
	 * @return the currentRelease, if this.published() == true, null otherwise
	 */
	Release currentRelease() {
		return releases?.sort({it.versionNumber}).find({ it.state == ReleaseState.PUBLISHED })
	}
	
}
