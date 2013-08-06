package srsurvey

class LoggingController {
    def personService
    def loggingService

    private static File output = new File("./phrasepairs-log.txt")

    def index() {
    }

    def append() {
        Person p = personService.getForSession(session)
        loggingService.append(p, request, params.message)
        render('okay')
    }
}
