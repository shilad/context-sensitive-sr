import srsurvey.*

class BootStrap {

    def init = { servletContext ->
        Person ari = new Person()
        ari.setEmail("aweiland@macalester.edu")
        ari.save(flush: true)

        Person ben = new Person()
        ben.setEmail("bhillman@macalester.edu")
        ben.save(flush: true)

        Person jesse = new Person()
        jesse.setEmail("jrussel1@macalester.edu")
        jesse.save(flush: true)

        Person mat = new Person()
        mat.setEmail("mgiesel@macalester.edu")
        mat.save(flush: true)

        Person rebecca = new Person()
        rebecca.setEmail("rgold1@macalester.edu")
        rebecca.save(flush: true)

        Person sam = new Person()
        sam.setEmail("snaden@macalester.edu")
        sam.save(flush: true)

        Person ken = new Person()
        ken.setEmail("zwang@macalester.edu")
        ken.save(flush: true)

        Person yulun = new Person()
        yulun.setEmail("yli2@macalester.edu")
        yulun.save(flush: true)

        ExperimentalGroup fire = new ExperimentalGroup("Fire")
        fire.save(flush: true)

        ExperimentalGroup water = new ExperimentalGroup("Water")
        water.save(flush: true)

        ExperimentalGroup grass = new ExperimentalGroup("Grass")
        grass.save(flush: true)

        Interest blaziken = new Interest("Blaziken")
        blaziken.save(flush: true)

        Interest typhlosion = new Interest("Typhlosion")
        typhlosion.save(flush: true)

        Interest charizard = new Interest("Charizard")
        charizard.save(flush: true)

        Interest feraligatr = new Interest("Feraligatr")
        feraligatr.save(flush: true)

        Interest blastoise = new Interest("Blastoise")
        blastoise.save(flush: true)

        Interest swampert = new Interest("Swampert")
        swampert.save(flush: true)

        Interest meganium = new Interest("Meganium")
        meganium.save(flush: true)

        Interest venasaur = new Interest("Venasaur")
        venasaur.save(flush: true)

        Interest sceptile = new Interest("Sceptile")
        sceptile.save(flush: true)

        Interest psyduck = new Interest("Psyduck")
        psyduck.save(flush: true)

        Survey pokemon = new Survey(ben)
        pokemon.save(flush: true)

        Question fireFire = new Question(1, blaziken, typhlosion, pokemon)
        fireFire.save(flush: true)

        Question fireWater = new Question(2, charizard, feraligatr, pokemon)
        fireWater.save(flush: true)

        Question waterWater = new Question(3, blastoise, swampert, pokemon)
        waterWater.save(flush: true)

        Question grassGrass = new Question(4, meganium, venasaur, pokemon)
        grassGrass.save(flush: true)

        Question grassWater = new Question(5, sceptile, psyduck, pokemon)
        grassWater.save(flush: true)
    }
    def destroy = {
    }
}
