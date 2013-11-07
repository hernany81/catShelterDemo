package org.catshelter.web

import org.codehaus.groovy.grails.web.json.JSONObject

import sun.security.util.PathList;

/**
 * Utility method to traverse a Json objects
 * 
 * @author Hernan
 */
class JSONHelper {

	/**
	 * Navigates through the JSON object graph following the given path to reach a determined property
	 * @param json
	 * @param path
	 * @return Property value
	 */
	def static Object getPropertyFromPath(JSONObject json, String path) {
		List<String> propsList = new ArrayList<String>(Arrays.asList(path.split("\\.")));

		retrieveProperty(json, propsList);		
	}
	
	/**
	 * For concurrent operation
	 * @return
	 */
	def private static Object retrieveProperty(JSONObject json, List<String> pathList) {
		if(null != json && pathList.size() > 0) {
			String propName = pathList.remove(0);
			
			if(!json.containsKey(propName)) {
				return null;
			}
			
			Object propValue = json.get(propName);
			
			if(pathList.empty) {
				// This is the final property we are looking for
				return propValue;
			}
			
			// The property we are looking for is deeper down
			if(null != propValue && propValue instanceof JSONObject) {
				return retrieveProperty(propValue, pathList);
			}
		}
		
		return null;
	}
}
