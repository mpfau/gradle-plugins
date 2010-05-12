package plugin


class Release {
	static belongsTo = [plugin:Plugin]
	
	
	String versionNumber
	File jar
	ReleaseState state = ReleaseState.STAGING
	
}
