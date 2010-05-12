package gradle.plugins



import org.apache.shiro.SecurityUtils;

import plugin.Plugin 
import user.ShiroUser;

class PluginController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    //static accessControl = {permission([action:'list', perm:'testPermission'])} 

    def index = {
        redirect(action: "list", params: params)
    }

    // Lists all plugins that the user is authorized to view
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def plugins = Plugin.list().findAll {SecurityUtils.subject.isPermitted("$controllerName:show:$it.id") }
        [pluginInstanceList: plugins, pluginInstanceTotal: Plugin.count()]
    }

    def create = {
        def pluginInstance = new Plugin()
        pluginInstance.properties = params
        return [pluginInstance: pluginInstance]
    }

    ShiroUser getUser() {
		def user = ShiroUser.findByUsername(SecurityUtils.subject.principal)
    }

    def save = {
        def pluginInstance = new Plugin(params)
        pluginInstance.addToOwners getUser()
        if (pluginInstance.save(flush: true)) {
			user.addToPermissions("plugin:show,edit,update:" + pluginInstance.id)
			user.save(flush:true)
			
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'plugin.label', default: 'Plugin'), pluginInstance.id])}"
            redirect(action: "show", id: pluginInstance.id)
        }
        else {
            render(view: "create", model: [pluginInstance: pluginInstance])
        }
    }

    def show = {
        def pluginInstance = Plugin.get(params.id)
        if (!pluginInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plugin.label', default: 'Plugin'), params.id])}"
            redirect(action: "list")
        }
        else {
            [pluginInstance: pluginInstance]
        }
    }

    def edit = {
        def pluginInstance = Plugin.get(params.id)
        if (!pluginInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plugin.label', default: 'Plugin'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [pluginInstance: pluginInstance]
        }
    }

    def update = {
        def pluginInstance = Plugin.get(params.id)
        if (pluginInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (pluginInstance.version > version) {
                    
                    pluginInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'plugin.label', default: 'Plugin')] as Object[], "Another user has updated this Plugin while you were editing")
                    render(view: "edit", model: [pluginInstance: pluginInstance])
                    return
                }
            }
            pluginInstance.properties = params
            if (!pluginInstance.hasErrors() && pluginInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'plugin.label', default: 'Plugin'), pluginInstance.id])}"
                redirect(action: "show", id: pluginInstance.id)
            }
            else {
                render(view: "edit", model: [pluginInstance: pluginInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plugin.label', default: 'Plugin'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def pluginInstance = Plugin.get(params.id)
        if (pluginInstance) {
            try {
                pluginInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'plugin.label', default: 'Plugin'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'plugin.label', default: 'Plugin'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plugin.label', default: 'Plugin'), params.id])}"
            redirect(action: "list")
        }
    }
}
