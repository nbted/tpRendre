package tprendre

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BibliothequeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Bibliotheque.list(params), model:[bibliothequeCount: Bibliotheque.count()]
    }

    def show(Bibliotheque bibliotheque) {
        respond bibliotheque
    }

    def create() {
        respond new Bibliotheque(params)
    }

    @Transactional
    def save(Bibliotheque bibliotheque) {
        if (bibliotheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bibliotheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bibliotheque.errors, view:'create'
            return
        }

        bibliotheque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), bibliotheque.id])
                redirect bibliotheque
            }
            '*' { respond bibliotheque, [status: CREATED] }
        }
    }

    def edit(Bibliotheque bibliotheque) {
        respond bibliotheque
    }

    @Transactional
    def update(Bibliotheque bibliotheque) {
        if (bibliotheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bibliotheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bibliotheque.errors, view:'edit'
            return
        }

        bibliotheque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), bibliotheque.id])
                redirect bibliotheque
            }
            '*'{ respond bibliotheque, [status: OK] }
        }
    }

    @Transactional
    def delete(Bibliotheque bibliotheque) {

        if (bibliotheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bibliotheque.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), bibliotheque.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bibliotheque.label', default: 'Bibliotheque'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
