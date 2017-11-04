package tprendre

class Bibliotheque {
    String nom
    String adresse
    Integer anneeConstruction

    static hasMany = [livre: Livre]

    static constraints = {
        nom blank: false
        adresse blank: false
        anneeConstruction blank:false
    }
}
