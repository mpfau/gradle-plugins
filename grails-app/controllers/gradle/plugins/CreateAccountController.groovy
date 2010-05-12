package gradle.plugins



import org.apache.shiro.crypto.hash.Sha1Hash 
import user.ShiroUser 




class CreateAccountController {
	
	static allowedMethods = [save: "POST"]
	
//	def accessControl = {}
	
	def index = { 
		redirect(action: "create", params: params) 
	}
	
	def create = {
		def shiroUserInstance = new ShiroUser()
		shiroUserInstance.properties = params
		return [shiroUserInstance: shiroUserInstance]
	}
	
	def save = {
		def shiroUserInstance = new ShiroUser(params)
		if (session['captcha'] != shiroUserInstance.captcha) {
			shiroUserInstance.validate()
			shiroUserInstance.errors.rejectValue('captcha', 'Please re-enter your approval code')   
			return render(view: "create", model: [shiroUserInstance: shiroUserInstance])
		}
		shiroUserInstance.passwordHash = new Sha1Hash(params.password).toHex()
		shiroUserInstance.addToPermissions "plugin:index,list,create,save"
		
		if (shiroUserInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'shiroUser.label', default: 'User'), shiroUserInstance.id])}"
			redirect(uri:'/')
		}
		else {
			render(view: "create", model: [shiroUserInstance: shiroUserInstance])
		}
	}
}
