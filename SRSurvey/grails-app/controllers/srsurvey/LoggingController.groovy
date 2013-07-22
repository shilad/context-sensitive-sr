package srsurvey

import javax.servlet.http.HttpServletRequest
import java.util.regex.Pattern

class LoggingController {
    def personService

    private static File output = new File("../sr-log.txt")

    def index() {
    }

    def append() {
        Person p = personService.getForSession(session)
        String id = (p.id) ? p.id : "unknown"
        String email = (p.email) ? p.email : "unknown"
        String ip = getIpAddress()
        String tstamp = new Date().format("yyyy-MM-dd hh:mm:ss")
        String message = params.message.replace("\n", " ")

        synchronized (output) {
            output.append("$tstamp\t$ip\t$id\t$email\t$message\n")
        }
        render('okay')
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddr = request.getHeader("X-Forwarded-For")
        if (ipAddr == null) {
            ipAddr = request.getRemoteAddr()
        }
        return ipAddr
    }
}
