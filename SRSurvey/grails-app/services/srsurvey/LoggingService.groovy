package srsurvey

import javax.servlet.http.HttpServletRequest

class LoggingService {

    private static File output = new File("./phrasepairs-log.txt")

    def append(Person p, HttpServletRequest request, String message) {
        String id = (p.id) ? p.id : "unknown"
        String email = (p.email) ? p.email : "unknown"
        String ip = getIpAddress(request)
        String tstamp = new Date().format("yyyy-MM-dd hh:mm:ss")
        message = message.replace("\n", " ")

        synchronized (output) {
            output.append("$tstamp\t$ip\t$id\t$email\t$message\n")
        }
    }

    private String getIpAddress(HttpServletRequest request) {
        String ipAddr = request.getHeader("X-Forwarded-For")
        if (ipAddr == null) {
            ipAddr = request.getRemoteAddr()
        }
        return ipAddr
    }
}
