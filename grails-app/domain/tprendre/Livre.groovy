package tprendre

import org.apache.tomcat.jni.Library

class Livre {

    String nom
    Date dateParution
    String isbn
    String author

    static belongsTo = [bibliotheque:Bibliotheque]

    static constraints = {
        nom blank: false
        dateParution nullable : false
        isbn blank:false
        author blank: false
    }
}
