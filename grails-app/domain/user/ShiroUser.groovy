package user

class ShiroUser {
    String username
    String passwordHash
	String mail
	String password, captcha
    
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique:true)
		mail(nullable:false, blank: false, email:true, unique:true)
		passwordHash(nullable:false, blank: false)
//		password(nullable:false, blank:false, minSize: 3)
    }
	
	static transients = ['password', 'captcha']
}
