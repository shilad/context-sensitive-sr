import srsurvey.Person

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

    }
    def destroy = {
    }
}
