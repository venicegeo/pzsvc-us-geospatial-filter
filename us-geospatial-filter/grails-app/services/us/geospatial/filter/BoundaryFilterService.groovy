package us.geospatial.filter


import geoscript.geom.Point
import groovy.json.JsonOutput
import groovy.json.JsonSlurper


class BoundaryFilterService {

	def grailsApplication


	def checkGeometry(coordinates) {
		def usBoundaries = grailsApplication.config.usBoundaries
		def point = new Point(coordinates[0], coordinates[1])
		
	
		if (usBoundaries.contains(point)) { return true }
		else { return false }
	}

	def serviceMethod(params, request) {
		// Get the submitted json
		def json = request.JSON
		if (json.isEmpty()) {
			def text = params.find { true }.key 
			json = new JsonSlurper().parseText(text)
		}

		// Iterate through the JSON applying the boundary filter
		def failed = []
		def passed = []
		def jsonClass = json.getClass().toString()
		if (jsonClass.contains("Object") || jsonClass.contains("Map")) {
			// Support GeoJSON
			if (json["features"]) {
				json.features.each() {
					def coordinates = it.geometry ? it.geometry.coordinates : null
					if (coordinates) {
						if (checkGeometry(coordinates)) { failed.push(it) }
						else { passed.push(it) } 
					}
					else { passed.push(it) }
				}
				
				json.features = []
				def filterMap =  [
					failed: new LinkedHashMap(json),
					passed: new LinkedHashMap(json) 
				]
				filterMap.passed.features = passed
				filterMap.failed.features = failed


				return filterMap
			}
			// Support regular JSON
			else { 
				def coordinates = null
				if (json.latitude && json.longitude) { coordinates = [json.longitude, json.latitude] }
                                else if (json.lat && json.lon) { coordinates = [json.lon, json.lat]; println coordinates }
				if (coordinates) {
					if (checkGeometry(coordinates)) { failed.push(json) }
					else { passed.push(json) }
				}
				else { passed.push(it) }


				return [passed: passed, failed: failed]
			}
		}
		else if (jsonClass.contains("Array")) { 
			json.each() {
				def coordinates = null
				if (it.latitude && it.longitude) { coordinates = [it.longitude, it.latitude] }
				else if (it.lat && it.lon) { coordinates = [it.lon, it.lat] }
				if (coordinates) {
					if (checkGeometry(coordinates)) { failed.push(it) }
					else { passed.push(it) }
				}
				else { passed.push(it) }
			}

		
			return [failed: failed, passed: passed]
		}
	}
}
