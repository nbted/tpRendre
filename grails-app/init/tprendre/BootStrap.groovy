package tprendre

import tpRendre.UserRole
import tpRendre.Role
import tpRendre.User


class BootStrap {
    def   springSecurityService

    def init = { servletContext ->

        def roleUtilisateur = new Role(authority: "ROLE_USER").save(flush :true , failOnError : true)

        def roleAdministrateur = new Role(authority: "ROLE_ADMIN").save(flush : true , failOnError : true)

        def utilisateur = new User(username: "teddy", password: ("teddy")).save(flush : true , failOnError :true)

        def adminstrateur = new User(username: "kwizera", password: ("kwizera")).save(flush : true , failOnError : true)


        UserRole.create(utilisateur , roleUtilisateur , true)

        UserRole.create(adminstrateur , roleAdministrateur , true)


        def bliblio1 = new Bibliotheque(nom:"bibliotheque Central" , adresse: "25 rue Latouche" ,anneeConstruction :1990)
                .addToLivre(new Livre(nom: "La tresse" , dateParution: 1930,isbn:"djk" ,author:"Laetitia Colombani"))
                 .addToLivre(new Livre(nom: "Trois baisers" , dateParution: 2007,isbn:"djk" ,author:"Katherine Pancol"))
                .addToLivre(new Livre(nom: "Frappe-toi le coeur" , dateParution: 2004,isbn:"djk" ,author:"Amélie Nothomb"))
                .addToLivre(new Livre(nom: "Origine" , dateParution: 1998,isbn:"djk" ,author:"Dan Brown"))


        def bliblio2 = new Bibliotheque(nom:"Bibliothèque universitaire Droit " , adresse: " 06000 Nice" ,anneeConstruction :1990)
                .addToLivre(new Livre(nom: "vaio" , dateParution: 1930,isbn:"djk" ,author:"Luc"))
                .addToLivre(new Livre(nom: "Trois baisers" , dateParution: 2007,isbn:"djk" ,author:"Katherine Pancol"))
                .addToLivre(new Livre(nom: "Frappe-toi le coeur" , dateParution: 2004,isbn:"djk" ,author:"Amélie Nothomb"))

        def bliblio3 = new Bibliotheque(nom:"Archimed" , adresse: "151 Route de Saint-Antoine, 06200 Nice" ,anneeConstruction :1980)
                .addToLivre(new Livre(nom: "bible" , dateParution: 1930,isbn:"djk" ,author:"Luc"))
                .addToLivre(new Livre(nom: "La tresse" , dateParution: 1930,isbn:"djk" ,author:"Laetitia Colombani"))
                .addToLivre(new Livre(nom: "Trois baisers" , dateParution: 2007,isbn:"djk" ,author:"Katherine Pancol"))


        bliblio1.save(flush : true , failOnError : true)
        bliblio2.save(flush : true , failOnError : true)
        bliblio3.save(flush : true , failOnError : true)




    }
    def destroy = {
    }
}
