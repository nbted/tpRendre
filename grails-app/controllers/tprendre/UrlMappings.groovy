package tprendre

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/bibliotheque/$idBib/livre/$idLiv" {
            controller = 'Api'
            action = 'biblio_livre'
        }

        "/api/bibliotheque/$idBib/livre/" {
            controller = 'Api'
            action = 'biblio_livre'
        }
        "/api/bibliotheque/$idbib/livres/" {
            controller = 'Api'
            action = 'biblio_livres'
        }
        "/api/logout"{
            controller = 'logout'
            view = 'logout'
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
