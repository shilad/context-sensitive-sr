export GRAILS_HOME=/opt/grails-2.2.3
export PATH=$GRAILS_HOME/bin:$PATH:/home/shilad/usr/downloads/jruby-1.7.4/bin

trap "" HUP && grails -Djava.io.tmpdir=/home/shilad/ghome -Dserver.port=11090 -Dserver.httpPort=11090 prod run-war >&log &

