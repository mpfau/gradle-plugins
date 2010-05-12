import org.apache.shiro.crypto.hash.Sha1Hash

import plugin.Plugin;
import user.ShiroUser;

class BootStrap {

     def init = { servletContext ->
		def admin = new ShiroUser(username: "admin", mail: 'admin@gradle.org', passwordHash: new Sha1Hash("admin").toHex())
		admin.addToPermissions("*:*")
		admin.save()
		def user = new ShiroUser(username: "map", mail: 'map@test.de', passwordHash: new Sha1Hash("map").toHex())
		user.addToPermissions("*:*")
		user.save()
		
		def user2 = new ShiroUser(username: "ben", mail: 'ben@test.de', passwordHash: new Sha1Hash("ben").toHex())
		user2.addToPermissions("plugin:index,list,create,save")
		user2.save()
		
		def plugin = new Plugin(name: 'pluginDev',description:'This is the plugin for supporting plugin development.', owners: [user])
		plugin.save()
		
     }
     def destroy = {
     }
} 