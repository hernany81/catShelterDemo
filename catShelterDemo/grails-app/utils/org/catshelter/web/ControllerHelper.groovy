package org.catshelter.web

import org.codehaus.groovy.grails.commons.GrailsControllerClass;

/**
 * @author Hernan
 */
class ControllerHelper {

	/**
	 * Helper method to filter which controllers are visible
	 * @param clazz
	 * @return
	 */
	def static boolean isVisible(GrailsControllerClass clazz) {
		return ['_DemoPage', 'breed', 'coat'].contains(clazz.logicalPropertyName);
	}
}
