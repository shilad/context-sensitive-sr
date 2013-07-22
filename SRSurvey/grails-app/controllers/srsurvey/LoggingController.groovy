package srsurvey

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
        String ip = getIp()
        String tstamp = new Date().format("yyyy-MM-dd hh:mm:ss")
        String message = params.message.replace("\n", " ")

        synchronized (output) {
            output.append("$tstamp\t$ip\t$id\t$email\t$message\n")
        }
        render('okay')
    }

    private static final Pattern IPADDRESS_PATTERN = Pattern.compile(
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\$"
    )

    /* By Grant Burton @ BURTONTECH.COM (11-30-2008): IP-Proxy-Cluster Fix */
    def isPrivate(String ip) {
        ip = ip?.trim()
        if (ip && IPADDRESS_PATTERN.matcher(ip).matches()) {
            def privateIps = [
                    ['0.0.0.0','2.255.255.255'],
                    ['10.0.0.0','10.255.255.255'],
                    ['127.0.0.0','127.255.255.255'],
                    ['169.254.0.0','169.254.255.255'],
                    ['172.16.0.0','172.31.255.255'],
                    ['192.0.2.0','192.0.2.255'],
                    ['192.168.0.0','192.168.255.255'],
                    ['255.255.255.0','255.255.255.255']
            ]

            for (List<String> r : privateIps) {
                long min = ip2long(r[0])
                long max = ip2long(r[1])
                if (ip2long(ip) >= min && ip2long(ip) <= max) return false;
            }
            return true;
        } else {
            return false;
        }
    }

    def getIp() {
        if (isPrivate(request.getHeader("HTTP_CLIENT_IP"))) {
            return request.getHeader("HTTP_CLIENT_IP");
        }
        if (request.getHeader("HTTP_X_FORWARDED_FOR")) {
            for (String ip : request.getHeader("HTTP_X_FORWARDED_FOR").split(",")) {
                if (isPrivate(ip)) {
                    return ip;
                }
            }
        }
        if (isPrivate(request.getHeader("HTTP_X_FORWARDED"))) {
            return request.getHeader("HTTP_X_FORWARDED");
        } else if (isPrivate(request.getHeader("HTTP_X_CLUSTER_CLIENT_IP"))) {
            return request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        } else if (isPrivate(request.getHeader("HTTP_FORWARDED_FOR"))) {
            return request.getHeader("HTTP_FORWARDED_FOR");
        } else if (isPrivate(request.getHeader("HTTP_FORWARDED"))) {
            return request.getHeader("HTTP_FORWARDED");
        } else {
            return request.getHeader("REMOTE_ADDR");
        }
    }

    public static Long ip2long(String dottedIP) {
        String[] addrArray = dottedIP.split("\\.");
        long num = 0;
        for (int i=0;i<addrArray.length;i++) {
            int power = 3-i;
            num += ((Integer.parseInt(addrArray[i]) % 256) * Math.pow(256,power));
        }
        return num;
    }
}
