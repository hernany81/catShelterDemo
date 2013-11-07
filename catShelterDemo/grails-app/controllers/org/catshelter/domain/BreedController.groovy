package org.catshelter.domain

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import static javax.servlet.http.HttpServletResponse.*

class BreedController {

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() { }

    def list() {
		params.sort = params.sort ?: 'name';
		params.order = params.order ?: 'asc';
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.max = Integer.MAX_VALUE;
		response.setIntHeader('X-Pagination-Total', Breed.count())
		render Breed.list(params) as JSON
    }

    def save() {
        def breedInstance = new Breed(request.JSON)
        def responseJson = [:]
        if (breedInstance.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = breedInstance.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'breed.label', default: 'Breed'), breedInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = breedInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def get() {
        def breedInstance = Breed.get(params.id)
        if (breedInstance) {
			render breedInstance as JSON
        } else {
			notFound params.id
		}
    }

    def update() {
        def breedInstance = Breed.get(params.id)
        if (!breedInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (breedInstance.version > request.JSON.version) {
				response.status = SC_CONFLICT
				responseJson.message = message(code: 'default.optimistic.locking.failure',
						args: [message(code: 'breed.label', default: 'Breed')],
						default: 'Another user has updated this Breed while you were editing')
				render responseJson as JSON
				return
            }
        }

        breedInstance.properties = request.JSON

        if (breedInstance.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = breedInstance.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'breed.label', default: 'Breed'), breedInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = breedInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def breedInstance = Breed.get(params.id)
        if (!breedInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            breedInstance.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'breed.label', default: 'Breed'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'breed.label', default: 'Breed'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'breed.label', default: 'Breed'), params.id])]
        render responseJson as JSON
    }
}
