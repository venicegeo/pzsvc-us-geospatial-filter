import geoscript.geom.MultiPolygon
import groovy.json.JsonSlurper


class BootStrap {
	
	def grailsApplication


	def init = { servletContext ->
		// load the US boundary geometry
		def file = getClass().getResource("/us_boundaries.geojson")
		def json = new JsonSlurper().parseText(file.getText())
		grailsApplication.config.usBoundaries = new MultiPolygon(json.features[0].geometry.coordinates)
	}

	def destroy = {}
}
