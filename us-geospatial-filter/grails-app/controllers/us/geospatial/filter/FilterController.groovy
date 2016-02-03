package us.geospatial.filter


import groovy.json.JsonOutput


class FilterController {
	def boundaryFilterService
	def grailsApplication


	def index() {
		grailsApplication.config.app.count++	
 
		def map = boundaryFilterService.serviceMethod(params, request)
		def json = new JsonOutput().toJson(map)


		response.contentType = "application/json"
		render json
	}
}
