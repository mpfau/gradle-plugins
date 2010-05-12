package gradle.plugins



import plugin.Plugin;

class HomeController {

	def index = {
		redirect(action: "list", params: params)
	}
	
	// Lists all plugins which have been published
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def plugins = Plugin.list().findAll { it.published() }
		[pluginInstanceList: plugins, pluginInstanceTotal: Plugin.count()]
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
}
