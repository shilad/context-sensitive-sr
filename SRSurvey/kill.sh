ps axww | grep grails | grep 11090 | grep -v grep | awk '{ print $1 }' | xargs kill
