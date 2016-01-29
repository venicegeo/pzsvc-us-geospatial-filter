# pzsvc-us-geospatial-filter
Filters an array of geometries based on whether they intersect the boundaries of the United States. 


## Requirements
* Vagrant 1.8.1

## Boundary Geometry
The boundary data is sourced from: `https://raw.githubusercontent.com/johan/world.geo.json/master/countries/USA.geo.json`.


## Example Usage
This service accepts `GET` and `POST` requests taking an array and returning a JSON containing two arrays, one which are those that "passed" the filter (i.e. lies outside the US boundaries) and the other that "failed". <br>
`http://localhost:8080/filter?[{"latitude":50,"longitude":15},...]` 
<br> OR <br>
`http://localhost:8080/filter?[<geojson>,...]`


## Service Standup
1. `vagrant up`.
2. `vagrant ssh`.
3. `cd sync/us-geospatial-filter`.
4. `grails run-app`.

