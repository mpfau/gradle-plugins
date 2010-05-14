package gradle.plugins

import org.apache.shiro.crypto.hash.Sha1Hash;

import user.ShiroUser;




class ShiroUserController {

    static allowedMethods = [update: "POST", updatePermissions: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [shiroUserInstanceList: ShiroUser.list(params), shiroUserInstanceTotal: ShiroUser.count()]
    }

    def show = {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            [shiroUserInstance: shiroUserInstance]
        }
    }

    def edit = {
    	return edit()
    }

    def edit() {
		def shiroUserInstance = ShiroUser.get(params.id)
		if (!shiroUserInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [shiroUserInstance: shiroUserInstance]
		}
    }

    def editPermissions = {
        return edit()
    }

    def update = {
        def shiroUserInstance = ShiroUser.get(params.id)
        shiroUserInstance.passwordHash = new Sha1Hash(params.password).toHex()
        if (shiroUserInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (shiroUserInstance.version > version) {
                    
                    shiroUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'shiroUser.label', default: 'ShiroUser')] as Object[], "Another user has updated this ShiroUser while you were editing")
                    render(view: "edit", model: [shiroUserInstance: shiroUserInstance])
                    return
                }
            }
            shiroUserInstance.properties = params
            if (!shiroUserInstance.hasErrors() && shiroUserInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), shiroUserInstance.id])}"
                redirect(action: "show", id: shiroUserInstance.id)
            }
            else {
                render(view: "edit", model: [shiroUserInstance: shiroUserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])}"
            redirect(action: "list")
        }
    }

    def updatePermissions = {
		def shiroUserInstance = ShiroUser.get(params.id)
		shiroUserInstance.permissions = params.permissions.tokenize('\n')
		if (shiroUserInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (shiroUserInstance.version > version) {
					
					shiroUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'shiroUser.label', default: 'ShiroUser')] as Object[], "Another user has updated this ShiroUser while you were editing")
					render(view: "edit", model: [shiroUserInstance: shiroUserInstance])
					return
				}
			}
			if (!shiroUserInstance.hasErrors() && shiroUserInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), shiroUserInstance.id])}"
				redirect(action: "show", id: shiroUserInstance.id)
			}
			else {
				render(view: "edit", model: [shiroUserInstance: shiroUserInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (shiroUserInstance) {
            try {
                shiroUserInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])}"
            redirect(action: "list")
        }
    }
}
