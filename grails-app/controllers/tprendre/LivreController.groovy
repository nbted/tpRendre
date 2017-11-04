package tprendre

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LivreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Livre.list(params), model:[livreCount: Livre.count()]
    }

    def show(Livre livre) {
        respond livre
    }

    def create() {
        respond new Livre(params)
    }

    @Transactional
    def save(Livre livre) {
        if (livre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (livre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond livre.errors, view:'create'
            return
        }

        livre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'livre.label', default: 'Livre'), livre.id])
                redirect livre
            }
            '*' { respond livre, [status: CREATED] }
        }
    }

    def edit(Livre livre) {
        respond livre
    }

    @Transactional
    def update(Livre livre) {
        if (livre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (livre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond livre.errors, view:'edit'
            return
        }

        livre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'livre.label', default: 'Livre'), livre.id])
                redirect livre
            }
            '*'{ respond livre, [status: OK] }
        }
    }

    @Transactional
    def delete(Livre livre) {

        if (livre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        livre.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'livre.label', default: 'Livre'), livre.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'livre.label', default: 'Livre'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
