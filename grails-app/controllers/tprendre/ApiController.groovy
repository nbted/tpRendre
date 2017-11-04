package tprendre

import grails.converters.JSON
import grails.converters.XML

import java.text.SimpleDateFormat

class ApiController {
    def livres(){

        switch (request.getMethod()){

            case "GET":
                if (Livre.getAll().size()==0){
                    render (status:400 , text: "pas des livres " )
                    return
                }
                else {
                    switch (request.getHeader("Accept")){
                        case "json":
                            render Livre.getAll() as JSON
                            break;
                        case "xml":
                            render Livre.getAll() as XML
                            break;
                    }

                    return
                }
            default:
                response.status=405
                break;
        }
    }
    def livre() {
        switch (request.getMethod()){
            case "POST":

                if (!Bibliotheque.get(params.bibliotheque.id)){
                    render(status: 400 ,text:"on peut pas attaché un livre à un bibliotheque inexistant${params.bibliotheque.id}")
                    return
                }
                def livreInstance = new Livre(params)
                if (livreInstance.save(flush :true)){

                    render(status: 201 ," livre ajouté à la base")
                }

                else{
                    println livreInstance.errors
                    response.status=400
                }

                break;

            case "GET":

                def livreInstance = Livre.get(params.id)
                if (!livreInstance)
                {

                    render (status:400 , text: "ce livre n'existe************* pas " )
                    return
                }
                else {

                    withFormat
                            {

                                json {render livreInstance as JSON}
                                xml{render livreInstance as XML}
                            }
                }

                break;

            case "PUT" :
                def livreInstance = Livre.get(params.id)
                if (!livreInstance){
                    render (status:400 , text: "ce livre n'existe pas " )
                    return
                }
                else {

                    if (params.nom)
                    livreInstance.nom = params.nom
                    if (params.dateParution)
                    livreInstance.dateParution=Date.parse("YYYY-MM-dd HH:mm:ss",params.dateParution);
                    if (params.isbn)
                    livreInstance.isbn=params.isbn
                    if (params.author)
                    livreInstance.author=params.author

                    if (livreInstance.save(flush :true)){
                        render (status: 201 ,"OK, livre mis à jour")
                        return
                    }

                    else{

                        render (status: 400 ,"Requete mal formulé")
                        return
                    }
                    return
                }
                break;

            case "DELETE":

                def livreInstance = Livre.get(params.id)

                if (!livreInstance){
                    render (status:400 , text: "ce livre n'existe pas dans la base " )
                    return
                }
                else{
                    livreInstance.delete( flush: true)
                    render (status: 200 ,"OK ,livre effacé de la base")
                    return
                }

                break;

            default:
                response.status=405
                break;

        }
    }

    def bibliotheque(){
        switch (request.getMethod()){
            case "POST":
                def biblioInstance = new Bibliotheque(params)
                if (biblioInstance.save(flush :true))
                {

                    render(status:  201 , "biblio ajouter avec succes")
                    return
                }


                else{

                    render(status:  400 , "biblio n'est pas ajouté ,veuiller voir votre requête")
                    return
                }

                break;
            case "GET":
                def biblioInstance = Bibliotheque.get(params.id)

                if (!biblioInstance)
                {
                    render (status:400 , text: "cette bibliotheque n'existe pas " )
                    return
                }
                else {

                    withFormat {
                        json {render biblioInstance as JSON}
                        xml{render biblioInstance as XML}
                    }

                }

                break;
            case "PUT":
                def biblioInstance = Bibliotheque.get(params.id)
                if (!biblioInstance){
                    render (status:400 , text: "cette bibliotheque n'existe pas " )
                    return
                }
                else {
                    if (params.nom)
                    biblioInstance.nom = params.nom
                    if (params.adresse)
                    biblioInstance.adresse=  params.adresse
                    if (params.anneeConstruction)
                    biblioInstance.anneeConstruction=Integer.parseInt(params.anneeConstruction)
                    if ( biblioInstance.save(flash:true)) {
                        render (status:201 , text: "Mis à jour effectuer avec succes " )
                        return
                    }
                    else
                    {
                        render (status: 400 ,"Requete mal formulé")
                        return
                    }
                }

                break;

            case "DELETE" :
                def biblioInstance = Bibliotheque.get(params.id)

                if (!biblioInstance){
                    render (status:400 , text: "ce livre n'existe pas " )
                    return
                }
                else{
                    biblioInstance.delete( flush: true)
                    render (status: 200 ,"bien effacer")
                }

                break;

        }
    }


    def bibliotheques(){

        switch (request.getMethod()){

            case "GET":
                if (Bibliotheque.getAll().size()==0){
                    render (status:400 , text: "pas des livres " )
                    return
                }
                else {
                    switch (request.getHeader("Accept")){
                        case "json":
                            render Bibliotheque.getAll() as JSON
                            break;
                        case "xml":
                            render Bibliotheque.getAll() as XML
                            break;
                    }
                    return
                }
            default:
                response.status=405
                break;
        }
    }

    def biblio_livres(){
        switch (request.getMethod()){
            case "GET":

                def bibliInstance = Bibliotheque.get(params.idbib)
                if (!bibliInstance){
                    render (status:400 , text: "le biblio n'existe pas****** " )
                    return
                }
                else {
                    Iterator iterator = bibliInstance.getLivre().iterator()
                     List<Livre> livre = new ArrayList<>()
                      while ( iterator.hasNext()) {
                        Livre livreInstance = (Livre) iterator.next();
                        livre.add(livreInstance)

                    }
                    render livre as JSON
                    return

                }

                break;
        }
    }
    def biblio_livre(){
        switch (request.getMethod()){
            case "GET":
                def livreInstance = Livre.get(params.idLiv)
                def bibliInstance = Bibliotheque.get(params.idBib)
                if (!bibliInstance){
                    render (status:400 , text: "le biblio n'existe pas " )
                    return
                }
                if(livreInstance) {
                    if(bibliInstance.getLivre().contains(livreInstance)) {
                        render livreInstance as JSON
                    } else {
                        render (status:400 , text: "ce livre n'existe pas dans la biblioppppppppppppp " )
                        return

                    }
                } else {
                    render (status:400 , text: "ce livre n'existe************* pas " )
                    return
                }
                break;


            case "POST":
                def bibliInstance = Bibliotheque.get(params.idBib)
                if (!bibliInstance){
                    render (status:400 , text: "le biblio n'existe pas****** " )
                    return
                }
                else{
                    def livreInstance =new Livre(params)
                    bibliInstance.addToLivre(livreInstance)
                    livreInstance.save(flush : true)
                    render (status:201 , text: "ajouter " )
                    return
                }


                    break;
            case "PUT":

                def livreInstance = Livre.get(params.idLiv)
                def bibliInstance = Bibliotheque.get(params.idBib)
                if (!bibliInstance){
                    render (status:400 , text: "le biblio n'existe pas****** " )
                    return
                }
                else{
                    if (!livreInstance){
                        render (status:400 , text: "le livre n'existe pas****** " )
                        return
                    }
                    livreInstance.nom = params.nom
                    livreInstance.dateParution=Date.parse("YYYY-MM-dd HH:mm:ss",params.dateParution);
                    livreInstance.isbn=params.isbn
                    livreInstance.author=params.author

                    if (livreInstance.save(flush :true)){
                        render (status: 201 ,"OK")
                        return
                    }

                    else{

                        render (status: 400 ,"Requete mal formulé")
                        return
                    }
                    return
                }
                break;
            case "DELETE":
                def livreInstance = Livre.get(params.idLiv)
                def bibliInstance = Bibliotheque.get(params.idBib)
                if (!bibliInstance){
                    render (status:400 , text: "le biblio n'existe pas****** " )
                    return
                }
                else{
                    if (!livreInstance){
                        render (status:400 , text: "le livre n'existe pas****** " )
                        return
                    }
                    livreInstance.delete()
                    render (status: 200 ,"ok")
                }
                break;
            default:
                response.status=405
                break;
        }
    }
}
