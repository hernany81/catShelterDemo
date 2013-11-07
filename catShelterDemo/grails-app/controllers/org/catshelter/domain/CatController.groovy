package org.catshelter.domain

import org.catshelter.web.JSONHelper;
import org.codehaus.groovy.grails.web.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import static javax.servlet.http.HttpServletResponse.*

class CatController {

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() { }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		response.setIntHeader('X-Pagination-Total', Cat.count())
		
		JSON.use('deep') {
        	render Cat.list(params) as JSON
		}
    }

    def save() {
		JSONObject jsonRequest = request.JSON;
        def catInstance = new Cat(jsonRequest)
		
		def breedId = JSONHelper.getPropertyFromPath(jsonRequest, "breed.id");
		def Breed breed = breedId ? Breed.get(breedId) : null;
		catInstance.breed = breed;
		
		def coatId = JSONHelper.getPropertyFromPath(jsonRequest, "coat.id");
		def Coat coat = coatId ? Coat.get(coatId) : null;
		catInstance.coat = coat;
		
        def responseJson = [:]
        if (catInstance.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = catInstance.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'cat.label', default: 'Cat'), catInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = catInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def get() {
        def catInstance = Cat.get(params.id)
        if (catInstance) {
			JSON.use('deep') {
				render catInstance as JSON
			}
        } else {
			notFound params.id
		}
    }

    def update() {
        def catInstance = Cat.get(params.id)
        if (!catInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (catInstance.version > request.JSON.version) {
				response.status = SC_CONFLICT
				responseJson.message = message(code: 'default.optimistic.locking.failure',
						args: [message(code: 'cat.label', default: 'Cat')],
						default: 'Another user has updated this Cat while you were editing')
				cache false
				render responseJson as JSON
				return
            }
        }

		def JSONObject jsonRequest = request.JSON;
		 
        catInstance.properties = jsonRequest; 
		
		def breedId = JSONHelper.getPropertyFromPath(jsonRequest, "breed.id");
		def Breed breed = breedId ? Breed.get(breedId) : null;
		catInstance.breed = breed;
		
		def coatId = JSONHelper.getPropertyFromPath(jsonRequest, "coat.id");
		def Coat coat = coatId ? Coat.get(coatId) : null;
		catInstance.coat = coat;

        if (catInstance.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = catInstance.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'cat.label', default: 'Cat'), catInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = catInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def catInstance = Cat.get(params.id)
        if (!catInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            catInstance.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'cat.label', default: 'Cat'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'cat.label', default: 'Cat'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'cat.label', default: 'Cat'), params.id])]
        render responseJson as JSON
    }
}
