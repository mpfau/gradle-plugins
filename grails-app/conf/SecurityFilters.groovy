/**
 * Generated by the Shiro plugin. This filters class protects all URLs
 * via access control by convention.
 */
class SecurityFilters {
    def filters = {
        all(uri: "/**") {
            before = {
                // Ignore direct views (e.g. the default main index page).
                if (!controllerName ||
				controllerName in ['createAccount', 'captcha', 'home']) return true
				
				// the release controller handles the authentication on its own
				if (controllerName == 'release' && actionName == 'add') return true
				
                // Access control by convention.
                accessControl()
            }
        }
    }
}
