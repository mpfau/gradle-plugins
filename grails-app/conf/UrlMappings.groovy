class UrlMappings {
    static mappings = {
  	  "/upload/$pluginName/$version"(controller:"release", action: 'add'){

	  }
		
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/"(controller:"home")
	  "500"(view:'/error')
	}
}
