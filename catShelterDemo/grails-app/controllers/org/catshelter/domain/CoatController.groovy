package org.catshelter.domain

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import static javax.servlet.http.HttpServletResponse.*

class CoatController {

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() { }

    def list() {
        params.sort = params.sort ?: 'label';
		params.order = params.order ?: 'asc';
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.max = Integer.MAX_VALUE;
		response.setIntHeader('X-Pagination-Total', Coat.count())
		render Coat.list(params) as JSON
    }

    def save() {
        def coatInstance = new Coat(request.JSON)
        def responseJson = [:]
        if (coatInstance.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = coatInstance.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'coat.label', default: 'Coat'), coatInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = coatInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def get() {
        def coatInstance = Coat.get(params.id)
        if (coatInstance) {
			render coatInstance as JSON
        } else {
			notFound params.id
		}
    }

    def update() {
        def coatInstance = Coat.get(params.id)
        if (!coatInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (coatInstance.version > request.JSON.version) {
				response.status = SC_CONFLICT
				responseJson.message = message(code: 'default.optimistic.locking.failure',
						args: [message(code: 'coat.label', default: 'Coat')],
						default: 'Another user has updated this Coat while you were editing')
				cache false
				render responseJson as JSON
				return
            }
        }

        coatInstance.properties = request.JSON

        if (coatInstance.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = coatInstance.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'coat.label', default: 'Coat'), coatInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = coatInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def coatInstance = Coat.get(params.id)
        if (!coatInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            coatInstance.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'coat.label', default: 'Coat'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'coat.label', default: 'Coat'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'coat.label', default: 'Coat'), params.id])]
        render responseJson as JSON
    }
}
