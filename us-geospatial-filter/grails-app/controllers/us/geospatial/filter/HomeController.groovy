package us.geospatial.filter


import groovy.json.JsonOutput


class HomeController {
	def boundaryFilterService


	def index() { 
		def map = boundaryFilterService.serviceMethod(params, request)
		def json = new JsonOutput().toJson(map)


		response.contentType = "application/json"
		render json
	}
}
